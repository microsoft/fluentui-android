package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.util.clickableWithTooltip
import com.microsoft.fluentui.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

/*
 CardStack.kt (Scrollable, snapping & expanding cards)

 - Improved scroll behaviour: when `enableScroll=true` you can drag vertically.
 - Scrolling is paged/snapped so each card expands to show full content when focused.
 - While a card is focused (front), the previously focused card becomes part of the stack (peeked).
 - Relative order of cards remains unchanged.
 - Front card remains swipeable horizontally to remove it (with animation).
 - Adding a new card animates it in and scrolls the stack to show the new front.

 Production notes:
 - `CardModel` still accepts a composable content lambda (don't persist lambdas across process death).
 - Tweak `spring`/`tween` timings to match your design system.
 - Accessibility: add semantics and roles for swipe/expand actions if needed.
*/

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.roundToInt

/** Model for a single card. */
data class CardModel(val id: String, val content: @Composable BoxScope.() -> Unit)

/** Public state controlling the stack. */
class CardStackState(internal val cards: MutableList<CardModel>) {
    internal val snapshotStateList = mutableStateListOf<CardModel>().apply { addAll(cards) }
    fun addCard(card: CardModel) { snapshotStateList.add(0, card) }
    fun removeCardById(id: String) { snapshotStateList.removeAll { it.id == id } }
    fun popFront(): CardModel? = if (snapshotStateList.isNotEmpty()) snapshotStateList.removeAt(0) else null
    fun size(): Int = snapshotStateList.size
}

@Composable
fun rememberCardStackState(initial: List<CardModel> = emptyList()): CardStackState {
    return remember { CardStackState(initial.toMutableList()) }
}

@Composable
fun CardStack(
    state: CardStackState,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 320.dp,
    cardHeight: Dp = 160.dp,
    peekHeight: Dp = 24.dp,
    enableScroll: Boolean = false,
    contentModifier: Modifier = Modifier
) {
    val listSnapshot = state.snapshotStateList.toList()
    val count by remember { derivedStateOf { listSnapshot.size } }

    val targetHeight by remember(count) {
        mutableStateOf(cardHeight + (if (count > 0) (count - 1) * peekHeight else 0.dp))
    }

    // Animate stack height changes
    val animatedStackHeight by androidx.compose.animation.core.animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = androidx.compose.animation.core.Spring.StiffnessMedium)
    )

    val density = LocalDensity.current
    val cardHeightPx = with(density) { cardHeight.toPx() }
    val peekPx = with(density) { peekHeight.toPx() }
    val step = max(1f, cardHeightPx - peekPx)

    // scrollAnim holds current scroll offset in pixels; 0 -> first card focused
    val scrollAnim = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()

    // When a new card is added to front, animate stack to show front (0)
    var prevCount by rememberSaveable { mutableStateOf(count) }
    LaunchedEffect(count) {
        if (count > prevCount) {
            // new card likely added to front
            scrollAnim.animateTo(0f, animationSpec = tween(durationMillis = 300, easing = FastOutSlowInEasing))
        }
        prevCount = count
    }

    // Max scroll range (when you focus last card)
    val maxScroll = max(0f, (max(0, count - 1) * step))

    // Drag handling for vertical scroll (when enabled)
    val dragModifier = if (enableScroll) {
        Modifier.pointerInput(listSnapshot) {
            detectDragGestures(
                onDragStart = { /* no-op */ },
                onDrag = { change, dragAmount ->
                    change.consume()
                    scope.launch { scrollAnim.snapTo((scrollAnim.value - dragAmount.y).coerceIn(0f, maxScroll)) }
                },
                onDragEnd = {
                    // Snap to nearest index
                    scope.launch {
                        val t = (scrollAnim.value / step).coerceIn(0f, (max(0, count - 1)).toFloat())
                        val targetIndex = t.roundToInt()
                        val target = (targetIndex * step).coerceIn(0f, maxScroll)
                        scrollAnim.animateTo(target, animationSpec = spring(stiffness = Spring.StiffnessMedium))
                    }
                },
                onDragCancel = {
                    scope.launch {
                        val t = (scrollAnim.value / step).coerceIn(0f, (max(0, count - 1)).toFloat())
                        val targetIndex = t.roundToInt()
                        val target = (targetIndex * step).coerceIn(0f, maxScroll)
                        scrollAnim.animateTo(target, animationSpec = spring(stiffness = Spring.StiffnessMedium))
                    }
                }
            )
        }
    } else Modifier

    Box(
        modifier = modifier
            .width(cardWidth)
            .height(animatedStackHeight)
            .then(dragModifier)
            .wrapContentHeight(align = Alignment.Top)
    ) {
        // compute current fractional stage
        val t = (if (step > 0f) scrollAnim.value / step else 0f).coerceIn(0f, max(0f, (count - 1).toFloat()))
        val k = t.toInt().coerceIn(0, max(0, count - 1))
        val frac = t - k

        // helper to compute positions at integer stage m
        fun posAtStage(m: Int, j: Int): Float {
            return if (j <= m) {
                (m - j) * peekPx
            } else {
                j * peekPx
            }
        }

        // iterate cards and render them at computed y positions; use zIndex so top/front is drawn last
        listSnapshot.forEachIndexed { j, card ->
            key(card.id) {
                // compute y using linear interpolation between stage k and k+1
                val nextStage = (k + 1).coerceAtMost(max(0, count - 1))
                val startPos = posAtStage(k, j)
                val endPos = posAtStage(nextStage, j)
                val y = (startPos * (1f - frac) + endPos * frac)

                // compute a z-index so smaller y (closer to top) is rendered on top
                val z = -y

                val selectedIndex = (t).roundToInt().coerceIn(0, max(0, count - 1))
                val isFront = j == selectedIndex

                CardStackItem(
                    model = card,
                    index = j,
                    yPx = y,
                    zIndex = z,
                    isFront = isFront,
                    cardWidth = cardWidth,
                    cardHeight = cardHeight,
                    onSwipedAway = { removedIndex, id ->
                        // remove from state and adjust scroll position to a sensible target
                        val beforeRemove = scrollAnim.value
                        state.removeCardById(id)

                        // recalc maxScroll and clamp
                        val newCount = state.size()
                        val newMax = max(0f, (max(0, newCount - 1) * step))
                        scope.launch {
                            val approxStage = (beforeRemove / step).coerceIn(0f, max(0f, (newCount - 1).toFloat()))
                            val targetStage = approxStage.roundToInt().coerceIn(0, max(0, newCount - 1))
                            val targetPx = (targetStage * step).coerceIn(0f, newMax)
                            scrollAnim.animateTo(targetPx, animationSpec = tween(durationMillis = 240, easing = FastOutLinearInEasing))
                        }
                    },
                    contentModifier = contentModifier
                )
            }
        }
    }
}

@Composable
private fun CardStackItem(
    model: CardModel,
    index: Int,
    yPx: Float,
    zIndex: Float,
    isFront: Boolean,
    cardWidth: Dp,
    cardHeight: Dp,
    onSwipedAway: (Int, String) -> Unit,
    contentModifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // slide-in animation for newly added cards (if they become front)
    val slideAnim = remember { Animatable(1f) } // 1f = off-screen (right), 0f = in-place
    LaunchedEffect(model.id, isFront) {
        if (isFront) {
            slideAnim.snapTo(1f)
            slideAnim.animateTo(0f, animationSpec = tween(durationMillis = 320, easing = FastOutSlowInEasing))
        } else {
            slideAnim.snapTo(0f)
        }
    }

    // horizontal swipe anim
    val swipeX = remember { Animatable(0f) }
    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .zIndex(zIndex)
            .offset {
                // x offset from slide & swipe, y offset from stack calculation
                val x = ((slideAnim.value) * with(localDensity) { 200.dp.toPx() } + swipeX.value).roundToInt()
                IntOffset(x, yPx.roundToInt())
            }
            .width(cardWidth)
            .height(cardHeight)
            .then(
                if (isFront) Modifier.pointerInput(model.id) {
                    detectDragGestures(
                        onDragEnd = {
                            // threshold based on width
                            val threshold = with(localDensity) { (cardWidth / 4).toPx() }
                            scope.launch {
                                if (abs(swipeX.value) > threshold) {
                                    val target = if (swipeX.value > 0) with(localDensity) { cardWidth.toPx() * 1.2f } else -with(localDensity) { cardWidth.toPx() * 1.2f }
                                    swipeX.animateTo(target, animationSpec = tween(durationMillis = 240, easing = FastOutLinearInEasing))
                                    onSwipedAway(index, model.id)
                                } else {
                                    swipeX.animateTo(0f, animationSpec = spring(stiffness = Spring.StiffnessMedium))
                                }
                            }
                        },
                        onDragCancel = {
                            scope.launch { swipeX.animateTo(0f, animationSpec = spring(stiffness = Spring.StiffnessMedium)) }
                        },
                        onDrag = { change, dragAmount ->
                            // only horizontal motion affects swipe
                            change.consume()
                            scope.launch { swipeX.snapTo(swipeX.value + dragAmount.x) }
                        }
                    )
                } else Modifier
            )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = if (isFront) 12.dp else 4.dp, shape = RoundedCornerShape(12.dp))
                .border(width = 1.dp, color = Color(0x22000000), shape = RoundedCornerShape(12.dp))
                .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                .then(contentModifier)
        ) {
            Box(modifier = Modifier.fillMaxSize(), content = model.content)
        }
    }
}

@Composable
fun DemoCardStack() {
    val stackState = rememberCardStackState()

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
        CardStack(
            state = stackState,
            modifier = Modifier.padding(16.dp),
            cardWidth = 340.dp,
            cardHeight = 180.dp,
            peekHeight = 28.dp,
            enableScroll = true
        )

        Spacer(modifier = Modifier.height(20.dp))

        Row {
            Button(onClick = {
                val id = java.util.UUID.randomUUID().toString()
                stackState.addCard(CardModel(id = id) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        BasicText("Card: $id")
                        BasicText("Some detail here")
                    }
                })
            },
                text = "Add card")

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = { stackState.popFront() }, text = "Remove front card")
        }
    }
}

open class StackableSnackbarBehavior : AnimationBehavior() {
    override suspend fun onShowAnimation() {
        animationVariables.offsetX = Animatable(100f)
        coroutineScope {
            launch { // move the entire stack up, pass offset Y in each card
                animationVariables.offsetY = Animatable(0f)
                animationVariables.offsetY.animateTo(
                    targetValue = -100f,
                    animationSpec = tween(
                        easing = LinearEasing,
                        durationMillis = 200,
                    )
                )
            }
            launch {
                animationVariables.alpha.animateTo(
                    targetValue = 1F,
                    animationSpec = tween(
                        easing = LinearEasing,
                        durationMillis = 200,
                    )
                )
            }

//            launch {
//                animationVariables.offsetX.animateTo(
//                    targetValue = 0f,
//                    animationSpec = tween(
//                        easing = LinearEasing,
//                        durationMillis = 200,
//                    )
//                )
//            }
        }
    }

    override suspend fun onDismissAnimation() {
        animationVariables.offsetX.animateTo(
            0f,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
        animationVariables.alpha.animateTo(
            0F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }
}
@Composable
fun Modifier.swipeToDismissFromStack(
    animationVariables: AnimationVariables,
    scope: CoroutineScope,
    onDismiss: () -> Unit,
): Modifier {
    val configuration = LocalConfiguration.current
    val dismissThreshold =
        dpToPx(configuration.screenWidthDp.dp) * 0.33f  // One-third of screen width
    return this.pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                if (animationVariables.offsetX.value < -dismissThreshold) {
                    scope.launch {
                        onDismiss()
                    }
                } else {
                    scope.launch {
                        animationVariables.offsetX.animateTo(
                            0f,
                            animationSpec = tween(300)
                        )
                    }
                }
            },
            onHorizontalDrag = { _, dragAmount ->
                scope.launch {
                    animationVariables.offsetX.snapTo(animationVariables.offsetX.value + dragAmount)
                }
            }
        )
    }
}
@Composable
fun StackableSnackbar(){
    var enableDialog by remember{ mutableStateOf(false) }
    Column() {

        if (!enableDialog) {
            SingleSnackbarTile()
            SingleSnackbarTile()
            SingleSnackbarTile()
        } else {
            val popupProperties = PopupProperties(
                focusable = true
            )
            Popup(
                onDismissRequest = {
                    enableDialog = false
                }
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                }
            }
        }
    }
}


@Composable
private fun SingleSnackbarTile(){
    var enableDialog by remember{ mutableStateOf(false) }
    var stackableSnackbarBehavior: StackableSnackbarBehavior = remember {
        StackableSnackbarBehavior()
    }
    var animationVariables = stackableSnackbarBehavior.animationVariables
    val scope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    Button(
        onClick = {
            scope.launch {
                if(isShown) {
                    stackableSnackbarBehavior.onShowAnimation()
                }
                else{
                    stackableSnackbarBehavior.onDismissAnimation()
                }
            }
            isShown = !isShown
        }
    )
    if(isShown) {
        BasicCard(
            modifier = Modifier.padding(10.dp).graphicsLayer(
                scaleX = animationVariables.scale.value,
                scaleY = animationVariables.scale.value,
                alpha = animationVariables.alpha.value,
                translationX = animationVariables.offsetX.value,
                translationY = animationVariables.offsetY.value
            ).swipeToDismissFromStack(
                animationVariables = animationVariables,
                scope = scope,
                onDismiss = {
                    isShown = false
                }
            ).clickableWithTooltip(
                onClick = {
                    // enableDialog = true

                },
                tooltipText = "Snackbar clicked",
                tooltipEnabled = true
            )
        ) {
            BasicText("Click here to show Stackable Snackbar")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}