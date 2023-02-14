package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class TabTextAlignment {
    VERTICAL,
    HORIZONTAL,
    NO_TEXT
}

class TabItemInfo(
    val tabTextAlignment: TabTextAlignment = TabTextAlignment.VERTICAL,
    val fluentStyle: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class TabItemTokens : ControlToken, Parcelable {

    @Composable
    open fun width(tabItemInfo: TabItemInfo): Dp {
        return 64.dp
    }

    @Composable
    open fun backgroundColor(tabItemInfo: TabItemInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun rippleColor(tabItemInfo: TabItemInfo): Color {
        return FluentColor(
            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black),
            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
        ).value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun iconColor(tabItemInfo: TabItemInfo): StateColor {
        return when (tabItemInfo.fluentStyle) {
            FluentStyle.Neutral -> StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                    themeMode = FluentTheme.themeMode
                ),
                pressed = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                    themeMode = FluentTheme.themeMode
                )
            )

            FluentStyle.Brand -> StateColor(
                rest = FluentColor(
                    light = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode),
                pressed = FluentColor(
                    light = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode),
                selected = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(),
                disabled = FluentColor(
                    light = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode)
            )
        }
    }

    @Composable
    open fun textColor(tabItemInfo: TabItemInfo): StateColor {
        return when (tabItemInfo.fluentStyle) {
            FluentStyle.Neutral -> StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    themeMode = FluentTheme.themeMode
                ),
                pressed = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = FluentTheme.themeMode
                ),
                disabled = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                    themeMode = FluentTheme.themeMode
                )
            )

            FluentStyle.Brand -> StateColor(
                rest = FluentColor(
                    light = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode),
                pressed = FluentColor(
                    light = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1Pressed].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode),
                selected = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(),
                disabled = FluentColor(
                    light = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled2].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        ThemeMode.Dark
                    )
                ).value(FluentTheme.themeMode)
            )
        }
    }
}