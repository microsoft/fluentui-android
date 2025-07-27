package com.microsoft.fluentui.tokenized.acrylicpane

import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneInfo
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneOrientation
import com.microsoft.fluentui.theme.token.controlTokens.AcrylicPaneTokens

@Composable
fun NonModalBlurredDialog(
    onDismissRequest: () -> Unit,
    orientation: AcrylicPaneOrientation = AcrylicPaneOrientation.BOTTOM,
    content: @Composable () -> Unit
) {
    val dialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false
    )

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = dialogProperties
    ) {
        val window = (LocalView.current.parent as? DialogWindowProvider)?.window

        SideEffect {
            if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                window.setBackgroundBlurRadius(60)
                window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                window.setDimAmount(0f)
                window.setGravity(
                    when (orientation) {
                        AcrylicPaneOrientation.TOP -> Gravity.TOP
                        AcrylicPaneOrientation.BOTTOM -> Gravity.BOTTOM
                        AcrylicPaneOrientation.CENTER -> Gravity.CENTER
                    }
                )
                // CRUCIAL: The window's decor view itself must be transparent.
                window.decorView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
        }
        content()
    }
}

@Composable
private fun AcrylicPane(
    modifier: Modifier = Modifier,
    orientation: AcrylicPaneOrientation = AcrylicPaneOrientation.BOTTOM,
    backgroundContent: @Composable () -> Unit,
    component: @Composable BoxScope.() -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        backgroundContent()

        NonModalBlurredDialog(
            onDismissRequest = {},
            orientation = orientation
        ) {
            Box(
                modifier = modifier
            ){
                component()
            }
        }
    }
}

fun roundToNearestTen(value: Int): Int { // Added for anti-aliasing
    return ((value + 5) / 10) * 10
}
/**
 * A composable function that creates an AcrylicPane with specified properties and content.
 *
 * @param modifier The modifier to be applied to the AcrylicPane.
 * @param paneHeight The height of the pane, default is 300.dp.
 * @param acrylicPaneStyle The style of the pane, default is FluentStyle.Neutral.
 * @param component The main composable content to be displayed within the pane.
 * @param backgroundContent The composable content to be displayed as the background of the pane.
 * @param acrylicPaneTokens Optional tokens to customize the appearance of the AcrylicPane.
 */

@Composable
fun AcrylicPane(modifier: Modifier = Modifier, orientation: AcrylicPaneOrientation = AcrylicPaneOrientation.BOTTOM, paneHeight: Dp = 300.dp, acrylicPaneStyle:FluentStyle = FluentStyle.Neutral, component: @Composable () -> Unit, backgroundContent: @Composable () -> Unit, acrylicPaneTokens: AcrylicPaneTokens? = null) {
    val paneInfo: AcrylicPaneInfo = AcrylicPaneInfo(style = acrylicPaneStyle, orientation = orientation)
    val newPaneHeight = roundToNearestTen(paneHeight.value.toInt()).dp
    val themeID = FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = acrylicPaneTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AcrylicPaneControlType] as AcrylicPaneTokens
    AcrylicPane(
        modifier = modifier
            .fillMaxWidth()
            .height(newPaneHeight)
            .background(
                token.acrylicPaneGradient(acrylicPaneInfo = paneInfo)
            ),
        orientation = orientation,
        backgroundContent = {
            backgroundContent()
        }
    ){
        component()
    }
}