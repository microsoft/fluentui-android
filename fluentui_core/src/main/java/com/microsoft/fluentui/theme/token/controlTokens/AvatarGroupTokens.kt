package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.globalTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import com.microsoft.fluentui.theme.token.IconSize
import kotlinx.parcelize.Parcelize

enum class AvatarGroupStyle {
    Stack,
    Pile
}

data class AvatarGroupInfo(
        val size: AvatarSize = AvatarSize.Medium,
        val style: AvatarGroupStyle = AvatarGroupStyle.Stack
) : ControlInfo

@Parcelize
open class AvatarGroupTokens : ControlToken, Parcelable {

    companion object {
        const val Type: String = "AvatarGroup"
    }

    @Composable
    open fun avatarSize(avatarGroupInfo: AvatarGroupInfo): Dp {
        return when (avatarGroupInfo.size) {
            AvatarSize.XSmall -> 16.dp
            AvatarSize.Small -> 24.dp
            AvatarSize.Medium -> 32.dp
            AvatarSize.Large -> 40.dp
            AvatarSize.XLarge -> 52.dp
            AvatarSize.XXLarge -> 72.dp
        }
    }

    @Composable
    open fun fontSize(avatarGroupInfo: AvatarGroupInfo): Dp {
        return when (avatarGroupInfo.size) {
            AvatarSize.XSmall -> 10.dp
            AvatarSize.Small -> 12.dp
            AvatarSize.Medium -> 14.dp
            AvatarSize.Large -> 18.dp
            AvatarSize.XLarge -> 24.dp
            AvatarSize.XXLarge -> 36.dp
        }
    }

    @Composable
    open fun spacing(avatarGroupInfo: AvatarGroupInfo, isActive: Boolean): Dp {
        return when (avatarGroupInfo.style) {
            AvatarGroupStyle.Stack -> when (avatarGroupInfo.size) {
                AvatarSize.XSmall -> if (isActive) 0.dp else (-2).dp
                AvatarSize.Small -> if (isActive) 0.dp else (-2).dp
                AvatarSize.Medium -> if (isActive) (-4).dp else (-4).dp
                AvatarSize.Large -> if (isActive) (-4).dp else (-8).dp
                AvatarSize.XLarge -> if (isActive) (-8).dp else (-12).dp
                AvatarSize.XXLarge -> if (isActive) (-6).dp else (-12).dp
            }

            AvatarGroupStyle.Pile -> when (avatarGroupInfo.size) {
                AvatarSize.XSmall -> globalTokens.spacing[GlobalTokens.SpacingTokens.XXSmall]
                AvatarSize.Small -> globalTokens.spacing[GlobalTokens.SpacingTokens.XXSmall]
                AvatarSize.Medium -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
                AvatarSize.Large -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
                AvatarSize.XLarge -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
                AvatarSize.XXLarge -> globalTokens.spacing[GlobalTokens.SpacingTokens.XSmall]
            }
        }
    }

    @Composable
    open fun iconSize(avatarGroupInfo: AvatarGroupInfo): IconSize {
        return when (avatarGroupInfo.size) {
            AvatarSize.XSmall -> FluentTheme.globalTokens.iconSize[GlobalTokens.IconSizeTokens.XXSmallSelected]
            AvatarSize.Small -> FluentTheme.globalTokens.iconSize[GlobalTokens.IconSizeTokens.XSmallSelected]
            AvatarSize.Medium -> FluentTheme.globalTokens.iconSize[GlobalTokens.IconSizeTokens.SmallSelected]
            AvatarSize.Large -> FluentTheme.globalTokens.iconSize[GlobalTokens.IconSizeTokens.MediumSelected]
            AvatarSize.XLarge -> FluentTheme.globalTokens.iconSize[GlobalTokens.IconSizeTokens.LargeSelected]
            AvatarSize.XXLarge -> FluentTheme.globalTokens.iconSize[GlobalTokens.IconSizeTokens.XXXLargeSelected]
        }
    }
}
