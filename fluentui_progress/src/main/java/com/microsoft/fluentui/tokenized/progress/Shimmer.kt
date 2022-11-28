package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.util.dpToPx
import kotlin.math.absoluteValue

val LocalShimmerTokens = compositionLocalOf { ShimmerTokens() }

@Composable
fun getShimmerTokens(): ShimmerTokens {
    return LocalShimmerTokens.current
}

/**
 * Create a Shimmer effect
 *
 * @param shape Shape of the shimmer. See [ShimmerShape] for shapes
 * @param modifier Modifier for shimmer
 * @param shimmerTokens Token values for shimmer
 *
 */
@Composable
fun Shimmer(
    shape: ShimmerShape = ShimmerShape.Box,
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null
) {
    val tokens = shimmerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Shimmer] as ShimmerTokens
    val configuration = LocalConfiguration.current
    val screenHeight = dpToPx(configuration.screenHeightDp.dp)
    val screenWidth = dpToPx(configuration.screenWidthDp.dp)
    val diagonal =
        Math.sqrt((screenHeight * screenHeight + screenWidth * screenWidth).toDouble()).toFloat()
    CompositionLocalProvider(
        LocalShimmerTokens provides tokens
    ) {
        val shimmerBackgroundColor =
            getShimmerTokens().getShimmerColor()
        val shimmerKnockoutEffectColor = getShimmerTokens().getShimmerKnockoutEffectColor()
        val cornerRadius =
            dpToPx(getShimmerTokens().getShimmerCornerRadius())
        val infiniteTransition = rememberInfiniteTransition()
        val shimmerEffect by infiniteTransition.animateFloat(
            0f,
            diagonal,
            infiniteRepeatable(
                animation = tween(
                    durationMillis = 1000,
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
        if (shape == ShimmerShape.Box) {
            Spacer(
                modifier = modifier
                    .width(240.dp)
                    .height(12.dp)
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(gradientColor)
            )
        } else {
            Spacer(
                modifier = modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(gradientColor)
            )
        }
    }
}