package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Size16
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarType
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipInfo
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Medium
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipSize.Small
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipStyle
import com.microsoft.fluentui.theme.token.controlTokens.PersonaChipTokens

val LocalPersonaChipTokens = compositionLocalOf { PersonaChipTokens() }
val LocalPersonaChipInfo = compositionLocalOf { PersonaChipInfo() }

private fun Modifier.clickAndSemanticsModifier(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    enabled: Boolean
): Modifier = composed {
    Modifier
        .clickable(
            enabled = enabled,
            onClick = onClick,
            role = Role.Tab,
        )
}

@Composable
fun PersonChip(
    text: String,
    modifier: Modifier = Modifier,
    style: PersonaChipStyle = PersonaChipStyle.Neutral,
    size: PersonaChipSize = Medium,
    enabled: Boolean = true,
    person: Person? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    personaChipTokens: PersonaChipTokens? = null
){
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
        var isChipSelected by remember { mutableStateOf(false) }
        val backgroundColor = getColorByState(stateData = getPersonaChipTokens().backgroundColor(personaChipInfo = getPersonaChipInfo()),
            enabled = enabled, selected = isChipSelected, interactionSource = interactionSource)
        val textColor = getColorByState(stateData = getPersonaChipTokens().textColor(personaChipInfo = getPersonaChipInfo()),
            enabled = enabled, selected = isChipSelected, interactionSource = interactionSource)
        val font = getPersonaChipTokens().fontSize(personaChipInfo = getPersonaChipInfo())
        val avatarSize = getPersonaChipTokens().avatarSize(personaChipInfo = getPersonaChipInfo())
        val verticalPadding = getPersonaChipTokens().verticalPadding(personaChipInfo = getPersonaChipInfo())
        val horizontalPadding = getPersonaChipTokens().horizontalPadding(personaChipInfo = getPersonaChipInfo())
        val avatarToTextSpacing = getPersonaChipTokens().avatarToTextSpacing(personaChipInfo = getPersonaChipInfo())
        val cornerRadius = getPersonaChipTokens().borderRadius(personaChipInfo = getPersonaChipInfo())


        Box(modifier = modifier
            .clickAndSemanticsModifier(interactionSource, {isChipSelected = !isChipSelected}, enabled)
            .background(backgroundColor)
            .padding(start = horizontalPadding, end = horizontalPadding, top = verticalPadding, bottom = verticalPadding)
            .clip(RoundedCornerShape(cornerRadius)), contentAlignment = Alignment.Center){
            Row(horizontalArrangement = Arrangement.spacedBy(avatarToTextSpacing), verticalAlignment = Alignment.CenterVertically) {
                if(person != null){
                    Avatar(person = person, size = avatarSize)
                }
                Text(text = text, color = textColor, fontSize = font.fontSize.size)
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