package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

class ContextualCommandBarInfo : ControlInfo

@Parcelize
open class ContextualCommandBarTokens : IControlToken, Parcelable {

    @Composable
    open fun actionButtonBackgroundBrush(contextualCommandBarInfo: ContextualCommandBarInfo): Brush {
        return SolidColor(
            aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun actionButtonIconColor(contextualCommandBarInfo: ContextualCommandBarInfo): Color {
        return aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = themeMode
        )
    }

    @Composable
    open fun actionButtonIconPadding(contextualCommandBarInfo: ContextualCommandBarInfo): PaddingValues {
        return PaddingValues(
            horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120),
            vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
        )
    }

    @Composable
    open fun actionButtonGradient(contextualCommandBarInfo: ContextualCommandBarInfo): List<Color> {
        return listOf(
            aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = themeMode
            ).copy(alpha = 0.0F),
            aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = themeMode
            ).copy(alpha = 1.0F)
        )
    }

    open fun actionButtonGradientWidth(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return 16.dp
    }

    @Composable
    open fun contextualCommandBarBackgroundBrush(contextualCommandBarInfo: ContextualCommandBarInfo): Brush {
        return SolidColor(
            aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun itemBorderRadius(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadiusNone)
    }

    @Composable
    open fun groupBorderRadius(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius120)
    }

    @Composable
    open fun buttonSpacing(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size20)
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
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun iconSize(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun iconVerticalPadding(contextualCommandBarInfo: ContextualCommandBarInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun typography(contextualCommandBarInfo: ContextualCommandBarInfo): TextStyle {
        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]
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
    open fun buttonBackgroundBrush(contextualCommandBarInfo: ContextualCommandBarInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(
                aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = themeMode
                )
            ),
            pressed = SolidColor(
                aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5Pressed].value(
                    themeMode = themeMode
                )
            ),
            focused = SolidColor(
                aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = themeMode
                )
            ),
            selected = SolidColor(
                FluentColor(
                    light = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                        themeMode = ThemeMode.Light
                    ),
                    dark = aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5Selected].value(
                        themeMode = ThemeMode.Dark
                    )
                ).value(themeMode = themeMode)
            ),
            selectedFocused = SolidColor(
                FluentColor(
                    light = aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                        themeMode = ThemeMode.Light
                    ),
                    dark = aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5Selected].value(
                        themeMode = ThemeMode.Dark
                    )
                ).value(themeMode = themeMode)
            ),
            disabled = SolidColor(
                aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = themeMode
                )
            )
        )
    }

    @Composable
    open fun iconColor(contextualCommandBarInfo: ContextualCommandBarInfo): StateColor {
        return StateColor(
            rest = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = themeMode
            ),
            pressed = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = themeMode
            ),
            focused = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = themeMode
            ),
            selected = FluentColor(
                light = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(
                    themeMode = ThemeMode.Light
                ),
                dark = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = ThemeMode.Dark
                )
            ).value(
                themeMode = themeMode
            ),
            selectedFocused = FluentColor(
                light = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(
                    themeMode = ThemeMode.Light
                ),
                dark = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                    themeMode = ThemeMode.Dark
                )
            ).value(
                themeMode = themeMode
            ),
            disabled = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = themeMode
            )
        )
    }

    @Composable
    open fun focusStroke(contextualCommandBarInfo: ContextualCommandBarInfo): List<BorderStroke> {
        return listOf(
            BorderStroke(
                2.dp,
                aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus2].value(
                    themeMode
                )
            ),
            BorderStroke(
                3.dp,
                aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.StrokeFocus1].value(
                    themeMode
                )
            )
        )
    }
}