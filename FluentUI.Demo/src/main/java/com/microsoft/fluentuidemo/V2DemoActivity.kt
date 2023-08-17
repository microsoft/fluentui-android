package com.microsoft.fluentuidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.widthIn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.drawer.BottomDrawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import kotlinx.coroutines.launch

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

    private var sideBar: @Composable () -> Unit = {}
    private var floatingActionButton: @Composable ()->Unit = {}
    fun setFab(floatingActionButton: @Composable ()->Unit = {}) {
        this@V2DemoActivity.floatingActionButton = floatingActionButton
    }

    fun setSideBar(sideBar: @Composable () -> Unit = {}) {
        this@V2DemoActivity.sideBar = sideBar
    }

    open val paramsUrl: String = ""
    open val controlTokensUrl: String = ""
    open val designTokensUrl: String = ""
    open val globalTokensUrl: String = ""
    open val aliasTokensUrl: String = ""

    @Composable
    fun BottomDrawerContent() {
        val uriHandler = LocalUriHandler.current
        Column(
            Modifier.padding(
                top = FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size120
                ),
                bottom = FluentGlobalTokens.size(
                    FluentGlobalTokens.SizeTokens.Size120
                )
            )
        ) {
            ListItem.Item(
                text = "GitHub Page",
                onClick = { uriHandler.openUri(activityUrl) },
                leadingAccessoryContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_book_number_24_regular),
                        contentDescription = stringResource(id = R.string.github_repo),
                        tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                    )
                },
                listItemTokens = CustomizedTokens.listItemTokens
            )

            if (paramsUrl.isNotEmpty()) {
                ListItem.Item(
                    text = stringResource(id = R.string.parameters),
                    onClick = { uriHandler.openUri(paramsUrl) },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_code_24_regular),
                            contentDescription = stringResource(id = R.string.parameters),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = CustomizedTokens.listItemTokens
                )
            }

            if (designTokensUrl.isNotEmpty()) {
                ListItem.Item(
                    text = stringResource(id = R.string.design_tokens),
                    onClick = { uriHandler.openUri(designTokensUrl) },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_design_ideas_24_regular),
                            contentDescription = stringResource(id = R.string.design_tokens),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = CustomizedTokens.listItemTokens
                )
            }

            if (globalTokensUrl.isNotEmpty()) {
                ListItem.Item(
                    text = stringResource(id = R.string.global_tokens),
                    onClick = { uriHandler.openUri(globalTokensUrl) },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_book_globe_24_regular),
                            contentDescription = stringResource(id = R.string.global_tokens),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = CustomizedTokens.listItemTokens
                )
            }

            if (aliasTokensUrl.isNotEmpty()) {
                ListItem.Item(
                    text = stringResource(id = R.string.alias_tokens),
                    onClick = { uriHandler.openUri(aliasTokensUrl) },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_paint_brush_24_regular),
                            contentDescription = stringResource(id = R.string.alias_tokens),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = CustomizedTokens.listItemTokens
                )
            }

            if (controlTokensUrl.isNotEmpty()) {
                ListItem.Item(
                    text = stringResource(id = R.string.control_tokens),
                    onClick = { uriHandler.openUri(controlTokensUrl) },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_edit_settings_24_regular),
                            contentDescription = stringResource(id = R.string.control_tokens),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = CustomizedTokens.listItemTokens
                )
            }
        }
    }

    open val appBarSize = AppBarSize.Medium

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val demoTitle = intent.getSerializableExtra(DEMO_TITLE) as String
        setContent {
            FluentTheme {
                SetStatusBarColor()

                val bottomDrawerState = rememberDrawerState()
                val scope = rememberCoroutineScope()

                Scaffold(
                    contentWindowInsets = WindowInsets.statusBars,
                    topBar = {
                        AppBar(
                            title = demoTitle,
                            navigationIcon = FluentIcon(
                                SearchBarIcons.Arrowback,
                                contentDescription = stringResource(id = R.string.app_bar_layout_navigation_icon_clicked),
                                onClick = { Navigation.backNavigation(this) }
                            ),
                            style = AppThemeViewModel.appThemeStyle.value,
                            appBarSize = appBarSize,
                            bottomBar = bottomAppBar,
                            rightAccessoryView = {
                                if (activityUrl.isNotEmpty()) {
                                    var isHiddenBottomDrawer by remember { mutableStateOf(true) }
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_fluent_info_24_regular),
                                        contentDescription = stringResource(id = R.string.demo_activity_github_link),
                                        modifier = Modifier.padding(
                                            FluentGlobalTokens.size(
                                                FluentGlobalTokens.SizeTokens.Size100
                                            )
                                        ),
                                        tint = if (AppThemeViewModel.appThemeStyle.value == FluentStyle.Neutral) {
                                            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                                FluentTheme.themeMode
                                            )
                                        } else {
                                            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                                                FluentTheme.themeMode
                                            )
                                        },
                                        onClick = {
                                            isHiddenBottomDrawer = if (isHiddenBottomDrawer) {
                                                scope.launch { bottomDrawerState.open() }
                                                !isHiddenBottomDrawer
                                            } else {
                                                scope.launch { bottomDrawerState.close() }
                                                !isHiddenBottomDrawer
                                            }
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
                    sideBar = sideBar,
                    floatingActionButton = floatingActionButton
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
                            .padding(it)
                    ) {
                        activityContent()

                        BottomDrawer(
                            drawerContent = {
                                BottomDrawerContent()
                            },
                            drawerState = bottomDrawerState
                        )
                    }
                }
            }
        }
    }
}