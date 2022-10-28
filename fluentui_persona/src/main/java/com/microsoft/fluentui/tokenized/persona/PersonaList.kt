package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem

@Composable
fun PersonaList(
    personas: List<Persona>,
    modifier: Modifier = Modifier,
    border: BorderType = NoBorder,
    borderInset: BorderInset = None,
    enableAvatarActivityRings: Boolean = false,
    enableAvatarPresence: Boolean = true,
    avatarTokens: AvatarTokens? = null,
    personaListTokens: ListItemTokens? = null
) {
    LazyColumn {
        items(personas) { item ->
            ListItem.Item(
                text = item.title,
                subText = item.subTitle,
                secondarySubText = item.footer,
                modifier = modifier,
                onClick = item.onClick,
                border = border,
                borderInset = borderInset,
                listItemTokens = personaListTokens,
                leadingAccessoryView = {
                    Avatar(
                        person = item.person,
                        size = getAvatarSize(item.subTitle, item.footer),
                        enableActivityRings = enableAvatarActivityRings,
                        enablePresence = enableAvatarPresence,
                        avatarToken = avatarTokens
                    )
                },
                trailingAccessoryView = item.trailingIcon
            )
        }
    }
}