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
import com.microsoft.fluentui.theme.token.controlTokens.FlairCardTokens
import kotlinx.coroutines.delay

enum class Flair {
    Start,
    Rest
}

var currentFlairState: Flair by mutableStateOf(Flair.Rest)

@Composable
fun FlairCard(
    modifier: Modifier = Modifier,
    flairCardTokens: FlairCardTokens? = null,
    content: @Composable () -> Unit,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = flairCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.FlairCard] as FlairCardTokens
    val transition = updateTransition(currentFlairState, label = "Flair state")
    var gradient = Brush.verticalGradient(
        0.0f to transition.animateColor(transitionSpec = {
            when {
                Flair.Rest isTransitioningTo Flair.Start ->
                    keyframes {
                        durationMillis = 1600
                        Color.Transparent at 0
                        Color.Transparent at 600 with LinearOutSlowInEasing
                        Color(0xFF464FEB) at 800
                        Color(0xFF464FEB) at 1400 with LinearOutSlowInEasing
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
                        Color(0xFF47CFFA) at 600
                        Color(0xFF47CFFA) at 1200 with LinearOutSlowInEasing
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
                        Color(0xFFB47CF8) at 400
                        Color(0xFFB47CF8) at 1000 with LinearOutSlowInEasing
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

        LaunchedEffect(key1 = currentFlairState, block = {
            if (currentFlairState == Flair.Start) {
                delay(2000)
                currentFlairState = Flair.Rest
            }
        })

        if (currentFlairState == Flair.Start) {
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
        BasicCard(modifier = Modifier.padding(2.dp), basicCardTokens = flairCardTokens) {
            content()
        }
    }
}

class FlairState(
    initialValue: Flair = Flair.Rest,
) {
    var currentValue: Flair by mutableStateOf(initialValue)

    fun start() {
        currentFlairState = if (currentFlairState == Flair.Start) {
            Flair.Rest
        } else {
            Flair.Start
        }
    }

    companion object {
        fun Saver() =
            Saver<FlairState, Flair>(
                save = { it.currentValue },
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