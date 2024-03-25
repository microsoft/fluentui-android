package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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
import kotlinx.parcelize.Parcelize

open class TextFieldInfo(
    val isStatusError: Boolean = false,
    val hasIcon: Boolean = true,
    val isFocused: Boolean = false,
    val textAvailable: Boolean = false
) : ControlInfo

@Parcelize
open class TextFieldTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundBrush(textFieldInfo: TextFieldInfo): Brush {
        return SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
    }

    @Composable
    open fun textAreaBackgroundBrush(textFieldInfo: TextFieldInfo): StateBrush {
        return StateBrush(
            rest = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value()),
            disabled = SolidColor(
                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                    themeMode = FluentTheme.themeMode
                )
            )
        )
    }

    @Composable
    open fun leadingIconColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isFocused && !textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()

    }

    @Composable
    open fun cursorColor(textFieldInfo: TextFieldInfo): Brush {
        return SolidColor(FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value())
    }

    @Composable
    open fun dividerColor(textFieldInfo: TextFieldInfo): Brush {
        return SolidColor(
            if (textFieldInfo.isStatusError)
                FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
            else if (textFieldInfo.isFocused)
                FluentTheme.aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1].value()
            else
                FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value()
        )
    }

    @Composable
    open fun labelColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else if (textFieldInfo.isFocused)
            FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun labelTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]
    }

    @Composable
    open fun assistiveTextColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun assistiveTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]
    }

    @Composable
    open fun hintColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun hintTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun inputTextColor(textFieldInfo: TextFieldInfo): StateColor {
        return StateColor(
            rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(),
            disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value()
        )
    }

    @Composable
    open fun inputTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun trailingAccessoryTextColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun trailingAccessoryTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun leftRightPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return PaddingValues(
            start = FluentGlobalTokens.SizeTokens.Size160.value,
            end = FluentGlobalTokens.SizeTokens.Size160.value
        )
    }

    @Composable
    open fun labelPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return if (textFieldInfo.hasIcon)
            PaddingValues(
                start = FluentGlobalTokens.SizeTokens.Size160.value + this.leadingIconSize(
                    textFieldInfo
                )
            )
        else PaddingValues(FluentGlobalTokens.SizeTokens.SizeNone.value)
    }

    @Composable
    open fun assistiveTextPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return if (textFieldInfo.hasIcon)
            PaddingValues(
                start = FluentGlobalTokens.SizeTokens.Size160.value + this.leadingIconSize(
                    textFieldInfo
                ),
                top = FluentGlobalTokens.SizeTokens.Size40.value,
                bottom = FluentGlobalTokens.SizeTokens.Size40.value
            )
        else PaddingValues(
            top = FluentGlobalTokens.SizeTokens.Size40.value,
            bottom = FluentGlobalTokens.SizeTokens.Size40.value
        )
    }

    @Composable
    open fun leadingIconSize(textFieldInfo: TextFieldInfo): Dp =
        FluentGlobalTokens.IconSizeTokens.IconSize240.value

    @Composable
    open fun trailingIconSize(textFieldInfo: TextFieldInfo): Dp =
        FluentGlobalTokens.IconSizeTokens.IconSize240.value

    @Composable
    open fun strokeWidth(textFieldInfo: TextFieldInfo): Dp =
        FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05.value

}