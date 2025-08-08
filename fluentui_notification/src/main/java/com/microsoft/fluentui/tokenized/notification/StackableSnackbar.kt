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
 CardStack.kt
 Production-ready Jetpack Compose component that displays a vertically-stacking deck of cards.
 Features:
  - Exposes CardStackState with addCard/removeCard functions
  - Each card is a Box with outline and elevation (Card composable)
  - New cards slide in from the right and push the stack up a bit
  - Front card is swipeable horizontally; swiping past threshold removes it with animation
  - Stack keeps the same width; height grows as you add cards (peek of cards visible)
  - Uses composable lambdas as card content so you can pass any UI inside cards

 Usage example at bottom.
*/


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.times
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.roundToInt

/** Single card model contains an id and a composable content lambda. */
data class CardModel(
    val id: String,
    val content: @Composable () -> Unit
)

/** Public state object to control the stack. */
class CardStackState(
    internal val cards: MutableList<CardModel>
) {
    internal val snapshotStateList = mutableStateListOf<CardModel>().apply { addAll(cards) }

    fun addCard(card: CardModel) {
        // add to front so index 0 is top
        snapshotStateList.add(0, card)
    }

    fun removeCardById(id: String) {
        snapshotStateList.removeAll { it.id == id }
    }

    fun popFront(): CardModel? {
        return if (snapshotStateList.isNotEmpty()) snapshotStateList.removeAt(0) else null
    }

    fun size(): Int = snapshotStateList.size
}

@Composable
fun rememberCardStackState(initial: List<CardModel> = emptyList()): CardStackState {
    return remember { CardStackState(initial.toMutableList()) }
}

/**
 * CardStack composable.
 * @param state state controlling cards
 * @param cardWidth fixed width of the stack
 * @param cardHeight base height for each card
 * @param peekHeight how much of the previous card is visible under the top card
 * @param contentModifier modifier applied to each card slot
 */
@Composable
fun CardStack(
    state: CardStackState,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 320.dp,
    cardHeight: Dp = 160.dp,
    peekHeight: Dp = 24.dp,
    contentModifier: Modifier = Modifier
) {
    // Total stack height: cardHeight + (count-1) * peekHeight
    val count by remember { derivedStateOf { state.size() } }

    val targetHeight by remember(count, cardHeight, peekHeight) {
        mutableStateOf(cardHeight + (if (count > 0) (count - 1) * peekHeight else 0.dp))
    }

    // Smoothly animate stack height when count changes
    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    Box(
        modifier = modifier
            .width(cardWidth)
            .height(animatedStackHeight)
            .wrapContentHeight(align = Alignment.Top)
    ) {
        // Show cards in reverse visual order: bottom-most drawn first
        val listSnapshot = state.snapshotStateList.toList()

        listSnapshot.reversed().forEachIndexed { visuallyReversedIndex, cardModel ->
            // compute logical index from top (0 is top)
            val logicalIndex = listSnapshot.size - 1 - visuallyReversedIndex
            val isTop = logicalIndex == 0

            key(cardModel.id) {
                // Each card will be placed offset from top by logicalIndex * peekHeight
                CardStackItem(
                    model = cardModel,
                    index = logicalIndex,
                    isTop = isTop,
                    cardHeight = cardHeight,
                    peekHeight = peekHeight,
                    cardWidth = cardWidth,
                    onSwipedAway = { idToRemove -> state.removeCardById(idToRemove) },
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
    isTop: Boolean,
    cardWidth: Dp,
    cardHeight: Dp,
    peekHeight: Dp,
    onSwipedAway: (String) -> Unit,
    contentModifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()

    // y offset for stacking
    val targetYOffset = with(LocalDensity.current) { (index * peekHeight).toPx() }
    val animatedYOffset = remember { Animatable(targetYOffset) }

    // When index changes (stack updated), animate to new y offset
    LaunchedEffect(index) {
        animatedYOffset.animateTo(
            targetYOffset,
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }

    // Slide in animation for a newly added top card
    val slideInProgress = remember { Animatable(1f) } // 1 = offscreen right, 0 = in place
    LaunchedEffect(model.id) {
        // if this is top when added, slide from right
        if (isTop) {
            slideInProgress.snapTo(1f)
            slideInProgress.animateTo(
                0f,
                animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
            )
        } else {
            slideInProgress.snapTo(0f)
        }
    }

    // horizontal drag and swipe logic (only for top)
    val swipeX = remember { Animatable(0f) }
    val removalJob = remember { mutableStateOf<Job?>(null) }

    val offsetX: Float = if (isTop) swipeX.value else 0f
    val localDensity = LocalDensity.current
    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.roundToInt() + (slideInProgress.value * with(localDensity) { 200.dp.toPx() }).roundToInt(),
                    animatedYOffset.value.roundToInt()
                )
            }
            .width(cardWidth)
            .height(cardHeight)
            .padding(horizontal = 0.dp)
            .then(if (isTop) Modifier.pointerInput(model.id) {
                detectDragGestures(
                    onDragStart = { /* no-op */ },
                    onDragEnd = {
                        // decide threshold
                        val threshold = with(localDensity) { (cardWidth / 4).toPx() }
                        scope.launch {
                            if (abs(swipeX.value) > threshold) {
                                // animate off screen in the drag direction then remove
                                val target =
                                    if (swipeX.value > 0) with(localDensity) { cardWidth.toPx() * 1.2f } else -with(
                                        localDensity
                                    ) { cardWidth.toPx() * 1.2f }
                                swipeX.animateTo(
                                    target,
                                    animationSpec = tween(
                                        durationMillis = 240,
                                        easing = FastOutLinearInEasing
                                    )
                                )
                                // remove after animation
                                onSwipedAway(model.id)
                            } else {
                                // return to center
                                swipeX.animateTo(
                                    0f,
                                    animationSpec = spring(stiffness = Spring.StiffnessMedium)
                                )
                            }
                        }
                    },
                    onDragCancel = {
                        scope.launch {
                            swipeX.animateTo(
                                0f,
                                animationSpec = spring(stiffness = Spring.StiffnessMedium)
                            )
                        }
                    },
                    onDrag = { change, dragAmount ->
                        change.consume()
                        scope.launch {
                            swipeX.snapTo(swipeX.value + dragAmount.x)
                        }
                    }
                )
            } else Modifier)
    ) {
        // Card visuals
        Box(
            modifier = Modifier
                .fillMaxSize()
                .shadow(elevation = if (isTop) 12.dp else 4.dp, shape = RoundedCornerShape(12.dp))
                .border(width = 1.dp, color = Color(0x22000000), shape = RoundedCornerShape(12.dp))
                .background(color = Color.LightGray, shape = RoundedCornerShape(12.dp))
                .then(contentModifier),
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                model.content()
            }
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
            peekHeight = 28.dp
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
            }, text = "Add card")

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                stackState.popFront()
            }, text = "Remove top card")
        }
    }
}

/* Notes & production tips:
 - This implementation stores composable lambdas in CardModel so you can pass arbitrary content.
 - If you plan to persist models across process death, store only IDs and data (not lambdas).
 - You can extend swipe gestures to support velocity and fling using androidx.compose.foundation.gestures.
 - Improve accessibility by adding semantics for swipe actions and content descriptions.
 - Tweak animation timings and easings to match your app design system.
*/


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
fun StackableSnackbar() {
    var enableDialog by remember { mutableStateOf(false) }
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
private fun SingleSnackbarTile() {
    var enableDialog by remember { mutableStateOf(false) }
    var stackableSnackbarBehavior: StackableSnackbarBehavior = remember {
        StackableSnackbarBehavior()
    }
    var animationVariables = stackableSnackbarBehavior.animationVariables
    val scope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    Button(
        onClick = {
            scope.launch {
                if (isShown) {
                    stackableSnackbarBehavior.onShowAnimation()
                } else {
                    stackableSnackbarBehavior.onDismissAnimation()
                }
            }
            isShown = !isShown
        }
    )
    if (isShown) {
        BasicCard(
            modifier = Modifier
                .padding(10.dp)
                .graphicsLayer(
                    scaleX = animationVariables.scale.value,
                    scaleY = animationVariables.scale.value,
                    alpha = animationVariables.alpha.value,
                    translationX = animationVariables.offsetX.value,
                    translationY = animationVariables.offsetY.value
                )
                .swipeToDismissFromStack(
                    animationVariables = animationVariables,
                    scope = scope,
                    onDismiss = {
                        isShown = false
                    }
                )
                .clickableWithTooltip(
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