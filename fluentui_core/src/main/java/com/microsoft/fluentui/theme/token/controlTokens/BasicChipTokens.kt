package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.StateColor
import kotlinx.android.parcel.Parcelize

open class BasicChipInfo : ControlInfo

@Parcelize
open class BasicChipTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(basicChipInfo: BasicChipInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = FluentTheme.themeMode
                )
            ),
            selected = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5Selected].value(
                    themeMode = FluentTheme.themeMode
                )
            ),
            disabled = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.BackgroundDisabled].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        )
    }

    @Composable
    open fun textColor(basicChipInfo: BasicChipInfo): StateColor {

        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                themeMode = FluentTheme.themeMode
            ),
            selected = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                themeMode = FluentTheme.themeMode
            ),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cornerRadius(basicChipInfo: BasicChipInfo): Dp {
        return FluentGlobalTokens.CornerRadiusTokens.CornerRadius80.value
    }

    @Composable
    open fun typography(basicChipInfo: BasicChipInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2]
    }

    @Composable
    open fun padding(basicChipInfo: BasicChipInfo): PaddingValues {
        return PaddingValues(
            horizontal = FluentGlobalTokens.SizeTokens.Size120.value,
            vertical = FluentGlobalTokens.SizeTokens.Size80.value
        )
    }

    @Composable
    open fun horizontalSpacing(basicChipInfo: BasicChipInfo): Dp {
        return FluentGlobalTokens.SizeTokens.Size80.value
    }
}