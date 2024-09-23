package com.microsoft.fluentui.tokenized.drawer

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupPositionProvider

object DraggableDefaults {
    val AnimationSpec = TweenSpec<Float>(durationMillis = 256)

    /**
     * The default velocity threshold for the drawer to snap to the nearest anchor.
     */
    val VelocityThreshold = 0.5f

    val PositionalThreshold = 0.5f

}
@Composable
fun convertDpToFloat(dpValue: Dp): Float {
    return with(LocalDensity.current) { dpValue.toPx() }
}

val EndDrawerPadding = 56.dp
val DrawerVelocityThreshold = 400.dp

const val DrawerOpenFraction = 0.5f

//Tag use for testing
const val DRAWER_HANDLE_TAG = "Fluent Drawer Handle"
const val DRAWER_CONTENT_TAG = "Fluent Drawer Content"
const val DRAWER_SCRIM_TAG = "Fluent Drawer Scrim"

//Drawer Handle height + padding
val DrawerHandleHeightOffset = 20.dp

class DrawerPositionProvider(val offset: IntOffset?) : PopupPositionProvider {
    override fun calculatePosition(
        anchorBounds: IntRect,
        windowSize: IntSize,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        if (offset != null) {
            return IntOffset(anchorBounds.left + offset.x, anchorBounds.top + offset.y)
        }
        return IntOffset(0, 0)
    }
}

@Composable
fun Scrim(
    open: Boolean,
    onClose: () -> Unit,
    fraction: () -> Float,
    color: Color,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {},
) {
    val dismissDrawer = if (open) {
        Modifier.pointerInput(onClose) {
            detectTapGestures {
                if (!preventDismissalOnScrimClick) {
                    onClose()
                }
                onScrimClick() //this function runs post onClose() so that the drawer is closed before the callback is invoked
            }
        }
    } else {
        Modifier
    }

    Canvas(
        Modifier
            .fillMaxSize()
            .then(dismissDrawer)
            .testTag(DRAWER_SCRIM_TAG)
    ) {
        drawRect(color, alpha = fraction())
    }
}