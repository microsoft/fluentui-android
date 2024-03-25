package com.microsoft.fluentuidemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.divider.Divider
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Dialog
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillBar
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import com.microsoft.fluentui.tokenized.shimmer.Shimmer
import com.microsoft.fluentuidemo.CustomizedTokens.listItemTokens
import kotlinx.coroutines.launch
import java.util.Locale

enum class Components {
    V1,
    V2,
    All
}

class V2DemoListActivity : ComponentActivity() {
    @Composable
    fun GetDialogContent() {
        Column(
            modifier = Modifier
                .background(color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value())
                .padding(all = FluentGlobalTokens.SizeTokens.Size160.value)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(
                FluentGlobalTokens.SizeTokens.Size100.value
            )
        ) {
            Label(
                text = application.assets.open("dogfood-release-notes.txt").bufferedReader()
                    .use { it.readText() },
                textStyle = FluentAliasTokens.TypographyTokens.Body2
            )

            Row {
                Label(
                    text = stringResource(id = R.string.release_notes_updates),
                    textStyle = FluentAliasTokens.TypographyTokens.Body2
                )

                val uriHandler = LocalUriHandler.current
                Label(
                    text = stringResource(id = R.string.click_here),
                    modifier = Modifier.clickable { uriHandler.openUri("https://github.com/microsoft/fluentui-android/releases") },
                    textStyle = FluentAliasTokens.TypographyTokens.Body2,
                    colorStyle = ColorStyle.Brand
                )
            }
        }
    }

    @Composable
    fun GetDrawerContent() {
        Column(
            verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.SizeTokens.Size320.value),
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
                .background(color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.SizeTokens.Size60.value),
                modifier = Modifier
                    .padding(top = FluentGlobalTokens.SizeTokens.Size480.value)
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.mipmap.fluent_logo),
                    contentDescription = stringResource(id = R.string.app_name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(FluentGlobalTokens.SizeTokens.Size560.value)
                        .clip(CircleShape),
                    alignment = Alignment.Center
                )

                Label(
                    text = stringResource(id = R.string.app_name),
                    textStyle = FluentAliasTokens.TypographyTokens.Title3
                )

                Label(
                    text = getString(R.string.sdk_version, BuildConfig.VERSION_NAME),
                    modifier = Modifier
                        .background(color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value()),
                    textStyle = FluentAliasTokens.TypographyTokens.Caption1
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    FluentGlobalTokens.SizeTokens.Size40.value
                ),
                modifier = Modifier.padding(FluentGlobalTokens.SizeTokens.Size100.value)
            ) {
                val gradientColors = listOf(
                    FluentColor(
                        Color(0XFFD3CBE8),
                        Color(0XFF756D88)
                    ).value(),

                    FluentColor(
                        Color(0xffE4CFDF),
                        Color(0XFF877482)
                    ).value()
                )

                Label(
                    text = stringResource(id = R.string.open_source_cross_platform),
                    textStyle = FluentAliasTokens.TypographyTokens.Title1,
                    modifier = Modifier
                        .padding(end = FluentGlobalTokens.SizeTokens.Size480.value)
                        .graphicsLayer(alpha = 0.99f)
                        .drawWithCache {
                            val brush = Brush.linearGradient(gradientColors)
                            onDrawWithContent {
                                drawContent()
                                drawRect(brush, blendMode = BlendMode.SrcAtop)
                            }
                        }
                )

                BasicText(
                    text = stringResource(id = R.string.intuitive_and_powerful),
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1].copy(
                        color = FluentColor(Color(0XFF2F3441), Color(0XFFA8AEBC)).value()
                    )
                )
            }

            Divider()

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    FluentGlobalTokens.SizeTokens.Size100.value
                )
            ) {
                ListItem.Item(
                    text = stringResource(id = R.string.design_tokens),
                    onClick = {
                        val packageContext = this@V2DemoListActivity
                        Navigation.forwardNavigation(
                            packageContext,
                            V2DesignTokensActivity::class.java,
                            Pair(V2DemoActivity.DEMO_TITLE, "V2 Design Tokens")
                        )
                    },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_fluent_24_regular),
                            contentDescription = stringResource(id = R.string.design_tokens),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = listItemTokens
                )

                var showDialog by rememberSaveable { mutableStateOf(false) }
                if (showDialog) {
                    Dialog(
                        dialogProperties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        ),
                        onDismiss = { showDialog = !showDialog }
                    ) { GetDialogContent() }
                }
                ListItem.Item(
                    text = stringResource(id = R.string.release_notes),
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_document_chevron_double_24_regular),
                            contentDescription = stringResource(id = R.string.release_notes),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    onClick = { showDialog = !showDialog },
                    listItemTokens = listItemTokens
                )
                val uriHandler = LocalUriHandler.current
                ListItem.Item(
                    text = stringResource(id = R.string.github_repo),
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_book_number_24_regular),
                            contentDescription = stringResource(id = R.string.github_repo),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    trailingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_link_24_regular),
                            contentDescription = stringResource(id = R.string.github_repo_link),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    onClick = { uriHandler.openUri("https://github.com/microsoft/fluentui-android") },
                    listItemTokens = listItemTokens
                )

                ListItem.Item(
                    text = stringResource(id = R.string.report_issue),
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_warning_24_regular),
                            contentDescription = stringResource(id = R.string.github_repo),
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    onClick = { uriHandler.openUri("https://github.com/microsoft/fluentui-android/issues") },
                    listItemTokens = listItemTokens
                )
            }
        }
    }

    private fun searchComponent(
        userInput: String,
        selectedSearchComponents: Components
    ): MutableList<Demo> {
        return if (userInput.isEmpty()) {
            when (selectedSearchComponents) {
                Components.V1 -> V1DEMO.toMutableList()
                Components.V2 -> V2DEMO.toMutableList()
                Components.All -> (V1DEMO + V2DEMO).toMutableList()
            }
        } else {
            when (selectedSearchComponents) {
                Components.V1 -> V1DEMO.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(userInput.lowercase(Locale.getDefault()))
                }.toMutableList()

                Components.V2 -> V2DEMO.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(userInput.lowercase(Locale.getDefault()))
                }.toMutableList()

                Components.All -> (V1DEMO + V2DEMO).filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(userInput.lowercase(Locale.getDefault()))
                }.toMutableList()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FluentTheme {
                val drawerState = rememberDrawerState()
                val scope = rememberCoroutineScope()

                Drawer(
                    drawerState = drawerState,
                    drawerContent = { GetDrawerContent() },
                    behaviorType = BehaviorType.LEFT_SLIDE_OVER,
                    scrimVisible = true
                )

                var searchModeEnabled by rememberSaveable { mutableStateOf(false) }
                var selectedComponents by rememberSaveable { mutableStateOf(Components.V2) }
                var selectedSearchComponents by rememberSaveable { mutableStateOf(Components.All) }
                var filteredDemoList by remember { mutableStateOf(V2DEMO.toMutableList()) }
                var inputString by rememberSaveable { mutableStateOf("") }

                Scaffold(
                    contentWindowInsets = WindowInsets.statusBars,
                    topBar = {
                        val appTitleDelta: Float by animateFloatAsState(
                            if (searchModeEnabled) 0F else 1F,
                            animationSpec = tween(durationMillis = 150, easing = LinearEasing)
                        )

                        SetStatusBarColor()

                        AppBar(
                            title = stringResource(id = R.string.app_name),
                            navigationIcon = FluentIcon(
                                SearchBarIcons.Arrowback,
                                contentDescription = stringResource(id = R.string.app_bar_layout_navigation_icon_clicked),
                            ),
                            appBarSize = AppBarSize.Large,
                            logo = {
                                Image(painter = painterResource(id = R.mipmap.fluent_logo),
                                    contentDescription = stringResource(id = R.string.fluent_logo),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .padding(FluentGlobalTokens.SizeTokens.Size100.value)
                                        .clip(CircleShape)
                                        .size(FluentGlobalTokens.SizeTokens.Size320.value)
                                        .clickable { scope.launch { drawerState.open() } }
                                )
                            },
                            searchMode = searchModeEnabled,
                            style = AppThemeViewModel.appThemeStyle.value,
                            rightAccessoryView = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fluent_search_24_regular),
                                    contentDescription = stringResource(id = R.string.app_bar_layout_menu_search),
                                    modifier = Modifier.padding(
                                        FluentGlobalTokens.SizeTokens.Size100.value
                                    ),
                                    onClick = { searchModeEnabled = true },
                                    tint = if (AppThemeViewModel.appThemeStyle.value == FluentStyle.Neutral) {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
                                    } else {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
                                    }
                                )

                                Box {
                                    AppBarMenu()
                                }
                            },
                            searchBar = if (searchModeEnabled) {
                                {
                                    SearchBar(
                                        onValueChange = { userInput, _ ->
                                            scope.launch {
                                                inputString = userInput
                                            }
                                        },
                                        style = AppThemeViewModel.appThemeStyle.value,
                                        navigationIconCallback = {
                                            searchModeEnabled = false
                                            selectedSearchComponents = Components.V2
                                            filteredDemoList = V2DEMO.toMutableList()
                                        },
                                        focusByDefault = true
                                    )
                                    filteredDemoList = searchComponent(
                                        userInput = inputString,
                                        selectedSearchComponents = selectedSearchComponents
                                    )
                                }
                            } else null,
                            bottomBar = {
                                if (!searchModeEnabled) {
                                    val buttonBarList = mutableListOf<PillMetaData>()
                                    buttonBarList.add(
                                        PillMetaData(
                                            text = stringResource(id = R.string.v1_components),
                                            enabled = true,
                                            onClick = {
                                                selectedComponents = Components.V1
                                                filteredDemoList = V1DEMO.toMutableList()
                                            }
                                        )
                                    )
                                    buttonBarList.add(
                                        PillMetaData(
                                            text = stringResource(id = R.string.v2_components),
                                            enabled = true,
                                            onClick = {
                                                selectedComponents = Components.V2
                                                filteredDemoList = V2DEMO.toMutableList()
                                            }
                                        )
                                    )
                                    PillTabs(
                                        style = AppThemeViewModel.appThemeStyle.value,
                                        metadataList = buttonBarList,
                                        selectedIndex = selectedComponents.ordinal
                                    )
                                } else {
                                    val searchList: MutableList<PillMetaData> = mutableListOf()

                                    searchList.add(
                                        PillMetaData(
                                            text = stringResource(id = R.string.all_components),
                                            onClick = {
                                                selectedSearchComponents = Components.All
                                                filteredDemoList = searchComponent(
                                                    userInput = inputString,
                                                    selectedSearchComponents = selectedSearchComponents
                                                )
                                            },
                                            selected = selectedSearchComponents == Components.All,
                                        )
                                    )

                                    searchList.add(
                                        PillMetaData(
                                            text = stringResource(id = R.string.v1_components),
                                            onClick = {
                                                selectedSearchComponents = Components.V1
                                                filteredDemoList = searchComponent(
                                                    userInput = inputString,
                                                    selectedSearchComponents = selectedSearchComponents
                                                )
                                            },
                                            selected = selectedSearchComponents == Components.V1,
                                        )
                                    )

                                    searchList.add(
                                        PillMetaData(
                                            text = stringResource(id = R.string.v2_components),
                                            onClick = {
                                                selectedSearchComponents = Components.V2
                                                filteredDemoList = searchComponent(
                                                    userInput = inputString,
                                                    selectedSearchComponents = selectedSearchComponents
                                                )
                                            },
                                            selected = selectedSearchComponents == Components.V2,
                                        )
                                    )

                                    PillBar(
                                        metadataList = searchList,
                                        style = AppThemeViewModel.appThemeStyle.value
                                    )
                                }
                            },
                            appTitleDelta = appTitleDelta
                        )
                    }
                ) {
                    LazyColumn(
                        Modifier
                            .padding(it)
                            .fillMaxSize()
                    ) {
                        filteredDemoList.forEach {
                            item {
                                ListItem.Item(
                                    text = it.title,
                                    onClick = {
                                        val packageContext = this@V2DemoListActivity
                                        Navigation.forwardNavigation(
                                            packageContext,
                                            it.demoClass.java,
                                            Pair(DemoActivity.DEMO_ID, it.id),
                                            Pair(V2DemoActivity.DEMO_TITLE, it.title)
                                        )
                                    },
                                    trailingAccessoryContent = if (it.badge != Badge.None) {
                                        {
                                            Badge(
                                                text = when (it.badge) {
                                                    Badge.New -> stringResource(id = R.string.new_badge)
                                                    Badge.Modified -> stringResource(id = R.string.modified_badge)
                                                    Badge.APIBreak -> stringResource(id = R.string.api_break_badge)
                                                    else -> ""
                                                },
                                                badgeType = BadgeType.List,
                                            )
                                        }
                                    } else null
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}