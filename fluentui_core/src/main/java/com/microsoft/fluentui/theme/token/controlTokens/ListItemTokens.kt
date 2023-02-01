package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
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
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.*
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

enum class ListTextType {
    Text,
    SubText,
    SecondarySubText,
    AccessoryText,
    ActionText,
    DescriptionText
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

@Parcelize
open class ListItemTokens : ControlToken, Parcelable {
    @Composable
    open fun backgroundColor(): StateColor {
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
    open fun borderColor(): StateColor {
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
    open fun iconColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun textColor(textType: ListTextType): StateColor {
        return when (textType) {
            Text ->
                StateColor(
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

            AccessoryText ->
                StateColor(
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
            SubText ->
                StateColor(
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
            SecondarySubText ->
                StateColor(
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
            ActionText -> StateColor(
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
            DescriptionText -> StateColor(
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
    }

    @Composable
    open fun unreadDotColor():Color {
        return FluentTheme.aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun padding(size: SpacingTokens): Dp {
        return GlobalTokens.spacing(size)
    }

    @Composable
    open fun borderSize(): Dp {
        return GlobalTokens.strokeWidth(StrokeWidth15)
    }

    @Composable
    open fun chevronTint(): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            FluentTheme.themeMode
        )
    }

    @Composable
    open fun textSize(textType: ListTextType): FontInfo {
        return when (textType) {
            Text -> FluentTheme.aliasTokens.typography[Body1]
            AccessoryText -> FluentTheme.aliasTokens.typography[Body1]
            SubText -> FluentTheme.aliasTokens.typography[Body2]
            SecondarySubText -> FluentTheme.aliasTokens.typography[Caption1]
            DescriptionText -> FluentTheme.aliasTokens.typography[Caption1]
            ActionText -> FluentTheme.aliasTokens.typography[Caption1Strong]
        }
    }

    @Composable
    open fun textSize(textType: ListTextType, style: SectionHeaderStyle): FontInfo {
        return when (style) {
            Standard -> {
                return when (textType) {
                    Text -> FluentTheme.aliasTokens.typography[Body1Strong]
                    ActionText -> FluentTheme.aliasTokens.typography[Body2Strong]
                    else -> FluentTheme.aliasTokens.typography[Body1Strong]
                }
            }
            Subtle -> {
                return when (textType) {
                    Text -> FluentTheme.aliasTokens.typography[Caption1]
                    ActionText -> FluentTheme.aliasTokens.typography[Caption1Strong]
                    else -> FluentTheme.aliasTokens.typography[Caption1]
                }
            }
        }
    }

    @Composable
    open fun cellHeight(listItemType: ListItemType): Dp {
        return when (listItemType) {
            OneLine -> 48.dp
            TwoLine -> 68.dp
            ThreeLine -> 88.dp
            SectionHeader -> 48.dp
            SectionDescription -> 68.dp
        }
    }

    @Composable
    open fun borderInset(inset: BorderInset): Dp {
        return when (inset) {
            BorderInset.None -> 0.dp
            BorderInset.Medium -> 16.dp
            BorderInset.Large -> 56.dp
            BorderInset.XXLarge -> 68.dp
            BorderInset.XXXXLarge -> 72.dp
            BorderInset.XXXXXXLarge -> 108.dp
        }
    }

    @Composable
    open fun descriptionPlacement(placement: TextPlacement): Alignment.Vertical {
        return when (placement) {
            Top -> Alignment.Top
            Bottom -> Alignment.Bottom
        }
    }
}