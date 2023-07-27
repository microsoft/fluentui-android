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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.core.R
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Dismisscircle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.DividerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DividerTokens
import com.microsoft.fluentui.theme.token.controlTokens.TextFieldInfo
import com.microsoft.fluentui.theme.token.controlTokens.TextFieldTokens
import com.microsoft.fluentui.tokenized.divider.Divider

// Tests used for testing
const val TEXT_FIELD = "Fluent TEXT_FIELD"
const val TEXT_FIELD_ICON = "Fluent TEXT_FIELD_ICON"
const val TEXT_FIELD_LABEL = "Fluent TEXT_FIELD_LABEL"
const val TEXT_FIELD_HINT_TEXT = "Fluent TEXT_FIELD_HINT_TEXT"
const val TEXT_FIELD_ASSISTIVE_TEXT = "Fluent TEXT_FIELD_ASSISTIVE_TEXT"
const val TEXT_FIELD_SECONDARY_TEXT = "Fluent TEXT_FIELD_TRAILING_ACCESSORY_TEXT"

/**
 * API to create a customized TextField for users to edit text via software and hardware keyboard
 * which has support for label, assistive text, error strings.
 *
 * Whenever the user edits the text, onValueChange is called with the most up to date string
 * with which developer is expected to update their state.
 *
 * It is crucial that the value provided in the onValueChange is fed back into BasicTextField
 * in order to have the final state of the text being displayed.
 *
 * @param value Input String text to be shown in TextField
 * @param onValueChange The callback that is triggered when the input service updates the text.
 * An updated text comes as a parameter of the callback
 * @param modifier Optional modifier for the TextField
 * @param hintText Hint to be shown on TextField. Displayed when [value] is empty and TextField
 * doesn't have focus.
 * @param label String which acts as a description for the TextField.
 * @param assistiveText String which assists users with the TextField
 * @param trailingAccessoryText String to be placed towards the end of TextField as secondary text.
 * @param errorString String to describe the error. TextField goes in error mode if this is provided.
 * @param leadingRestIcon Icon which is displayed when the textField is in rest state.
 * @param leadingFocusIcon Icon which is displayed when the textField is in focus state.
 * @param leadingIconContentDescription String which acts as content description for leading icon.
 * @param trailingAccessoryIcon Icon which is displayed towards the end of textField and mainly
 * acts as dismiss icon.
 * @param keyboardOptions software keyboard options that contains configuration such as [KeyboardType] and [ImeAction].
 * @param keyboardActions when the input service emits an IME action, the corresponding callback is called.
 * Note that this IME action may be different from what you specified in [KeyboardOptions.imeAction].
 * @param visualTransformation he visual transformation filter for changing the visual representation
 * of the input. By default no visual transformation is applied.
 * @param textFieldTokens Optional Tokens to customize appearance of TextField.
 */
@Composable
fun TextField(
    value: String,
    onValueChange: ((String) -> Unit),
    modifier: Modifier = Modifier,
    hintText: String? = null,
    label: String? = null,
    assistiveText: String? = null,
    trailingAccessoryText: String? = null,
    errorString: String? = null,
    leadingRestIcon: ImageVector? = null,
    leadingFocusIcon: ImageVector? = null,
    leadingIconContentDescription: String? = null,
    trailingAccessoryIcon: FluentIcon? = FluentIcon(
        SearchBarIcons.Dismisscircle,
        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_clear_text)
    ),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    keyboardActions: KeyboardActions = KeyboardActions(),
    visualTransformation: VisualTransformation = VisualTransformation.None,
    textFieldContentDescription: String? = null,
    decorationBox: (@Composable (innerTextField: @Composable () -> Unit) -> Unit)? = null,
    textFieldTokens: TextFieldTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = textFieldTokens
        ?: (FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TextField] as TextFieldTokens)

    var isFocused: Boolean by rememberSaveable { mutableStateOf(false) }

    val textFieldInfo = TextFieldInfo(
        isStatusError = !errorString.isNullOrBlank(),
        hasIcon = (leadingRestIcon != null),
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
        .background(token.backgroundBrush(textFieldInfo))
        .padding(token.leftRightPadding(textFieldInfo))) {
        if (!label.isNullOrBlank()) {
            Spacer(Modifier.requiredHeight(12.dp))
            BasicText(
                label,
                style = token.labelTypography(textFieldInfo).merge(
                    TextStyle(
                        color = token.labelColor(textFieldInfo)
                    )
                ),
                modifier = Modifier
                    .padding(token.labelPadding(textFieldInfo))
                    .testTag(TEXT_FIELD_LABEL)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (leadingRestIcon != null) {
                Icon(
                    if (isFocused && errorString.isNullOrBlank() && leadingFocusIcon != null)
                        leadingFocusIcon
                    else
                        leadingRestIcon,
                    leadingIconContentDescription,
                    modifier = Modifier
                        .size(token.leadingIconSize(textFieldInfo))
                        .testTag(TEXT_FIELD_ICON),
                    tint = token.leadingIconColor(textFieldInfo)
                )
                Spacer(Modifier.requiredWidth(16.dp))
            }
            Column(Modifier.weight(1F)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BasicTextField(
                        value = value,
                        onValueChange = onValueChange,
                        modifier = Modifier
                            .semantics { contentDescription = textFieldContentDescription ?: "" }
                            .testTag(TEXT_FIELD)
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
                        decorationBox = decorationBox ?: { innerTextField ->
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
                                        modifier = Modifier.testTag(TEXT_FIELD_HINT_TEXT),
                                        style = token.hintTextTypography(textFieldInfo)
                                            .merge(
                                                TextStyle(
                                                    color = token.hintColor(textFieldInfo)
                                                )
                                            )
                                    )
                                }
                            }
                            innerTextField()
                        },
                        textStyle = token.inputTextTypography(textFieldInfo).merge(
                            TextStyle(
                                color = token.inputTextColor(textFieldInfo),
                                textDirection = TextDirection.ContentOrLtr
                            )
                        ),
                        cursorBrush = token.cursorColor(textFieldInfo)
                    )
                    if (!trailingAccessoryText.isNullOrBlank()) {
                        Spacer(Modifier.requiredWidth(8.dp))
                        BasicText(
                            trailingAccessoryText,
                            modifier = Modifier
                                .padding(vertical = 12.dp)
                                .testTag(TEXT_FIELD_SECONDARY_TEXT),
                            style = token.trailingAccessoryTextTypography(textFieldInfo)
                                .merge(
                                    TextStyle(
                                        color = token.trailingAccessoryTextColor(
                                            textFieldInfo
                                        )
                                    )
                                )
                        )
                    }
                    if (value.isNotBlank() && trailingAccessoryIcon?.isIconAvailable() == true) {
                        Icon(
                            trailingAccessoryIcon,
                            Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = LocalIndication.current,
                                    enabled = true,
                                    onClickLabel = trailingAccessoryIcon.contentDescription,
                                    role = Role.Button
                                ) {
                                    if (trailingAccessoryIcon.onClick != null)
                                        trailingAccessoryIcon.onClick!!.invoke()
                                    else
                                        onValueChange("")
                                }
                                .padding(8.dp)
                                .size(token.trailingIconSize(textFieldInfo))
                        )
                    }
                }
                Divider(
                    height = token.strokeWidth(textFieldInfo),
                    dividerToken = object : DividerTokens() {
                        @Composable
                        override fun verticalPadding(dividerInfo: DividerInfo): PaddingValues {
                            return PaddingValues(0.dp)
                        }

                        @Composable
                        override fun dividerBrush(dividerInfo: DividerInfo): Brush =
                            token.dividerColor(textFieldInfo)
                    }
                )
            }
        }
        if (!assistiveText.isNullOrBlank() || !errorString.isNullOrBlank()) {
            BasicText(
                errorString ?: assistiveText!!,
                style = token.assistiveTextTypography(textFieldInfo).merge(
                    TextStyle(
                        color = token.assistiveTextColor(textFieldInfo)
                    )
                ),
                modifier = Modifier
                    .padding(token.assistiveTextPadding(textFieldInfo))
                    .testTag(TEXT_FIELD_ASSISTIVE_TEXT)
            )
        }
    }
}