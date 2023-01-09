package com.microsoft.fluentui.tokenized.controls

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import kotlin.math.roundToInt

val LocalToggleSwitchTokens = compositionLocalOf { ToggleSwitchTokens() }
val LocalToggleSwitchInfo = compositionLocalOf { ToggleSwitchInfo() }

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

    val token = switchTokens
        ?: FluentTheme.controlTokens.tokens[ControlType.ToggleSwitch] as ToggleSwitchTokens

    CompositionLocalProvider(
        LocalToggleSwitchTokens provides token,
        LocalToggleSwitchInfo provides ToggleSwitchInfo(checkedState)
    ) {

        val backgroundColor: Color =
            getToggleSwitchToken().trackColor(switchInfo = getToggleSwitchInfo()).getColorByState(
                enabled = enabledSwitch,
                selected = checkedState,
                interactionSource = interactionSource
            )
        val foregroundColor: Color =
            getToggleSwitchToken().knobColor(switchInfo = getToggleSwitchInfo()).getColorByState(
                enabled = enabledSwitch,
                selected = checkedState,
                interactionSource = interactionSource
            )
        val elevation: Dp =
            getToggleSwitchToken().elevation(fabInfo = getToggleSwitchInfo()).getElevationByState(
                enabled = enabledSwitch,
                selected = checkedState,
                interactionSource = interactionSource
            )
        val padding: Dp = getToggleSwitchToken().paddingTrack


        // Swipe Logic
        val knobMovementWidth = 23.dp
        val minBound = with(LocalDensity.current) { padding.toPx() }
        val maxBound = with(LocalDensity.current) { knobMovementWidth.toPx() }
        val animationSpec = TweenSpec<Float>(durationMillis = 100)
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
        val toggleModifier = modifier.toggleable(
            value = getToggleSwitchInfo().checked,
            enabled = enabledSwitch,
            role = Role.Switch,
            onValueChange = onValueChange,
            interactionSource = interactionSource,
            indication = null
        )

        // UI Implementation
        Box(
            modifier = Modifier
                .then(toggleModifier)
                .swipeable(
                    state = swipeState,
                    anchors = mapOf(minBound to false, maxBound to true),
                    thresholds = { _, _ -> FractionalThreshold(0.5f) },
                    orientation = Orientation.Horizontal,
                    enabled = enabledSwitch,
                    reverseDirection = isRtl,
                    interactionSource = interactionSource,
                    resistance = null
                ), contentAlignment = Alignment.CenterStart
        ) {
            Box(
                modifier = Modifier
                    .width(getToggleSwitchToken().fixedTrackWidth)
                    .height(getToggleSwitchToken().fixedTrackHeight)
                    .clip(CircleShape)
                    .background(backgroundColor)
            )
            Spacer(modifier = Modifier
                .offset { IntOffset(swipeState.offset.value.roundToInt(), 0) }
                .shadow(elevation, CircleShape)
                .indication(
                    interactionSource = interactionSource,
                    indication = rememberRipple(
                        false,
                        getToggleSwitchToken().knobRippleRadius
                    )
                )
                .size(getToggleSwitchToken().fixedKnobDiameter)
                .clip(CircleShape)
                .background(foregroundColor)
            )
        }
    }
}

@Composable
fun getToggleSwitchToken(): ToggleSwitchTokens {
    return LocalToggleSwitchTokens.current
}

@Composable
fun getToggleSwitchInfo(): ToggleSwitchInfo {
    return LocalToggleSwitchInfo.current
}
