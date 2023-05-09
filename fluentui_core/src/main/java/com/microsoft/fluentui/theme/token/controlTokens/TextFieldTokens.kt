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
import kotlinx.parcelize.Parcelize

data class TextFieldInfo(
    val isStatusError: Boolean = false,
    val hasIcon: Boolean = true,
    val isFocused: Boolean = false,
    val textAvailable: Boolean = false
) : ControlInfo

@Parcelize
open class TextFieldTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundColor(textFieldInfo: TextFieldInfo): Brush {
        return SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
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
    open fun inputTextColor(textFieldInfo: TextFieldInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
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
            start = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160),
            end = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
        )
    }

    @Composable
    open fun labelPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return if (textFieldInfo.hasIcon)
            PaddingValues(
                start = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160) + this.leadingIconSize(
                    textFieldInfo
                )
            )
        else PaddingValues(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.SizeNone))
    }

    @Composable
    open fun assistiveTextPadding(textFieldInfo: TextFieldInfo): PaddingValues {
        return if (textFieldInfo.hasIcon)
            PaddingValues(
                start = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160) + this.leadingIconSize(
                    textFieldInfo
                ),
                top = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40),
                bottom = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
            )
        else PaddingValues(
            top = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40),
            bottom = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
        )
    }

    @Composable
    open fun leadingIconSize(textFieldInfo: TextFieldInfo): Dp =
        FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)

    @Composable
    open fun trailingIconSize(textFieldInfo: TextFieldInfo): Dp =
        FluentGlobalTokens.iconSize(FluentGlobalTokens.IconSizeTokens.IconSize240)

    @Composable
    open fun strokeWidth(textFieldInfo: TextFieldInfo): Dp =
        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)

}