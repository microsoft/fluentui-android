package com.microsoft.fluentuidemo

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.SearchBarTokens

object CustomizedTokens {
    val listItemTokens = object : ListItemTokens() {
        @Composable
        override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
            return StateBrush(
                rest = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value()),
                pressed = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2Pressed].value())
            )
        }
    }
}

object CustomizedSearchBarTokens: SearchBarTokens() {
    @Composable
    override fun height(searchBarInfo: SearchBarInfo): Dp {
        return FluentGlobalTokens.SizeTokens.Size480.value
    }

    @Composable
    override fun elevation(searchBarInfo: SearchBarInfo): Dp =
        FluentGlobalTokens.ShadowTokens.Shadow16.value

    @Composable
    override fun cornerRadius(searchBarInfo: SearchBarInfo): Dp =
        FluentGlobalTokens.CornerRadiusTokens.CornerRadius160.value

    @Composable
    override fun borderWidth(searchBarInfo: SearchBarInfo): Dp =
        FluentGlobalTokens.StrokeWidthTokens.StrokeWidth10.value

    @Composable
    override fun inputBackgroundBrush(searchBarInfo: SearchBarInfo): Brush {
        return SolidColor(
            when (searchBarInfo.style) {
                FluentStyle.Neutral ->
                    FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                        themeMode = FluentTheme.themeMode
                    )

                FluentStyle.Brand ->
                    FluentColor(
                        light = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground2].value(
                            ThemeMode.Light
                        ),
                        dark = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            ThemeMode.Dark
                        )
                    ).value(themeMode = FluentTheme.themeMode)
            }
        )
    }
}