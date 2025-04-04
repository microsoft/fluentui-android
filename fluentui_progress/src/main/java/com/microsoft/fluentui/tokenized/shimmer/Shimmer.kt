package com.microsoft.fluentui.tokenized.shimmer

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
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
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
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
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerOrientation
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerTokens
import com.microsoft.fluentui.util.dpToPx
import kotlin.math.absoluteValue
import kotlin.math.sqrt

private const val DEFAULT_CORNER_RADIUS = 4

/**
 * Create an empty Shimmer effect
 *
 * @param modifier Modifier for shimmer
 * @param shimmerTokens Token values for shimmer
 *
 */
@Composable
fun Shimmer(
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null,
    shimmerDelay: Int = 1000,
    isShimmering: Boolean = true,
    shimmerOrientation: ShimmerOrientation = ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT,
) {
    InternalShimmer(
        cornerRadius = DEFAULT_CORNER_RADIUS.dp,
        modifier = modifier,
        shimmerTokens = shimmerTokens,
        shimmerDelay = shimmerDelay,
        isShimmering = isShimmering,
        shimmerOrientation = shimmerOrientation
    )
}

/**
 * Create Shimmer effect on some content
 *
 * @param cornerRadius Corner radius of the shimmer
 * @param modifier Modifier for shimmer
 * @param shimmerTokens Token values for shimmer
 * @param content Content to be shimmered
 *
 */
@Composable
fun Shimmer(
    cornerRadius: Dp,
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null,
    shimmerDelay: Int = 1000,
    isShimmering: Boolean = true,
    shimmerOrientation: ShimmerOrientation = ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT,
    content: @Composable () -> Unit,
) {
    InternalShimmer(
        cornerRadius = cornerRadius,
        modifier = modifier,
        shimmerTokens = shimmerTokens,
        shimmerDelay = shimmerDelay,
        isShimmering = isShimmering,
        shimmerOrientation = shimmerOrientation,
    ) {
        content()
    }
}

@Composable
internal fun InternalShimmer(
    cornerRadius: Dp,
    modifier: Modifier = Modifier,
    shimmerTokens: ShimmerTokens? = null,
    shimmerDelay: Int = 1000,
    isShimmering: Boolean = true,
    shimmerOrientation: ShimmerOrientation = ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT,
    content: (@Composable () -> Unit)? = null,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val tokens = shimmerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ShimmerControlType] as ShimmerTokens
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
    val infiniteTransition = rememberInfiniteTransition()
    val isLtr = if (shimmerOrientation in listOf(
            ShimmerOrientation.LEFT_TO_RIGHT,
            ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT
        )
    ) (LocalLayoutDirection.current == LayoutDirection.Ltr) else (LocalLayoutDirection.current == LayoutDirection.Rtl)
    val initialValue = if (isLtr) 0f else diagonal
    val targetValue = if (isLtr) diagonal else 0f
    val shimmerEffect by if (isShimmering) {
            infiniteTransition.animateFloat(
                initialValue,
                targetValue,
                infiniteRepeatable(
                    animation = tween(
                        durationMillis = shimmerDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
    }
     else {
        remember { mutableFloatStateOf(0f) }
    }

    val startOffset: Offset = when (shimmerOrientation) {
        ShimmerOrientation.LEFT_TO_RIGHT -> Offset.Zero
        ShimmerOrientation.RIGHT_TO_LEFT -> Offset.Zero
        ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT -> Offset.Zero
        ShimmerOrientation.BOTTOMRIGHT_TO_TOPLEFT -> Offset.Zero
        else -> Offset.Zero
    }
    val endOffset: Offset = if (isShimmering) {
        when (shimmerOrientation) {
            ShimmerOrientation.LEFT_TO_RIGHT -> Offset(shimmerEffect.absoluteValue, 0F)
            ShimmerOrientation.RIGHT_TO_LEFT -> Offset(shimmerEffect.absoluteValue, 0F)
            ShimmerOrientation.TOPLEFT_TO_BOTTOMRIGHT -> Offset(shimmerEffect.absoluteValue, shimmerEffect.absoluteValue)
            ShimmerOrientation.BOTTOMRIGHT_TO_TOPLEFT -> Offset(shimmerEffect.absoluteValue, shimmerEffect.absoluteValue)
            else -> Offset(shimmerEffect.absoluteValue, shimmerEffect.absoluteValue)
        }
    } else {
        Offset.Zero
    }
    val gradientColor = Brush.linearGradient(
        0f to shimmerBackgroundColor,
        0.5f to shimmerKnockoutEffectColor,
        1.0f to shimmerBackgroundColor,
        start = startOffset,
        end = endOffset
    )
    if (content != null) {
        Box(
            modifier
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
        ) {
            content()
            if(isShimmering) {
                Spacer(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(cornerRadius))
                        .background(gradientColor)
                )
            }
        }
    } else {
        if (true) {
            Spacer(
                modifier = modifier
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(gradientColor)
            )
        }
    }
}