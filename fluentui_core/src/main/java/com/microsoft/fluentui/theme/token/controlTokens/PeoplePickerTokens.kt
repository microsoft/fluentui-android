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

data class PeoplePickerInfo(
    val isStatusError: Boolean = false,
    val isFocused: Boolean = false
) : ControlInfo

@Parcelize
open class PeoplePickerTokens : IControlToken, Parcelable {

    @Composable
    open fun backgroundColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun leadingAccessorySpacing(peoplePickerInfo: PeoplePickerInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160)
    }

    @Composable
    open fun chipTopPadding(peoplePickerInfo: PeoplePickerInfo): PaddingValues {
        return PaddingValues(vertical = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
    }

    @Composable
    open fun chipSpacing(peoplePickerInfo: PeoplePickerInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun trailingAccessoryEndPadding(peoplePickerInfo: PeoplePickerInfo): PaddingValues {
        return PaddingValues(horizontal = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
    }

    @Composable
    open fun minHeight(peoplePickerInfo: PeoplePickerInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size480)
    }

    @Composable
    open fun textColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    }

    @Composable
    open fun typography(peoplePickerInfo: PeoplePickerInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }

    @Composable
    open fun cursorColor(peoplePickerInfo: PeoplePickerInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }
    @Composable
    open fun labelTypography(peoplePickerInfo: PeoplePickerInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]
    }
    @Composable
    open fun labelColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return if (peoplePickerInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else if (peoplePickerInfo.isFocused)
            FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }
    @Composable
    open fun assistiveTextColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return if (peoplePickerInfo.isStatusError)
            FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
        else
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun assistiveTextTypography(peoplePickerInfo: PeoplePickerInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2]
    }

    @Composable
    open fun assistiveTextPadding(peoplePickerInfo: PeoplePickerInfo): PaddingValues {
        return PaddingValues(
            top = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40),
            bottom = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
        )
    }
    @Composable
    open fun hintColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun hintTextTypography(peoplePickerInfo: PeoplePickerInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }

    open fun strokeWidth(peoplePickerInfo: PeoplePickerInfo): Dp =
        FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)

    @Composable
    open fun dividerColor(peoplePickerInfo: PeoplePickerInfo): Brush {
        return SolidColor(
            if (peoplePickerInfo.isStatusError)
                FluentTheme.aliasTokens.errorAndStatusColor[FluentAliasTokens.ErrorAndStatusColorTokens.DangerForeground1].value()
            else if (peoplePickerInfo.isFocused)
                FluentTheme.aliasTokens.brandStroke[FluentAliasTokens.BrandStrokeColorTokens.BrandStroke1].value()
            else
                FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value()
        )
    }
}