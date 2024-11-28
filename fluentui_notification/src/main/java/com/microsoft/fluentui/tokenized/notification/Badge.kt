package com.microsoft.fluentui.tokenized.notification

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BadgeInfo
import com.microsoft.fluentui.theme.token.controlTokens.BadgeTokens
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.util.dpToPx

/**
 * Badge represents dynamic information such as a number of pending requests on tab.
 * @param text Text to display, if not provided then it appear as dot.
 * @param modifier the [Modifier] to be applied to this item
 * @param badgeType Badge type could be [BadgeType.Character] to use it on tab or [BadgeType.List] to use in List.
 * @param badgeTokens provide appearance values. If not provided then tokens will be picked from AppThemeController
 *
 */

@OptIn(ExperimentalTextApi::class)
@Composable
fun Badge(
    modifier: Modifier = Modifier,
    text: String? = null,
    badgeType: BadgeType = BadgeType.List,
    badgeTokens: BadgeTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = badgeTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BadgeControlType] as BadgeTokens
    val badgeInfo = BadgeInfo(badgeType)
    val background = token.backgroundBrush(badgeInfo = badgeInfo)
    val borderStroke = token.borderStroke(badgeInfo = badgeInfo)
    if (text.isNullOrEmpty()) {
        Box(
            modifier = modifier
                .defaultMinSize(16.dp, 16.dp)
        ) {
            Canvas(
                modifier = Modifier
                    .padding(start = 5.dp, end = 3.dp, top = 3.dp, bottom = 5.dp)
                    .sizeIn(minWidth = 8.dp, minHeight = 8.dp)
            ) {
                drawCircle(
                    brush = borderStroke.brush,
                    radius = dpToPx(borderStroke.width + 4.dp)
                )
                drawCircle(
                    brush = background,
                    style = Fill,
                    radius = dpToPx(4.dp)
                )
            }
        }
    } else {
        val textColor = token.textColor(badgeInfo = badgeInfo)
        var typography = token.typography(badgeInfo = badgeInfo)
        val fontSize = remember { mutableStateOf(typography.fontSize) }
        var textStyle by remember(textColor) {
            mutableStateOf(
                typography.merge(TextStyle(color = textColor, fontSize = fontSize.value,  platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )))
            )
        }
        val paddingValues = token.padding(badgeInfo = badgeInfo)
        val shape = RoundedCornerShape(token.cornerRadius(badgeInfo = badgeInfo))

        Row(
            modifier
                .requiredHeight(if (badgeType == BadgeType.Character) 20.dp else 27.dp)
                .border(borderStroke.width, borderStroke.brush, shape)
                .padding(0.5.dp) //TODO to check fix for https://issuetracker.google.com/issues/228985905
                .background(background, shape)
                .clip(shape),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            BasicText(
                text,
                modifier = Modifier.padding(paddingValues),
                style = textStyle,
                onTextLayout = { textLayoutResult ->
                    if (textLayoutResult.didOverflowHeight) {
                        textStyle.fontSize
                        fontSize.value *= 0.9
                        textStyle = textStyle.copy(fontSize = fontSize.value)
                    }
                }
            )
        }
    }
}