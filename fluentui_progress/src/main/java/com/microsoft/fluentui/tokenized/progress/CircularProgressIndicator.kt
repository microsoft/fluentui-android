package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.progressSemantics
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
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorInfo
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorTokens
import com.microsoft.fluentui.util.dpToPx

val LocalCircularProgressIndicatorTokens = compositionLocalOf { CircularProgressIndicatorTokens() }
val LocalCircularProgressIndicatorInfo = compositionLocalOf { CircularProgressIndicatorInfo() }

@Composable
fun getCircularProgressIndicatorTokens(): CircularProgressIndicatorTokens {
    return LocalCircularProgressIndicatorTokens.current
}

@Composable
fun getCircularProgressIndicatorInfo(): CircularProgressIndicatorInfo {
    return LocalCircularProgressIndicatorInfo.current
}

/**
 * Create a Determinate Circular Progress Indicator
 *
 * @param progress Progress of the progress indicator. 0.0 represents no progress and 1.0 represents full progress.
 * @param size Optional size of the circular progress indicator
 * @param modifier Modifier for circular progress indicator
 * @param style Style of progress indicator. Default: [FluentStyle.Neutral]
 * @param circularProgressIndicatorTokens Token values for circular progress indicator
 *
 */
@Composable
fun CircularProgressIndicator(
    progress: Float,
    size: CircularProgressIndicatorSize = CircularProgressIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    circularProgressIndicatorTokens: CircularProgressIndicatorTokens? = null
) {
    val tokens = circularProgressIndicatorTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CircularProgressIndicator] as CircularProgressIndicatorTokens
    CompositionLocalProvider(
        LocalCircularProgressIndicatorTokens provides tokens,
        LocalCircularProgressIndicatorInfo provides CircularProgressIndicatorInfo(
            circularProgressIndicatorSize = size,
            style = style
        )
    ) {
        val currentProgress = animateFloatAsState(
            targetValue = progress.coerceIn(0f..1f),
            animationSpec = tween(
                delayMillis = 0,
                durationMillis = 750,
                easing = LinearOutSlowInEasing
            )
        )
        val circularProgressIndicatorColor =
            getCircularProgressIndicatorTokens().color(
                getCircularProgressIndicatorInfo()
            )
        val circularProgressIndicatorSize =
            getCircularProgressIndicatorTokens().size(
                getCircularProgressIndicatorInfo()
            )
        val circularProgressIndicatorStrokeWidth =
            getCircularProgressIndicatorTokens().strokeWidth(
                getCircularProgressIndicatorInfo()
            )
        val indicatorSizeInPx = dpToPx(circularProgressIndicatorSize)
        Canvas(
            modifier = modifier
                .requiredSize(circularProgressIndicatorSize)
                .progressSemantics(progress)
        ) {
            drawArc(
                circularProgressIndicatorColor,
                -90f,
                currentProgress.value * 360,
                false,
                size = Size(
                    indicatorSizeInPx,
                    indicatorSizeInPx
                ),
                style = Stroke(dpToPx(circularProgressIndicatorStrokeWidth), cap = StrokeCap.Round)
            )
        }
    }
}

/**
 * Create an Indeterminate Circular Progress indicator
 *
 * @param size Optional size of the circular progress indicator
 * @param modifier Modifier for circular progress indicator
 * @param style Style of progress indicator. Default: [FluentStyle.Neutral]
 * @param circularProgressIndicatorTokens Token values for circular progress indicator
 *
 */
@Composable
fun CircularProgressIndicator(
    size: CircularProgressIndicatorSize = CircularProgressIndicatorSize.XXSmall,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    circularProgressIndicatorTokens: CircularProgressIndicatorTokens? = null
) {
    val tokens = circularProgressIndicatorTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CircularProgressIndicator] as CircularProgressIndicatorTokens
    CompositionLocalProvider(
        LocalCircularProgressIndicatorTokens provides tokens,
        LocalCircularProgressIndicatorInfo provides CircularProgressIndicatorInfo(
            circularProgressIndicatorSize = size,
            style = style
        )
    ) {
        val circularProgressIndicatorColor =
            getCircularProgressIndicatorTokens().color(
                getCircularProgressIndicatorInfo()
            )
        val circularProgressIndicatorSize =
            getCircularProgressIndicatorTokens().size(
                getCircularProgressIndicatorInfo()
            )
        val circularProgressIndicatorStrokeWidth =
            getCircularProgressIndicatorTokens().strokeWidth(
                getCircularProgressIndicatorInfo()
            )
        val infiniteTransition = rememberInfiniteTransition()
        val startAngle by infiniteTransition.animateFloat(
            0f,
            360f,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
                    easing = LinearEasing
                )
            )
        )
        val indicatorSizeInPx = dpToPx(circularProgressIndicatorSize)
        Canvas(
            modifier = modifier
                .requiredSize(circularProgressIndicatorSize)
                .progressSemantics()
        ) {
            drawArc(
                circularProgressIndicatorColor,
                startAngle,
                270f,
                false,
                size = Size(
                    indicatorSizeInPx, indicatorSizeInPx
                ),
                style = Stroke(dpToPx(circularProgressIndicatorStrokeWidth), cap = StrokeCap.Round)
            )
        }
    }
}