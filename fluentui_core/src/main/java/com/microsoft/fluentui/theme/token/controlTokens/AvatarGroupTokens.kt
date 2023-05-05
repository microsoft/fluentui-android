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
    Pile
}

data class AvatarGroupInfo(
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
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size20 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size24 -> TextStyle(
                fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size100),
                lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size100),
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size32 -> TextStyle(
                fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size200),
                lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size200),
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size40 -> TextStyle(
                fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size300),
                lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size300),
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size56 -> TextStyle(
                fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size500),
                lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size500),
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium)
            )
            AvatarSize.Size72 -> TextStyle(
                fontSize = FluentGlobalTokens.fontSize(FluentGlobalTokens.FontSizeTokens.Size700),
                lineHeight = FluentGlobalTokens.lineHeight(FluentGlobalTokens.LineHeightTokens.Size700),
                fontWeight = FluentGlobalTokens.fontWeight(FluentGlobalTokens.FontWeightTokens.Medium)
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
                AvatarSize.Size16 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size40
                )
                AvatarSize.Size20 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size40
                )
                AvatarSize.Size24 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size40
                )
                AvatarSize.Size32 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size80
                )
                AvatarSize.Size40 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size80
                )
                AvatarSize.Size56 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size80
                )
                AvatarSize.Size72 -> FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size80
                )
            }
        }
    }

    @Composable
    open fun pilePadding(avatarGroupInfo: AvatarGroupInfo): Dp {
        return when (avatarGroupInfo.size) {
            AvatarSize.Size16 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size40
            )
            AvatarSize.Size20 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size24 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size32 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size40 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size56 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size40
            )
            AvatarSize.Size72 -> FluentGlobalTokens.size(
                FluentGlobalTokens.SizeTokens.Size80
            )
        }
    }

    @Composable
    open fun avatarSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize120)
            AvatarSize.Size20 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize160)
            AvatarSize.Size24 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize160)
            AvatarSize.Size32 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize200)
            AvatarSize.Size40 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
            AvatarSize.Size56 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize280)
            AvatarSize.Size72 -> FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize480)
        }
    }
}
