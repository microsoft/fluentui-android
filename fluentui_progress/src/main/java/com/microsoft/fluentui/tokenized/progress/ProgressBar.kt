package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.util.dpToPx

val LocalProgressBarTokens = compositionLocalOf { ProgressBarTokens() }
val LocalProgressBarInfo = compositionLocalOf { ProgressBarInfo() }
const val animationDelay = 0
const val animationDuration = 1000
val animationEasing = LinearOutSlowInEasing

@Composable
fun getProgressBarTokens(): ProgressBarTokens {
    return LocalProgressBarTokens.current
}

@Composable
fun getProgressBarInfo(): ProgressBarInfo {
    return LocalProgressBarInfo.current
}

@Composable
fun LinearProgressBar(
    progress: Float,
    progressbarHeight: LinearProgressBarHeight = LinearProgressBarHeight.XXXSmall,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(
        delayMillis = animationDelay,
        durationMillis = animationDuration,
        easing = animationEasing
    ),
    progressBarTokens: ProgressBarTokens? = null
) {
    val tokens = progressBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ProgressBar] as ProgressBarTokens
    CompositionLocalProvider(
        LocalProgressBarTokens provides tokens,
        LocalProgressBarInfo provides ProgressBarInfo(
            progressBarType = ProgressBarType.LinearProgressBar,
            linearProgressBarHeight = progressbarHeight
        )
    ) {
        val currentProgress = animateFloatAsState(
            targetValue = if (progress >= 1) 1f else progress,
            animationSpec = animationSpec
        )
        val progressBarHeight =
            getProgressBarTokens().getLinearProgressBarHeight(getProgressBarInfo())
        val progressBarBackgroundColor = getProgressBarTokens().getProgressBarBackgroundColor(
            getProgressBarInfo()
        )
        val progressBarIndicatorColor = getProgressBarTokens().getProgressBarIndicatorColor(
            getProgressBarInfo()
        )
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .height(progressBarHeight)
        ) {
            val yOffset = dpToPx(progressBarHeight) / 2
            drawLine(
                progressBarBackgroundColor,
                Offset(0f, yOffset),
                Offset(size.width, yOffset),
                dpToPx(progressBarHeight)
            )
            drawLine(
                progressBarIndicatorColor,
                Offset(0f, yOffset),
                Offset(currentProgress.value * (size.width), yOffset),
                dpToPx(progressBarHeight)
            )
        }
    }

}

@Composable
fun CircularProgressBar(
    progress: Float,
    size: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XSmall,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(
        delayMillis = animationDelay,
        durationMillis = animationDuration,
        easing = animationEasing
    ),
    progressBarTokens: ProgressBarTokens? = null
) {
    val tokens = progressBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ProgressBar] as ProgressBarTokens
    CompositionLocalProvider(
        LocalProgressBarTokens provides tokens,
        LocalProgressBarInfo provides ProgressBarInfo(
            progressBarType = ProgressBarType.LinearProgressBar,
            circularProgressBarIndicatorSize = size
        )
    ) {
        val currentProgress = animateFloatAsState(
            targetValue = progress,
            animationSpec = animationSpec
        )
        val circularProgressBarIndicatorColor =
            getProgressBarTokens().getProgressBarIndicatorColor(
                getProgressBarInfo()
            )
        val circularProgressBarIndicatorSize =
            getProgressBarTokens().getCircularProgressBarIndicatorSize(getProgressBarInfo())
        Canvas(modifier = modifier.size(circularProgressBarIndicatorSize)) {
            drawArc(
                circularProgressBarIndicatorColor,
                -90f,
                currentProgress.value * 360,
                false,
                size = Size(
                    dpToPx(circularProgressBarIndicatorSize),
                    dpToPx(circularProgressBarIndicatorSize)
                ),
                style = Stroke(dpToPx(2.dp))
            )
        }
    }

}