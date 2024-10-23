package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class AvatarGroupStyle {
    Stack,
    Pile,
    Pie
}

open class AvatarGroupInfo(
    val size: AvatarSize = AvatarSize.Size32,
    val style: AvatarGroupStyle = AvatarGroupStyle.Stack
) : ControlInfo

@Parcelize
open class AvatarGroupTokens : IControlToken, Parcelable {

    @Composable
    open fun avatarSize(avatarGroupInfo: AvatarGroupInfo): Dp {
        return when (avatarGroupInfo.size) {
            AvatarSize.Size16 -> 16.dp
            AvatarSize.Size20 -> 24.dp
            AvatarSize.Size24 -> 24.dp
            AvatarSize.Size32 -> 32.dp
            AvatarSize.Size40 -> 40.dp
            AvatarSize.Size56 -> 56.dp
            AvatarSize.Size72 -> 72.dp
        }
    }

    @Composable
    open fun fontInfo(avatarInfo: AvatarInfo): TextStyle {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )
            AvatarSize.Size20 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )
            AvatarSize.Size24 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size100.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size100.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )
            AvatarSize.Size32 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size200.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size200.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )
            AvatarSize.Size40 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size300.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size300.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Regular.value
            )
            AvatarSize.Size56 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size500.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size500.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value
            )
            AvatarSize.Size72 -> TextStyle(
                fontSize = FluentGlobalTokens.FontSizeTokens.Size700.value,
                lineHeight = FluentGlobalTokens.LineHeightTokens.Size700.value,
                fontWeight = FluentGlobalTokens.FontWeightTokens.Medium.value
            )
        }
    }

    @Composable
    open fun spacing(avatarGroupInfo: AvatarGroupInfo, isActive: Boolean): Dp {
        return when (avatarGroupInfo.style) {
            AvatarGroupStyle.Stack -> when (avatarGroupInfo.size) {
                AvatarSize.Size16 -> if (isActive) 0.dp else (-2).dp
                AvatarSize.Size20 -> if (isActive) 0.dp else (-2).dp
                AvatarSize.Size24 -> if (isActive) 0.dp else (-2).dp
                AvatarSize.Size32 -> if (isActive) (-4).dp else (-4).dp
                AvatarSize.Size40 -> if (isActive) (-4).dp else (-8).dp
                AvatarSize.Size56 -> if (isActive) (-8).dp else (-12).dp
                AvatarSize.Size72 -> if (isActive) (-6).dp else (-12).dp
            }

            AvatarGroupStyle.Pile -> when (avatarGroupInfo.size) {
                AvatarSize.Size16 -> FluentGlobalTokens.SizeTokens.Size40
                    .value
                AvatarSize.Size20 -> FluentGlobalTokens.SizeTokens.Size40
                    .value
                AvatarSize.Size24 -> FluentGlobalTokens.SizeTokens.Size40
                    .value
                AvatarSize.Size32 -> FluentGlobalTokens.SizeTokens.Size80
                    .value
                AvatarSize.Size40 -> FluentGlobalTokens.SizeTokens.Size80
                    .value
                AvatarSize.Size56 -> FluentGlobalTokens.SizeTokens.Size80
                    .value
                AvatarSize.Size72 -> FluentGlobalTokens.SizeTokens.Size80
                    .value
            }

            AvatarGroupStyle.Pie -> 0.dp
        }
    }

    @Composable
    open fun pilePadding(avatarGroupInfo: AvatarGroupInfo): Dp {
        return when (avatarGroupInfo.size) {
            AvatarSize.Size16 -> FluentGlobalTokens.SizeTokens.Size40
                .value
            AvatarSize.Size20 -> FluentGlobalTokens.SizeTokens.Size20
                .value
            AvatarSize.Size24 -> FluentGlobalTokens.SizeTokens.Size20
                .value
            AvatarSize.Size32 -> FluentGlobalTokens.SizeTokens.Size20
                .value
            AvatarSize.Size40 -> FluentGlobalTokens.SizeTokens.Size20
                .value
            AvatarSize.Size56 -> FluentGlobalTokens.SizeTokens.Size40
                .value
            AvatarSize.Size72 -> FluentGlobalTokens.SizeTokens.Size80
                .value
        }
    }

    @Composable
    open fun avatarSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> FluentGlobalTokens.IconSizeTokens.IconSize120.value
            AvatarSize.Size20 -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
            AvatarSize.Size24 -> FluentGlobalTokens.IconSizeTokens.IconSize160.value
            AvatarSize.Size32 -> FluentGlobalTokens.IconSizeTokens.IconSize200.value
            AvatarSize.Size40 -> FluentGlobalTokens.IconSizeTokens.IconSize240.value
            AvatarSize.Size56 -> FluentGlobalTokens.IconSizeTokens.IconSize280.value
            AvatarSize.Size72 -> FluentGlobalTokens.IconSizeTokens.IconSize480.value
        }
    }
}
