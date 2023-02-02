package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.*
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralStrokeColorTokens.Stroke2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.*
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens
import com.microsoft.fluentui.theme.token.GlobalTokens.StrokeWidthTokens.StrokeWidth15
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Standard
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Subtle
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Bottom
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top
import kotlinx.parcelize.Parcelize

enum class ListItemType {
    OneLine,
    TwoLine,
    ThreeLine,
    SectionHeader,
    SectionDescription
}

enum class SectionHeaderStyle {
    Standard,
    Subtle
}

enum class BorderInset {
    None,
    Medium,
    Large,
    XXLarge,
    XXXXLarge,
    XXXXXXLarge
}

enum class BorderType {
    NoBorder,
    Top,
    Bottom,
    TopBottom
}

enum class TextPlacement {
    Top,
    Bottom
}

enum class ListItemTextAlignment {
    Regular,
    Centered
}

data class ListItemInfo(
    val style: SectionHeaderStyle = Standard,
    val listItemType: ListItemType = OneLine,
    val borderInset: BorderInset = BorderInset.None,
    val placement: TextPlacement = Top,
    val horizontalSpacing: SpacingTokens = SpacingTokens.Small,
    val verticalSpacing: SpacingTokens = SpacingTokens.Small,
    val unreadDot: Boolean = false
) : ControlInfo

@Parcelize
open class ListItemTokens : ControlToken, Parcelable {
    @Composable
    open fun backgroundColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralStrokeColor[Stroke2].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralStrokeColor[Stroke2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun iconColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cellHeight(listItemInfo: ListItemInfo): Dp {
        return when (listItemInfo.listItemType) {
            OneLine -> 48.dp
            TwoLine -> 68.dp
            ThreeLine -> 88.dp
            SectionHeader -> 48.dp
            SectionDescription -> 68.dp
        }
    }

    @Composable
    open fun unreadDotColor(listItemInfo: ListItemInfo): Color {
        return FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun padding(listItemInfo: ListItemInfo): PaddingValues {
        return PaddingValues(
            start = GlobalTokens.spacing(listItemInfo.horizontalSpacing),
            end = GlobalTokens.spacing(listItemInfo.horizontalSpacing),
            top = GlobalTokens.spacing(listItemInfo.verticalSpacing),
            bottom = GlobalTokens.spacing(listItemInfo.verticalSpacing)
        )
    }

    @Composable
    open fun borderSize(listItemInfo: ListItemInfo): Dp {
        return GlobalTokens.strokeWidth(StrokeWidth15)
    }

    @Composable
    open fun chevronTint(listItemInfo: ListItemInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun borderInset(listItemInfo: ListItemInfo): Dp {
        return when (listItemInfo.borderInset) {
            BorderInset.None -> 0.dp
            BorderInset.Medium -> 16.dp
            BorderInset.Large -> 56.dp
            BorderInset.XXLarge -> 68.dp
            BorderInset.XXXXLarge -> 72.dp
            BorderInset.XXXXXXLarge -> 108.dp
        }
    }

    @Composable
    open fun descriptionPlacement(listItemInfo: ListItemInfo): Alignment.Vertical {
        return when (listItemInfo.placement) {
            Top -> Alignment.Top
            Bottom -> Alignment.Bottom
        }
    }

    @Composable
    open fun primaryTextColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun subTextColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun secondarySubTextColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun actionTextColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.brandForegroundColor[BrandForeground1].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.brandForegroundColor[BrandForegroundDisabled1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.brandForegroundColor[BrandForeground1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun descriptionTextColor(listItemInfo: ListItemInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun rippleColor(listItemInfo: ListItemInfo): Color {
        return FluentColor(
            light = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.Black),
            dark = GlobalTokens.neutralColor(GlobalTokens.NeutralColorTokens.White)
        ).value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun primaryTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return if (listItemInfo.unreadDot) {
            FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1Strong]
        } else {
            FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
        }
    }

    @Composable
    open fun subTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[Body2]
    }

    @Composable
    open fun secondarySubTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[Caption1]
    }

    @Composable
    open fun actionTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[Caption1Strong]
    }

    @Composable
    open fun descriptionTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return FluentTheme.aliasTokens.typography[Caption1]
    }

    @Composable
    open fun sectionHeaderPrimaryTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return when (listItemInfo.style) {
            Standard -> {
                FluentTheme.aliasTokens.typography[Body1Strong]
            }
            Subtle -> {
                FluentTheme.aliasTokens.typography[Caption1]
            }
        }
    }

    @Composable
    open fun sectionHeaderActionTextTypography(listItemInfo: ListItemInfo): FontInfo {
        return when (listItemInfo.style) {
            Standard -> {
                FluentTheme.aliasTokens.typography[Body2Strong]
            }
            Subtle -> {
                FluentTheme.aliasTokens.typography[Caption1Strong]
            }
        }
    }
}