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

open class PeoplePickerInfo() : TextFieldInfo()

@Parcelize
open class PeoplePickerTokens : TextFieldTokens() {

    @Composable
    open fun chipSpacing(peoplePickerInfo: PeoplePickerInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun hintColor(peoplePickerInfo: PeoplePickerInfo): Color {
        return FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
    }

    @Composable
    open fun hintTextTypography(peoplePickerInfo: PeoplePickerInfo): TextStyle {
        return FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
    }
}