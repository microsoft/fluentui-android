package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.*
import kotlinx.parcelize.Parcelize

data class PeoplePickerInfo(
    val style: FluentStyle
):ControlInfo
@Parcelize
open class PeoplePickerTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun accessoryPadding(peoplePickerInfo: PeoplePickerInfo): PaddingValues{
        return PaddingValues(horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size160))
    }

    @Composable
    open fun chipPadding(peoplePickerInfo: PeoplePickerInfo): PaddingValues{
        return PaddingValues(vertical = GlobalTokens.size(GlobalTokens.SizeTokens.Size120), horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size80))
    }

    @Composable
    open fun trailingAccessoryEndPadding(peoplePickerInfo: PeoplePickerInfo): PaddingValues{
        return PaddingValues(horizontal = GlobalTokens.size(GlobalTokens.SizeTokens.Size120))
    }

    @Composable
    open fun maxHeight(peoplePickerInfo: PeoplePickerInfo): Dp{
        return GlobalTokens.size(GlobalTokens.SizeTokens.Size480)
    }

    @Composable
    open fun leadingIconSize(peoplePickerInfo: PeoplePickerInfo): Dp {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun trailingIconSize(peoplePickerInfo: PeoplePickerInfo): Dp {
        return GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)
    }

    @Composable
    open fun textColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return when (peoplePickerInfo.style) {
            FluentStyle.Neutral ->
                FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                    themeMode = FluentTheme.themeMode
                )
            FluentStyle.Brand ->
                FluentColor(
                    light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        ThemeMode.Light
                    ),
                    dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        ThemeMode.Dark
                    )
                ).value(themeMode = FluentTheme.themeMode)
        }
    }
    @Composable
    open fun typography(peoplePickerInfo: PeoplePickerInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
    }

}