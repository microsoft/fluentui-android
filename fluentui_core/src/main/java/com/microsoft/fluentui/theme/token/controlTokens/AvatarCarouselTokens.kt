package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class AvatarCarouselSize{
    Medium,
    Large
}
enum class TextType {
    Text,
    SubText
}
data class AvatarCarouselInfo(
    val size: AvatarCarouselSize = AvatarCarouselSize.Medium
): ControlInfo

@Parcelize
open class AvatarCarouselTokens : ControlToken, Parcelable {
    @Composable
    open fun getAvatarSize(avatarCarouselInfo: AvatarCarouselInfo):AvatarSize{
        return when(avatarCarouselInfo.size){
            AvatarCarouselSize.Medium -> AvatarSize.Size56
            AvatarCarouselSize.Large -> AvatarSize.Size72
        }
    }
    @Composable
    open fun backgroundColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1Pressed].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun getTextColor(avatarCarouselInfo: AvatarCarouselInfo):StateColor{
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }
    @Composable
    open fun getSubTextColor(avatarCarouselInfo: AvatarCarouselInfo):StateColor{
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }
    @Composable
    open fun getTextSize(avatarCarouselInfo: AvatarCarouselInfo): FontInfo {
        return when(avatarCarouselInfo.size){
            AvatarCarouselSize.Medium -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
            AvatarCarouselSize.Large -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body2]
        }
    }
    @Composable
    open fun getSubTextSize(avatarCarouselInfo: AvatarCarouselInfo): FontInfo {
        return when(avatarCarouselInfo.size){
            AvatarCarouselSize.Medium -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
            AvatarCarouselSize.Large -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
        }
    }

    @Composable
    open fun padding(avatarCarouselInfo: AvatarCarouselInfo): Dp {
        return when(avatarCarouselInfo.size){
            AvatarCarouselSize.Medium -> GlobalTokens.spacing(GlobalTokens.SpacingTokens.XSmall)
            AvatarCarouselSize.Large -> GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small)
        }
    }
}