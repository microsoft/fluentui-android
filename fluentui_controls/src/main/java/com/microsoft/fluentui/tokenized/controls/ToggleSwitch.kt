package com.microsoft.fluentui.tokenized.controls

import android.view.KeyEvent.KEYCODE_ENTER
import android.view.KeyEvent.KEYCODE_SPACE
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.rememberSwipeableState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material.swipeable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.ToggleSwitchInfo
import com.microsoft.fluentui.theme.token.controlTokens.ToggleSwitchTokens
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

/**
 * API to create a Toggle Switch. This switch toggles state on tap and swipe gestures.
 * The two states of toggle are mutually exclusive.
 *
 * @param onValueChange Function to be invoked when state of switch changes.
 * @param modifier Optional modifier for Toggle Switch.
 * @param enabledSwitch Boolean to enable/disable switch. Default: [true]
 * @param checkedState Boolean representing current state of switch. Default: [false]
 * @param interactionSource Interaction Source for user interactions.
 * @param switchTokens Tokens to customize Toggle Switch's design.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ToggleSwitch(
    modifier: Modifier = Modifier,
    onValueChange: ((Boolean) -> Unit),
    enabledSwitch: Boolean = true,
    checkedState: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    switchTokens: ToggleSwitchTokens? = null
) {

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = switchTokens
        ?: FluentTheme.controlTokens.tokens[ControlType.ToggleSwitchControlType] as ToggleSwitchTokens
    val toggleSwitchInfo = ToggleSwitchInfo(checkedState)
    val backgroundColor: Color = animateColorAsState(
        targetValue =
        token.trackColor(switchInfo = toggleSwitchInfo).getColorByState(
            enabled = enabledSwitch,
            selected = checkedState,
            interactionSource = interactionSource
        ),
        animationSpec = tween(500)
    ).value
    val foregroundColor: Color =
        token.knobColor(switchInfo = toggleSwitchInfo).getColorByState(
            enabled = enabledSwitch,
            selected = checkedState,
            interactionSource = interactionSource
        )
    val elevation: Dp =
        token.elevation(switchInfo = toggleSwitchInfo)
            .getElevationByState(
                enabled = enabledSwitch,
                selected = checkedState,
                interactionSource = interactionSource
            )
    val padding: Dp = animateDpAsState(
        targetValue =
        if (interactionSource.collectIsPressedAsState().value)
            token.pressedPaddingTrack
        else
            token.restPaddingTrack
    ).value
    val knobMovementWidth: Dp = animateDpAsState(
        targetValue =
        if (interactionSource.collectIsPressedAsState().value)
            22.dp
        else
            23.dp
    ).value

    // Swipe Logic
    val minBound = with(LocalDensity.current) { padding.toPx() }
    val maxBound = with(LocalDensity.current) { knobMovementWidth.toPx() }
    val animationSpec = TweenSpec<Float>(durationMillis = 300)
    val swipeState =
        rememberSwipeableState(checkedState, animationSpec, confirmStateChange = { true })
    val isRtl = LocalLayoutDirection.current == LayoutDirection.Rtl

    val forceAnimationCheck = remember { mutableStateOf(false) }
    LaunchedEffect(checkedState, forceAnimationCheck.value) {
        if (checkedState != swipeState.currentValue) {
            swipeState.animateTo(checkedState)
        }
    }
    DisposableEffect(swipeState.currentValue) {
        if (checkedState != swipeState.currentValue) {
            onValueChange.invoke(swipeState.currentValue)
            forceAnimationCheck.value = !forceAnimationCheck.value
        }
        onDispose { }
    }

    // Toggle Logic
    val toggleModifier = Modifier.toggleable(
        value = toggleSwitchInfo.checked,
        enabled = enabledSwitch,
        role = Role.Switch,
        onValueChange = onValueChange,
        interactionSource = interactionSource,
        indication = rememberRipple()
    )
    val scope = rememberCoroutineScope()

    // UI Implementation
    Box(
        modifier = modifier
            .swipeable(
                state = swipeState,
                anchors = mapOf(minBound to false, maxBound to true),
                thresholds = { _, _ -> FractionalThreshold(0.5f) },
                orientation = Orientation.Horizontal,
                enabled = enabledSwitch,
                reverseDirection = isRtl,
                interactionSource = interactionSource,
                resistance = null
            )
            .width(token.fixedTrackWidth)
            .height(token.fixedTrackHeight)
            .clip(CircleShape)
            .background(backgroundColor)
            .then(toggleModifier)
            .onKeyEvent { keyEvent ->
                when (keyEvent.nativeKeyEvent.keyCode) {
                    KEYCODE_ENTER, KEYCODE_SPACE -> {
                        scope.launch { swipeState.animateTo(!swipeState.currentValue) }
                        true
                    }
                    else -> false
                }
            }, contentAlignment = Alignment.CenterStart
    ) {
        Spacer(modifier = Modifier
            .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
            .shadow(elevation, CircleShape)
            .size(
                animateDpAsState(
                    targetValue =
                    if (interactionSource.collectIsPressedAsState().value)
                        token.pressedKnobDiameter
                    else
                        token.restKnobDiameter
                ).value
            )
            .clip(CircleShape)
            .background(foregroundColor)
        )
    }
}
