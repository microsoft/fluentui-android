package com.microsoft.fluentui.tokenized.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerInfo
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerTokens
import com.microsoft.fluentui.util.dpToPx
import kotlin.math.absoluteValue
import kotlin.math.sqrt

const val DEFAULT_WIDTH = 120
const val DEFAULT_HEIGHT = 80
const val DEFAULT_CORNER_RADIUS = 4

/**
 * Create a Shimmer effect
 *
 * @param height Height of the shimmer
 * @param width Width of the shimmer
 * @param cornerRadius Corner radius of the shimmer
 * @param modifier Modifier for shimmer
 * @param shimmerTokens Token values for shimmer
 *
 */
@Composable
fun Shimmer(
    height: Dp = DEFAULT_HEIGHT.dp,
    width: Dp = DEFAULT_WIDTH.dp,
    cornerRadius: Dp = DEFAULT_CORNER_RADIUS.dp,
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null
) {
    InternalShimmer(
        height = height,
        width = width,
        cornerRadius = cornerRadius,
        modifier = modifier,
        shimmerTokens = shimmerTokens
    )
}

/**
 * Create a Shimmer effect
 *
 * @param content Content to be shimmered
 * @param cornerRadius Corner radius of the shimmer
 * @param modifier Modifier for shimmer
 * @param shimmerTokens Token values for shimmer
 *
 */
@Composable
fun Shimmer(
    cornerRadius: Dp = DEFAULT_CORNER_RADIUS.dp,
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null,
    content: @Composable () -> Unit,
) {
    InternalShimmer(
        cornerRadius = cornerRadius,
        modifier = modifier,
        shimmerTokens = shimmerTokens
    ) {
        content()
    }
}

@Composable
internal fun InternalShimmer(
    height: Dp? = null,
    width: Dp? = null,
    cornerRadius: Dp,
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null,
    content: (@Composable () -> Unit)? = null,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val tokens = shimmerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Shimmer] as ShimmerTokens
    val configuration = LocalConfiguration.current
    val screenHeight = dpToPx(configuration.screenHeightDp.dp)
    val screenWidth = dpToPx(configuration.screenWidthDp.dp)
    val diagonal =
        sqrt((screenHeight * screenHeight + screenWidth * screenWidth).toDouble()).toFloat()
    val shimmerInfo = ShimmerInfo()
    val shimmerBackgroundColor = if (content != null) {
        Color.Transparent
    } else {
        tokens.color(shimmerInfo)
    }
    val shimmerKnockoutEffectColor = tokens.knockoutEffectColor(shimmerInfo)
    val cornerRadius =
        dpToPx(cornerRadius)
    val shimmerDelay = tokens.delay(shimmerInfo)
    val infiniteTransition = rememberInfiniteTransition()
    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
    val initialValue = if (isLtr) 0f else screenWidth
    val targetValue = if (isLtr) diagonal else 0f
    val shimmerEffect by infiniteTransition.animateFloat(
        initialValue,
        targetValue,
        infiniteRepeatable(
            animation = tween(
                durationMillis = shimmerDelay,
                easing = LinearEasing
            )
        )
    )
    val gradientColor = Brush.linearGradient(
        0f to shimmerBackgroundColor,
        0.5f to shimmerKnockoutEffectColor,
        1.0f to shimmerBackgroundColor,
        start = Offset.Zero,
        end = Offset(shimmerEffect.absoluteValue, shimmerEffect.absoluteValue)
    )
    if (content != null) {
        Box(
            Modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
        ) {
            content()
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(gradientColor)
            )
        }
    } else {
        Spacer(
            modifier = modifier
                .width(width ?: DEFAULT_WIDTH.dp)
                .height(height ?: DEFAULT_HEIGHT.dp)
                .clip(RoundedCornerShape(cornerRadius))
                .background(gradientColor)
        )
    }

}