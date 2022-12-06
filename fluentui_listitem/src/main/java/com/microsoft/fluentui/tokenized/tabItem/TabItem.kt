package com.microsoft.fluentui.tokenized.tabItem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.TabItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabTextAlignment
import com.microsoft.fluentui.tokenized.listitem.getColorByState

val LocalTabItemTokens = compositionLocalOf { TabItemTokens() }
val LocalTabItemInfo =
    compositionLocalOf { TabItemInfo(TabTextAlignment.VERTICAL, FluentStyle.Neutral) }

@Composable
fun getTabItemTokens(): TabItemTokens {
    return LocalTabItemTokens.current
}

@Composable
fun getTabItemInfo(): TabItemInfo {
    return LocalTabItemInfo.current
}

@Composable
internal fun TabItem(
    title: String,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    style: FluentStyle = FluentStyle.Neutral,
    textAlignment: TabTextAlignment = TabTextAlignment.VERTICAL,
    enabled: Boolean = true,
    fixedWidth: Boolean = false,
    onClick: () -> Unit,
    accessory: (@Composable () -> Unit)?,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tabItemTokens: TabItemTokens? = null
) {
    val token =
        tabItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItem] as TabItemTokens

    CompositionLocalProvider(
        LocalTabItemTokens provides token,
        LocalTabItemInfo provides TabItemInfo(textAlignment, style)
    ) {
        val textColor = getColorByState(
            stateData = getTabItemTokens().textColor(tabItemInfo = getTabItemInfo()),
            enabled = enabled,
            interactionSource = interactionSource
        )
        val iconColor = getColorByState(
            stateData = getTabItemTokens().iconColor(tabItemInfo = getTabItemInfo()),
            enabled = enabled,
            interactionSource = interactionSource
        )

        val backgroundColor = getTabItemTokens().background(tabItemInfo = getTabItemInfo())
        val rippleColor = getTabItemTokens().rippleColor(tabItemInfo = getTabItemInfo())

        val clickableModifier = Modifier
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = rememberRipple(color = rippleColor),
                onClickLabel = null,
                enabled = enabled,
                onClick = onClick,
                role = Role.Tab
            )

        val widthModifier = if (fixedWidth) {
            Modifier.width(getTabItemTokens().width(tabItemInfo = getTabItemInfo()))
        } else {
            Modifier
        }
        val iconContent: @Composable () -> Unit = {
            Box(
                contentAlignment = Alignment.Center
            ) {
                if (icon != null) {
                    Icon(
                        imageVector = icon,
                        modifier = Modifier.height(if (textAlignment == TabTextAlignment.NO_TEXT) 28.dp else 24.dp),
                        contentDescription = if (textAlignment == TabTextAlignment.NO_TEXT) title else null,
                        tint = iconColor
                    )
                }
                if (accessory != null) {
                    accessory()
                }
            }
        }
        if (textAlignment == TabTextAlignment.HORIZONTAL) {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier
                    .then(clickableModifier)
                    .background(backgroundColor)
                    .padding(top = 8.dp, start = 4.dp, bottom = 4.dp, end = 8.dp)
                    .then(widthModifier)
            ) {
                iconContent()
                Spacer(modifier = Modifier.width(2.dp))
                Text(
                    text = title,
                    color = textColor,
                    textAlign = TextAlign.Justify
                )
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = modifier
                    .then(clickableModifier)
                    .background(backgroundColor)
                    .padding(top = 8.dp, start = 8.dp, bottom = 4.dp, end = 8.dp)
                    .then(widthModifier)
            ) {
                iconContent()
                if (textAlignment == TabTextAlignment.VERTICAL) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = title,
                        color = textColor,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}