package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.*
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens

val LocalButtonTokens = compositionLocalOf { ButtonTokens() }
val LocalButtonInfo = compositionLocalOf { ButtonInfo() }

/**
 * API to create a button, containing icon as well as text.
 *
 * @param onClick OnClick behaviour for the button
 * @param modifier Optional modifier for Button
 * @param style Style of Button. Default: [ButtonStyle.Button]
 * @param size Size of Button. Default: [ButtonSize.Medium]
 * @param enabled Boolean for enabling/disabling button. Default: [true]
 * @param interactionSource Interaction Source to handle user interactions
 * @param icon ImageVector for Icon Content on buttton. Default: [null]
 * @param text String to be displayed as text on button. Default: [null]
 * @param contentDescription Content Description for Icon. Default: [null]
 * @param buttonTokens Tokens to customize appearance of button. Default: [null]
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun Button(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: ButtonStyle = ButtonStyle.Button,
    size: ButtonSize = ButtonSize.Medium,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: ImageVector? = null,
    text: String? = null,
    contentDescription: String? = null,
    buttonTokens: ButtonTokens? = null
) {
    val token = buttonTokens ?: FluentTheme.controlTokens.tokens[ControlType.Button] as ButtonTokens

    CompositionLocalProvider(
        LocalButtonTokens provides token,
        LocalButtonInfo provides ButtonInfo(style, size)
    ) {
        val clickAndSemanticsModifier = Modifier.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(),
            enabled = enabled,
            onClickLabel = null,
            role = Role.Button,
            onClick = onClick
        )
        val backgroundColor =
            getButtonToken().backgroundColor(buttonInfo = getButtonInfo()).getColorByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )
        val contentPadding = getButtonToken().padding(getButtonInfo())
        val iconSpacing = getButtonToken().spacing(getButtonInfo())
        val shape = RoundedCornerShape(getButtonToken().cornerRadius(getButtonInfo()))
        val borders: List<BorderStroke> =
            getButtonToken().borderStroke(buttonInfo = getButtonInfo()).getBorderStrokeByState(
                enabled = enabled,
                selected = false,
                interactionSource = interactionSource
            )

        var borderModifier: Modifier = Modifier
        var borderWidth = 0.dp
        for (border in borders) {
            borderWidth += border.width
            borderModifier = borderModifier.border(borderWidth, border.brush, shape)
        }

        Box(
            modifier
                .height(getButtonToken().fixedHeight(getButtonInfo()))
                .background(
                    color = backgroundColor,
                    shape = shape
                )
                .clip(shape)
                .semantics(true) {
                    editableText = AnnotatedString(text ?: "")
                    this.contentDescription = contentDescription ?: ""
                }
                .then(clickAndSemanticsModifier)
                .then(borderModifier),
            propagateMinConstraints = true
        ) {
            Row(
                Modifier.padding(contentPadding),
                horizontalArrangement = Arrangement.spacedBy(
                    iconSpacing,
                    Alignment.CenterHorizontally
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {

                if (icon != null)
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .size(
                                getButtonToken().iconSize(buttonInfo = getButtonInfo())
                            ),
                        tint = getButtonToken().iconColor(buttonInfo = getButtonInfo())
                            .getColorByState(
                                enabled = enabled,
                                selected = false,
                                interactionSource = interactionSource
                            )
                    )

                if (text != null)
                    Text(
                        text = text,
                        modifier = Modifier.clearAndSetSemantics { },
                        color = getButtonToken().textColor(buttonInfo = getButtonInfo())
                            .getColorByState(
                                enabled = enabled,
                                selected = false,
                                interactionSource = interactionSource
                            ),
                        style = getButtonToken().typography(getButtonInfo()).merge(
                            TextStyle(
                                platformStyle = PlatformTextStyle(includeFontPadding = false)
                            )
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
            }
        }
    }
}

@Composable
fun getButtonToken(): ButtonTokens {
    return LocalButtonTokens.current
}

@Composable
fun getButtonInfo(): ButtonInfo {
    return LocalButtonInfo.current
}
