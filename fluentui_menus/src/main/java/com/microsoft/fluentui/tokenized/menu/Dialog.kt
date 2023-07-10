package com.microsoft.fluentui.tokenized.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.SecureFlagPolicy
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.DialogInfo
import com.microsoft.fluentui.theme.token.controlTokens.DialogTokens

const val TEST_TAG = "Dialog"

/**
 * Create a dialog with the given content. The dialog does not fill the screen and is normally
 * used for modal events that require users to take an action before they can proceed.
 * @param onDismiss Execute any instruction when user tries to close the dialog.
 * @param dismissOnBackPress dismiss the dialog when back button is pressed.
 * @param dismissOnClickedOutside dismiss the dialog when clicked outside the dialog box.
 * @param modifier Optional modifier for dialog
 * @param dialogTokens Optional tokens for customizing dialog's visual appearance
 * @param content content to be displayed inside the dialog
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Dialog(
    onDismiss: () -> Unit,
    dialogProperties: DialogProperties = DialogProperties(
        dismissOnBackPress = false,
        dismissOnClickOutside = false,
        securePolicy = SecureFlagPolicy.Inherit,
        usePlatformDefaultWidth = true
    ),
    modifier: Modifier = Modifier,
    dialogTokens: DialogTokens? = null,
    content: @Composable () -> Unit
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = dialogTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Dialog] as DialogTokens
    val dialogInfo = DialogInfo()
    val cornerRadius = token.cornerRadius(dialogInfo = dialogInfo)
    val backgroundBrush = token.backgroundBrush(dialogInfo = dialogInfo)
    val borderBrush = token.borderBrush(dialogInfo = dialogInfo)
    val elevation = token.elevation(dialogInfo = dialogInfo)
    val borderStrokeWidth = token.borderStrokeWidth(dialogInfo = dialogInfo)
    val shape = RoundedCornerShape(cornerRadius)
    Dialog(
        onDismissRequest = onDismiss,
        properties = dialogProperties
    ) {
        Box(
            modifier = modifier
                .shadow(elevation, shape, false)
                .clip(shape)
                .background(
                    backgroundBrush
                )
                .border(
                    borderStrokeWidth, borderBrush, shape
                )
                .testTag(TEST_TAG)
        ) {
            content()
        }
    }
}