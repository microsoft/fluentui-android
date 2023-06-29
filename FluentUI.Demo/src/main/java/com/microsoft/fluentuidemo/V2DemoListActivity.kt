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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
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
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
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
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Dialog
import com.microsoft.fluentui.tokenized.menu.Menu
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import kotlinx.coroutines.launch
import java.util.Locale

enum class Components {
    V1,
    V2
}

class V2DemoListActivity : ComponentActivity() {
    private val listItemToken = object : ListItemTokens() {
        @Composable
        override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
            return StateBrush(
                rest = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value()),
                pressed = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2Pressed].value())
            )
        }
    }

    @Composable
    fun GetDialogContent() {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .background(color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value())
                .padding(all = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size160))
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(
                FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100)
            )
        ) {
            Label(
                text = application.assets.open("dogfood-release-notes.txt").bufferedReader()
                    .use { it.readText() },
                textStyle = FluentAliasTokens.TypographyTokens.Body2
            )

            Row {
                Label(
                    text = "For updates on Release Notes, ",
                    textStyle = FluentAliasTokens.TypographyTokens.Body2
                )

                val uriHandler = LocalUriHandler.current
                Label(
                    text = "click here.",
                    modifier = Modifier.clickable { uriHandler.openUri("https://github.com/microsoft/fluentui-android/releases") },
                    textStyle = FluentAliasTokens.TypographyTokens.Body2,
                    colorStyle = ColorStyle.Brand
                )
            }
        }
    }

    @Composable
    fun GetDrawerContent(onRelNotepress: () -> Unit) {
        val scrollState = rememberScrollState()

        Column(
            verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size320)),
            modifier = Modifier
                .fillMaxHeight()
                .verticalScroll(scrollState)
                .background(color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value())
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    FluentGlobalTokens.size(
                        FluentGlobalTokens.SizeTokens.Size60
                    )
                ),
                modifier = Modifier
                    .padding(top = FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size480))
                    .fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fluent_logo),
                    contentDescription = "Fluent UI Demo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size560))
                        .clip(CircleShape),
                    alignment = Alignment.Center
                )

                Label(
                    text = "Fluent UI Demo",
                    textStyle = FluentAliasTokens.TypographyTokens.Title3
                )

                Label(
                    text = String.format(getString(R.string.sdk_version, BuildConfig.VERSION_NAME)),
                    textStyle = FluentAliasTokens.TypographyTokens.Caption1
                )
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    FluentGlobalTokens.size(
                        FluentGlobalTokens.SizeTokens.Size40
                    )
                ),
                modifier = Modifier.padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100))
            ) {
                val gradientColors = listOf(
                    FluentColor(
                        Color(0XFFD3CBE8),
                        Color(0XFF524E5B)
                    ).value(),

                    FluentColor(
                        Color(0xffE4CFDF),
                        Color(0XFF3D373B)
                    ).value()
                )

                Label(
                    text = "Open source cross platform Design System.",
                    textStyle = FluentAliasTokens.TypographyTokens.Title1,
                    modifier = Modifier
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
                    text = "Intuitive & Powerful.",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1].copy(
                        color = FluentColor(Color(0XFF2F3441), Color(0XFFA8AEBC)).value()
                    )
                )
            }

            Divider(color = FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value())

            Column(
                verticalArrangement = Arrangement.spacedBy(
                    FluentGlobalTokens.size(
                        FluentGlobalTokens.SizeTokens.Size100
                    )
                )
            ) {
                ListItem.Item(
                    text = "Design Tokens",
                    onClick = {
                        val packageContext = this@V2DemoListActivity
                        Navigation.forwardNavigation(
                            packageContext,
                            V2DesignTokens::class.java,
                            Pair(V2DemoActivity.DEMO_TITLE, "V2 Design Token")
                        )
                    },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_color_20_regular),
                            contentDescription = "Design Tokens Icon",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = listItemToken
                )

                ListItem.Item(
                    text = "Release Notes",
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_document_text_20_regular),
                            contentDescription = "Release Notes Icon",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    onClick = onRelNotepress,
                    listItemTokens = listItemToken
                )

                val uriHandler = LocalUriHandler.current
                ListItem.Item(
                    text = "Github Repo",
                    trailingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_link_24_regular),
                            contentDescription = "Fluent Link",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                FluentTheme.themeMode
                            )
                        )
                    },
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_code_20_regular),
                            contentDescription = "GitHub Repo Icon",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = listItemToken,
                    onClick = { uriHandler.openUri("https://github.com/microsoft/fluentui-android") }
                )

                ListItem.Item(
                    text = "Your Feedback",
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_mail_20_regular),
                            contentDescription = "Feedback Icon",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    listItemTokens = listItemToken
                )
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FluentTheme {
                val drawerState = rememberDrawerState()
                val scope = rememberCoroutineScope()
                var showDialog by remember { mutableStateOf(false) }

                if (showDialog) {
                    Dialog(
                        dialogProperties = DialogProperties(
                            dismissOnBackPress = true,
                            dismissOnClickOutside = true
                        ),
                        onDismiss = { showDialog = !showDialog }
                    ) { GetDialogContent() }
                }

                Drawer(
                    drawerState = drawerState,
                    drawerContent = { GetDrawerContent { showDialog = !showDialog } },
                    behaviorType = BehaviorType.LEFT_SLIDE_OVER,
                    scrimVisible = true
                )

                var searchModeEnabled by rememberSaveable { mutableStateOf(false) }
                val enableButtonBar by rememberSaveable { mutableStateOf(true) }
                var selectedComponents by remember { mutableStateOf(Components.V2) }
                var filteredDemoList by remember { mutableStateOf(V2DEMO.toMutableList()) }

                Scaffold(
                    topBar = {
                        val appTitleDelta: Float by animateFloatAsState(
                            if (searchModeEnabled) 0F else 1F,
                            animationSpec = tween(durationMillis = 150, easing = LinearEasing)
                        )

                        val buttonBarList = mutableListOf<PillMetaData>()
                        buttonBarList.add(
                            PillMetaData(
                                text = "V1 Components",
                                enabled = true,
                                onClick = {
                                    selectedComponents = Components.V1
                                    filteredDemoList = V1DEMO.toMutableList()
                                }
                            )
                        )
                        buttonBarList.add(
                            PillMetaData(
                                text = "V2 Components",
                                enabled = true,
                                onClick = {
                                    selectedComponents = Components.V2
                                    filteredDemoList = V2DEMO.toMutableList()
                                }
                            )
                        )

                        val appThemeStyle by AppTheme.observeThemeStyle(initial = FluentStyle.Neutral)
                        AppBar(
                            title = "Fluent UI Demo",
                            navigationIcon = FluentIcon(
                                SearchBarIcons.Arrowback,
                                contentDescription = "Navigate Back",
                            ),
                            appBarSize = AppBarSize.Large,
                            logo = {
                                Image(painter = painterResource(id = R.drawable.fluent_logo),
                                    contentDescription = "Fluent Logo",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .clickable { scope.launch { drawerState.open() } }
                                        .size(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size320))
                                        .clip(CircleShape)
                                )
                            },
                            searchMode = searchModeEnabled,
                            style = appThemeStyle,
                            rightAccessoryView = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fluent_search_24_regular),
                                    contentDescription = "Search Icon",
                                    modifier = Modifier.clickable { searchModeEnabled = true },
                                    tint = if (appThemeStyle == FluentStyle.Neutral) {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
                                    } else {
                                        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
                                    }
                                )

                                var expandedMenu by remember { mutableStateOf(false) }
                                Box {
                                    Icon(
                                        painter = painterResource(id = R.drawable.ic_fluent_more_vertical_24_regular),
                                        contentDescription = "More",
                                        modifier = Modifier
                                            .padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100))
                                            .clickable { expandedMenu = true },
                                        tint = if (appThemeStyle == FluentStyle.Neutral) {
                                            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value()
                                        } else {
                                            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value()
                                        }
                                    )
                                    Menu(
                                        opened = expandedMenu,
                                        onDismissRequest = { expandedMenu = false },
                                    ) {
                                        val scrollState = rememberScrollState()
                                        Column(modifier = Modifier.verticalScroll(scrollState)) {
                                            ListItem.Item(
                                                text = "Brand",
                                                trailingAccessoryContent = {
                                                    ToggleSwitch(
                                                        onValueChange = {
                                                            if (it) {
                                                                AppTheme.updateThemeStyle(
                                                                    FluentStyle.Brand
                                                                )
                                                            } else {
                                                                AppTheme.updateThemeStyle(
                                                                    FluentStyle.Neutral
                                                                )
                                                            }
                                                        },
                                                        checkedState = appThemeStyle == FluentStyle.Brand,
                                                    )
                                                },
                                                border = BorderType.Bottom,
                                                listItemTokens = listItemToken
                                            )

                                            ListItem.SectionHeader(
                                                title = "Choose your brand theme:",
                                                enableChevron = false,
                                                style = SectionHeaderStyle.Subtle,
                                                listItemTokens = listItemToken
                                            )

                                            Column {
                                                AppTheme.SetAppTheme()
                                            }
                                        }
                                    }
                                }
                            },
                            searchBar = if (searchModeEnabled) {
                                {
                                    SearchBar(
                                        onValueChange = { userInput, _ ->
                                            val demo = if (selectedComponents == Components.V1) {
                                                V1DEMO.toMutableList()
                                            } else {
                                                V2DEMO.toMutableList()
                                            }

                                            filteredDemoList = if (userInput.isEmpty()) {
                                                demo
                                            } else {
                                                demo.filter {
                                                    it.title.lowercase(Locale.getDefault())
                                                        .contains(userInput.lowercase(Locale.getDefault()))
                                                }.toMutableList()
                                            }
                                        },
                                        style = appThemeStyle,
                                        navigationIconCallback = { searchModeEnabled = false },
                                        focusByDefault = true
                                    )
                                }
                            } else null,
                            bottomBar = if (enableButtonBar) {
                                {
                                    PillTabs(
                                        style = appThemeStyle,
                                        metadataList = buttonBarList,
                                        selectedIndex = selectedComponents.ordinal,
                                    )
                                }
                            } else null,
                            appTitleDelta = appTitleDelta
                        )
                    },

                    floatingActionButton = {
                        FloatingActionButton(
                            icon = ImageVector.vectorResource(id = R.drawable.ic_fluent_document_text_24_regular),
                            onClick = { showDialog = !showDialog }
                        )
                    }
                ) {
                    LazyColumn(
                        Modifier
                            .fillMaxSize()
                            .padding(it)
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
                                    trailingAccessoryContent = {
                                        if (it.isNew) {
                                            Spacer(
                                                modifier = Modifier.width(
                                                    FluentGlobalTokens.size(
                                                        FluentGlobalTokens.SizeTokens.Size80
                                                    )
                                                )
                                            )
                                            Badge(text = "New", badgeType = BadgeType.List)
                                        }

                                        if (it.isModified) {
                                            Spacer(
                                                modifier = Modifier.width(
                                                    FluentGlobalTokens.size(
                                                        FluentGlobalTokens.SizeTokens.Size80
                                                    )
                                                )
                                            )
                                            Badge(text = "Modified", badgeType = BadgeType.List)
                                        }
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}