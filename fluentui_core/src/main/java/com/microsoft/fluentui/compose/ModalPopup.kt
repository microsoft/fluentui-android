package com.microsoft.fluentui.compose

import android.content.Context
import android.graphics.Outline
import android.graphics.PixelFormat
import android.graphics.Rect
import android.os.Build
import android.os.Looper
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.annotation.VisibleForTesting
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
import androidx.compose.runtime.snapshots.SnapshotStateObserver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.UiComposable
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.AbstractComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.ViewRootForInspector
import androidx.compose.ui.semantics.popup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntRect
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastMap
import androidx.compose.ui.window.PopupProperties
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.lifecycle.findViewTreeViewModelStoreOwner
import androidx.lifecycle.setViewTreeLifecycleOwner
import androidx.lifecycle.setViewTreeViewModelStoreOwner
import androidx.savedstate.findViewTreeSavedStateRegistryOwner
import androidx.savedstate.setViewTreeSavedStateRegistryOwner
import java.util.UUID
import kotlin.math.roundToInt

/**
 * Popup specific for modal bottom drawer.
 */

internal class AlignmentOffsetPositionProvider(
    val alignment: Alignment,
    val offset: IntOffset
)  {
     fun calculatePosition(
        anchorBounds: IntRect,
        layoutDirection: LayoutDirection,
        popupContentSize: IntSize
    ): IntOffset {
        return anchorBounds.topLeft +
                alignment.align(
                    IntSize.Zero,
                    anchorBounds.size,
                    layoutDirection
                ) -
                alignment.align(
                    IntSize.Zero,
                    popupContentSize,
                    layoutDirection
                ) +
                IntOffset(
                    offset.x * (if (layoutDirection == LayoutDirection.Ltr) 1 else -1),
                    offset.y
                )
    }
}

@Composable
fun ModalPopup(
    onDismissRequest:(() -> Unit)? = null,
    windowInsetsType: Int = WindowInsetsCompat.Type.systemBars(),
    content: @Composable () -> Unit,
) {
    val alignment = Alignment.TopStart
    val offset = IntOffset(0, 0)
    val popupPositionProvider = remember(alignment, offset) {
        AlignmentOffsetPositionProvider(
            alignment,
            offset
        )
    }
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
            initialPositionProvider = popupPositionProvider,
            saveId = id,
        ).apply {
            setCustomContent(parentComposition) {
                Box(
                    Modifier
                        .semantics { this.popup() }

                        .onSizeChanged {
                            popupContentSize = it
                        }

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
                val parentCoordinates = childCoordinates.parentLayoutCoordinates!!
                modalWindow.updateParentLayoutCoordinates(parentCoordinates)
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
    initialPositionProvider: AlignmentOffsetPositionProvider,
    private val popupLayoutHelper: PopupLayoutHelper = if (Build.VERSION.SDK_INT >= 29) {
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

    var positionProvider = initialPositionProvider

    var parentLayoutDirection: LayoutDirection = LayoutDirection.Ltr
    var popupContentSize: IntSize? by mutableStateOf(null)
    private var parentLayoutCoordinates: LayoutCoordinates? by mutableStateOf(null)
    private var parentBounds: IntRect? = null

    val canCalculatePosition by derivedStateOf {
        parentLayoutCoordinates != null && popupContentSize != null
    }

    private val maxSupportedElevation = 8.dp

    private val previousWindowVisibleFrame = Rect()

    override val subCompositionView: AbstractComposeView get() = this

    private val snapshotStateObserver = SnapshotStateObserver(onChangedExecutor = { command ->
        if (handler?.looper === Looper.myLooper()) {
            command()
        } else {
            handler?.post(command)
        }
    })

    init {
        id = android.R.id.content
        // Set up view owners
        setViewTreeLifecycleOwner(composeView.findViewTreeLifecycleOwner())
        setViewTreeViewModelStoreOwner(composeView.findViewTreeViewModelStoreOwner())
        setViewTreeSavedStateRegistryOwner(composeView.findViewTreeSavedStateRegistryOwner())
        setTag(androidx.compose.ui.R.id.compose_view_saveable_id_tag, "Popup:$saveId")
        // Enable children to draw their shadow by not clipping them
        clipChildren = false
        with(density) { elevation = maxSupportedElevation.toPx() }
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

    private fun setIsFocusable(isFocusable: Boolean) = applyNewFlags(
        if (!isFocusable) {
            params.flags or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        } else {
            params.flags and (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE.inv())
        }
    )

    private fun setClippingEnabled(clippingEnabled: Boolean) = applyNewFlags(
        if (clippingEnabled) {
            params.flags and (WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS.inv())
        } else {
            params.flags or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        }
    )

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
        setIsFocusable(properties.focusable)
        setClippingEnabled(properties.clippingEnabled)
        superSetLayoutDirection(layoutDirection)
    }

    private fun applyNewFlags(flags: Int) {
        params.flags = flags
        popupLayoutHelper.updateViewLayout(windowManager, this, params)
    }

    fun updateParentLayoutCoordinates(parentLayoutCoordinates: LayoutCoordinates) {
        this.parentLayoutCoordinates = parentLayoutCoordinates
        updateParentBounds()
    }

    fun updateParentBounds() {
        val coordinates = parentLayoutCoordinates ?: return
        val layoutSize = coordinates.size

        val position = coordinates.positionInWindow()
        val layoutPosition = IntOffset(position.x.roundToInt(), position.y.roundToInt())

        val newParentBounds = IntRect(layoutPosition, layoutSize)
        if (newParentBounds != parentBounds) {
            this.parentBounds = newParentBounds
            updatePosition()
        }
    }

    fun updatePosition() {
        val parentBounds = parentBounds ?: return
        val popupContentSize = popupContentSize ?: return

        val windowSize = previousWindowVisibleFrame.let {
            popupLayoutHelper.getWindowVisibleDisplayFrame(composeView, it)
            val bounds = it.toIntBounds()
            IntSize(width = bounds.width, height = bounds.height)
        }

        val popupPosition = positionProvider.calculatePosition(
            parentBounds,
            parentLayoutDirection,
            popupContentSize
        )

        params.x = popupPosition.x
        params.y = popupPosition.y

        if (properties.excludeFromSystemGesture) {
            popupLayoutHelper.setGestureExclusionRects(this, windowSize.width, windowSize.height)
        }

        popupLayoutHelper.updateViewLayout(windowManager, this, params)
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

@VisibleForTesting
internal interface PopupLayoutHelper {
    fun getWindowVisibleDisplayFrame(composeView: View, outRect: Rect)
    fun setGestureExclusionRects(composeView: View, width: Int, height: Int)
    fun updateViewLayout(
        windowManager: WindowManager,
        popupView: View,
        params: ViewGroup.LayoutParams
    )
}

private open class PopupLayoutHelperImpl : PopupLayoutHelper {
    override fun getWindowVisibleDisplayFrame(composeView: View, outRect: Rect) {
        composeView.getWindowVisibleDisplayFrame(outRect)
    }

    override fun setGestureExclusionRects(composeView: View, width: Int, height: Int) {
        // do nothing
    }

    override fun updateViewLayout(
        windowManager: WindowManager,
        popupView: View,
        params: ViewGroup.LayoutParams
    ) {
        windowManager.updateViewLayout(popupView, params)
    }
}

@RequiresApi(29)
private class PopupLayoutHelperImpl29 : PopupLayoutHelperImpl() {
    override fun setGestureExclusionRects(composeView: View, width: Int, height: Int) {
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

private fun Rect.toIntBounds() = IntRect(
    left = left,
    top = top,
    right = right,
    bottom = bottom
)