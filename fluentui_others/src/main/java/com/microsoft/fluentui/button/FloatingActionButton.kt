package com.microsoft.fluentui.button

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.FABInfo
import com.microsoft.fluentui.theme.token.controlTokens.FABSize
import com.microsoft.fluentui.theme.token.controlTokens.FABState
import com.microsoft.fluentui.theme.token.controlTokens.FABTokens

val LocalFABTokens = compositionLocalOf { FABTokens() }
val LocalFABInfo = compositionLocalOf { FABInfo() }

@Composable
fun FloatingActionButton(onClick: () -> Unit,
                         modifier: Modifier = Modifier,
                         state: FABState = FABState.Expanded,
                         size: FABSize = FABSize.Large,
                         expanded: Boolean = true,
                         enabled: Boolean = true,
                         fabTokens: FABTokens? = null,
                         interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
                         icon: ImageVector? = null,
                         text: String? = null
) {
    val token = remember {
        (fabTokens ?: FluentTheme.tokens[ControlType.FloatingActionButton] as FABTokens)
    }

    CompositionLocalProvider(
            LocalFABTokens provides token,
            LocalFABInfo provides FABInfo(state, size)
    ) {
        val clickAndSemanticsModifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                enabled = enabled,
                onClickLabel = null,
                role = Role.Button,
                onClick = onClick
        )
        val fabExpandedState: Boolean = (text != null && expanded)
        val backgroundColor = backgroundColor(getFABToken(), getFABInfo(), enabled, interactionSource)
        val contentPadding = if (fabExpandedState) getFABToken().textPadding(getFABInfo())
        else getFABToken().iconPadding(getFABInfo())
        val iconSpacing = if (fabExpandedState) getFABToken().spacing(getFABInfo()) else 0.dp
        val shape = CircleShape
        val borders: List<BorderStroke> = borderStroke(getFABToken(), getFABInfo(), enabled, interactionSource)

        var borderModifier: Modifier = Modifier
        var borderWidth = 0.dp
        for (border in borders) {
            borderWidth += border.width
            borderModifier = borderModifier.border(borderWidth, border.brush, shape)
        }

        Box(
                modifier
                        .height(getFABToken().fixedHeight(getFABInfo()))
                        .defaultMinSize(minWidth = getFABToken().minWidth(getFABInfo()))
                        .shadow(
                                elevation = elevation(
                                        enabled = enabled,
                                        interactionSource = interactionSource),
                                shape = CircleShape
                        )
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
                                    getFABToken().iconSize(getFABInfo()).size),
                            tint = iconColor(getFABToken(), getFABInfo(), enabled, interactionSource)
                    )

                AnimatedVisibility(fabExpandedState) {
                    Text(text = text!!,
                            fontSize = getFABToken().fontInfo(getFABInfo()).fontSize.size,
                            lineHeight = getFABToken().fontInfo(getFABInfo()).fontSize.lineHeight,
                            fontWeight = getFABToken().fontInfo(getFABInfo()).weight,
                            color = textColor(getFABToken(), getFABInfo(), enabled, interactionSource),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}


@Composable
fun getFABToken(): FABTokens {
    return LocalFABTokens.current
}

@Composable
fun getFABInfo(): FABInfo {
    return LocalFABInfo.current
}

@Composable
fun elevation(enabled: Boolean, interactionSource: InteractionSource): Dp {
    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return getFABToken().elevation(getFABInfo()).pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return getFABToken().elevation(getFABInfo()).focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return getFABToken().elevation(getFABInfo()).focused

        return getFABToken().elevation(getFABInfo()).rest
    } else {
        return getFABToken().elevation(getFABInfo()).disabled
    }
}
