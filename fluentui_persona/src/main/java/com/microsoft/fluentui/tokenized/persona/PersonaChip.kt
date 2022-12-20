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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipInfo
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Medium
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipTokens

val LocalPersonaChipTokens = compositionLocalOf { PersonaChipTokens() }
val LocalPersonaChipInfo = compositionLocalOf { PersonaChipInfo() }

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
 * @param onCloseClick onClick action for close button. This should be used when showCloseButton is enabled
 * @param showCloseButton Boolean value to show/hide close icon.
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
    showCloseButton: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    personaChipTokens: PersonaChipTokens? = null
) {
    val token = personaChipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PersonaChip] as PersonaChipTokens
    CompositionLocalProvider(
        LocalPersonaChipTokens provides token,
        LocalPersonaChipInfo provides PersonaChipInfo(
            style,
            enabled,
            size
        )
    ) {
        val backgroundColor = getColorByState(
            stateData = getPersonaChipTokens().backgroundColor(personaChipInfo = getPersonaChipInfo()),
            enabled = enabled, selected = selected, interactionSource = interactionSource
        )
        val textColor = getColorByState(
            stateData = getPersonaChipTokens().textColor(personaChipInfo = getPersonaChipInfo()),
            enabled = enabled, selected = selected, interactionSource = interactionSource
        )
        val font = getPersonaChipTokens().fontSize(personaChipInfo = getPersonaChipInfo())
        val avatarSize = getPersonaChipTokens().avatarSize(personaChipInfo = getPersonaChipInfo())
        val verticalPadding =
            getPersonaChipTokens().verticalPadding(personaChipInfo = getPersonaChipInfo())
        val horizontalPadding =
            getPersonaChipTokens().horizontalPadding(personaChipInfo = getPersonaChipInfo())
        val avatarToTextSpacing =
            getPersonaChipTokens().avatarToTextSpacing(personaChipInfo = getPersonaChipInfo())
        val cornerRadius =
            getPersonaChipTokens().borderRadius(personaChipInfo = getPersonaChipInfo())

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
                modifier
                    .padding(
                        horizontal = horizontalPadding,
                        vertical = verticalPadding
                    )
                    .clip(RoundedCornerShape(cornerRadius))
                    .background(backgroundColor),
                horizontalArrangement = Arrangement.spacedBy(avatarToTextSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showCloseButton && size == Medium && selected) {
                    Icon(
                        Icons.Filled.Close,
                        modifier = Modifier
                            .size(16.dp)
                            .clickable(
                                enabled = true,
                                onClick = onCloseClick ?: {},
                                role = Role.Button
                            ),
                        contentDescription = "Close",
                        tint = textColor
                    )

                } else {
                    if (size == Medium) {
                        Avatar(person = person, size = avatarSize)
                    }
                }
                Text(
                    text = person.getLabel(),
                    color = textColor,
                    fontSize = font.fontSize.size,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun getPersonaChipTokens(): PersonaChipTokens {
    return LocalPersonaChipTokens.current
}

@Composable
fun getPersonaChipInfo(): PersonaChipInfo {
    return LocalPersonaChipInfo.current
}