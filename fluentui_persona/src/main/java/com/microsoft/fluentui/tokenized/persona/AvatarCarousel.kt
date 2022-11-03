package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Text
import androidx.compose.material.contentColorFor
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarCarouselTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.TextType

val LocalAvatarCarouselTokens = compositionLocalOf { AvatarCarouselTokens() }

@Composable
fun getAvatarCarouselTokens(): AvatarCarouselTokens {
    return LocalAvatarCarouselTokens.current
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
            role = Role.Tab
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
    avatarCarouselTokens: AvatarCarouselTokens? = null,
) {
    val token = avatarCarouselTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarCarousel] as AvatarCarouselTokens
    CompositionLocalProvider(LocalAvatarCarouselTokens provides token) {

        val avatarSize = getAvatarCarouselTokens().getAvatarSize(carouselSize = size)
        val textSize =
            getAvatarCarouselTokens().getTextSize(textType = TextType.Text, carouselSize = size)
        val subTextSize =
            getAvatarCarouselTokens().getTextSize(textType = TextType.SubText, carouselSize = size)
        val avatarTextPadding = getAvatarCarouselTokens().padding(size = size)

        LazyRow{
            items(avatarList) { item ->
                val interactionSource = remember { MutableInteractionSource() }
                val backgroundColor = getColorByState(
                    stateData = getAvatarCarouselTokens().backgroundColor(),
                    enabled = item.enabled,
                    interactionSource = interactionSource
                )

                val textColor = getAvatarCarouselTokens().getTextColor(
                    textType = TextType.Text,
                    enabled = item.enabled
                )
                val subTextColor = getAvatarCarouselTokens().getTextColor(
                    textType = TextType.SubText,
                    enabled = item.enabled
                )
                Column(
                    modifier
                        .background(backgroundColor)
                        .requiredWidth(88.dp)
                        .alpha(if (item.enabled) 1f else 0.7f)
                        .clickAndSemanticsModifier(interactionSource, {item.onItemClick}, item.enabled), horizontalAlignment = Alignment.CenterHorizontally
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
                            .padding(start = 2.dp, end = 2.dp, top = avatarTextPadding, bottom = (if(size == AvatarCarouselSize.Medium) 8.dp else 0.dp))
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = item.person.firstName,
                            color = textColor,
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
                                color = subTextColor,
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
