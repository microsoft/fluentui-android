package com.microsoft.fluentui.tokenized.persona

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.microsoft.fluentui.theme.token.controlTokens.AvatarType
import com.microsoft.fluentui.theme.token.controlTokens.SlicedAvatarSize

private val SPACER_SIZE = 2.dp

@Composable
fun AvatarPie(
    group: Group, size: AvatarSize, noOfVisibleAvatars: Int = 2, avatarTokens: AvatarTokens? = null
) {
    val avatarInfo = AvatarInfo(
        size,
        AvatarType.Group,
        isImageAvailable = group.isImageAvailable(),
        hasValidInitials = group.getInitials().isNotEmpty(),
        calculatedColorKey = group.groupName
    )
    val token = avatarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AvatarControlType] as AvatarTokens
    val avatarSize = token.avatarSize(avatarInfo)

    if (noOfVisibleAvatars == 1) {
        BasicText(
            "Pie should have minimum 2 members visible",
        )
        return
    }
    Box(
        modifier = Modifier
            .height(avatarSize)
            .width(avatarSize)
            .background(
                color = Color.White, shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        val slicedAvatarDimen = avatarSize / 2 - SPACER_SIZE / 2
        if (noOfVisibleAvatars == 2) {
            Row(
                modifier = Modifier
                    .height(avatarSize)
                    .width(avatarSize)
                    .clip(CircleShape),
            ) {
                SlicedAvatar(
                    group.members[0],
                    slicedAvatarSize = getSlicedAvatarSize(avatarSize),
                    width = slicedAvatarDimen,
                    modifier = Modifier
                        .height(avatarSize)
                        .width(slicedAvatarDimen)
                )
                AddVerticalSpacer()
                SlicedAvatar(
                    group.members[1],
                    slicedAvatarSize = getSlicedAvatarSize(avatarSize),
                    width = slicedAvatarDimen,
                    modifier = Modifier
                        .height(avatarSize)
                        .width(slicedAvatarDimen)
                )
            }

        } else if (noOfVisibleAvatars >= 3) {
            Row(
                modifier = Modifier
                    .height(avatarSize)
                    .width(avatarSize)
                    .clip(CircleShape)
            ) {
                SlicedAvatar(
                    group.members[0],
                    slicedAvatarSize = getSlicedAvatarSize(avatarSize),
                    width = slicedAvatarDimen,
                    modifier = Modifier
                        .height(avatarSize)
                        .width(slicedAvatarDimen).align(Alignment.CenterVertically)
                )
                AddVerticalSpacer()
                Column(
                    modifier = Modifier
                        .height(avatarSize)
                        .width(slicedAvatarDimen),
                ) {
                    SlicedAvatar(
                        group.members[1],
                        slicedAvatarSize = getSlicedAvatarSize(slicedAvatarDimen),
                        width = slicedAvatarDimen,
                        modifier = Modifier
                            .height(slicedAvatarDimen)
                            .width(slicedAvatarDimen)
                    )
                    AddHorizontalSpacer()
                    SlicedAvatar(
                        group.members[2],
                        slicedAvatarSize = getSlicedAvatarSize(slicedAvatarDimen),
                        width = slicedAvatarDimen,
                        modifier = Modifier
                            .height(slicedAvatarDimen)
                            .width(slicedAvatarDimen)
                    )
                }

            }
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


private fun getSlicedAvatarSize(size: Dp): SlicedAvatarSize {
    return when (size) {
        7.dp -> SlicedAvatarSize.Size7
        9.dp -> SlicedAvatarSize.Size9
        11.dp -> SlicedAvatarSize.Size11
        15.dp -> SlicedAvatarSize.Size15
        16.dp -> SlicedAvatarSize.Size16
        19.dp -> SlicedAvatarSize.Size19
        20.dp -> SlicedAvatarSize.Size20
        24.dp -> SlicedAvatarSize.Size24
        27.dp -> SlicedAvatarSize.Size27
        32.dp -> SlicedAvatarSize.Size32
        35.dp -> SlicedAvatarSize.Size35
        40.dp -> SlicedAvatarSize.Size40
        56.dp -> SlicedAvatarSize.Size56
        72.dp -> SlicedAvatarSize.Size72
        else -> {
            SlicedAvatarSize.Size24
        }
    }
}
