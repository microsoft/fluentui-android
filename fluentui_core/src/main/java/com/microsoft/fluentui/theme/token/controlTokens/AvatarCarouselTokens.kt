package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class AvatarCarouselSize {
    Small,
    Large
}

data class AvatarCarouselInfo(
    val size: AvatarCarouselSize = AvatarCarouselSize.Small
) : ControlInfo

@Parcelize
open class AvatarCarouselTokens : ControlToken, Parcelable {
    @Composable
    open fun getAvatarSize(avatarCarouselInfo: AvatarCarouselInfo): AvatarSize {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> AvatarSize.Size56
            AvatarCarouselSize.Large -> AvatarSize.Size72
        }
    }

    @Composable
    open fun backgroundColor(avatarCarouselInfo: AvatarCarouselInfo): StateColor {
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
    open fun getTextColor(avatarCarouselInfo: AvatarCarouselInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun getSubTextColor(avatarCarouselInfo: AvatarCarouselInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun getTextStyle(avatarCarouselInfo: AvatarCarouselInfo): TextStyle {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
            AvatarCarouselSize.Large -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body2]
        }
    }

    @Composable
    open fun getSubTextStyle(avatarCarouselInfo: AvatarCarouselInfo): TextStyle {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
            AvatarCarouselSize.Large -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
        }
    }

    @Composable
    open fun padding(avatarCarouselInfo: AvatarCarouselInfo): Dp {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> GlobalTokens.size(GlobalTokens.SizeTokens.Size160)
            AvatarCarouselSize.Large -> GlobalTokens.size(GlobalTokens.SizeTokens.Size80)
        }
    }
}