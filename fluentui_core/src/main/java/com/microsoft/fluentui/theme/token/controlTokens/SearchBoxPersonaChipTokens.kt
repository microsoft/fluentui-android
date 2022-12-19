package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class SearchBoxPersonaChipSize {
    Small,
    Medium
}

data class SearchBoxPersonaChipInfo(
    val style: FluentStyle = FluentStyle.Neutral,
    val enabled: Boolean = true,
    val size: SearchBoxPersonaChipSize = SearchBoxPersonaChipSize.Small
) : ControlInfo

@Parcelize
open class SearchBoxPersonaChipTokens : ControlToken, Parcelable {
    @Composable
    open fun backgroundColor(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): StateColor {
        when (searchBoxPersonaChipInfo.style) {
            FluentStyle.Neutral -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background6].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.BackgroundInverted].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background6].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            FluentStyle.Brand -> return StateColor(
                rest = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground3].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground3].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    open fun textColor(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): StateColor {
        when (searchBoxPersonaChipInfo.style) {
            FluentStyle.Neutral -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable2].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            FluentStyle.Brand -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    open fun borderRadius(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): Dp {
        return GlobalTokens.borderRadius(GlobalTokens.BorderRadiusTokens.Medium)
    }

    @Composable
    open fun fontSize(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun verticalPadding(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size20)
    }

    @Composable
    open fun horizontalPadding(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun avatarToTextSpacing(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): Dp {
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun avatarSize(searchBoxPersonaChipInfo: SearchBoxPersonaChipInfo): AvatarSize {
        return AvatarSize.Size16
    }
}