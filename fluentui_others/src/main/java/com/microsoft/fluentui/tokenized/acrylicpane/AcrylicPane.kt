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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
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
private fun BlurBehindDialog(
    orientation: AcrylicPaneOrientation = AcrylicPaneOrientation.BOTTOM,
    blurRadius: Int = 60,
    offset: IntOffset = IntOffset(0, 0),
    content: @Composable () -> Unit
) {
    val dialogProperties = DialogProperties(
        usePlatformDefaultWidth = false,
        decorFitsSystemWindows = false,
        dismissOnBackPress = false,
        dismissOnClickOutside = false
    )

    Dialog(
        onDismissRequest = {},
        properties = dialogProperties
    ) {
        val window = (LocalView.current.parent as? DialogWindowProvider)?.window

        SideEffect {
            if (window != null) {
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)
                window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    window.setBackgroundBlurRadius(blurRadius)
                }
                window.setDimAmount(0f)
                window.setGravity(
                    when (orientation) {
                        AcrylicPaneOrientation.TOP -> Gravity.TOP
                        AcrylicPaneOrientation.BOTTOM -> Gravity.BOTTOM
                        AcrylicPaneOrientation.CENTER -> Gravity.CENTER
                    }
                )
                window.attributes.x = offset.x
                window.attributes.y = offset.y
                window.decorView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
            }
        }
        content()
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
fun AcrylicPane(
    modifier: Modifier = Modifier,
    orientation: AcrylicPaneOrientation = AcrylicPaneOrientation.BOTTOM,
    offset: IntOffset = IntOffset(0, 0),
    paneHeight: Dp = 300.dp,
    acrylicPaneStyle: FluentStyle = FluentStyle.Neutral,
    component: @Composable () -> Unit,
    backgroundContent: @Composable () -> Unit,
    acrylicPaneTokens: AcrylicPaneTokens? = null
) {
    val paneInfo: AcrylicPaneInfo =
        AcrylicPaneInfo(style = acrylicPaneStyle, orientation = orientation)
    val newPaneHeight = roundToNearestTen(paneHeight.value.toInt()).dp
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = acrylicPaneTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AcrylicPaneControlType] as AcrylicPaneTokens
    val backgroundColor: Brush = token.acrylicPaneGradient(acrylicPaneInfo = paneInfo)
    val blurRadius: Int = token.acrylicPaneBlurRadius(acrylicPaneInfo = paneInfo)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        backgroundContent()

        BlurBehindDialog(
            orientation = orientation,
            blurRadius = blurRadius,
            offset = offset
        ) {
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(newPaneHeight)
                    .background(
                        backgroundColor
                    )
            ) {
                component()
            }
        }
    }
}