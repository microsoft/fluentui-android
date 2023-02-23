package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
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
open class AvatarGroupTokens : ControlToken, Parcelable {

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
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size20 -> TextStyle(
                fontSize = 9.sp,
                lineHeight = 12.sp,
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size24 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size100),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size100),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size32 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size200),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size200),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size40 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size300),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size300),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Regular)
            )
            AvatarSize.Size56 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size500),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size500),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
            )
            AvatarSize.Size72 -> TextStyle(
                fontSize = GlobalTokens.fontSize(GlobalTokens.FontSizeTokens.Size700),
                lineHeight = GlobalTokens.lineHeight(GlobalTokens.LineHeightTokens.Size700),
                fontWeight = GlobalTokens.fontWeight(GlobalTokens.FontWeightTokens.Medium)
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
                AvatarSize.Size16 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size40
                )
                AvatarSize.Size20 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size40
                )
                AvatarSize.Size24 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size40
                )
                AvatarSize.Size32 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size80
                )
                AvatarSize.Size40 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size80
                )
                AvatarSize.Size56 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size80
                )
                AvatarSize.Size72 -> GlobalTokens.size(
                    GlobalTokens.SizeTokens.Size80
                )
            }
        }
    }

    @Composable
    open fun pilePadding(avatarGroupInfo: AvatarGroupInfo): Dp {
        return when (avatarGroupInfo.size) {
            AvatarSize.Size16 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size40
            )
            AvatarSize.Size20 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size24 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size32 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size40 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size20
            )
            AvatarSize.Size56 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size40
            )
            AvatarSize.Size72 -> GlobalTokens.size(
                GlobalTokens.SizeTokens.Size80
            )
        }
    }

    @Composable
    open fun avatarSize(avatarInfo: AvatarInfo): Dp {
        return when (avatarInfo.size) {
            AvatarSize.Size16 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize120)
            AvatarSize.Size20 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize160)
            AvatarSize.Size24 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize160)
            AvatarSize.Size32 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize200)
            AvatarSize.Size40 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)
            AvatarSize.Size56 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize280)
            AvatarSize.Size72 -> GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize480)
        }
    }
}
