package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.util.dpToPx

val LocalProgressBarTokens = compositionLocalOf { ProgressBarTokens() }
val LocalProgressBarInfo = compositionLocalOf { ProgressBarInfo() }

const val lineOffset = 150
const val headAnimationDelay = 0
const val tailAnimationDelay = 300
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
        delayMillis = 0,
        durationMillis = 1000,
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
            val strokeWidth = dpToPx(progressBarHeight)
            val yOffset = strokeWidth / 2
            drawLine(
                progressBarBackgroundColor,
                Offset(0f, yOffset),
                Offset(size.width, yOffset),
                strokeWidth
            )
            drawLine(
                progressBarIndicatorColor,
                Offset(0f, yOffset),
                Offset(currentProgress.value * (size.width), yOffset),
                strokeWidth
            )
        }
    }

}

@Composable
fun LinearProgressBar(
    progressbarHeight: LinearProgressBarHeight = LinearProgressBarHeight.XXXSmall,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1500,
    animationWaitDelay: Int = 150,
    easing: Easing = FastOutSlowInEasing,
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
        val infiniteTransition = rememberInfiniteTransition()
        val headAnimationDuration = animationDuration / 2 - lineOffset
        val tailAnimationDuration = animationDuration / 2 + lineOffset
        val indicatorHead by infiniteTransition.animateFloat(
            0f,
            1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration + animationWaitDelay
                    0f at headAnimationDelay with easing
                    1f at headAnimationDuration + headAnimationDelay
                }
            )
        )
        val indicatorTail by infiniteTransition.animateFloat(
            0f,
            1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration + animationWaitDelay
                    0f at tailAnimationDelay with easing
                    1f at tailAnimationDuration + tailAnimationDelay
                }
            )
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
            val strokeWidth = dpToPx(progressBarHeight)
            val yOffset = strokeWidth / 2
            drawLine(
                progressBarBackgroundColor,
                Offset(0f, yOffset),
                Offset(size.width, yOffset),
                strokeWidth
            )
            drawLine(
                progressBarIndicatorColor,
                Offset(indicatorHead * size.width, yOffset),
                Offset(indicatorTail * size.width, yOffset),
                strokeWidth
            )
        }
    }
}

@Composable
fun CircularProgressBar(
    progress: Float,
    size: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(
        delayMillis = 0,
        durationMillis = 1000,
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
                style = Stroke(dpToPx(2.dp), cap = StrokeCap.Round)
            )
        }
    }

}

@Composable
fun CircularProgressBar(
    size: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    animationDuration: Int = 1500,
    animationWaitDelay: Int = 150,
    easing: Easing = FastOutSlowInEasing,
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
        val infiniteTransition = rememberInfiniteTransition()
        val headAnimationDuration = animationDuration / 2 - lineOffset
        val tailAnimationDuration = animationDuration / 2 + lineOffset
        val indicatorHead by infiniteTransition.animateFloat(
            0f,
            1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration + animationWaitDelay
                    0f at headAnimationDelay with easing
                    1f at headAnimationDuration + headAnimationDelay
                }
            )
        )
        val indicatorTail by infiniteTransition.animateFloat(
            0f,
            1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration + animationWaitDelay
                    0f at tailAnimationDelay with easing
                    1f at tailAnimationDuration + tailAnimationDelay
                }
            )
        )
        val circularProgressBarIndicatorColor =
            getProgressBarTokens().getProgressBarIndicatorColor(
                getProgressBarInfo()
            )
        val circularProgressBarIndicatorSize =
            getProgressBarTokens().getCircularProgressBarIndicatorSize(getProgressBarInfo())
        Canvas(modifier = modifier.size(circularProgressBarIndicatorSize)) {
            val strokeWidth = 2.dp
            drawArc(
                circularProgressBarIndicatorColor,
                indicatorTail*360,
                indicatorHead*360,
                false,
                size = Size(
                    dpToPx(circularProgressBarIndicatorSize),
                    dpToPx(circularProgressBarIndicatorSize)
                ),
                style = Stroke(dpToPx(2.dp), cap = StrokeCap.Round)
            )
        }
    }
    LinearProgressIndicator()
}