package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarPersonaChipInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarPersonaChipTokens

/**
 * [SearchBarPersonaChip] is a compact representations of entities(most commonly, people)that can be types in, deleted or dragged easily
 *
 * @param person Person data for the persona chip
 * @param modifier Modifier for the persona chip
 * @param style Optional persona chip style. See [FluentStyle]
 * @param size Option persona chip size. See [PersonaChipSize]
 * @param enabled Whether persona chip is enabled or disabled. Enabled by default.
 * @param selected Whether persona chip is selected or unselected. Unselected by default.
 * @param onClick onClick action for persona chip
 * @param onCloseClick onClick action for close button. This action is performed after the chip is selected and on the close icon
 * @param interactionSource Optional interactionSource
 * @param searchbarPersonaChipTokens Optional tokens for persona chip
 */
@Composable
fun SearchBarPersonaChip(
    person: Person,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    size: PersonaChipSize = PersonaChipSize.Medium,
    enabled: Boolean = true,
    selected: Boolean = false,
    onClick: (() -> Unit)? = null,
    onCloseClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    searchbarPersonaChipTokens: SearchBarPersonaChipTokens? = null
) {
    val token = searchbarPersonaChipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SearchBarPersonaChip] as SearchBarPersonaChipTokens
    val searchBarPersonaChipInfo = SearchBarPersonaChipInfo(
        style,
        enabled,
        size = size
    )
    val backgroundColor =
        token.backgroundColor(searchBarPersonaChipInfo = searchBarPersonaChipInfo)
            .getColorByState(
                enabled = enabled, selected = selected, interactionSource = interactionSource
            )
    val textColor =
        token.textColor(searchBarPersonaChipInfo = searchBarPersonaChipInfo)
            .getColorByState(
                enabled = enabled, selected = selected, interactionSource = interactionSource
            )
    val fontStyle =
        token.typography(personaChipInfo = searchBarPersonaChipInfo)
    val avatarSize =
        token.avatarSize(personaChipInfo = searchBarPersonaChipInfo)
    val verticalPadding =
        token.verticalPadding(personaChipInfo = searchBarPersonaChipInfo)
    val horizontalPadding =
        token.horizontalPadding(personaChipInfo = searchBarPersonaChipInfo)
    val avatarToTextSpacing =
        token.avatarToTextSpacing(personaChipInfo = searchBarPersonaChipInfo)
    val cornerRadius =
        token.cornerRadius(personaChipInfo = searchBarPersonaChipInfo)

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .clickable(
                enabled = enabled,
                onClick = onClick ?: {},
                interactionSource = interactionSource,
                indication = rememberRipple()
            )
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
            if (size == PersonaChipSize.Medium) {
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
                    Avatar(person = person, size = avatarSize)
                }
            }
            Text(
                modifier = Modifier.padding(bottom = 2.dp),//Vertically center align text
                text = person.getLabel(),
                color = textColor,
                style = fontStyle
            )
        }
    }
}