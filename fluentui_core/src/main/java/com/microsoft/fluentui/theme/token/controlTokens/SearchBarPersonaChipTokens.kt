package com.microsoft.fluentui.theme.token.controlTokens

import androidx.compose.runtime.Composable
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.parcelize.Parcelize

data class SearchBarPersonaChipInfo(
    val style: FluentStyle = FluentStyle.Neutral,
    override val enabled: Boolean = true,
    override val size: PersonaChipSize = PersonaChipSize.Small
) : PersonaChipControlInfo()

@Parcelize
open class SearchBarPersonaChipTokens : PersonaChipTokens() {
    @Composable
    override fun backgroundColor(searchBarPersonaChipInfo: PersonaChipControlInfo): StateColor {
        searchBarPersonaChipInfo as SearchBarPersonaChipInfo
        when (searchBarPersonaChipInfo.style) {
            FluentStyle.Neutral -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background6].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundInverted].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background6].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            FluentStyle.Brand -> return StateColor(
                rest = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground3].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    override fun textColor(searchBarPersonaChipInfo: PersonaChipControlInfo): StateColor {
        searchBarPersonaChipInfo as SearchBarPersonaChipInfo
        when (searchBarPersonaChipInfo.style) {
            FluentStyle.Neutral -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable2].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            FluentStyle.Brand -> return StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                    themeMode = FluentTheme.themeMode
                ),
                selected = FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }
}