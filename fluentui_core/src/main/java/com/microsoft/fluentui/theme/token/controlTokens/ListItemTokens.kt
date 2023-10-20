package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundDisabled1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.Foreground1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.Foreground3
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralStrokeColorTokens.Stroke2
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens.Body1Strong
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens.Body2
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens.Body2Strong
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens.Caption1
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens.Caption1Strong
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.StrokeWidthTokens.StrokeWidth15
import com.microsoft.fluentui.theme.token.IControlToken
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.OneLine
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.SectionDescription
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.SectionHeader
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.ThreeLine
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.TwoLine
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Bold
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
    Bold,
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

open class ListItemInfo(
    val style: SectionHeaderStyle = Bold,
    val listItemType: ListItemType = OneLine,
    val borderInset: BorderInset = BorderInset.None,
    val placement: TextPlacement = Top,
    val horizontalSpacing: FluentGlobalTokens.SizeTokens = FluentGlobalTokens.SizeTokens.Size120,
    val verticalSpacing: FluentGlobalTokens.SizeTokens = FluentGlobalTokens.SizeTokens.Size120,
    val unreadDot: Boolean = false
) : ControlInfo

@Parcelize
open class ListItemTokens : IControlToken, Parcelable {
    @Composable
    open fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[Background1].value(
                    themeMode = FluentTheme.themeMode
                )
            ),
            pressed = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[Background1Pressed].value(
                    themeMode = FluentTheme.themeMode
                )
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
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            ),
        )
    }

    @Composable
    open fun cellHeight(listItemInfo: ListItemInfo): Dp {
        return when (listItemInfo.listItemType) {
            OneLine -> 48.dp
            TwoLine -> 68.dp
            ThreeLine -> 88.dp
            SectionHeader -> 48.dp
            SectionDescription -> 64.dp
        }
    }

    @Composable
    open fun unreadDotColor(listItemInfo: ListItemInfo): Color {
        return FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun padding(listItemInfo: ListItemInfo): PaddingValues {
        return PaddingValues(
            start = FluentGlobalTokens.size(listItemInfo.horizontalSpacing),
            end = FluentGlobalTokens.size(listItemInfo.horizontalSpacing),
            top = FluentGlobalTokens.size(listItemInfo.verticalSpacing),
            bottom = FluentGlobalTokens.size(listItemInfo.verticalSpacing)
        )
    }

    @Composable
    open fun borderSize(listItemInfo: ListItemInfo): Dp {
        return FluentGlobalTokens.strokeWidth(StrokeWidth15)
    }

    @Composable
    open fun chevronTint(listItemInfo: ListItemInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
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
            ),
            focused = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
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
            ),
            focused = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
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
            ),
            focused = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
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
            ),
            focused = FluentTheme.aliasTokens.brandForegroundColor[BrandForeground1].value(
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
            ),
            focused = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun rippleColor(listItemInfo: ListItemInfo): Color {
        return FluentColor(
            light = FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.Black),
            dark = FluentGlobalTokens.neutralColor(FluentGlobalTokens.NeutralColorTokens.White)
        ).value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun primaryTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return if (listItemInfo.unreadDot) {
            FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong]
        } else {
            FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
        }
    }

    @Composable
    open fun subTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[Body2]
    }

    @Composable
    open fun secondarySubTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[Caption1]
    }

    @Composable
    open fun actionTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[Caption1Strong]
    }

    @Composable
    open fun descriptionTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[Caption1]
    }

    @Composable
    open fun sectionHeaderPrimaryTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return when (listItemInfo.style) {
            Bold -> {
                FluentTheme.aliasTokens.typography[Body1Strong]
            }

            Subtle -> {
                FluentTheme.aliasTokens.typography[Caption1]
            }
        }
    }

    @Composable
    open fun sectionHeaderActionTextTypography(listItemInfo: ListItemInfo): TextStyle {
        return when (listItemInfo.style) {
            Bold -> {
                FluentTheme.aliasTokens.typography[Body2Strong]
            }

            Subtle -> {
                FluentTheme.aliasTokens.typography[Caption1Strong]
            }
        }
    }

    @Composable
    open fun textAccessoryContentTextSpacing(listItemInfo: ListItemInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
    }
}