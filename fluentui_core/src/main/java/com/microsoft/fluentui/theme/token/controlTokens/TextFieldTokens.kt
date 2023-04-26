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
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.ControlToken
import com.microsoft.fluentui.theme.token.GlobalTokens
import kotlinx.parcelize.Parcelize

data class TextFieldInfo(
    val isStatusError: Boolean = false,
    val hasIcon: Boolean = true,
    val isFocused: Boolean = false,
    val textAvailable: Boolean = false
) : ControlInfo

@Parcelize
open class TextFieldTokens : ControlToken, Parcelable {

    @Composable
    open fun backgroundColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[AliasTokens.NeutralBackgroundColorTokens.Background1].value()
    }

    @Composable
    open fun leadingIconColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isFocused && !textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()

    }

    @Composable
    open fun cursorColor(textFieldInfo: TextFieldInfo): Brush {
        return SolidColor(FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value())
    }

    @Composable
    open fun dividerColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else if (textFieldInfo.isFocused)
            FluentTheme.aliasTokens.brandStroke[AliasTokens.BrandStrokeColorTokens.BrandStroke1].value()
        else
            FluentTheme.aliasTokens.neutralStrokeColor[AliasTokens.NeutralStrokeColorTokens.Stroke2].value()

    }

    @Composable
    open fun labelColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else if (textFieldInfo.isFocused)
            FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun labelTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption2]
    }

    @Composable
    open fun assistiveTextColor(textFieldInfo: TextFieldInfo): Color {
        return if (textFieldInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[AliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun assistiveTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Caption2]
    }

    @Composable
    open fun hintColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun hintTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun inputTextColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
    }

    @Composable
    open fun inputTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun trailingAccessoryTextColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun trailingAccessoryTextTypography(textFieldInfo: TextFieldInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun leftRightPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return PaddingValues(
            start = GlobalTokens.size(GlobalTokens.SizeTokens.Size160),
            end = GlobalTokens.size(GlobalTokens.SizeTokens.Size160)
        )
    }

    @Composable
    open fun labelPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return if (textFieldInfo.hasIcon)
            PaddingValues(
                start = GlobalTokens.size(GlobalTokens.SizeTokens.Size160) + this.leadingIconSize(
                    textFieldInfo
                )
            )
        else PaddingValues(GlobalTokens.size(GlobalTokens.SizeTokens.SizeNone))
    }

    @Composable
    open fun assistiveTextPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return if (textFieldInfo.hasIcon)
            PaddingValues(
                start = GlobalTokens.size(GlobalTokens.SizeTokens.Size160) + this.leadingIconSize(
                    textFieldInfo
                ),
                top = GlobalTokens.size(GlobalTokens.SizeTokens.Size40),
                bottom = GlobalTokens.size(GlobalTokens.SizeTokens.Size40)
            )
        else PaddingValues(
            top = GlobalTokens.size(GlobalTokens.SizeTokens.Size40),
            bottom = GlobalTokens.size(GlobalTokens.SizeTokens.Size40)
        )
    }

    @Composable
    open fun leadingIconSize(textFieldInfo: TextFieldInfo): Dp =
        GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)

    @Composable
    open fun trailingIconSize(textFieldInfo: TextFieldInfo): Dp =
        GlobalTokens.iconSize(GlobalTokens.IconSizeTokens.IconSize240)

    @Composable
    open fun strokeWidth(textFieldInfo: TextFieldInfo): Dp =
        GlobalTokens.strokeWidth(GlobalTokens.StrokeWidthTokens.StrokeWidth05)

}