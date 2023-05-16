package com.microsoft.fluentui.tokenized.progress

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ProgressTextIcons
import com.microsoft.fluentui.icons.progresstexticons.DismissCircle
import com.microsoft.fluentui.progress.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.ProgressTextInfo
import com.microsoft.fluentui.theme.token.controlTokens.ProgressTextTokens
import com.microsoft.fluentui.util.dpToPx

/**
 * ProgressTexts are used to give information through a text about the current progress.
 * A ProgressText consists of a Text and a progressbar.
 * @param text Text or info to display
 * @param progress Progress of the progress indicator. 0.0 represents no progress and 1.0 represents full progress.
 * @param leadingIconAccessory Add an optional leading icon.
 * @param modifier Modifier for the progress text
 * @param progressTextTokens Token values for the ProgressText
 */
@Composable
fun ProgressText(
    text: String,
    progress: Float,
    leadingIconAccessory: FluentIcon? = null,
    modifier: Modifier = Modifier,
    progressTextTokens: ProgressTextTokens? = null
) {
    val tokens = progressTextTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ProgressText] as ProgressTextTokens
    val progressTextInfo = ProgressTextInfo(progress)
    val currentProgress = animateFloatAsState(
        targetValue = progress.coerceIn(0f..1f), animationSpec = tween(
            delayMillis = 0, durationMillis = 1000, easing = LinearOutSlowInEasing
        )
    )
    val backgroundBrush = tokens.backgroundBrush(progressTextInfo = progressTextInfo)
    val border = tokens.borderWidth(progressTextInfo = progressTextInfo)
    val borderColor = tokens.borderColor(progressTextInfo = progressTextInfo)
    val typography = tokens.typography(progressTextInfo = progressTextInfo)
    val textColor = tokens.textColor(progressTextInfo = progressTextInfo)
    val progressIndicatorHeight = tokens.progressbarHeight(progressTextInfo = progressTextInfo)
    val progressIndicatorBackgroundColor =
        tokens.progressbarBackgroundColor(progressTextInfo = progressTextInfo)
    val progressIndicatorColor = tokens.progressbarBrush(progressTextInfo = progressTextInfo)
    val iconTextSpacing = tokens.iconTextSpacing(progressTextInfo = progressTextInfo)
    val padding = tokens.padding(progressTextInfo = progressTextInfo)
    val iconSize = tokens.iconSize(progressTextInfo = progressTextInfo)
    val iconColor = tokens.iconColor(progressTextInfo = progressTextInfo)
    val shape = RoundedCornerShape(12.dp)
    Box(modifier = Modifier.clip(shape)) {
        Box(
            modifier = modifier
                .background(
                    backgroundBrush, shape
                )
                .border(
                    border, borderColor, shape
                )
        ) {
            Row(
                modifier = Modifier.padding(padding),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (leadingIconAccessory != null) {
                    Icon(modifier = Modifier.size(iconSize), icon = leadingIconAccessory, tint = iconColor)
                    Spacer(modifier = Modifier.width(iconTextSpacing))
                }
                BasicText(text = text, style = typography.merge(TextStyle(color = textColor)))
            }
        }
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            Canvas(
                modifier = modifier
                    .fillMaxWidth()
                    .requiredHeight(progressIndicatorHeight)
                    .progressSemantics(progress)
                    .testTag("progressBar")
            ) {
                val strokeWidth = dpToPx(progressIndicatorHeight)
                val yOffset = strokeWidth / 2
                val isLtr = layoutDirection == LayoutDirection.Ltr
                val barStart = (if (isLtr) 0f else size.width)
                val barEnd = (if (isLtr) size.width else 0f)
                drawLine(
                    progressIndicatorBackgroundColor,
                    Offset(barStart, yOffset),
                    Offset(barEnd, yOffset),
                    strokeWidth
                )
                val progressIndicatorWidth = currentProgress.value * size.width
                val indicatorLineEnd =
                    if (isLtr) progressIndicatorWidth else size.width - progressIndicatorWidth
                drawLine(
                    progressIndicatorColor,
                    Offset(barStart, yOffset),
                    Offset(indicatorLineEnd, yOffset),
                    strokeWidth,
                    StrokeCap.Round
                )
            }
        }
    }
}