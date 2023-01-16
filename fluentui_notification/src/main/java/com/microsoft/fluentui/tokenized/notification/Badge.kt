package com.microsoft.fluentui.tokenized.notification

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
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

val LocalBadgeInfo = compositionLocalOf { BadgeInfo(BadgeType.Character) }
val LocalBadgeToken = compositionLocalOf { BadgeTokens() }

@Composable
fun getBadgeInfo(): BadgeInfo {
    return LocalBadgeInfo.current
}

@Composable
fun getBadgeTokens(): BadgeTokens {
    return LocalBadgeToken.current
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun Badge(
    text: String? = null,
    modifier: Modifier = Modifier,
    badgeType: BadgeType = BadgeType.List,
    badgeTokens: BadgeTokens? = null
) {
    val token = badgeTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Badge] as BadgeTokens
    CompositionLocalProvider(
        LocalBadgeToken provides token,
        LocalBadgeInfo provides BadgeInfo(badgeType)
    ) {
        val background = getBadgeTokens().backgroundColor(badgeInfo = getBadgeInfo())
        val borderStroke = getBadgeTokens().borderStroke(badgeInfo = getBadgeInfo())
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
                        color = background,
                        style = Fill,
                        radius = dpToPx(4.dp)
                    )
                }
            }
        } else {
            val textColor = getBadgeTokens().textColor(badgeInfo = getBadgeInfo())
            val typography = getBadgeTokens().typography(badgeInfo = getBadgeInfo())
            val paddingValues = getBadgeTokens().padding(badgeInfo = getBadgeInfo())

            Row(
                modifier
                    .requiredHeight(if (badgeType == BadgeType.Character) 20.dp else 27.dp)
                    .border(borderStroke.width, borderStroke.brush, CircleShape)
                    .padding(0.5.dp) //TODO to check fix for https://issuetracker.google.com/issues/228985905
                    .background(background, CircleShape)
                    .clip(RoundedCornerShape(100.dp)),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text,
                    modifier = Modifier.padding(paddingValues),
                    style = LocalTextStyle.current.merge(
                        TextStyle(
                            color = textColor,
                            lineHeight = typography.fontSize.lineHeight,
                            fontSize = typography.fontSize.size,
                            fontWeight = typography.weight,
                            platformStyle = PlatformTextStyle(
                                includeFontPadding = false
                            )
                        )
                    )
                )
            }
        }
    }
}