package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselInfo
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import kotlinx.coroutines.launch
import kotlin.math.max

val LocalAvatarCarouselTokens = compositionLocalOf { AvatarCarouselTokens() }
val LocalAvatarCarouselInfo = compositionLocalOf { AvatarCarouselInfo() }

@Composable
fun getAvatarCarouselTokens(): AvatarCarouselTokens {
    return LocalAvatarCarouselTokens.current
}

@Composable
fun getAvatarCarouselInfo(): AvatarCarouselInfo {
    return LocalAvatarCarouselInfo.current
}

private fun Modifier.clickAndSemanticsModifier(
    interactionSource: MutableInteractionSource,
    onClick: () -> Unit,
    enabled: Boolean
): Modifier = composed {
    Modifier
        .clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(),
            onClickLabel = null,
            enabled = enabled,
            onClick = onClick,
            role = Role.Button
        )
}

/**
 * Generate an AvatarCarousel. This is a horizontally scrollable bar which is made up of [AvatarCarouselItem].
 * Avatar Carousel internally is a group of [AvatarCarouselItem] which can be used to create onClick based Avatar buttons.
 *
 * @param avatarList List of Avatars to be created in a carousel
 * @param size size of the carousel
 * @param modifier Optional Modifier for Avatar carousel
 * @param enablePresence enable/disable presence indicator on avatar
 * @param avatarTokens Token to provide appearance values to Avatar
 * @param avatarCarouselTokens Token to provide appearance values to Avatar Carousel
 */
@Composable
fun AvatarCarousel(
    avatarList: List<AvatarCarouselItem>,
    size: AvatarCarouselSize,
    modifier: Modifier = Modifier,
    enablePresence: Boolean = false,
    avatarTokens: AvatarTokens? = null,
    avatarCarouselTokens: AvatarCarouselTokens? = null
) {
    val token = avatarCarouselTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarCarousel] as AvatarCarouselTokens
    CompositionLocalProvider(
        LocalAvatarCarouselTokens provides token,
        LocalAvatarCarouselInfo provides AvatarCarouselInfo(size)
    ) {
        val scope = rememberCoroutineScope()
        val lazyListState = rememberLazyListState()
        val avatarSize = getAvatarCarouselTokens().getAvatarSize(getAvatarCarouselInfo())
        val textSize =
            getAvatarCarouselTokens().getTextSize(getAvatarCarouselInfo())
        val subTextSize =
            getAvatarCarouselTokens().getSubTextSize(getAvatarCarouselInfo())
        val avatarTextPadding = getAvatarCarouselTokens().padding(getAvatarCarouselInfo())
        val bottomPadding = if (size == AvatarCarouselSize.Medium) 8.dp else 0.dp

        LazyRow(state = lazyListState) {
            itemsIndexed(avatarList) { index, item ->
                val interactionSource = remember { MutableInteractionSource() }
                val backgroundColor = getColorByState(
                    stateData = getAvatarCarouselTokens().backgroundColor(getAvatarCarouselInfo()),
                    enabled = item.enabled,
                    interactionSource = interactionSource
                )
                val textColor = getAvatarCarouselTokens().getTextColor(getAvatarCarouselInfo())
                val subTextColor =
                    getAvatarCarouselTokens().getSubTextColor(getAvatarCarouselInfo())
                Column(
                    modifier
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
                        .clickAndSemanticsModifier(
                            interactionSource,
                            item.onItemClick ?: {},
                            item.enabled
                        ), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Avatar(
                        modifier = Modifier.padding(top = 8.dp),
                        person = item.person,
                        size = avatarSize,
                        avatarToken = avatarTokens,
                        enablePresence = enablePresence
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
                        Text(
                            text = item.person.firstName,
                            color = if (item.enabled) textColor.rest else textColor.disabled,
                            fontSize = textSize.fontSize.size,
                            fontWeight = textSize.weight,
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
                            Text(
                                text = item.person.lastName,
                                color = if (item.enabled) subTextColor.rest else subTextColor.disabled,
                                fontSize = subTextSize.fontSize.size,
                                fontWeight = subTextSize.weight,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }
        }
    }
}
