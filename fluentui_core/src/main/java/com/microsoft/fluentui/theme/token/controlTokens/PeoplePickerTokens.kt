package com.microsoft.fluentui.theme.token.controlTokens

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import kotlinx.parcelize.Parcelize

open class PeoplePickerInfo() : TextFieldInfo()

@Parcelize
open class PeoplePickerTokens : TextFieldTokens() {

    @Composable
    open fun chipHorizontalSpacing(peoplePickerInfo: PeoplePickerInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size80)
    }

    @Composable
    open fun chipVerticalSpacing(peoplePickerInfo: PeoplePickerInfo): Dp {
        return FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)
    }
}