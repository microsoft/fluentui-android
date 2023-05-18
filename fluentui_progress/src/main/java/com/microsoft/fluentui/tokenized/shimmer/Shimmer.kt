package com.microsoft.fluentui.tokenized.shimmer

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerInfo
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerShape
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerTokens
import com.microsoft.fluentui.util.dpToPx
import kotlin.math.absoluteValue
import kotlin.math.sqrt

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
    modifier: Modifier = Modifier,
    shape: ShimmerShape = ShimmerShape.Box,
    shimmerTokens: ShimmerTokens? = null
) {
    val themeID = FluentTheme.themeID
    val tokens = shimmerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Shimmer] as ShimmerTokens
    val configuration = LocalConfiguration.current
    val screenHeight = dpToPx(configuration.screenHeightDp.dp)
    val screenWidth = dpToPx(configuration.screenWidthDp.dp)
    val diagonal =
        sqrt((screenHeight * screenHeight + screenWidth * screenWidth).toDouble()).toFloat()
    val shimmerInfo = ShimmerInfo(shape = shape)
    val shimmerBackgroundColor =
        tokens.color(shimmerInfo)
    val shimmerKnockoutEffectColor = tokens.knockoutEffectColor(shimmerInfo)
    val cornerRadius =
        dpToPx(tokens.cornerRadius(shimmerInfo))
    val infiniteTransition = rememberInfiniteTransition()
    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr
    val initialValue = if (isLtr) 0f else screenWidth
    val targetValue = if (isLtr) diagonal else 0f
    val shimmerEffect by infiniteTransition.animateFloat(
        initialValue,
        targetValue,
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