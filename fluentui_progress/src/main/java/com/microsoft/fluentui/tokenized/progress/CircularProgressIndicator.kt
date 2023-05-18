package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.progressSemantics
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorInfo
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorTokens
import com.microsoft.fluentui.util.dpToPx

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
    val themeID = FluentTheme.themeID
    val tokens = circularProgressIndicatorTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CircularProgressIndicator] as CircularProgressIndicatorTokens
    val circularProgressIndicatorInfo = CircularProgressIndicatorInfo(
        circularProgressIndicatorSize = size,
        style = style
    )
    val currentProgress = animateFloatAsState(
        targetValue = progress.coerceIn(0f..1f),
        animationSpec = tween(
            delayMillis = 0,
            durationMillis = 750,
            easing = LinearOutSlowInEasing
        )
    )
    val circularProgressIndicatorColor =
        tokens.brush(
            circularProgressIndicatorInfo
        )
    val circularProgressIndicatorSize =
        tokens.size(
            circularProgressIndicatorInfo
        )
    val circularProgressIndicatorStrokeWidth =
        tokens.strokeWidth(
            circularProgressIndicatorInfo
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
    val themeID = FluentTheme.themeID
    val tokens = circularProgressIndicatorTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CircularProgressIndicator] as CircularProgressIndicatorTokens
    val circularProgressIndicatorInfo = CircularProgressIndicatorInfo(
        circularProgressIndicatorSize = size,
        style = style
    )
    val circularProgressIndicatorColor =
        tokens.brush(
            circularProgressIndicatorInfo
        )
    val circularProgressIndicatorSize =
        tokens.size(
            circularProgressIndicatorInfo
        )
    val circularProgressIndicatorStrokeWidth =
        tokens.strokeWidth(
            circularProgressIndicatorInfo
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
