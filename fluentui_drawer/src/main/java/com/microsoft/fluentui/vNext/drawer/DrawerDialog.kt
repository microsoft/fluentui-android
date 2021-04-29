package com.microsoft.fluentui.vNext.drawer

import android.content.Context
import android.graphics.Point
import android.os.Handler
import android.view.*
import androidx.annotation.StyleRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.microsoft.fluentui.drawer.R
import com.microsoft.fluentui.drawer.SideSheetBehavior
import com.microsoft.fluentui.drawer.TopSheetBehavior
import com.microsoft.fluentui.drawer.databinding.DialogSideVNextDrawerBinding
import com.microsoft.fluentui.generator.DrawerBehavior
import com.microsoft.fluentui.generator.DrawerType
import com.microsoft.fluentui.generator.resourceProxies.DrawerTokensSystem
import com.microsoft.fluentui.theming.FluentUIContextThemeWrapper
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentui.util.displaySize
import com.microsoft.fluentui.util.statusBarHeight

open class DrawerDialog @JvmOverloads constructor(appContext: Context, val drawerType: DrawerType = DrawerType.LEFT_NAV, @StyleRes theme: Int = 0) : AppCompatDialog(FluentUIContextThemeWrapper(appContext,R.style.Theme_FluentUI_Drawer), if (theme == 0) R.style.Drawer_FluentUI else theme) {
    companion object {
        private const val DISMISS_THRESHOLD = 0.005f
    }

    var onDrawerContentCreatedListener: OnDrawerContentCreatedListener? = null

    private var sheetBehavior: CoordinatorLayout.Behavior<View>? = null

    private val sheetCallback = object : CustomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            if(newState == BottomSheetBehavior.STATE_COLLAPSED) // when state is STATE_COLLAPSED
                dismissDialog()
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            if (isExpanded && slideOffset < DISMISS_THRESHOLD && slideOffset > 0)
                dismiss()
        }
    }

    private var isExpanded: Boolean = false
    protected val container: View
    protected val drawerContent: ViewGroup
    private val drawer: View

    /**
     * This field [dimValue] contains dim value of background[0.0 -> no fade, 0.5-> dim, 1.0->opaque]
     * This field [anchorView] contains the anchor view  below which the dialog will appear
     * This field [titleBehavior] stores whether the dialog should  be below title bar or hide titler bar.
     * */
    private var dimValue = 0.5f
    private var drawerBehavior:DrawerBehavior = DrawerBehavior.FULL
    private var anchorView: View? = null
    private var tokensSystem: DrawerTokensSystem

    init {
        when(drawerType){
            DrawerType.LEFT_NAV, DrawerType.RIGHT_NAV -> {
                val binding = DialogSideVNextDrawerBinding.inflate(layoutInflater)
                container = binding.root
                drawerContent = binding.drawerContent
                drawer = binding.drawer
            }
        }
        container.setOnClickListener {
            collapse()
        }
        tokensSystem = DrawerTokensSystem(context, context.resources)
    }

    constructor(appContext: Context, drawerType:DrawerType = DrawerType.LEFT_NAV, dimValue: Float=0.5f, anchorView: View?=null, drawerBehavior: DrawerBehavior=DrawerBehavior.FULL, @StyleRes theme: Int = 0) : this(appContext, drawerType, theme) {
        this.dimValue = dimValue
        this.anchorView = anchorView
        this.drawerBehavior = drawerBehavior
        tokensSystem.drawerType = drawerType
        tokensSystem.drawerBehavior = drawerBehavior
        val opacityColorhex = Integer.toHexString(tokensSystem.backgroundDimmedColor).substring(0,2)
        this.dimValue = opacityColorhex.toLong(16)/255f
    }

    override fun setContentView(layoutResID: Int) {
        val content = layoutInflater.inflate(layoutResID, drawerContent, false)
        setContentView(content)
        onDrawerContentCreatedListener?.onDrawerContentCreated(content)
        sheetBehavior = when (drawerType) {
            DrawerType.LEFT_NAV, DrawerType.RIGHT_NAV-> SideSheetBehavior.from(drawer as View)
        }
    }

    override fun setContentView(view: View) {
        drawerContent.removeAllViews()
        drawerContent.addView(view)
        sheetBehavior = when (drawerType) {
            DrawerType.RIGHT_NAV, DrawerType.LEFT_NAV -> SideSheetBehavior.from(drawer as View)
        }
    }

    override fun show() {
        super.show()
        val expandDelayMilliseconds = context.resources.getInteger(R.integer.fluentui_drawer_fade_in_milliseconds).toLong()
        Handler().postDelayed(::expand, expandDelayMilliseconds)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        var topMargin = 0
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)

        if(anchorView != null) {
            val screenPos = IntArray(2)
            anchorView?.getLocationOnScreen(screenPos)
            topMargin= screenPos[1]+anchorView!!.height
        }
        else {
            when(drawerBehavior) {
                DrawerBehavior.BELOW_STATUS -> {
                    topMargin = context.statusBarHeight
                }
                DrawerBehavior.BELOW_TOOLBAR -> {
                    val actionBar: ActionBar? = context.activity?.supportActionBar
                    if(actionBar != null)
                        topMargin = context.statusBarHeight+ actionBar.height
                }
                DrawerBehavior.FULL -> {
                    topMargin = 0
                }
                DrawerBehavior.ANCHORED ->{}
            }
        }

        val displaySize: Point = context.displaySize
        val layoutParams: WindowManager.LayoutParams? = window?.attributes
        layoutParams?.gravity = Gravity.TOP
        layoutParams?.y = topMargin
        layoutParams?.dimAmount = this.dimValue
        window?.attributes = layoutParams
        window?.setLayout(displaySize.x, displaySize.y-topMargin)

        super.setContentView(container)
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).setBottomSheetCallback(sheetCallback)
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setTopSheetCallback(sheetCallback)
            is SideSheetBehavior -> {
                (sheetBehavior as SideSheetBehavior<View>).setSideSheetCallBack(sheetCallback)
                (sheetBehavior as SideSheetBehavior<View>).behaviorType = when(drawerType) {
                    DrawerType.RIGHT_NAV -> SideSheetBehavior.Companion.BehaviorType.RIGHT
                    DrawerType.LEFT_NAV -> SideSheetBehavior.Companion.BehaviorType.LEFT
                }
            }
        }
    }

    override fun onBackPressed() {
        collapse()
    }

    override fun dismiss() {
        isExpanded = false
        // Dismiss may be called by external objects so state is set to STATE_COLLAPSED in order for
        // the drawer to animate up
        when(sheetBehavior){
            is BottomSheetBehavior -> {
                (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_COLLAPSED
                if((sheetBehavior as BottomSheetBehavior<View>).state == BottomSheetBehavior.STATE_COLLAPSED)
                    dismissDialog()
            }
            is TopSheetBehavior -> {
                (sheetBehavior as TopSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
                if((sheetBehavior as TopSheetBehavior<View>).getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    dismissDialog()
            }
            is SideSheetBehavior -> {
                (sheetBehavior as SideSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
                if((sheetBehavior as SideSheetBehavior<View>).getState() == BottomSheetBehavior.STATE_COLLAPSED)
                    dismissDialog()
            }
            else -> dismissDialog()
        }
    }

    fun dismissDialog() {
        super.dismiss()
    }

    private fun expand() {
        // For persistent instances calling requestLayout fixes incorrect positioning of the drawer
        // after rotation from landscape to portrait
        drawer.requestLayout()
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_EXPANDED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_EXPANDED)
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_EXPANDED)
        }
        isExpanded = true
    }

    protected fun collapse() {
        when(sheetBehavior){
            is BottomSheetBehavior -> (sheetBehavior as BottomSheetBehavior<View>).state = BottomSheetBehavior.STATE_COLLAPSED
            is TopSheetBehavior -> (sheetBehavior as TopSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
            is SideSheetBehavior -> (sheetBehavior as SideSheetBehavior<View>).setStateOuter(BottomSheetBehavior.STATE_COLLAPSED)
            else -> dismiss()
        }
    }

    // external method to set dimValue
    fun setFade(x: Float) {
        this.dimValue = x
        this.onAttachedToWindow()
    }
}

interface OnDrawerContentCreatedListener {
    fun onDrawerContentCreated(drawerContents: View)
}

abstract class CustomSheetCallback: com.microsoft.fluentui.drawer.CustomSheetCallback()
