package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.core.R
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.DividerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DividerTokens
import com.microsoft.fluentui.theme.token.controlTokens.TextFieldInfo
import com.microsoft.fluentui.theme.token.controlTokens.TextFieldTokens
import com.microsoft.fluentui.tokenized.divider.Divider

@Composable
fun TextField(
    value: String,
    onValueChange: ((String) -> Unit),
    modifier: Modifier = Modifier,
    hintText: String? = null,
    label: String? = null,
    assistiveText: String? = null,
    rightAccessoryText: String? = null,
    errorString: String? = null,
    leftPrimaryIcon: ImageVector? = null,
    leftSecondaryIcon: ImageVector? = null,
    leftIconContentDescription: String? = null,
    rightAccessoryIcon: FluentIcon? = FluentIcon(
        SearchBarIcons.Dismisscircle,
        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_clear_text)
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textFieldTokens: TextFieldTokens = TextFieldTokens()
) {
    var isFocused: Boolean by rememberSaveable { mutableStateOf(false) }

    val textFieldInfo = TextFieldInfo(
        isStatusError = !errorString.isNullOrBlank(),
        hasIcon = (leftPrimaryIcon != null),
        isFocused = isFocused,
        textAvailable = value.isNotEmpty()
    )

    val focusRequester = remember { FocusRequester() }

    Column(modifier = modifier
        .onFocusChanged { focusState ->
            when {
                focusState.isFocused -> {
                    focusRequester.requestFocus()
                }
            }
        }
        .background(textFieldTokens.backgroundColor(textFieldInfo))
        .padding(textFieldTokens.leftRightPadding(textFieldInfo))) {
        if (!label.isNullOrBlank()) {
            Spacer(Modifier.requiredHeight(12.dp))
            BasicText(
                label,
                style = textFieldTokens.labelTypography(textFieldInfo).merge(
                    TextStyle(
                        color = textFieldTokens.labelColor(textFieldInfo)
                    )
                ),
                modifier = Modifier.padding(textFieldTokens.labelPadding(textFieldInfo))
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leftPrimaryIcon != null) {
                Icon(
                    if (isFocused && errorString.isNullOrBlank() && leftSecondaryIcon != null)
                        leftSecondaryIcon
                    else
                        leftPrimaryIcon,
                    leftIconContentDescription,
                    modifier = Modifier.size(textFieldTokens.leftIconSize(textFieldInfo)),
                    tint = textFieldTokens.leftIconColor(textFieldInfo)
                )
                Spacer(Modifier.requiredWidth(16.dp))
            }
            Column(Modifier.weight(1F)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .padding(vertical = 12.dp)
                            .weight(1F)
                            .focusRequester(focusRequester)
                            .onFocusChanged { state ->
                                isFocused = false
                                when {
                                    state.isFocused ->
                                        isFocused = true
                                }
                            },
                        singleLine = true,
                        keyboardOptions = keyboardOptions,
                        keyboardActions = keyboardActions,
                        visualTransformation = visualTransformation,
                        decorationBox = @Composable { innerTextField ->
                            if (value.isEmpty() && !hintText.isNullOrBlank()) {
                                Box(
                                    Modifier.fillMaxWidth(),
                                    contentAlignment = if (LocalLayoutDirection.current == LayoutDirection.Rtl)
                                        Alignment.CenterEnd
                                    else
                                        Alignment.CenterStart
                                ) {
                                    BasicText(
                                        hintText,
                                        style = textFieldTokens.hintTextTypography(textFieldInfo).merge(
                                            TextStyle(
                                                color = textFieldTokens.hintColor(textFieldInfo)
                                            )
                                        )
                                    )
                                }
                            }
                            innerTextField()
                        },
                        textStyle = textFieldTokens.inputTextTypography(textFieldInfo).merge(
                            TextStyle(
                                color = textFieldTokens.inputTextColor(textFieldInfo),
                                textDirection = TextDirection.ContentOrLtr
                            )
                        ),
                        cursorBrush = textFieldTokens.cursorColor(textFieldInfo)
                    )
                    if (!rightAccessoryText.isNullOrBlank()) {
                        Spacer(Modifier.requiredWidth(8.dp))
                        BasicText(
                            rightAccessoryText,
                            modifier = Modifier.padding(vertical = 12.dp),
                            style = textFieldTokens.rightAccessoryTextTypography(textFieldInfo).merge(
                                TextStyle(
                                    color = textFieldTokens.rightAccessoryTextColor(textFieldInfo)
                                )
                            )
                        )
                    }
                    if (value.isNotBlank() && rightAccessoryIcon?.isIconAvailable() == true) {
                        Icon(
                            rightAccessoryIcon,
                            Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current,
                                    enabled = true,
                                    onClickLabel = rightAccessoryIcon.contentDescription,
                                    role = Role.Button
                                ) {
                                    if (rightAccessoryIcon.onClick != null)
                                        rightAccessoryIcon.onClick!!.invoke()
                                    else
                                        onValueChange("")
                                }
                                .padding(8.dp)
                                .size(textFieldTokens.rightIconSize(textFieldInfo))
                        )
                    }
                }
                Divider(
                    height = textFieldTokens.strokeWidth(textFieldInfo),
                    dividerToken = object : DividerTokens() {
                        @Composable
                        override fun verticalPadding(dividerInfo: DividerInfo): PaddingValues {
                            return PaddingValues(0.dp)
                        }

                        @Composable
                        override fun dividerColor(dividerInfo: DividerInfo): Color =
                            textFieldTokens.dividerColor(textFieldInfo)
                    }
                )
            }
        }
        if (!assistiveText.isNullOrBlank() || !errorString.isNullOrBlank()) {
            BasicText(
                errorString ?: assistiveText!!,
                style = textFieldTokens.assistiveTextTypography(textFieldInfo).merge(
                    TextStyle(
                        color = textFieldTokens.assistiveTextColor(textFieldInfo)
                    )
                ),
                modifier = Modifier.padding(textFieldTokens.assistiveTextPadding(textFieldInfo))
            )
        }
    }
}