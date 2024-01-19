package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.stateDescription
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.None
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType.NoBorder
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.tokenized.listitem.ListItem
import kotlinx.coroutines.launch

/**
 * A customized  list of personas. Can be a Single or multiline Persona.
 *
 * @param personas List of [Persona]
 * @param modifier Optional modifier for List item.
 * @param border [BorderType] Optional border for the list item.
 * @param borderInset [BorderInset]Optional borderInset for list item.
 * @param enableAvatarActivityRings if avatar activity rings are enabled/disabled
 * @param enableAvatarPresence if avatar presence is enabled/disabled
 * @param textAccessibilityProperties Accessibility properties for the text in list item.
 * @param avatarTokens tokens for the avatar in [Person]
 * @param personaListTokens tokens for the persona list
 *
 */
@Composable
fun PersonaList(
    personas: List<Persona>,
    modifier: Modifier = Modifier,
    border: BorderType = NoBorder,
    borderInset: BorderInset = None,
    enableAvatarActivityRings: Boolean = false,
    enableAvatarPresence: Boolean = true,
    textAccessibilityProperties: (SemanticsPropertyReceiver.() -> Unit)? = null,
    avatarTokens: AvatarTokens? = null,
    personaListTokens: ListItemTokens? = null
) {
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val positionString: String = LocalContext.current.resources.getString(R.string.position_string)
    val statusString: String = LocalContext.current.resources.getString(R.string.status_string)
    LazyColumn(
        state = lazyListState, modifier = modifier.draggable(
            orientation = Orientation.Vertical,
            state = rememberDraggableState { delta ->
                scope.launch {
                    lazyListState.scrollBy(-delta)
                }
            },
        )
    ) {
        itemsIndexed(personas) { index, item ->
            ListItem.Item(
                text = item.title,
                modifier = Modifier
                    .clearAndSetSemantics {
                        contentDescription = "${item.person.getName()}, ${item.subTitle}" + if(enableAvatarPresence) statusString.format( item.person.status )else ""
                        stateDescription = if (personas.size > 1) positionString.format(index+1, personas.size ) else ""
                        role = Role.Button
                                          },
                subText = item.subTitle,
                secondarySubText = item.footer,
                onClick = item.onClick,
                border = border,
                borderInset = borderInset,
                listItemTokens = personaListTokens,
                enabled = item.enabled,
                leadingAccessoryContent = {
                    Avatar(
                        person = item.person,
                        size = getAvatarSize(item.subTitle, item.footer),
                        enableActivityRings = enableAvatarActivityRings,
                        enablePresence = enableAvatarPresence,
                        avatarToken = avatarTokens
                    )
                },
                trailingAccessoryContent = item.trailingIcon,
                textAccessibilityProperties = textAccessibilityProperties
            )
        }
    }
}