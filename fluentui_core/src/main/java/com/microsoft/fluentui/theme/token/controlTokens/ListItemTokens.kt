package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Alignment
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.AliasTokens.BrandForegroundColorTokens.BrandForeground1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralBackgroundColorTokens.Background1Pressed
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground1
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralForegroundColorTokens.Foreground3
import com.microsoft.fluentui.theme.token.AliasTokens.NeutralStrokeColorTokens.Stroke2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body1
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body1Strong
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body2
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Body2Strong
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Caption1
import com.microsoft.fluentui.theme.token.AliasTokens.TypographyTokens.Caption1Strong
import com.microsoft.fluentui.theme.token.GlobalTokens.BorderSizeTokens.Thin
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.AvatarCarousel
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.ThreeLine
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.TwoLine
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.OneLine
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.SectionDescription
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.SectionHeader
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.AccessoryText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.ActionText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.DescriptionText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.FirstName
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.LastName
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SecondarySubLabelText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.SubLabelText
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType.Text
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Bottom
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Standard
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Subtle
import kotlinx.parcelize.Parcelize

enum class ListItemType {
    OneLine,
    TwoLine,
    ThreeLine,
    AvatarCarousel,
    SectionHeader,
    SectionDescription
}
enum class ListTextType {
    Text,
    SubLabelText,
    SecondarySubLabelText,
    AccessoryText,
    ActionText,
    DescriptionText,
    FirstName,
    LastName
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
    No_Border,
    Top,
    Bottom,
    Top_Bottom
}

enum class TextPlacement {
    Top,
    Bottom
}

enum class AvatarCarouselSize{
    Medium,
    Large
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
                    )
                )

            AccessoryText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            SubLabelText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            SecondarySubLabelText ->
                StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            ActionText -> StateColor(
                rest = FluentTheme.aliasTokens.brandForegroundColor[BrandForeground1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            DescriptionText -> StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            FirstName -> StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground1].value(
                    themeMode = FluentTheme.themeMode
                )
            )
            LastName -> StateColor(
                rest = FluentTheme.aliasTokens.neutralForegroundColor[Foreground3].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        }
    }

    @Composable
    open fun padding(size:SpacingTokens): Dp {
        return GlobalTokens.spacing(size)
    }

    @Composable
    open fun borderSize(): Dp {
        return GlobalTokens.borderSize(Thin)
    }

    @Composable
    open fun textSize(textType: ListTextType): FontInfo {
        return when (textType) {
            Text -> FluentTheme.aliasTokens.typography[Body1]
            AccessoryText -> FluentTheme.aliasTokens.typography[Body1]
            SubLabelText -> FluentTheme.aliasTokens.typography[Body2]
            SecondarySubLabelText -> FluentTheme.aliasTokens.typography[Caption1]
            DescriptionText -> FluentTheme.aliasTokens.typography[Caption1]
            ActionText -> FluentTheme.aliasTokens.typography[Caption1Strong]
            FirstName -> FluentTheme.aliasTokens.typography[Body2]
            LastName -> FluentTheme.aliasTokens.typography[Caption1]
        }
    }

    @Composable
    open fun textSize(textType: ListTextType, style: SectionHeaderStyle): FontInfo {
        return when (style) {
            Standard -> {
                return when(textType){
                    Text -> FluentTheme.aliasTokens.typography[Body1Strong]
                    ActionText -> FluentTheme.aliasTokens.typography[Body2Strong]
                    else -> FluentTheme.aliasTokens.typography[Body1Strong]
                }
            }
            Subtle -> {
                return when(textType){
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
            AvatarCarousel -> 88.dp
            SectionHeader -> 48.dp
            SectionDescription -> 68.dp
        }
    }

    @Composable
    open fun borderInset(inset:BorderInset): Dp{
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
    open fun descriptionPlacement(placement: TextPlacement): Alignment.Vertical{
        return when(placement){
            Top -> Alignment.Top
            Bottom -> Alignment.Bottom
        }
    }

    @Composable
    open fun avatarTextHorizontalPadding(carouselSize: AvatarCarouselSize): Dp{
        return when(carouselSize){
            AvatarCarouselSize.Medium -> 8.dp
            AvatarCarouselSize.Large -> 2.dp
        }
    }

    @Composable
    open fun avatarTextVerticalPadding(carouselSize: AvatarCarouselSize): Dp{
        return when(carouselSize){
            AvatarCarouselSize.Medium -> 8.dp
            AvatarCarouselSize.Large -> 12.dp
        }
    }
}