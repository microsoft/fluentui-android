package com.microsoft.fluentui.materialized.controls

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens


@Composable
fun ButtonV3(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ButtonStyle = ButtonStyle.Button,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    text: String? = null,
    contentDescription: String? = null,
    buttonTokens: ButtonTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = buttonTokens
        ?: FluentTheme.controlTokens.tokens[ControlType.ButtonControlType] as ButtonTokens
    val buttonInfo = ButtonInfo(style, size)
//    val shape = token.shape(buttonInfo)
    val borderStroke = token.borderStroke(buttonInfo).getBorderStrokeByState(
        enabled = enabled,
        selected = false,
        interactionSource = interactionSource
    )
    val backgroundColor = token.backgroundColor(buttonInfo).getColorByState(
        enabled = enabled,
        selected = false,
        interactionSource = interactionSource
    )
    val contentColor = token.textColor(buttonInfo).getColorByState(
        enabled = enabled,
        selected = false,
        interactionSource = interactionSource
    )
    val contentPadding = token.padding(buttonInfo)

    if (style == ButtonStyle.Button) {
        Button(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            interactionSource = interactionSource,
//            shape = shape,
            border = borderStroke.get(0),
            colors = ButtonDefaults.buttonColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            ),
            contentPadding = contentPadding,
            content = {
                ButtonContent(
                    icon = icon,
                    trailingIcon = trailingIcon,
                    text = text,
                    buttonTokens = token,
                    buttonInfo = buttonInfo,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    contentDescription = contentDescription
                )
            }
        )
    } else if (style == ButtonStyle.OutlinedButton) {
        OutlinedButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            interactionSource = interactionSource,
//            shape = shape,
            border = borderStroke.get(0),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = contentColor
            ),
            contentPadding = contentPadding,
            content = {
                ButtonContent(
                    icon = icon,
                    trailingIcon = trailingIcon,
                    text = text,
                    buttonTokens = token,
                    buttonInfo = buttonInfo,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    contentDescription = contentDescription
                )
            }
        )
    } else if (style == ButtonStyle.TextButton) {
        TextButton(
            onClick = onClick,
            modifier = modifier,
            enabled = enabled,
            interactionSource = interactionSource,
//            shape = shape,
            border = borderStroke.get(0),
            colors = ButtonDefaults.textButtonColors(
                containerColor = backgroundColor,
                contentColor = contentColor
            ),
            contentPadding = contentPadding,
            content = {
                ButtonContent(
                    icon = icon,
                    trailingIcon = trailingIcon,
                    text = text,
                    buttonTokens = token,
                    buttonInfo = buttonInfo,
                    enabled = enabled,
                    interactionSource = interactionSource,
                    contentDescription = contentDescription
                )
            }
        )
    }
}


@Composable
fun ButtonContent(
    icon: ImageVector?,
    trailingIcon: ImageVector?,
    text: String?,
    buttonTokens: ButtonTokens,
    buttonInfo: ButtonInfo,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource,
    contentDescription: String? = null
) {
    val iconSpacing = buttonTokens.spacing(buttonInfo)
    Row(
        horizontalArrangement = Arrangement.spacedBy(
            iconSpacing,
            Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (icon != null)
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(
                        buttonTokens.iconSize(buttonInfo = buttonInfo)
                    ),
                tint = buttonTokens.iconColor(buttonInfo = buttonInfo)
                    .getColorByState(
                        enabled = enabled,
                        selected = false,
                        interactionSource = interactionSource
                    )
            )

        if (text != null)
            BasicText(
                text = text,
                modifier = Modifier
                    .weight(1f, fill = false)
                    .clearAndSetSemantics { },
                style = buttonTokens.typography(buttonInfo).merge(
                    TextStyle(
                        color = buttonTokens.textColor(buttonInfo = buttonInfo)
                            .getColorByState(
                                enabled = enabled,
                                selected = false,
                                interactionSource = interactionSource
                            )
                    )
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

        if (trailingIcon != null) {
            Icon(
                imageVector = trailingIcon,
                contentDescription = contentDescription,
                modifier = Modifier
                    .size(
                        buttonTokens.iconSize(buttonInfo = buttonInfo)
                    ),
                tint = buttonTokens.iconColor(buttonInfo = buttonInfo)
                    .getColorByState(
                        enabled = enabled,
                        selected = false,
                        interactionSource = interactionSource
                    )
            )
        }
    }
}
