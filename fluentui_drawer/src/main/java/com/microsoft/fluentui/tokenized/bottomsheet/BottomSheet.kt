/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.fluentui.tokenized.bottomsheet

import android.view.*
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.*
import com.microsoft.fluentui.compose.SwipeableDefaults
import com.microsoft.fluentui.compose.SwipeableState
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BottomSheetInfo
import com.microsoft.fluentui.theme.token.controlTokens.BottomSheetTokens
import com.microsoft.fluentui.tokenized.calculateFraction
import com.microsoft.fluentui.util.dpToPx
import com.microsoft.fluentui.util.pxToDp
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.launch
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/**
 * Possible values of [BottomSheetState].
 */

enum class BottomSheetValue {
    /**
     * The bottom sheet is not visible.
     */
    Hidden,

    /**
     * The bottom sheet is visible at full height if its height is less than 50% of screen.
     * If its height more than 50% then its visible to 50% of screen height.
     */
    Shown,

    /**
     * The bottom sheet is partially visible at 50% of the screen height. This state is only
     * enabled if the height of the bottom sheet is more than 50% of the screen height.
     */
    Expanded
}

/**
 * State of the [BottomSheet] composable.
 *
 * @param initialValue The initial value of the state which is set to [BottomSheetValue.Hidden].
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
class BottomSheetState(
    internal val initialValue: BottomSheetValue = BottomSheetValue.Hidden,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BottomSheetValue) -> Boolean = { true }
) : SwipeableState<BottomSheetValue>(
    initialValue = initialValue,
    animationSpec = animationSpec,
    confirmStateChange = confirmStateChange
) {
    /**
     * Whether the bottom sheet is visible.
     */
    val isVisible: Boolean
        get() = currentValue != BottomSheetValue.Hidden

    internal val hasExpandedState: Boolean
        get() = anchors.values.contains(BottomSheetValue.Expanded)

    /**
     * Fully expand the bottom sheet with animation and suspend until it if fully expanded or
     * animation has been cancelled.
     * *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun expand() {
        val targetValue = when {
            hasExpandedState -> BottomSheetValue.Expanded
            else -> BottomSheetValue.Shown
        }
        animateTo(targetValue = targetValue)
    }

    /**
     * Show the bottom sheet with animation and suspend until it's shown. The bottom sheet will have
     * peek height visibility.
     *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun show() = animateTo(BottomSheetValue.Shown)

    /**
     * Hide the bottom sheet with animation and suspend until it if fully hidden or animation has
     * been cancelled.
     *
     * @throws [CancellationException] if the animation is interrupted
     */
    suspend fun hide() = animateTo(BottomSheetValue.Hidden)

    companion object {
        /**
         * The default [Saver] implementation for [BottomSheetState].
         */
        fun Saver(
            animationSpec: AnimationSpec<Float>,
            confirmStateChange: (BottomSheetValue) -> Boolean
        ): Saver<BottomSheetState, *> = Saver(
            save = { it.currentValue },
            restore = {
                BottomSheetState(
                    initialValue = it,
                    animationSpec = animationSpec,
                    confirmStateChange = confirmStateChange
                )
            }
        )
    }
}

/**
 * Create a [BottomSheetState] and [remember] it.
 *
 * @param initialValue The initial value of the state.
 * @param animationSpec The default animation that will be used to animate to a new state.
 * @param confirmStateChange Optional callback invoked to confirm or veto a pending state change.
 */
@Composable
fun rememberBottomSheetState(
    initialValue: BottomSheetValue,
    animationSpec: AnimationSpec<Float> = SwipeableDefaults.AnimationSpec,
    confirmStateChange: (BottomSheetValue) -> Boolean = { true }
): BottomSheetState {
    return rememberSaveable(
        initialValue, animationSpec, confirmStateChange,
        saver = BottomSheetState.Saver(
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    ) {
        BottomSheetState(
            initialValue = initialValue,
            animationSpec = animationSpec,
            confirmStateChange = confirmStateChange
        )
    }
}

//Tag use for testing
private const val BOTTOMSHEET_HANDLE_TAG = "BottomSheet Handle"
private const val BOTTOMSHEET_CONTENT_TAG = "BottomSheet Content"
private const val BOTTOMSHEET_SCRIM_TAG = "BottomSheet Scrim"

private const val BottomSheetOpenFraction = 0.5f

internal val LocalBottomSheetTokens = compositionLocalOf { BottomSheetTokens() }
internal val LocalBottomSheetInfo = compositionLocalOf { BottomSheetInfo() }

@Composable
private fun getDrawerTokens(): BottomSheetTokens {
    return LocalBottomSheetTokens.current
}

@Composable
private fun getBottomSheetInfo(): BottomSheetInfo {
    return LocalBottomSheetInfo.current
}

/**
 *
 * Bottom sheets present a set of choices while blocking interaction with the rest of the
 * screen. They are an alternative to inline menus and simple dialogs, providing
 * additional room for content, iconography, and actions.
 *
 *
 * @param sheetContent The content of the BottomSheet.
 * @param modifier Optional [Modifier] for the entire component.
 * @param sheetState The state of the bottom sheet.
 * @param expandable if true BottomSheet would expand on drag else BottomSheet open till
 * peeked/wrapped height. The default value is true
 * @param peekHeight The visible height of the BottomSheet in [BottomSheetValue.Shown] state. The
 * peek height should be less than or equal to half screen height. If more than half of the screen
 * height provided, then peek height would be consider half of the screen height.
 * @param scrimVisible create obscures background when scrim visible set to true when the
 * BottomSheet expand. Scrim also blocks interaction with the rest of the screen
 * when visible. The default value is true
 * @param showHandle if true, the handle would be visible on top of the BottomSheet. The default
 * value is true.
 * @param slideOver if true, then sheetContent would be drawn in full length & it just get slided
 * in the visible region. If false then, the sheetContainer placed at the bottom & its height could be at peekHeight, fullheight or hidden when dragged by Handle or swipe down.
 * @param bottomSheetTokens tokens to provide appearance values. If not provided then bottomSheet
 * tokens will be picked from [AppThemeController]
 * @param content The content of rest of the screen.
 */

@Composable
fun BottomSheet(
    sheetContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    sheetState: BottomSheetState = rememberBottomSheetState(BottomSheetValue.Hidden),
    expandable: Boolean = true,
    peekHeight: Dp = 110.dp,
    scrimVisible: Boolean = true,
    showHandle: Boolean = true,
    slideOver: Boolean = true,
    bottomSheetTokens: BottomSheetTokens? = null,
    content: @Composable () -> Unit
) {
    val tokens = bottomSheetTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BottomSheet] as BottomSheetTokens

    CompositionLocalProvider(
        LocalBottomSheetTokens provides tokens,
        LocalBottomSheetInfo provides BottomSheetInfo()
    ) {

        val sheetShape: Shape = RoundedCornerShape(
            topStart = getDrawerTokens().cornerRadius(getBottomSheetInfo()),
            topEnd = getDrawerTokens().cornerRadius(getBottomSheetInfo())
        )
        val sheetElevation: Dp = getDrawerTokens().elevation(getBottomSheetInfo())
        val sheetBackgroundColor: Color = getDrawerTokens().backgroundColor(getBottomSheetInfo())
        val sheetContentColor: Color = Color.Transparent
        val sheetHandleColor: Color = getDrawerTokens().handleColor(getBottomSheetInfo())
        val scrimOpacity: Float = getDrawerTokens().scrimOpacity(getBottomSheetInfo())
        val scrimColor: Color =
            getDrawerTokens().scrimColor(getBottomSheetInfo()).copy(alpha = scrimOpacity)

        val scope = rememberCoroutineScope()

        BoxWithConstraints(modifier) {
            val fullHeight = constraints.maxHeight.toFloat()
            val sheetHeightState =
                remember(sheetContent.hashCode()) { mutableStateOf<Float?>(null) }

            Box(Modifier.fillMaxSize()) {
                content()
                if (slideOver) {
                    Scrim(
                        color = if (scrimVisible) scrimColor else Color.Transparent,
                        onDismiss = {
                            if (sheetState.confirmStateChange(BottomSheetValue.Hidden)) {
                                scope.launch { sheetState.show() }
                            }
                        },
                        fraction = {
                            if (sheetState.anchors.isEmpty()
                                || !sheetState.anchors.containsValue(BottomSheetValue.Expanded)
                            ) {
                                0.toFloat()
                            } else {
                                calculateFraction(
                                    sheetState.anchors.entries.firstOrNull { it.value == BottomSheetValue.Shown }?.key!!,
                                    sheetState.anchors.entries.firstOrNull { it.value == BottomSheetValue.Expanded }?.key!!,
                                    sheetState.offset.value
                                )
                            }
                        },
                        visible = (sheetState.targetValue == BottomSheetValue.Expanded
                                || (sheetState.targetValue == BottomSheetValue.Shown
                                && sheetState.currentValue == BottomSheetValue.Expanded)
                                )
                    )
                }
            }

            Surface(
                Modifier
                    .fillMaxWidth()
                    .nestedScroll(if (slideOver) sheetState.PreUpPostDownNestedScrollConnection else sheetState.PostDownNestedScrollConnection)
                    .offset {
                        val y = if (sheetState.anchors.isEmpty()) {
                            // if we don't know our anchors yet, render the sheet as hidden
                            fullHeight.roundToInt()
                        } else {
                            // if we do know our anchors, respect them
                            sheetState.offset.value.roundToInt()
                        }
                        IntOffset(0, y)
                    }
                    .bottomSheetSwipeable(
                        sheetState,
                        expandable,
                        peekHeight,
                        fullHeight,
                        sheetHeightState.value,
                        slideOver
                    )
                    .onGloballyPositioned {
                        if (slideOver) {
                            val originalSize = it.size.height.toFloat()
                            sheetHeightState.value = if (expandable) {
                                originalSize
                            } else {
                                min(
                                    originalSize,
                                    min(dpToPx(peekHeight), fullHeight * BottomSheetOpenFraction)
                                )
                            }
                        }
                    }
                    .sheetHeight(
                        expandable,
                        slideOver,
                        fullHeight,
                        peekHeight,
                        sheetState
                    )
                    .semantics(mergeDescendants = true) {
                        if (sheetState.isVisible) {
                            dismiss {
                                if (sheetState.confirmStateChange(BottomSheetValue.Hidden)) {
                                    scope.launch { sheetState.hide() }
                                }
                                true
                            }
                            if (sheetState.currentValue == BottomSheetValue.Shown) {
                                expand {
                                    if (sheetState.confirmStateChange(BottomSheetValue.Expanded)) {
                                        scope.launch { sheetState.expand() }
                                    }
                                    true
                                }
                            } else if (sheetState.hasExpandedState) {
                                collapse {
                                    if (sheetState.confirmStateChange(BottomSheetValue.Shown)) {
                                        scope.launch { sheetState.show() }
                                    }
                                    true
                                }
                            }
                        }
                    },
                shape = sheetShape,
                elevation = sheetElevation,
                color = sheetBackgroundColor,
                contentColor = sheetContentColor
            ) {
                Column {
                    if (showHandle) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth()
                                //TODO : Revisit SwipeableState usage across module to abstract out common modifier.
                                .draggable(
                                    orientation = Orientation.Vertical,
                                    state = rememberDraggableState { delta ->
                                        sheetState.performDrag(delta)
                                    },
                                    onDragStopped = { velocity ->
                                        launch {
                                            sheetState.performFling(velocity)
                                            if (!sheetState.isVisible) {
                                                scope.launch { sheetState.hide() }
                                            }
                                        }
                                    },
                                )
                                .testTag(BOTTOMSHEET_HANDLE_TAG)
                        ) {
                            Icon(
                                painterResource(id = com.microsoft.fluentui.drawer.R.drawable.ic_drawer_handle),
                                contentDescription =
                                if (sheetState.currentValue == BottomSheetValue.Expanded || (sheetState.hasExpandedState && sheetState.isVisible)) {
                                    LocalContext.current.resources.getString(R.string.drag_handle)
                                } else {
                                    null
                                },
                                tint = sheetHandleColor,
                                modifier = Modifier
                                    .clickable(
                                        enabled = sheetState.hasExpandedState,
                                        role = Role.Button,
                                        onClickLabel =
                                        if (sheetState.currentValue == BottomSheetValue.Expanded) {
                                            LocalContext.current.resources.getString(R.string.collapse)
                                        } else {
                                            if (sheetState.hasExpandedState && sheetState.isVisible) LocalContext.current.resources.getString(
                                                R.string.expand
                                            ) else null
                                        }
                                    ) {
                                        if (sheetState.currentValue == BottomSheetValue.Expanded) {
                                            if (sheetState.confirmStateChange(BottomSheetValue.Shown)) {
                                                scope.launch { sheetState.show() }
                                            }
                                        } else if (sheetState.hasExpandedState) {
                                            if (sheetState.confirmStateChange(BottomSheetValue.Expanded)) {
                                                scope.launch { sheetState.expand() }
                                            }
                                        }
                                    }
                            )
                        }
                    }
                    Column(modifier = Modifier
                        .testTag(BOTTOMSHEET_CONTENT_TAG)
                        .then(if (slideOver) Modifier else Modifier.fillMaxSize()),
                        content = { sheetContent() })
                }
            }
        }
    }
}

private fun Modifier.bottomSheetSwipeable(
    sheetState: BottomSheetState,
    expandable: Boolean,
    peekHeight: Dp,
    fullHeight: Float,
    sheetHeight: Float?,
    slideOver: Boolean

): Modifier {
    var peekHeightPx = min(dpToPx(peekHeight), fullHeight * BottomSheetOpenFraction)
    val modifier = if (slideOver) {
        if (sheetHeight != null) {
            val anchors = if (!expandable) {
                mapOf(
                    fullHeight to BottomSheetValue.Hidden,
                    fullHeight - min(sheetHeight, peekHeightPx) to BottomSheetValue.Shown
                )
            } else if (sheetHeight <= peekHeightPx) {
                mapOf(
                    fullHeight to BottomSheetValue.Hidden,
                    fullHeight - sheetHeight to BottomSheetValue.Shown
                )
            } else {
                mapOf(
                    fullHeight to BottomSheetValue.Hidden,
                    fullHeight - peekHeightPx to BottomSheetValue.Shown,
                    max(0f, fullHeight - sheetHeight) to BottomSheetValue.Expanded
                )
            }
            if (sheetState.initialValue == BottomSheetValue.Expanded
                && anchors.entries.firstOrNull { it.value == BottomSheetValue.Expanded } == null
            ) {
                throw IllegalArgumentException(
                    "BottomSheet initial value must not be set to Expanded " +
                            "if the whole content is visible in Shown state itself"
                )
            }
            Modifier.swipeable(
                state = sheetState,
                anchors = anchors,
                orientation = Orientation.Vertical,
                enabled = sheetState.currentValue != BottomSheetValue.Hidden,
                resistance = null
            )
        } else {
            Modifier
        }
    } else {
        peekHeightPx = dpToPx(peekHeight)
        val anchors = if (expandable) {
            mapOf(
                fullHeight to BottomSheetValue.Hidden,
                fullHeight - peekHeightPx to BottomSheetValue.Shown,
                0F to BottomSheetValue.Expanded
            )
        } else {
            mapOf(
                fullHeight to BottomSheetValue.Hidden,
                fullHeight - peekHeightPx to BottomSheetValue.Shown
            )
        }
        Modifier.swipeable(
            state = sheetState,
            anchors = anchors,
            orientation = Orientation.Vertical,
            enabled = sheetState.currentValue != BottomSheetValue.Hidden,
            resistance = null
        )
    }

    return this.then(modifier)
}

private fun Modifier.sheetHeight(
    expandable: Boolean,
    slideOver: Boolean,
    fullHeight: Float,
    peekHeight: Dp,
    sheetState: BottomSheetState
): Modifier {
    val modifier =
        if (slideOver) {
            if (expandable) {
                Modifier
            } else {
                Modifier.heightIn(
                    0.dp,
                    pxToDp(min(fullHeight * BottomSheetOpenFraction, dpToPx(peekHeight)))
                )
            }
        } else {
            Modifier.heightIn(0.dp, pxToDp(fullHeight - sheetState.offset.value))
        }
    return this.then(modifier)
}

//TODO : Revisit Scrim usage across module to check single scrim implementation across module.
@Composable
private fun Scrim(
    color: Color,
    onDismiss: () -> Unit,
    fraction: () -> Float,
    visible: Boolean
) {
    if (visible) {
        val closeSheet = LocalContext.current.resources.getString(R.string.fluentui_close_sheet)
        val dismissModifier = Modifier
            .pointerInput(onDismiss) { detectTapGestures { onDismiss() } }
            .semantics(mergeDescendants = true) {
                contentDescription = closeSheet
                onClick { onDismiss(); true }
            }

        Canvas(
            Modifier
                .fillMaxSize()
                .then(dismissModifier)
                .testTag(BOTTOMSHEET_SCRIM_TAG)

        ) {
            drawRect(color = color, alpha = fraction())
        }
    }
}