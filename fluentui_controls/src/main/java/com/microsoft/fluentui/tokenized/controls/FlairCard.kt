package com.microsoft.fluentui.tokenized.controls

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.FlairCardInfo
import com.microsoft.fluentui.theme.token.controlTokens.FlairCardTokens
import kotlinx.coroutines.delay

enum class Flair {
    Start,
    Rest
}

data class GradientColors(val color1: Color, val color2: Color, val color3: Color)

/**
 * Cards are flexible containers that group related content and actions together. They reveal more information upon interaction.
 * A Flair card is a basic card with flair animation.
 *
 * @param modifier Modifier for the card
 * @param gradientColors List of colors for the flair gradient. Use [GradientColors]
 * @param flairCardTokens Optional tokens for customizing the card
 * @param content Content for the card
 */
@Composable
fun FlairCard(
    modifier: Modifier = Modifier,
    gradientColors: GradientColors = GradientColors(
        Color(0xFFB47CF8),
        Color(0xFF47CFFA),
        Color(0xFF464FEB)
    ),
    flairState: FlairState,
    flairCardTokens: FlairCardTokens? = null,
    content: @Composable () -> Unit,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = flairCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.FlairCard] as FlairCardTokens
    val borderStrokeWidth = token.borderStrokeWidth(flairCardInfo = FlairCardInfo())
    val transition = updateTransition(flairState.currentFlairState, label = "Flair state")
    val gradient = Brush.verticalGradient(
        0.0f to transition.animateColor(transitionSpec = {
            when {
                Flair.Rest isTransitioningTo Flair.Start ->
                    keyframes {
                        durationMillis = 1600
                        Color.Transparent at 0
                        Color.Transparent at 600 with LinearOutSlowInEasing
                        gradientColors.color3 at 800
                        gradientColors.color3 at 1400 with LinearOutSlowInEasing
                        Color.Transparent at 1600
                    }
                else -> tween()
            }

        }, label = "color-2 transition") { state ->
            when (state) {
                Flair.Rest -> Color.Transparent
                Flair.Start -> Color.Transparent
            }
        }.value,
        0.7f to transition.animateColor(transitionSpec = {
            when {
                Flair.Rest isTransitioningTo Flair.Start ->
                    keyframes {
                        durationMillis = 1600
                        Color.Transparent at 0
                        Color.Transparent at 400 with LinearOutSlowInEasing
                        gradientColors.color2 at 600
                        gradientColors.color2 at 1200 with LinearOutSlowInEasing
                        Color.Transparent at 1400
                    }
                else -> tween()
            }
        }, label = "color-1 transition") { state ->
            when (state) {
                Flair.Rest -> Color.Transparent
                Flair.Start -> Color.Transparent
            }
        }.value,
        0.92f to transition.animateColor(transitionSpec = {
            when {
                Flair.Rest isTransitioningTo Flair.Start ->
                    keyframes {
                        durationMillis = 1600
                        Color.Transparent at 0
                        Color.Transparent at 200 with LinearOutSlowInEasing
                        gradientColors.color1 at 400
                        gradientColors.color1 at 1000 with LinearOutSlowInEasing
                        Color.Transparent at 1200
                    }
                else -> tween()
            }
        }, label = "color-0 transition") { state ->
            when (state) {
                Flair.Rest -> Color.Transparent
                Flair.Start -> Color.Transparent
            }
        }.value
    )

    val infiniteTransition = rememberInfiniteTransition()
    val startAngle by infiniteTransition.animateFloat(
        0f,
        720f,
        infiniteRepeatable(
            animation = tween(
                durationMillis = 2000,
                easing = LinearEasing
            )
        )
    )
    val angle by transition.animateFloat(transitionSpec = {
        when {
            Flair.Rest isTransitioningTo Flair.Start ->
                tween(2000, easing = LinearEasing)
            else ->
                tween()
        }

    }, label = "Flair animation") { state ->
        when (state) {
            Flair.Start -> 720f
            Flair.Rest -> 0f
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .width(IntrinsicSize.Max)
            .height(IntrinsicSize.Max)
    ) {

        LaunchedEffect(key1 = flairState.currentFlairState, block = {
            if (flairState.currentFlairState == Flair.Start) {
                delay(2000)
                flairState.currentFlairState = Flair.Rest
            }
        })
        if (flairState.currentFlairState == Flair.Start) {
            Canvas(
                modifier = Modifier
                    .fillMaxSize(1f)
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .rotate(45f)
                    .rotate(angle)
            ) {
                drawCircle(
                    gradient,
                    radius = size.maxDimension
                )
            }
        }
        BasicCard(
            modifier = Modifier.padding(borderStrokeWidth),
            basicCardTokens = flairCardTokens
        ) {
            content()
        }
    }
}

class FlairState(
    initialValue: Flair = Flair.Rest,
) {
    var currentFlairState: Flair by mutableStateOf(initialValue)

    fun start() {
        currentFlairState = Flair.Start
    }

    companion object {
        fun Saver() =
            Saver<FlairState, Flair>(
                save = { it.currentFlairState },
                restore = { FlairState(it) }
            )
    }
}

@Composable
fun rememberFlairState(): FlairState {
    return rememberSaveable(saver = FlairState.Saver()) {
        FlairState(Flair.Rest)
    }
}