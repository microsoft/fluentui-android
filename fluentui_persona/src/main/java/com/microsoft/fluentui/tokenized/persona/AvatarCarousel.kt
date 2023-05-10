package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.persona.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselInfo
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.util.getStringResource
import kotlinx.coroutines.launch
import kotlin.math.max

/**
 * Generate an AvatarCarousel. This is a horizontally scrollable bar which is made up of [AvatarCarouselItem].
 * Avatar Carousel internally is a group of [AvatarCarouselItem] which can be used to create onClick based Avatar buttons.
 *
 * @param avatarList List of Avatars to be created in a carousel
 * @param modifier Optional Modifier for Avatar carousel
 * @param size size of the carousel
 * @param enablePresence enable/disable presence indicator on avatar
 * @param avatarTokens Token to provide appearance values to Avatar
 * @param avatarCarouselTokens Token to provide appearance values to Avatar Carousel
 */
@Composable
fun AvatarCarousel(
    avatarList: List<AvatarCarouselItem>,
    modifier: Modifier = Modifier,
    size: AvatarCarouselSize = AvatarCarouselSize.Small,
    enablePresence: Boolean = false,
    avatarTokens: AvatarTokens? = null,
    avatarCarouselTokens: AvatarCarouselTokens? = null
) {
    val token = avatarCarouselTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarCarousel] as AvatarCarouselTokens
    val avatarCarouselInfo = AvatarCarouselInfo(size)
    val statusString = getStringResource(R.string.Status)
    val outOfOfficeString = getStringResource(R.string.Out_Of_Office)
    val activeString = getStringResource(R.string.Active)
    val inActiveString = getStringResource(R.string.Inactive)
    val scope = rememberCoroutineScope()
    val lazyListState = rememberLazyListState()
    val avatarSize = token.avatarSize(avatarCarouselInfo)
    val textStyle =
        token.textTypography(avatarCarouselInfo)
    val subTextStyle =
        token.subTextTypography(avatarCarouselInfo)
    val avatarTextPadding = token.padding(avatarCarouselInfo)
    val bottomPadding = if (size == AvatarCarouselSize.Small) 8.dp else 0.dp
    val textColor = token.textColor(avatarCarouselInfo)
    val subTextColor =
        token.subTextColor(avatarCarouselInfo)


    LazyRow(
        state = lazyListState,
        modifier = modifier.draggable(
            orientation = Orientation.Horizontal,
            state = rememberDraggableState { delta ->
                scope.launch {
                    lazyListState.scrollBy(-delta)
                }
            },
        )
    ) {
        itemsIndexed(avatarList) { index, item ->
            val backgroundColor =
                token.backgroundBrush(avatarCarouselInfo)
                    .getBrushByState(
                        enabled = item.enabled,
                        selected = false,
                        interactionSource = remember { MutableInteractionSource() }
                    )
            val nameString =
                if (size == AvatarCarouselSize.Large) "${item.person.getName()}. " else "${item.person.firstName}. "
            Column(
                Modifier
                    .onFocusEvent { focusState ->
                        if (focusState.isFocused) {
                            scope.launch {
                                lazyListState.animateScrollToItem(
                                    max(
                                        0,
                                        index - 2
                                    )
                                )
                            }
                        }
                    }
                    .background(backgroundColor)
                    .requiredWidth(88.dp)
                    .alpha(if (item.enabled) 1f else 0.7f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        onClickLabel = null,
                        enabled = item.enabled,
                        onClick = item.onItemClick ?: {},
                        role = Role.Button
                    )
                    .testTag("item $index")
                    .clearAndSetSemantics {
                        contentDescription =
                            nameString + "${if (enablePresence) "${statusString}, ${item.person.status}," else ""} " +
                                    "${if (enablePresence && item.person.isOOO) "${outOfOfficeString}," else ""} " +
                                    if (item.enableActivityRing) {
                                        if (item.person.isActive) activeString else inActiveString
                                    } else ""
                    },
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Avatar(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    person = item.person,
                    size = avatarSize,
                    avatarToken = avatarTokens,
                    enablePresence = enablePresence,
                    enableActivityRings = item.enableActivityRing
                )
                Row(
                    Modifier
                        .padding(
                            start = 2.dp,
                            end = 2.dp,
                            top = avatarTextPadding,
                            bottom = bottomPadding
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    BasicText(
                        modifier = Modifier.clearAndSetSemantics { },
                        text = item.person.firstName,
                        style = textStyle.merge(
                            TextStyle(
                                color = if (item.enabled) textColor.rest else textColor.disabled
                            )
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                if (size == AvatarCarouselSize.Large) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(start = 2.dp, end = 2.dp, bottom = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        BasicText(
                            modifier = Modifier.clearAndSetSemantics { },
                            text = item.person.lastName,
                            style = subTextStyle.merge(
                                TextStyle(
                                    color = if (item.enabled) subTextColor.rest else subTextColor.disabled
                                )
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}