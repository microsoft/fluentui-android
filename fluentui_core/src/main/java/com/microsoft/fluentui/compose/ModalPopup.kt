package com.microsoft.fluentui.compose

import android.content.Context
import android.graphics.Outline
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.mandatorySystemGestures
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.tappableElement
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.semantics.popup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import java.util.UUID

/**
 * Popup specific for modal bottom drawer.
 */
@Composable
fun ModalPopup(
    onDismissRequest:(() -> Unit)? = null,
    windowInsetsType: Int = WindowInsetsCompat.Type.systemBars(),
    content: @Composable () -> Unit,
) {
    val properties = PopupProperties()
    val view = LocalView.current
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current
    val parentComposition = rememberCompositionContext()
    val currentContent by rememberUpdatedState(content)
    val id = rememberSaveable { UUID.randomUUID() }
    val modalWindow = remember {
        ModalWindow(
            onDismissRequest = onDismissRequest,
            properties = properties,
            composeView = view,
            density = density,
            saveId = id
        ).apply {
            setCustomContent(parentComposition) {
                Box(
                    Modifier
                        .semantics { this.popup() }
                        // Get the size of the content
                        .onSizeChanged {
                            popupContentSize = it
                        }
                        // Hide the popup while we can't position it correctly
                        .alpha(if (canCalculatePosition) 1f else 0f)
                        .windowInsetsPadding(
                            convertWindowInsetsCompatTypeToWindowInsets(windowInsetsType)
                        )
                        .imePadding()
                ) {
                    currentContent()
                }
            }
        }
    }

    DisposableEffect(modalWindow) {
        modalWindow.show()
        modalWindow.updateParameters(
            onDismissRequest = onDismissRequest,
            properties = properties,
            layoutDirection = layoutDirection
        )
        onDispose {
            modalWindow.disposeComposition()
            modalWindow.dismiss()
        }
    }

    Layout(
        content = {},
        modifier = Modifier
            .onGloballyPositioned { childCoordinates ->
                val parentCoordinates = childCoordinates.parentLayoutCoordinates
                if (parentCoordinates != null) {
                    modalWindow.updateParentLayoutCoordinates(parentCoordinates)
                }
            }
    ) { _, _ ->
        modalWindow.parentLayoutDirection = layoutDirection
        layout(0, 0) {}
    }
}

@Composable
fun convertWindowInsetsCompatTypeToWindowInsets(windowInsetsCompatType: Int): WindowInsets {
    return when (windowInsetsCompatType) {
        WindowInsetsCompat.Type.statusBars() -> WindowInsets.statusBars
        WindowInsetsCompat.Type.navigationBars() -> WindowInsets.navigationBars
        WindowInsetsCompat.Type.systemBars() -> WindowInsets.systemBars
        WindowInsetsCompat.Type.ime() -> WindowInsets.ime
        WindowInsetsCompat.Type.tappableElement() -> WindowInsets.tappableElement
        WindowInsetsCompat.Type.displayCutout() -> WindowInsets.displayCutout
        WindowInsetsCompat.Type.captionBar() -> WindowInsets.captionBar
        WindowInsetsCompat.Type.mandatorySystemGestures() -> WindowInsets.mandatorySystemGestures
        else -> WindowInsets.systemBars
    }
}

/** Custom compose view for [BottomDrawer] */
private class ModalWindow(
    private var onDismissRequest: (() -> Unit)? = null,
    private var properties: PopupProperties,
    private val composeView: View,
    density: Density,
    saveId: UUID,
    private val popupLayoutHelper: PopupLayoutHelperImpl = if (Build.VERSION.SDK_INT >= 29) {
        PopupLayoutHelperImpl29()
    } else {
        PopupLayoutHelperImpl()
    }
) :
    AbstractComposeView(composeView.context),
    ViewRootForInspector {
    private val windowManager =
        composeView.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager

    private val params: WindowManager.LayoutParams = createLayoutParams()
    var parentLayoutDirection: LayoutDirection = LayoutDirection.Ltr
    var popupContentSize: IntSize? by mutableStateOf(null)
    private var parentLayoutCoordinates: LayoutCoordinates? by mutableStateOf(null)
    val canCalculatePosition by derivedStateOf {
        parentLayoutCoordinates != null && popupContentSize != null
    }

    override val subCompositionView: AbstractComposeView get() = this

    init {
        id = android.R.id.content
        // Set up view owners
        this.setViewTreeLifecycleOwner(composeView.findViewTreeLifecycleOwner())
        this.setViewTreeViewModelStoreOwner(composeView.findViewTreeViewModelStoreOwner())
        setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())
        setTag(androidx.compose.ui.R.id.compose_view_saveable_id_tag, "Popup:$saveId")
        // Enable children to draw their shadow by not clipping them
        clipChildren = false
        with(density) { elevation = 8.dp.toPx() }
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, result: Outline) {
                result.setRect(0, 0, view.width, view.height)
                result.alpha = 0f
            }
        }
    }

    private var content: @Composable () -> Unit by mutableStateOf({})

    override var shouldCreateCompositionOnAttachedToWindow: Boolean = false
        private set

    fun show() {
        windowManager.addView(this, params)
    }

    fun setCustomContent(
        parent: CompositionContext? = null,
        content: @Composable () -> Unit
    ) {
        setParentCompositionContext(parent)
        this.content = content
        shouldCreateCompositionOnAttachedToWindow = true
    }

    @Composable
    override fun Content() {
        content()
    }

    private fun focusable(isFocusable: Boolean) = applyNewFlags(
        if (!isFocusable) {
            params.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        } else {
            params.flags and (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv())
        }
    )

    private fun applyNewFlags(flags: Int) {
        params.flags = flags
        popupLayoutHelper.updateViewLayout(windowManager, this, params)
    }

    fun updateParameters(
        onDismissRequest: (() -> Unit)?,
        properties: PopupProperties,
        layoutDirection: LayoutDirection
    ) {
        this.onDismissRequest = onDismissRequest
        if (properties.usePlatformDefaultWidth && !this.properties.usePlatformDefaultWidth) {
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            popupLayoutHelper.updateViewLayout(windowManager, this, params)
        }
        this.properties = properties
        focusable(properties.focusable)
        superSetLayoutDirection(layoutDirection)
    }

    fun updateParentLayoutCoordinates(parentLayoutCoordinates: LayoutCoordinates) {
        this.parentLayoutCoordinates = parentLayoutCoordinates
    }

    fun dismiss() {
        this.setViewTreeLifecycleOwner(null)
        setViewTreeSavedStateRegistryOwner(null)
        windowManager.removeViewImmediate(this)
    }

    override fun setLayoutDirection(layoutDirection: Int) {
        // Do nothing.
    }

    fun superSetLayoutDirection(layoutDirection: LayoutDirection) {
        val direction = when (layoutDirection) {
            LayoutDirection.Ltr -> android.util.LayoutDirection.LTR
            LayoutDirection.Rtl -> android.util.LayoutDirection.RTL
        }
        super.setLayoutDirection(direction)
    }

    private fun createLayoutParams(): WindowManager.LayoutParams{
        return WindowManager.LayoutParams().apply {
            // Position bottom sheet from the bottom of the screen
            gravity = Gravity.BOTTOM or Gravity.START
            // Application panel window
            type = WindowManager.LayoutParams.TYPE_APPLICATION_PANEL
            // Fill up the entire app view
            width = WindowManager.LayoutParams.MATCH_PARENT
            // for build versions less than or equal to S_V2, set the height to wrap content
            height = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2)
                WindowManager.LayoutParams.WRAP_CONTENT
            else
                WindowManager.LayoutParams.MATCH_PARENT

            // Format of screen pixels
            format = PixelFormat.TRANSLUCENT
            // Title used as fallback for a11y services
            // TODO: Provide bottom sheet window resource
            title = composeView.context.resources.getString(
                androidx.compose.ui.R.string.default_popup_window_title
            )
            // Get the Window token from the parent view
            token = composeView.applicationWindowToken

            // Flags specific to modal bottom sheet.
            flags = flags and (
                    WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES or
                            WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                    ).inv()

            flags = if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.S_V2) {
                flags
            } else flags or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        }
    }
}

private open class PopupLayoutHelperImpl {

    open fun setGestureExclusionRects(composeView: View, width: Int, height: Int) {
        //For Android versions below API 29, it’s not necessary to explicitly exclude the entire screen from system gestures.
        // The skeleton method is defined to keep consistency in the two objects.
    }

    fun updateViewLayout(
        windowManager: WindowManager,
        popupView: View,
        params: ViewGroup.LayoutParams
    ) {
        windowManager.updateViewLayout(popupView, params)
    }
}

@RequiresApi(29) // android.view.View#setSystemGestureExclusionRects call requires API 29 and above
private class PopupLayoutHelperImpl29 : PopupLayoutHelperImpl() {
    override fun setGestureExclusionRects(composeView: View, width: Int, height: Int) { // We need to explicitly specify to exclude the entire screen from system gestures
        composeView.systemGestureExclusionRects = mutableListOf(
            Rect(
                0,
                0,
                width,
                height
            )
        )
    }
}
