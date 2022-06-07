package com.microsoft.fluentui.button

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens

val LocalButtonTokens = compositionLocalOf { ButtonTokens() }
val LocalButtonInfo = compositionLocalOf { ButtonInfo() }

@Composable
fun Button(
        onClick: () -> Unit,
        modifier: Modifier = Modifier,
        style: ButtonStyle = ButtonStyle.Button,
        size: ButtonSize = ButtonSize.Medium,
        enabled: Boolean = true,
        buttonTokens: ButtonTokens? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
        icon: ImageVector? = null,
        text: String? = null
) {
    val token = remember {
        (buttonTokens ?: FluentTheme.tokens[ControlType.Button] as ButtonTokens)
    }

    CompositionLocalProvider(
            LocalButtonTokens provides token,
            LocalButtonInfo provides ButtonInfo(style, size)
    ) {
        val clickAndSemanticsModifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = enabled,
                onClickLabel = null,
                role = Role.Button,
                onClick = onClick
        )
        val backgroundColor = backgroundColor(getButtonToken(), getButtonInfo(), enabled, interactionSource)
        val contentPadding = getButtonToken().padding(getButtonInfo())
        val iconSpacing = getButtonToken().spacing(getButtonInfo())
        val shape = RoundedCornerShape(getButtonToken().borderRadius(getButtonInfo()))
        val borders: List<BorderStroke> = borderStroke(getButtonToken(), getButtonInfo(), enabled, interactionSource)

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
                        .then(borderModifier)
                        .then(clickAndSemanticsModifier),
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
                    Icon(imageVector = icon,
                            contentDescription = text,
                            modifier = Modifier.size(
                                    getButtonToken().iconSize(buttonInfo = getButtonInfo()).size),
                            tint = iconColor(getButtonToken(), getButtonInfo(), enabled, interactionSource)
                    )

                if (text != null)
                    Text(text = text,
                            fontSize = getButtonToken().fontInfo(getButtonInfo()).fontSize.size,
                            lineHeight = getButtonToken().fontInfo(getButtonInfo()).fontSize.lineHeight,
                            fontWeight = getButtonToken().fontInfo(getButtonInfo()).weight,
                            color = textColor(getButtonToken(), getButtonInfo(), enabled, interactionSource),
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
