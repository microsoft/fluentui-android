package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class AvatarCarouselSize {
    Small,
    Large
}

open class AvatarCarouselInfo(
    val size: AvatarCarouselSize = AvatarCarouselSize.Small
) : ControlInfo

@Parcelize
open class AvatarCarouselTokens : IControlToken, Parcelable {
    @Composable
    open fun avatarSize(avatarCarouselInfo: AvatarCarouselInfo): AvatarSize {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> AvatarSize.Size56
            AvatarCarouselSize.Large -> AvatarSize.Size72
        }
    }

    @Composable
    open fun backgroundBrush(avatarCarouselInfo: AvatarCarouselInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                    themeMode = FluentTheme.themeMode
                )
            ),
            pressed = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1Pressed].value(
                    themeMode = FluentTheme.themeMode
                )
            ),
            disabled = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        )
    }

    @Composable
    open fun textColor(avatarCarouselInfo: AvatarCarouselInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun subTextColor(avatarCarouselInfo: AvatarCarouselInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun textTypography(avatarCarouselInfo: AvatarCarouselInfo): TextStyle {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
            AvatarCarouselSize.Large -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
        }
    }

    @Composable
    open fun subTextTypography(avatarCarouselInfo: AvatarCarouselInfo): TextStyle {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
            AvatarCarouselSize.Large -> FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1]
        }
    }

    @Composable
    open fun padding(avatarCarouselInfo: AvatarCarouselInfo): Dp {
        return when (avatarCarouselInfo.size) {
            AvatarCarouselSize.Small -> FluentGlobalTokens.SizeTokens.Size160.value
            AvatarCarouselSize.Large -> FluentGlobalTokens.SizeTokens.Size80.value
        }
    }
}