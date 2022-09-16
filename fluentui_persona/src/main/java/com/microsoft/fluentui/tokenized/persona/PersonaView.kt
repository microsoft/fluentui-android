package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem

@Composable
fun PersonaView(person: Person,
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String? = null,
    tertiaryText: String? = null,
    onClick: (() -> Unit)? = null,
    border: BorderType = NoBorder,
    borderInset: BorderInset = None,
    enableAvatarActivityRings: Boolean = false,
    enableAvatarPresence: Boolean = true,
    avatarTokens: AvatarTokens? = null,
    personaViewTokens: ListItemTokens? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

    var avatarSize = if (secondaryText != null || tertiaryText != null) AvatarSize.Large else  AvatarSize.Small
    Box{
        ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, modifier = modifier, onClick = onClick, border = border, borderInset = borderInset, listItemTokens = personaViewTokens, leadingAccessoryView = { Avatar(
            person = person,
            modifier = modifier,
            size = avatarSize,
            enableActivityRings = enableAvatarActivityRings,
            enablePresence = enableAvatarPresence,
            avatarToken = avatarTokens
        )}, interactionSource = interactionSource)
    }
}