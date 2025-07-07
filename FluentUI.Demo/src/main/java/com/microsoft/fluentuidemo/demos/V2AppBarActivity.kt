package com.microsoft.fluentuidemo.demos

import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.boundsInWindow
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.core.view.drawToBitmap
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AppBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.AppBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.TooltipControls
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillBar
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlin.math.max
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.boundsInRoot
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.core.graphics.applyCanvas
import kotlin.math.roundToInt


const val APP_BAR_MODIFIABLE_PARAMETER_SECTION = "App Bar Modifiable Parameters"
const val APP_BAR_SUBTITLE_PARAM = "App Bar Subtitle Param"
const val APP_BAR_STYLE_PARAM = "App Bar AppBar Style Param"
const val APP_BAR_BUTTONBAR_PARAM = "App Bar ButtonBar Param"
const val APP_BAR_SEARCHBAR_PARAM = "App Bar SearchBar Param"
const val APP_BAR_LOGO_PARAM = "App Bar Logo Param"
const val APP_BAR_CENTER_ALIGN_PARAM = "App Bar Center Align Param"
const val APP_BAR_ENABLE_TOOLTIPS_PARAM = "App Bar Enable Tooltips Param"
const val APP_BAR_NAVIGATION_ICON_PARAM = "App Bar Navigation Icon Param"

class V2AppBarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens"

    @Composable
    fun backgroundComposable(){
        val scrollState = rememberScrollState()
        // Background that we want to capture a part of.
        Column(modifier = Modifier.verticalScroll(scrollState)) {
            for(i in 0..15){
                Image(
                    painter = painterResource(id = R.drawable.avatar_miguel_garcia), // Add a background_image to your drawables
                    contentDescription = "Background",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @Composable
    fun BlurredWindowDialog(
        onDismissRequest: () -> Unit,
        content: @Composable () -> Unit
    ) {
        // We must use a custom DialogProperties to disable the default platform decorations.
        val dialogProperties = DialogProperties(
            usePlatformDefaultWidth = false, // Allow us to control the size
            decorFitsSystemWindows = false // Important for edge-to-edge
        )

        Dialog(
            onDismissRequest = onDismissRequest,
            properties = dialogProperties
        ) {
            // This is the key: Find the Dialog's specific window.
            val dialogWindowProvider = LocalView.current.parent as? DialogWindowProvider
            val window = dialogWindowProvider?.window
            if (window != null) {
                window.setBackgroundBlurRadius(60)
            } // Set the blur radius
            // Apply the blur effect to the Dialog's window.
            SideEffect {
                if (window != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    window.attributes.gravity = android.view.Gravity.TOP or android.view.Gravity.START
                    window.setBackgroundBlurRadius(60)
                    window.addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)

                    // Make the dialog's background transparent so we can see the blur.
                    window.decorView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                    window.setDimAmount(0f) // No dimming of the background
                }
            }

            // This is where the content you want inside the blurred window goes.
            content()
        }
    }
    @Composable
    fun NonModalBlurredDialog(
        onDismissRequest: () -> Unit,
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

                    // CRUCIAL: The window's decor view itself must be transparent.
                    window.decorView.setBackgroundColor(android.graphics.Color.TRANSPARENT)
                }
            }
            content()
        }
    }
    @Composable
    fun foregroundComposable() {
        val context = LocalContext.current
        Column(
            modifier = Modifier   .background(Color.Transparent),
        ) {
            Button(
                onClick = {
                    Toast.makeText(context, "Button Clicked", Toast.LENGTH_SHORT).show()
                },
                modifier = Modifier
                    .padding(16.dp).background(Color.Transparent),
            )
            Text(
                text = "Now try to capture this background",
            )
            Text(
                text = "This text is inside the box whose background we want to capture.",
                modifier = Modifier
                    .padding(16.dp)
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setActivityContent {
            backgroundComposable()
            NonModalBlurredDialog(
                onDismissRequest = {}
            ) {
                foregroundComposable()
            }
        }
    }
}
