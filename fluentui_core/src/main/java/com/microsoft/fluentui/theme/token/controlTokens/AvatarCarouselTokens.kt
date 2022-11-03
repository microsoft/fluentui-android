package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

enum class AvatarCarouselSize{
    Medium,
    Large
}
enum class TextType {
    Text,
    SubText
}

@Parcelize
open class AvatarCarouselTokens : ControlToken, Parcelable {
    @Composable
    open fun getAvatarSize(carouselSize:AvatarCarouselSize):AvatarSize{
        return when(carouselSize){
            AvatarCarouselSize.Medium -> AvatarSize.XLarge
            AvatarCarouselSize.Large -> AvatarSize.XXLarge
        }
    }
    @Composable
    open fun backgroundColor(): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                themeMode = FluentTheme.themeMode
            ),
            pressed = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1Pressed].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun getTextColor(textType: TextType, enabled:Boolean):Color{
        return when(textType){
            TextType.Text -> {
                return if (enabled) {
                    FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        themeMode = FluentTheme.themeMode
                    )
                }else{
                    FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = FluentTheme.themeMode
                    )
                }
            }
            TextType.SubText -> {
                return if (enabled) {
                    FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        themeMode = FluentTheme.themeMode
                    )
                }else{
                    FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = FluentTheme.themeMode
                    )
                }
            }
        }
    }
    @Composable
    open fun getTextSize(textType: TextType, carouselSize: AvatarCarouselSize): FontInfo {
        return when(carouselSize){
            AvatarCarouselSize.Medium ->
                return when(textType){
                    TextType.Text -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
                    TextType.SubText -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
                }
            AvatarCarouselSize.Large ->
                return when(textType){
                    TextType.Text -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body2]
                    TextType.SubText -> FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption1]
                }
        }
    }
    @Composable
    open fun padding(size: AvatarCarouselSize): Dp {
        return when(size){
            AvatarCarouselSize.Medium -> GlobalTokens.spacing(GlobalTokens.SpacingTokens.XSmall)
            AvatarCarouselSize.Large -> GlobalTokens.spacing(GlobalTokens.SpacingTokens.Small)
        }
    }
}