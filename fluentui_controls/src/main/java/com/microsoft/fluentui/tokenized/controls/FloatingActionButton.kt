package com.microsoft.fluentui.tokenized.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.FABInfo
import com.microsoft.fluentui.theme.token.controlTokens.FABSize
import com.microsoft.fluentui.theme.token.controlTokens.FABState
import com.microsoft.fluentui.theme.token.controlTokens.FABTokens

/**
 * API to create a Floating Action Button. This button has elevation and can be expanded and collapsed.
 * In expanded state, Icon + Text are displayed. In collapsed state, only icon is displayed.
 *
 * @param onClick OnClick behaviour for the button
 * @param modifier Optional Modifier for FAB
 * @param state State the FAB is supposed to be in. Default: [FABState.Expanded]
 * @param size Size of the FAB. Default: [FABSize.Large]
 * @param enabled Boolean for enabling/disabling FAB. Default: [true]
 * @param interactionSource Interaction Source for user interactions.
 * @param icon ImageVector for Icon to be displayed. Default: [null]
 * @param text String to be displayed. Default: [null]
 * @param fabTokens Tokens to customize design of FAB. Default: [null]
 */
@Composable
fun FloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    state: FABState = FABState.Expanded,
    size: FABSize = FABSize.Large,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    icon: ImageVector? = null,
    text: String? = null,
    fabTokens: FABTokens? = null
) {
    if (icon == null && (text == null || text == ""))
        return

    val token = fabTokens
        ?: FluentTheme.controlTokens.tokens[ControlType.FloatingActionButton] as FABTokens

    val fabInfo = FABInfo(state, size)
    val clickAndSemanticsModifier = Modifier.clickable(
        interactionSource = interactionSource,
        indication = LocalIndication.current,
        enabled = enabled,
        onClickLabel = null,
        role = Role.Button,
        onClick = onClick
    )
    val isFabExpanded: Boolean =
        (text != null && text != "" && fabInfo.state == FABState.Expanded)
    val backgroundColor = token.backgroundColor(fabInfo = fabInfo).getColorByState(
        enabled = enabled,
        selected = false,
        interactionSource = interactionSource
    )
    val contentPadding = if (isFabExpanded) token.textPadding(fabInfo)
    else token.iconPadding(fabInfo)
    val iconSpacing = if (isFabExpanded) token.spacing(fabInfo) else 0.dp
    val shape = CircleShape
    val borders: List<BorderStroke> =
        token.borderStroke(fabInfo = fabInfo).getBorderStrokeByState(
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
            .height(token.fixedHeight(fabInfo))
            .defaultMinSize(minWidth = token.minWidth(fabInfo))
            .shadow(
                elevation = token
                    .elevation(fabInfo = fabInfo)
                    .getElevationByState(
                        enabled = enabled,
                        selected = false,
                        interactionSource = interactionSource
                    ),
                shape = CircleShape
            )
            .background(
                color = backgroundColor,
                shape = shape
            )
            .clip(shape)
            .semantics(mergeDescendants = true) { contentDescription = text ?: "" }
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
                    contentDescription = text,
                    modifier = Modifier
                        .size(
                            token.iconSize(fabInfo)
                        )
                        .clearAndSetSemantics { },
                    tint = token.iconColor(fabInfo = fabInfo).getColorByState(
                        enabled = enabled,
                        selected = false,
                        interactionSource = interactionSource
                    )
                )

            AnimatedVisibility(isFabExpanded) {
                Text(
                    text = text!!,
                    modifier = Modifier.clearAndSetSemantics { },
                    style = token.typography(fabInfo),
                    color = token.textColor(fabInfo = fabInfo).getColorByState(
                        enabled = enabled,
                        selected = false,
                        interactionSource = interactionSource
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
