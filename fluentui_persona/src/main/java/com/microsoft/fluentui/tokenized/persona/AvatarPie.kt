package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize

private val SPACER_SIZE = 2.dp

@Composable
fun AvatarPie(
    group: Group, size: AvatarSize, noOfVisibleAvatars: Int = 2, avatarTokens: AvatarTokens? = null
) {
    val avatarInfo = AvatarInfo(
        size
    )
    val token = avatarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarControlType] as AvatarTokens
    val avatarSize = token.avatarSize(avatarInfo)

    Box(
        modifier = Modifier
            .requiredSize(avatarSize)
            .background(
                color = Color.White, shape = CircleShape
            ), contentAlignment = Alignment.Center
    ) {
        val slicedAvatarDimen = avatarSize / 2 - SPACER_SIZE / 2
        if (noOfVisibleAvatars == 2) {
            RenderTwoSlices(avatarSize, slicedAvatarDimen, group, size)
        } else if (noOfVisibleAvatars >= 3) {
            RenderThreeSlices(avatarSize, slicedAvatarDimen, group, size)
        }
    }
}

@Composable
private fun RenderTwoSlices(
    avatarSize: Dp, slicedAvatarDimen: Dp, group: Group, size: AvatarSize
) {
    Row(
        modifier = Modifier
            .requiredSize(avatarSize)
            .clip(CircleShape)
    ) {
        SlicedAvatar(
            group.members[0],
            slicedAvatarSize = avatarSize,
            width = slicedAvatarDimen,
            modifier = Modifier
                .height(avatarSize)
                .width(slicedAvatarDimen),
            size = size
        )
        AddVerticalSpacer()
        SlicedAvatar(
            group.members[1],
            slicedAvatarSize = avatarSize,
            width = slicedAvatarDimen,
            modifier = Modifier
                .height(avatarSize)
                .width(slicedAvatarDimen),
            size = size
        )
    }
}

@Composable
private fun RenderThreeSlices(
    avatarSize: Dp, slicedAvatarDimen: Dp, group: Group, size: AvatarSize
) {
    Row(
        modifier = Modifier
            .requiredSize(avatarSize)
            .clip(CircleShape)
    ) {
        SlicedAvatar(
            group.members[0],
            slicedAvatarSize = avatarSize,
            width = slicedAvatarDimen,
            modifier = Modifier
                .height(avatarSize)
                .width(slicedAvatarDimen)
                .align(Alignment.CenterVertically),
            size = size
        )
        AddVerticalSpacer()
        Column(
            modifier = Modifier
                .height(avatarSize)
                .width(slicedAvatarDimen),
        ) {
            SlicedAvatar(
                group.members[1],
                slicedAvatarSize = slicedAvatarDimen,
                width = slicedAvatarDimen,
                modifier = Modifier
                    .height(slicedAvatarDimen)
                    .width(slicedAvatarDimen),
                size = size
            )
            AddHorizontalSpacer()
            SlicedAvatar(
                group.members[2],
                slicedAvatarSize = slicedAvatarDimen,
                width = slicedAvatarDimen,
                modifier = Modifier
                    .height(slicedAvatarDimen)
                    .width(slicedAvatarDimen),
                size = size
            )
        }

    }
}

@Composable
private fun AddVerticalSpacer() {
    Spacer(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxHeight()
            .width(SPACER_SIZE)
    )
}

@Composable
private fun AddHorizontalSpacer() {
    Spacer(
        modifier = Modifier
            .background(color = Color.White)
            .fillMaxWidth()
            .height(SPACER_SIZE)
    )
}
