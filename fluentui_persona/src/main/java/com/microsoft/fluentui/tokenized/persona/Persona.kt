package com.microsoft.fluentui.tokenized.persona

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem

/**
 * A customized  list item. Can be a Single or multiline Avatar item. Size of the persona is based on the texts provided
 *
 * @param person person details like avatar
 * @param modifier Optional modifier for List item.
 * @param primaryText Primary text.
 * @param secondaryText Optional secondaryText or a subtitle.
 * @param tertiaryText Optional tertiary text or a footer.
 * @param onClick Optional onClick action for list item.
 * @param border [BorderType] Optional border for the list item.
 * @param borderInset [BorderInset]Optional borderInset for list item.
 * @param enabled Optional enable/disable List item
 * @param enableAvatarActivityRings if avatar activity rings are enabled/disabled
 * @param enableAvatarPresence if avatar presence is enabled/disabled
 * @param avatarTokens tokens for the avatar in [Person]
 * @param personaTokens tokens for the persona
 *
 */
@Composable
fun Persona(
    person: Person,
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String? = null,
    tertiaryText: String? = null,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    border: BorderType = NoBorder,
    borderInset: BorderInset = None,
    enabled: Boolean = true,
    enableAvatarActivityRings: Boolean = false,
    enableAvatarPresence: Boolean = true,
    avatarTokens: AvatarTokens? = null,
    personaTokens: ListItemTokens? = null
) {

    var avatarSize = getAvatarSize(secondaryText, tertiaryText)
    ListItem.Item(
        text = primaryText,
        subText = secondaryText,
        secondarySubText = tertiaryText,
        onClick = onClick,
        onLongClick = onLongClick,
        modifier = modifier,
        border = border,
        borderInset = borderInset,
        listItemTokens = personaTokens,
        enabled = enabled,
        leadingAccessoryContent = {
            Avatar(
                person = person,
                size = avatarSize,
                enableActivityRings = enableAvatarActivityRings,
                enablePresence = enableAvatarPresence,
                avatarToken = avatarTokens
            )
        })
}