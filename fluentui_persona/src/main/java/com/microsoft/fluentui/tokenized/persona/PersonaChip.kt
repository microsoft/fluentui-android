package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipInfo
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Medium
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipTokens

/**
 * [PersonaChip] is a compact representations of entities(most commonly, people)that can be types in, deleted or dragged easily
 *
 * @param person Person data for the persona chip
 * @param modifier Modifier for the persona chip
 * @param style Optional persona chip style. See [PersonaChipStyle]
 * @param size Option persona chip size. See [PersonaChipSize]
 * @param enabled Whether persona chip is enabled or disabled. Enabled by default.
 * @param selected Whether persona chip is selected or unselected. Unselected by default.
 * @param onClick onClick action for persona chip
 * @param onCloseClick onClick action for close button. This action is performed after the chip is selected and on the close icon
 * @param interactionSource Optional interactionSource
 * @param personaChipTokens Optional tokens for persona chip
 */
@Composable
fun PersonaChip(
    person: Person,
    modifier: Modifier = Modifier,
    style: PersonaChipStyle = PersonaChipStyle.Neutral,
    size: PersonaChipSize = Medium,
    enabled: Boolean = true,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    personaChipTokens: PersonaChipTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = personaChipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PersonaChipControlType] as PersonaChipTokens
    val personaChipInfo = PersonaChipInfo(
        style,
        enabled,
        size
    )
    val backgroundColor =
        token.backgroundBrush(personaChipInfo = personaChipInfo)
            .getBrushByState(
                enabled = enabled, selected = selected, interactionSource = interactionSource
            )
    val textColor = token.textColor(personaChipInfo = personaChipInfo)
        .getColorByState(
            enabled = enabled, selected = selected, interactionSource = interactionSource
        )
    val typography = token.typography(personaChipInfo = personaChipInfo)
    val avatarSize = token.avatarSize(personaChipInfo = personaChipInfo)
    val verticalPadding =
        token.verticalPadding(personaChipInfo = personaChipInfo)
    val horizontalPadding =
        token.horizontalPadding(personaChipInfo = personaChipInfo)
    val avatarToTextSpacing =
        token.avatarToTextSpacing(personaChipInfo = personaChipInfo)
    val cornerRadius =
        token.cornerRadius(personaChipInfo = personaChipInfo)
    val maxHeight =
        token.maxHeight(personaChipInfo = personaChipInfo)
    val selectedString = if (selected)
        LocalContext.current.resources.getString(R.string.fluentui_selected)
    else
        LocalContext.current.resources.getString(R.string.fluentui_not_selected)

    val enabledString = if (enabled)
        LocalContext.current.resources.getString(R.string.fluentui_enabled)
    else
        LocalContext.current.resources.getString(R.string.fluentui_disabled)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .heightIn(max = maxHeight)
            .clickable(
                enabled = enabled,
                onClick = onClick ?: {},
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
            .then(if (onCloseClick != null && selected) Modifier else Modifier.clearAndSetSemantics {
                this.contentDescription = "${person.getLabel()} $selectedString $enabledString"
            })
    )
    {
        Row(
            Modifier
                .padding(
                    horizontal = horizontalPadding,
                    vertical = verticalPadding
                ),
            horizontalArrangement = Arrangement.spacedBy(avatarToTextSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (size == Medium) {
                if (onCloseClick != null && selected) {
                    Icon(
                        Icons.Filled.Close,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable(
                                enabled = true,
                                onClick = onCloseClick,
                                role = Role.Button
                            ),
                        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_close),
                        tint = textColor
                    )
                } else {
                    Avatar(
                        modifier = Modifier.clearAndSetSemantics { },
                        person = person,
                        size = avatarSize
                    )
                }
            }
            BasicText(
                text = person.getLabel(),
                style = typography.merge(
                    TextStyle(color = textColor)
                )
            )
        }
    }
}