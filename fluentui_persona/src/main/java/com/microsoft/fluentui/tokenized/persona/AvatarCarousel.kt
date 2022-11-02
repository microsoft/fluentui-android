package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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

@Composable
fun AvatarCarousel(
    avatarList: List<AvatarCarouselItem>,
    size: AvatarCarouselSize,
    modifier: Modifier = Modifier,
    enableActivityRings: Boolean = false,
    enablePresence: Boolean = false,
    avatarTokens: AvatarTokens? = null,
    avatarCarouselTokens: AvatarCarouselTokens? = null
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

        LazyRow(modifier) {
            items(avatarList) { item ->
                val backgroundColor =
                    getAvatarCarouselTokens().backgroundColor(enabled = item.enabled)
                val textColor = getAvatarCarouselTokens().getTextColor(
                    textType = TextType.Text,
                    enabled = item.enabled
                )
                val subTextColor = getAvatarCarouselTokens().getTextColor(
                    textType = TextType.SubText,
                    enabled = item.enabled
                )
                val alpha = if (item.enabled) 1f else 0.7f
                Column(
                    Modifier
                        .background(backgroundColor)
                        .requiredWidth(88.dp)
                        .padding(top = 8.dp)
                        .alpha(alpha), horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Avatar(
                        person = item.person,
                        size = avatarSize,
                        avatarToken = avatarTokens,
                        enableActivityRings = enableActivityRings,
                        enablePresence = enablePresence
                    )
                    Row(
                        Modifier
                            .padding(start = 2.dp, end = 2.dp, top = avatarTextPadding)
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
                                .padding(start = 2.dp, end = 2.dp),
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