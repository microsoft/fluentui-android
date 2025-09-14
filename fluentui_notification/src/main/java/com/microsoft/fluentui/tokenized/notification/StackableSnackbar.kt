package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.times
import com.microsoft.fluentui.theme.token.controlTokens.SnackBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SnackBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarStyle
import com.microsoft.fluentui.tokenized.controls.BasicCard
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.roundToInt

private const val FADE_OUT_DURATION = 350
private const val STACKED_WIDTH_SCALE_FACTOR = 0.95f

//TODO: Make stack scrollable in expanded state
//TODO: Add accessibility support for the stack and individual cards
//TODO: Make dynamically sized cards based on content
//TODO: Make card owner of the hide/show animation states
/**
 * Represents a single item within the Snackbar stack.
 *
 * @param id A unique identifier for this snackbar item.
 * @param hidden A mutable state to control the visibility (hidden/shown) of the card. `true` if hidden, `false` otherwise.
 * @param isReshown A mutable state to indicate if the card is being reshown after being hidden. This helps in triggering specific animations.
 * @param content The composable content to be displayed inside the snackbar card.
 */
@Stable
data class SnackbarItemModel(
    val id: String,
    val hidden: MutableState<Boolean> = mutableStateOf(false),
    val snackbarToken: SnackBarTokens = SnackBarTokens(),
    val snackbarStyle: SnackbarStyle = SnackbarStyle.Neutral,
    val content: @Composable () -> Unit,
)

/**
 * A state object that can be hoisted to control and observe the [SnackbarStack].
 * It provides methods to add, remove, and manage the state of snackbar items.
 *
 * @param cards The initial list of [SnackbarItemModel] to populate the stack.
 * @param scope The [CoroutineScope] to launch operations like adding, removing, and animating cards.
 * @param maxCollapsedSize The maximum number of visible cards when the stack is collapsed.
 * @param maxExpandedSize The maximum number of visible cards when the stack is expanded.
 */
class SnackbarStackState(
    internal val cards: MutableList<SnackbarItemModel>,
    internal val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main),
    internal val maxCollapsedSize: Int = 3,
    internal val maxExpandedSize: Int = 20
) {
    internal val snapshotStateList: MutableList<SnackbarItemModel> =
        mutableStateListOf<SnackbarItemModel>().apply { addAll(cards) }

    internal var expanded by mutableStateOf(false)
    private set

    internal var maxCurrentSize = maxCollapsedSize

    /**
     * Adds a new snackbar card to the top of the stack.
     * If the stack exceeds the maximum size, the oldest card will be hidden.
     * @param card The [SnackbarItemModel] to add.
     */
    fun addCard(card: SnackbarItemModel) {
        snapshotStateList.add(card)
        if (sizeVisible() > maxCurrentSize) {
            popBack(remove = false)
        }
    }

    /**
     * Removes a snackbar card from the stack by its unique [id].
     * @param id The id of the card to remove.
     */
    fun removeCardById(id: String) {
        snapshotStateList.firstOrNull { it.id == id }?.let {
            snapshotStateList.remove(it)
        }
    }

    /**
     * Toggles the stack between its collapsed and expanded states.
     * It automatically handles showing or hiding cards to match the respective size limits.
     */
    private val expandMutex = Mutex()
    fun toggleExpanded() {
        scope.launch {
            expandMutex.withLock {
                val currentSize = snapshotStateList.count { !it.hidden.value }
                expanded = !expanded
                maxCurrentSize = if (expanded) maxExpandedSize else maxCollapsedSize

                if (currentSize > maxCurrentSize) {
                    val cardsToHide = mutableListOf<SnackbarItemModel>()
                    val extraCards = currentSize - maxCurrentSize
                    snapshotStateList.forEach {
                        if (cardsToHide.size >= extraCards) {
                            return@forEach
                        }
                        if (!it.hidden.value && cardsToHide.size < extraCards) {
                            cardsToHide.add(it)
                        }
                    }
                    hideAtParallel(cardsToHide = cardsToHide, remove = false)
                } else {
                    val cardsToShow = mutableListOf<SnackbarItemModel>()
                    var leftoverSlots = maxCurrentSize - currentSize
                    snapshotStateList.reversed().forEach { card ->
                        if (cardsToShow.size >= leftoverSlots) {
                            return@forEach
                        }
                        if (card.hidden.value && cardsToShow.size < leftoverSlots) {
                            cardsToShow.add(card)
                        }
                    }
                    showAtParallel(cardsToShow = cardsToShow)
                }
            }
        }
    }

    /**
     * Hides the oldest visible card from the stack (the one at the bottom).
     * @param remove If `true`, the card is permanently removed. If `false`, it's moved to a hidden list and can be reshown later.
     */
    fun popBack(remove: Boolean = true) {
        snapshotStateList.firstOrNull { !it.hidden.value }?.let {
            it.hidden.value = true
            if (remove) {
                snapshotStateList.remove(it)
            }
        }
    }

    /**
     * Hides the newest visible card from the stack (the one at the top).
     * @param remove If `true`, the card is permanently removed. If `false`, it's moved to a hidden list and can be reshown later.
     */
    fun popFront(remove: Boolean = true) {
        snapshotStateList.lastOrNull { !it.hidden.value }?.let {
            it.hidden.value = true
            if (remove) {
                snapshotStateList.remove(it)
            }
        }
    }

    fun showBack() {
        snapshotStateList.lastOrNull { it.hidden.value }?.let {
            it.hidden.value = false
        }
    }

    /**
     * Reveals all previously hidden cards.
     */
    fun showAll() {
        scope.launch {
            snapshotStateList.forEach {
                it.hidden.value = false
            }
        }
    }

    /**
     * Shows cards in parallel for smooth animation.
     */
    private fun showAtParallel(cardsToShow: List<SnackbarItemModel>) {
        cardsToShow.forEach { card ->
            card.hidden.value = false
        }
    }

    /**
     * Hides cards in parallel for smooth animation.
     */
    private suspend fun hideAtParallel(cardsToHide: List<SnackbarItemModel>, remove: Boolean) {
        cardsToHide.forEach {
            it.hidden.value = true
        }

        if (cardsToHide.isNotEmpty()) {
            delay(FADE_OUT_DURATION.toLong())
            cardsToHide.forEach { card ->
                if (remove) {
                    snapshotStateList.remove(card)
                }
            }
        }
    }

    /**
     * Returns the current number of visible cards in the stack.
     */
    fun size(): Int = snapshotStateList.size

    fun sizeVisible(): Int = snapshotStateList.count { !it.hidden.value }
}

/**
 * Creates and remembers a [SnackbarStackState] in the current composition.
 * This is the recommended way to create a state object for the [SnackbarStack].
 *
 * @param initial An optional initial list of [SnackbarItemModel]s to populate the stack.
 * @return A remembered [SnackbarStackState] instance.
 */
@Composable
fun rememberSnackbarStackState(initial: List<SnackbarItemModel> = emptyList()): SnackbarStackState {
    return remember { SnackbarStackState(initial.toMutableList()) }
}

/**
 * A composable that displays a stack of snackbar notifications.
 * It animates the cards based on the provided [SnackbarStackState].
 * The stack can be expanded or collapsed by clicking on it.
 *
 * @param state The [SnackbarStackState] that controls the content and behavior of the stack.
 * @param modifier The [Modifier] to be applied to the stack container.
 * @param cardWidth The fixed width for each card in the stack.
 * @param cardHeight The base height for each card in the stack.
 * @param peekHeight The height of the portion of the underlying cards that is visible when the stack is collapsed.
 * @param stackAbove If `true`, the stack builds upwards from the bottom. If `false`, it builds downwards from the top.
 * @param contentModifier A modifier to be applied to each individual card slot within the stack.
 */
@Composable
fun SnackbarStack(
    state: SnackbarStackState,
    modifier: Modifier = Modifier,
    cardWidth: Dp = 320.dp,
    cardHeight: Dp = 160.dp,
    peekHeight: Dp = 10.dp,
    stackAbove: Boolean = true,
    contentModifier: Modifier = Modifier
) {
    val count by remember { derivedStateOf { state.sizeVisible() } }

    val targetHeight by remember {
        derivedStateOf {
            if (count == 0) {
                0.dp
            } else if (state.expanded) {
                cardHeight * (count + 1) + (count - 1) * peekHeight
            } else {
                cardHeight + (count - 1) * peekHeight
            }
        }
    }

    val animatedStackHeight by animateDpAsState(
        targetValue = targetHeight,
        animationSpec = spring(stiffness = Spring.StiffnessMedium)
    )

    val toggleExpanded = remember { { state.toggleExpanded() } }
    val scrollState = rememberScrollState()

    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val parentMaxHeight = this.maxHeight
        val contentHeight = if (state.size() == 0) 0.dp else animatedStackHeight

        val visibleHeight =
            if (state.expanded) minOf(contentHeight, parentMaxHeight) else contentHeight

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(visibleHeight)
                .then(if (state.expanded) Modifier.verticalScroll(scrollState) else Modifier),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(contentHeight),
                contentAlignment = Alignment.BottomCenter
            ) {
                val totalVisibleCards = state.sizeVisible()
                var visibleIndex = 0
                state.snapshotStateList.forEach { snackBarModel ->
                    var logicalIndex = state.maxCurrentSize
                    if (!snackBarModel.hidden.value) {
                        logicalIndex = totalVisibleCards - 1 - visibleIndex++
                    }
                    key(snackBarModel.id) {
                        SnackbarStackItem(
                            model = snackBarModel,
                            isHidden = snackBarModel.hidden.value,
                            expanded = state.expanded,
                            index = logicalIndex,
                            cardHeight = cardHeight,
                            peekHeight = peekHeight,
                            cardWidth = cardWidth,
                            onSwipedAway = { idToRemove ->
                                state.removeCardById(idToRemove)
                                state.showBack()
                            },
                            stackAbove = stackAbove,
                            onClick = {
                                toggleExpanded()
                            }
                        )
                    }
                }
            }
        }
    }
}


/**
 * Manages the vertical offset animation of a card when the stack's state changes.
 */
@Composable
private fun CardAdjustAnimation(
    expanded: Boolean,
    index: Int,
    stackAbove: Boolean = true,
    targetYOffset: Float,
    animatedYOffset: Animatable<Float, AnimationVector1D>
) {
    LaunchedEffect(index, expanded) {
        animatedYOffset.animateTo(
            targetYOffset * (if (stackAbove) -1f else 1f),
            animationSpec = spring(stiffness = Spring.StiffnessLow)
        )
    }
}

/**
 * A private composable that represents a single, animatable card within the [SnackbarStack].
 * It handles its own animations for position, width, opacity, and swipe gestures.
 */
@Composable
private fun SnackbarStackItem(
    model: SnackbarItemModel,
    isHidden: Boolean,
    expanded: Boolean,
    index: Int,
    cardWidth: Dp,
    cardHeight: Dp,
    peekHeight: Dp,
    stackedWidthScaleFactor: Float = STACKED_WIDTH_SCALE_FACTOR,
    onSwipedAway: (String) -> Unit,
    onClick: () -> Unit = {},
    stackAbove: Boolean = false
) {
    val scope = rememberCoroutineScope()
    val localDensity = LocalDensity.current
    val isTop = index == 0
    val targetYOffset =
        with(localDensity) { if (expanded) (index * (peekHeight + cardHeight)).toPx() else (index * peekHeight).toPx() }
    val animatedYOffset = remember {
        Animatable(0f)
    }
    CardAdjustAnimation(
        expanded = expanded,
        index = index,
        stackAbove = stackAbove,
        targetYOffset = targetYOffset,
        animatedYOffset = animatedYOffset
    )

    val targetWidth = with(localDensity) {
        if (expanded) {
            cardWidth.toPx()
        } else if (isHidden) {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        } else {
            cardWidth.toPx() * stackedWidthScaleFactor.pow(index)
        }
    }
    val animatedWidth = animateFloatAsState(
        targetValue = targetWidth,
        animationSpec = tween(durationMillis = 100, easing = FastOutSlowInEasing)
    )

    val slideInProgress = remember { Animatable(1f) }
    LaunchedEffect(Unit) {
        slideInProgress.animateTo(
            0f,
            animationSpec = tween(durationMillis = 350, easing = FastOutSlowInEasing)
        )
    }

    val opacityProgress = remember { Animatable(0f) }
    LaunchedEffect(isHidden) {
        if (isHidden) {
            opacityProgress.animateTo(0f, tween(FADE_OUT_DURATION))
        } else {
            opacityProgress.animateTo(1f, tween(FADE_OUT_DURATION))
        }
    }

    val swipeX = remember { Animatable(0f) }
    val offsetX: Float = if (isTop || expanded) swipeX.value else 0f

    val token = model.snackbarToken
    val snackBarInfo = SnackBarInfo(model.snackbarStyle, false)

    Box(
        modifier = Modifier
            .offset {
                IntOffset(
                    offsetX.roundToInt() + (slideInProgress.value * with(localDensity) { 200.dp.toPx() }).roundToInt(),
                    animatedYOffset.value.roundToInt()
                )
            }
            .graphicsLayer(
                alpha = opacityProgress.value,
                scaleX = animatedWidth.value / with(localDensity) { cardWidth.toPx() },
            )
            .width(cardWidth)
            .height(cardHeight)
            .padding(horizontal = 0.dp)
            .then(
                if (isTop || expanded) Modifier.pointerInput(model.id) {
                    detectHorizontalDragGestures(
                        onDragStart = {},
                        onDragEnd = {
                            val threshold = with(localDensity) { (cardWidth / 4).toPx() }
                            scope.launch {
                                if (abs(swipeX.value) > threshold) {
                                    val target = if (swipeX.value > 0)
                                        with(localDensity) { cardWidth.toPx() * 1.2f }
                                    else
                                        -with(localDensity) { cardWidth.toPx() * 1.2f }

                                    swipeX.animateTo(
                                        target,
                                        animationSpec = tween(
                                            durationMillis = 240,
                                            easing = FastOutLinearInEasing
                                        )
                                    )
                                    onSwipedAway(model.id)
                                } else {
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
                        }
                    ) { change, dragAmountX ->
                        change.consume()
                        scope.launch {
                            swipeX.snapTo(swipeX.value + dragAmountX)
                        }
                    }
                } else Modifier
            )

    ) {
        BasicCard(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(12.dp))
                .shadow(
                    elevation = token.shadowElevationValue(snackBarInfo)
                )
                .background(token.backgroundBrush(snackBarInfo))
                .clickable(
                    onClick = {
                        onClick()
                    }
                )
        )
        {
            model.content()
        }
    }
}