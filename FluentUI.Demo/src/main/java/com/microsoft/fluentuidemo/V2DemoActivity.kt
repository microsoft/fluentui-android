package com.microsoft.fluentuidemo

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheet
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetValue
import com.microsoft.fluentui.tokenized.bottomsheet.rememberBottomSheetState
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import com.microsoft.fluentuidemo.AppTheme.AppBarMenu
import kotlinx.coroutines.launch

enum class Controls {
    Params,
    ControlTokens
}

open class V2DemoActivity : ComponentActivity() {
    companion object {
        const val DEMO_TITLE = "demo_title"
    }

    private var content: @Composable () -> Unit = {}
    fun setActivityContent(content: @Composable () -> Unit) {
        this@V2DemoActivity.content = content
    }

    private var bottomAppBar: @Composable (RowScope.() -> Unit)? = null
    fun setBottomAppBar(bottomAppBar: @Composable (RowScope.() -> Unit)) {
        this@V2DemoActivity.bottomAppBar = bottomAppBar
    }

    private var bottomBar: @Composable () -> Unit = {}
    fun setBottomBar(bottomBar: @Composable () -> Unit = {}) {
        this@V2DemoActivity.bottomBar = bottomBar
    }

    private var bottomSheetContent: @Composable () -> Unit = {}
    fun setBottomSheetContent(bottomSheetContent: @Composable () -> Unit) {
        this@V2DemoActivity.bottomSheetContent = bottomSheetContent
    }

    @Composable
    fun BottomSheetWebView(mUrl: String) {
        var isPageLoaded by remember { mutableStateOf(false) }
        Box(modifier = Modifier.fillMaxSize()) {
            if (!isPageLoaded) {
                CircularProgressIndicator(
                    size = CircularProgressIndicatorSize.Large
                )
            }
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = object : WebViewClient() {
                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                isPageLoaded = true
                            }
                        }
                        Thread.sleep(2000)
                        loadUrl(mUrl)
                    }
                },
                update = {
                    it.loadUrl(mUrl)
                })
        }
    }

    open val appBarSize = AppBarSize.Medium
    open lateinit var demoActivityLink: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoTitle = intent.getSerializableExtra(DEMO_TITLE) as String
        setContent {
            FluentTheme {
                AppTheme.SetStatusBarColor()

                val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Hidden)
                val scope = rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        AppBar(
                            title = demoTitle,
                            navigationIcon = FluentIcon(
                                SearchBarIcons.Arrowback,
                                contentDescription = "Navigate Back",
                                onClick = { Navigation.backNavigation(this) }
                            ),
                            style = AppTheme.appThemeStyle.value,
                            appBarSize = appBarSize,
                            bottomBar = bottomAppBar,
                            rightAccessoryView = {
                                val uriHandler = LocalUriHandler.current
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fluent_code_24_regular),
                                    contentDescription = "Control Token Icon",
                                    modifier = Modifier.padding(
                                        FluentGlobalTokens.size(
                                            FluentGlobalTokens.SizeTokens.Size100
                                        )
                                    ),
                                    tint = if (AppTheme.appThemeStyle.value == FluentStyle.Neutral) {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                            FluentTheme.themeMode
                                        )
                                    } else {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                                            FluentTheme.themeMode
                                        )
                                    },
                                    onClick = {
                                        uriHandler.openUri(demoActivityLink)
                                    }
                                )

                                Box {
                                    AppBarMenu()
                                }
                            }
                        )
                    },
                    bottomBar = bottomBar,
                    floatingActionButton = {
                        var isHiddenBottomSheet by remember { mutableStateOf(true) }
                        FloatingActionButton(
                            icon = ImageVector.vectorResource(id = R.drawable.ic_fluent_info_24_regular),
                            modifier = Modifier.padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100)),
                            onClick = {
                                isHiddenBottomSheet = if (isHiddenBottomSheet) {
                                    scope.launch { bottomSheetState.expand() }
                                    !isHiddenBottomSheet
                                } else {
                                    scope.launch { bottomSheetState.hide() }
                                    !isHiddenBottomSheet
                                }
                            }
                        )
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .background(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
                            .padding(it)
                    ) {
                        var selectedControl by remember { mutableStateOf(Controls.Params) }
                        BottomSheet(
                            sheetContent = {
                                val controlsList = listOf(
                                    PillMetaData(
                                        text = "Params",
                                        enabled = true,
                                        onClick = {
                                            selectedControl = Controls.Params
                                        }
                                    ),
                                    PillMetaData(
                                        text = "Control Tokens",
                                        enabled = true,
                                        onClick = {
                                            selectedControl = Controls.ControlTokens
                                        }
                                    )
                                ) as MutableList<PillMetaData>

                                PillTabs(
                                    style = FluentStyle.Neutral,
                                    metadataList = controlsList,
                                    selectedIndex = selectedControl.ordinal
                                )

                                Column(
                                    modifier = Modifier
                                        .verticalScroll(rememberScrollState())
                                ) {
                                    bottomSheetContent()
                                }
                            },
                            sheetState = bottomSheetState
                        ) {
                            content()
                        }
                    }
                }
            }
        }
    }
}