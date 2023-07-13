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
import androidx.compose.ui.res.stringResource
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
import com.microsoft.fluentui.theme.token.controlTokens.FABState
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheet
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetValue
import com.microsoft.fluentui.tokenized.bottomsheet.rememberBottomSheetState
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import kotlinx.coroutines.launch

enum class Controls {
    Params,
    ControlTokens
}

open class V2DemoActivity : ComponentActivity() {
    companion object {
        const val DEMO_TITLE = "demo_title"
    }

    private var activityUrl: String = ""
    fun setupActivity(activityClass: ComponentActivity) {
        activityUrl =
            "https://github.com/microsoft/fluentui-android/blob/master/FluentUI.Demo/src/main/java/com/microsoft/fluentuidemo/demos/${activityClass::class.simpleName}.kt"
    }

    private var activityContent: @Composable () -> Unit = {}
    fun setActivityContent(activityContent: @Composable () -> Unit) {
        this@V2DemoActivity.activityContent = activityContent
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
        Box(modifier = Modifier.fillMaxSize()) {
            AndroidView(
                factory = {
                    WebView(it).apply {
                        layoutParams = ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT
                        )
                        webViewClient = WebViewClient()
                        loadUrl(mUrl)
                    }
                },
                update = {
                    it.loadUrl(mUrl)
                }
            )
        }
    }

    open val appBarSize = AppBarSize.Medium

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoTitle = intent.getSerializableExtra(DEMO_TITLE) as String
        setContent {
            FluentTheme {
                SetStatusBarColor()

                val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Hidden)
                val scope = rememberCoroutineScope()

                Scaffold(
                    topBar = {
                        AppBar(
                            title = demoTitle,
                            navigationIcon = FluentIcon(
                                SearchBarIcons.Arrowback,
                                contentDescription = stringResource(id = R.string.app_bar_layout_navigation_icon_clicked),
                                onClick = { Navigation.backNavigation(this) }
                            ),
                            style = AppTheme.appThemeStyle.value,
                            appBarSize = appBarSize,
                            bottomBar = bottomAppBar,
                            rightAccessoryView = {
                                if (activityUrl.isNotEmpty()) {
                                    val uriHandler = LocalUriHandler.current
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_fluent_code_24_regular),
                                        contentDescription = stringResource(id = R.string.demo_activity_github_link),
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
                                            uriHandler.openUri(activityUrl)
                                        }
                                    )
                                }

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
                            text = stringResource(id = R.string.control_tokens_details),
                            state = FABState.Collapsed,
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
                                        text = stringResource(id = R.string.parameters),
                                        enabled = true,
                                        onClick = {
                                            selectedControl = Controls.Params
                                        }
                                    ),
                                    PillMetaData(
                                        text = stringResource(id = R.string.control_tokens),
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
                            activityContent()
                        }
                    }
                }
            }
        }
    }
}