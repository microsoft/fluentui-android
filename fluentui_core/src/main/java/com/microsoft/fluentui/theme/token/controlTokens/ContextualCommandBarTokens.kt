package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

class ContextualCommandBarInfo : ControlInfo

@Parcelize
open class ContextualCommandBarTokens : ControlToken, Parcelable {

    @Composable
    open fun actionButtonBackgroundColor(contextualCommandBarInfo: ContextualCommandBarInfo): Color {
        return aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun actionButtonIconColor(contextualCommandBarInfo: ContextualCommandBarInfo): Color {
        return aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun actionButtonIconPadding(contextualCommandBarInfo: ContextualCommandBarInfo): PaddingValues {
        return PaddingValues(
            horizontal = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small),
            vertical = GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium)
        )
    }

    @Composable
    open fun actionButtonGradient(contextualCommandBarInfo: ContextualCommandBarInfo): List<Color> {
        return listOf(
            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = themeMode
            ).copy(alpha = 0.0F),
            aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = themeMode
            ).copy(alpha = 1.0F)
        )
    }

    open fun actionButtonGradientWidth(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return 16.dp
    }

    @Composable
    open fun contextualCommandBarBackgroundColor(contextualCommandBarInfo: ContextualCommandBarInfo): Color {
        return aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background2].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun itemBorderRadius(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadiusNone)
    }

    @Composable
    open fun groupBorderRadius(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return GlobalTokens.cornerRadius(GlobalTokens.CornerRadiusTokens.CornerRadius120)
    }

    @Composable
    open fun buttonSpacing(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return GlobalTokens.spacing(GlobalTokens.SpacingTokens.XXXSmall)
    }

    @Composable
    open fun buttonPadding(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return 8.dp
    }

    @Composable
    open fun buttonMinWidth(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return 44.dp
    }

    @Composable
    open fun groupSpacing(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return GlobalTokens.spacing(GlobalTokens.SpacingTokens.Medium)
    }

    @Composable
    open fun iconSize(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.Medium)
    }

    @Composable
    open fun iconVerticalPadding(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return GlobalTokens.spacing(GlobalTokens.SpacingTokens.XSmall)
    }

    @Composable
    open fun typography(contextualCommandBarInfo: ContextualCommandBarInfo): FontInfo {
        return aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong]
    }

    @Composable
    open fun itemIconHorizontalPadding(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return 10.dp
    }

    @Composable
    open fun groupIconHorizontalPadding(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return 12.dp
    }

    @Composable
    open fun buttonBackgroundColor(contextualCommandBarInfo: ContextualCommandBarInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5Pressed].value(
                themeMode = themeMode
            ),
            focused = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                themeMode = themeMode
            ),
            selected = FluentColor(
                light = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                    themeMode = ThemeMode.Light
                ),
                dark = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5Selected].value(
                    themeMode = ThemeMode.Dark
                )
            ).value(themeMode = themeMode),
            selectedFocused = FluentColor(
                light = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                    themeMode = ThemeMode.Light
                ),
                dark = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5Selected].value(
                    themeMode = ThemeMode.Dark
                )
            ).value(themeMode = themeMode),
            disabled = aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background5].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun iconColor(contextualCommandBarInfo: ContextualCommandBarInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = themeMode
            ),
            focused = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = themeMode
            ),
            selected = FluentColor(
                light = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(
                    themeMode = ThemeMode.Light
                ),
                dark = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = ThemeMode.Dark
                )
            ).value(
                themeMode = themeMode
            ),
            selectedFocused = FluentColor(
                light = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(
                    themeMode = ThemeMode.Light
                ),
                dark = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = ThemeMode.Dark
                )
            ).value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun focusStroke(contextualCommandBarInfo: ContextualCommandBarInfo): List<BorderStroke> {
        return listOf(
            BorderStroke(
                2.dp,
                aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                    themeMode
                )
            ),
            BorderStroke(
                3.dp,
                aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                    themeMode
                )
            )
        )
    }
}