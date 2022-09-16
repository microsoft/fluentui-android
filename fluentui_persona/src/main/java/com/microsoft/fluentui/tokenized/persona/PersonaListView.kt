package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens

@Composable
fun PersonaListView(persons: List<Person>,
    onItemClick: (() -> Unit)? = null,
    itemBorder: BorderType = NoBorder,
    itemBorderInset: BorderInset = None,
    enableAvatarActivityRings: Boolean = false,
    enableAvatarPresence: Boolean = false,
    avatarTokens: AvatarTokens? = null,
    personaListViewTokens: ListItemTokens? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
){
    Box{
        LazyColumn{
            itemsIndexed(persons) { _, item ->
                PersonaView(person = item, primaryText = "${item.firstName} ${item.lastName}", secondaryText = item.email, onClick = onItemClick, border = itemBorder, borderInset = itemBorderInset, avatarTokens = avatarTokens, personaViewTokens = personaListViewTokens, enableAvatarActivityRings = enableAvatarActivityRings, enableAvatarPresence = enableAvatarPresence, interactionSource = interactionSource)
            }
        }
    }
}