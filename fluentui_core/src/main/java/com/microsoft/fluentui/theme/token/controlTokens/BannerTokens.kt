package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class BannerInfo(
    val isAccessoryButtonEnabled: Boolean = false
) : ControlInfo

@Parcelize
open class BannerTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundColor(bannerInfo: BannerInfo): Brush {
        return SolidColor(aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background4].value())
    }

    @Composable
    open fun textColor(bannerInfo: BannerInfo): Color {
        return aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
    }

    @Composable
    open fun actionIconColor(bannerInfo: BannerInfo): Color {
        return aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun leadingIconColor(bannerInfo: BannerInfo): Color {
        return aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun actionButtonColor(bannerInfo: BannerInfo): Color {
        return aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
    }

    @Composable
    open fun leadingIconSize(bannerInfo: BannerInfo): Dp = 24.dp

    @Composable
    open fun actionIconSize(bannerInfo: BannerInfo): Dp = 20.dp

    @Composable
    open fun padding(bannerInfo: BannerInfo): PaddingValues =
        bannerInfo.isAccessoryButtonEnabled.let {
            if (it) {
                PaddingValues(horizontal = 16.dp, vertical = 8.dp)
            } else {
                PaddingValues(horizontal = 16.dp, vertical = 12.dp)
            }
        }

    @Composable
    open fun textTypography(bannerInfo: BannerInfo): TextStyle {
        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun actionButtonTextTypography(bannerInfo: BannerInfo): TextStyle {
        return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]
    }

    @Composable
    open fun leadingIconAndTextSpacing(bannerInfo: BannerInfo): Dp = 12.dp

    @Composable
    open fun textAndActionButtonSpacing(bannerInfo: BannerInfo): Dp = 12.dp

    @Composable
    open fun accessoryActionButtonsSpacing(bannerInfo: BannerInfo): Dp = 24.dp

    @Composable
    open fun textAndAccessoryButtonSpacing(bannerInfo: BannerInfo): Dp = 8.dp
}