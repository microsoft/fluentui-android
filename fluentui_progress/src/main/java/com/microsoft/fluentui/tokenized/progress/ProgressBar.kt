package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.util.dpToPx

val LocalProgressBarTokens = compositionLocalOf { ProgressBarTokens() }
val LocalProgressBarInfo = compositionLocalOf { ProgressBarInfo() }

@Composable
fun getProgressBarTokens(): ProgressBarTokens {
    return LocalProgressBarTokens.current
}

@Composable
fun getProgressBarInfo(): ProgressBarInfo {
    return LocalProgressBarInfo.current
}

/**
 * Create a Determinate Linear Progressbar
 *
 * @param progress Progress of the progress indicator. 0.0 represents no progress and 1.0 represents full progress.
 * @param progressbarHeight Optional width of the progress bar
 * @param modifier Modifier for linear progress bar
 * @param animationSpec Optional animation for the progress indicator. Look at [AnimationSpec]
 * @param progressBarTokens Token values for linear progress bar
 *
 */
@Composable
fun LinearProgressBar(
    progress: Float,
    progressbarHeight: LinearProgressBarHeight = LinearProgressBarHeight.XXXSmall,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(
        delayMillis = 0,
        durationMillis = 1000,
        easing = LinearOutSlowInEasing
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
            getProgressBarTokens().getLinearProgressbarStrokeWidth(getProgressBarInfo())
        val progressBarBackgroundColor = getProgressBarTokens().getProgressBarBackgroundColor(
            getProgressBarInfo()
        )
        val progressBarIndicatorColor = getProgressBarTokens().getProgressBarIndicatorColor(
            getProgressBarInfo()
        )
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(progressBarHeight)
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

/**
 * Create an Indeterminate Linear Progressbar
 *
 * @param progressbarHeight Optional width of the progress bar
 * @param modifier Modifier for linear progress bar
 * @param totalAnimationDuration Optional total animation duration of the indicator to complete one cycle.
 * @param animationWaitDelay Optional animation wait delay for the indicator. Time to wait for the indicator to appear again after disappearing
 * @param easing Optional easing animation for the progress indicator. Look at [Easing]
 * @param progressBarTokens Token values for linear progress bar
 *
 */
@Composable
fun LinearProgressBar(
    progressbarHeight: LinearProgressBarHeight = LinearProgressBarHeight.XXXSmall,
    modifier: Modifier = Modifier,
    totalAnimationDuration: Int = 1500,
    animationWaitDelay: Int = 750,
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
        val progressBarHeight =
            getProgressBarTokens().getLinearProgressbarStrokeWidth(getProgressBarInfo())
        val progressBarBackgroundColor = getProgressBarTokens().getProgressBarBackgroundColor(
            getProgressBarInfo()
        )
        val progressBarIndicatorColor = getProgressBarTokens().getProgressBarIndicatorColor(
            getProgressBarInfo()
        )
        val infiniteTransition = rememberInfiniteTransition()
        val animationDuration = (totalAnimationDuration * 0.5f).toInt()
        val headAnimationDelay = 0
        val tailAnimationDelay = (animationDuration * 0.5f).toInt()
        val indicatorHead by infiniteTransition.animateFloat(
            0f,
            1f,
            infiniteRepeatable(
                animation = keyframes {
                    durationMillis = animationDuration + animationWaitDelay
                    0f at headAnimationDelay with easing
                    1f at animationDuration + headAnimationDelay
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
                    1f at animationDuration + tailAnimationDelay
                }
            )
        )
        Canvas(
            modifier = modifier
                .fillMaxWidth()
                .requiredHeight(progressBarHeight)
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
                Brush.linearGradient(
                    0f to progressBarBackgroundColor,
                    0.5f to progressBarIndicatorColor,
                    1.0f to progressBarBackgroundColor,
                    start = Offset(indicatorHead * size.width, yOffset),
                    end = Offset(indicatorTail * size.width, yOffset)
                ),
                Offset(indicatorHead * size.width, yOffset),
                Offset(indicatorTail * size.width, yOffset),
                strokeWidth
            )
        }
    }
}

/**
 * Create a Determinate Circular Progressbar
 *
 * @param progress Progress of the progress indicator. 0.0 represents no progress and 1.0 represents full progress.
 * @param size Optional size of the circular progress bar
 * @param modifier Modifier for linear progress bar
 * @param animationSpec Optional animation for the progress indicator. Look at [AnimationSpec]
 * @param progressBarTokens Token values for circular progress bar
 *
 */
@Composable
fun CircularProgressBar(
    progress: Float,
    size: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    animationSpec: AnimationSpec<Float> = tween(
        delayMillis = 0,
        durationMillis = 1000,
        easing = LinearOutSlowInEasing
    ),
    progressBarTokens: ProgressBarTokens? = null
) {
    val tokens = progressBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ProgressBar] as ProgressBarTokens
    CompositionLocalProvider(
        LocalProgressBarTokens provides tokens,
        LocalProgressBarInfo provides ProgressBarInfo(
            progressBarType = ProgressBarType.CircularProgressBar,
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
        val circularProgressBarStrokeWidth =
            getProgressBarTokens().getCircularProgressbarStrokeWidth(
                progressBarInfo = getProgressBarInfo()
            )
        val indicatorSizeInPx = dpToPx(circularProgressBarIndicatorSize)
        Canvas(modifier = modifier.requiredSize(circularProgressBarIndicatorSize)) {
            drawArc(
                circularProgressBarIndicatorColor,
                -90f,
                currentProgress.value * 360,
                false,
                size = Size(
                    indicatorSizeInPx,
                    indicatorSizeInPx
                ),
                style = Stroke(dpToPx(circularProgressBarStrokeWidth), cap = StrokeCap.Round)
            )
        }
    }
}

/**
 * Create an Indeterminate Circular Progressbar
 *
 * @param size Optional size of the circular progress bar
 * @param modifier Modifier for linear progress bar
 * @param totalAnimationDuration Optional total animation duration of the indicator to complete one cycle.
 * @param easing Optional easing animation for the progress indicator. Look at [Easing]
 * @param progressBarTokens Token values for circular progress bar
 *
 */
@Composable
fun CircularProgressBar(
    size: CircularProgressBarIndicatorSize = CircularProgressBarIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    totalAnimationDuration: Int = 1000,
    easing: Easing = LinearEasing,
    progressBarTokens: ProgressBarTokens? = null
) {
    val tokens = progressBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ProgressBar] as ProgressBarTokens
    CompositionLocalProvider(
        LocalProgressBarTokens provides tokens,
        LocalProgressBarInfo provides ProgressBarInfo(
            progressBarType = ProgressBarType.CircularProgressBar,
            circularProgressBarIndicatorSize = size
        )
    ) {
        val circularProgressBarIndicatorColor =
            getProgressBarTokens().getProgressBarIndicatorColor(
                getProgressBarInfo()
            )
        val circularProgressBarIndicatorSize =
            getProgressBarTokens().getCircularProgressBarIndicatorSize(getProgressBarInfo())
        val circularProgressBarStrokeWidth =
            getProgressBarTokens().getCircularProgressbarStrokeWidth(
                progressBarInfo = getProgressBarInfo()
            )
        val infiniteTransition = rememberInfiniteTransition()
        val startAngle by infiniteTransition.animateFloat(
            0f,
            360f,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = totalAnimationDuration,
                    easing = easing
                )
            )
        )
        val indicatorSizeInPx = dpToPx(circularProgressBarIndicatorSize)
        Canvas(
            modifier = Modifier
                .requiredSize(circularProgressBarIndicatorSize)
                .rotate(startAngle)
        ) {
            drawArc(
                Brush.sweepGradient(
                    0f to Color.Transparent,
                    0.6f to circularProgressBarIndicatorColor
                ),
                0f,
                270f,
                false,
                size = Size(
                    indicatorSizeInPx, indicatorSizeInPx
                ),
                style = Stroke(dpToPx(circularProgressBarStrokeWidth))
            )
            drawCircle(
                color = circularProgressBarIndicatorColor,
                radius = dpToPx(circularProgressBarStrokeWidth) / 2,
                center = Offset(indicatorSizeInPx / 2, 0f)
            )
        }
    }
}