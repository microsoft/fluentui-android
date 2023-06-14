package com.microsoft.fluentuidemo

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Brush.Companion.linearGradient
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.LinearGradient
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import com.example.theme.token.ExcelAliasTokens
import com.example.theme.token.M365AliasTokens
import com.example.theme.token.MyAppBarToken
import com.example.theme.token.MyButtonTokens
import com.example.theme.token.MyFABToken
import com.example.theme.token.OneNoteAliasTokens
import com.example.theme.token.PowerPointAliasTokens
import com.example.theme.token.WordAliasTokens
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.icons.searchbaricons.Search
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.controlTokens
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.FABState
import com.microsoft.fluentui.theme.token.controlTokens.LabelTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.listitem.TextIcons
import com.microsoft.fluentui.tokenized.menu.Dialog
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import kotlinx.coroutines.launch
import java.io.File
import java.time.format.TextStyle
import java.util.Locale

enum class Components (val selected :Int) {
    V1(0),
    V2(1)
}
class V2DemoListActivity : ComponentActivity() {
    @Composable
    fun GetDrawerContent() {
        val scrollState = rememberScrollState()
        Column(
            verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size320)),
            modifier = Modifier
                .verticalScroll(scrollState)
                .background(
                    color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                        FluentTheme.themeMode
                    )
                )
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size60)),
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
                verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100)),
                modifier = Modifier.padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100))
            ) {
                Label(
                    text = "Open source cross platform Design System.",
                    textStyle = FluentAliasTokens.TypographyTokens.Title1
                )

//                linearGradient(
//                    colors = listOf(Color.Cyan, Color.Green),
//                    start = Offset.Zero,
//                    end = Offset.Infinite
//                )

                Label(
                    text = "Intuitive & Powerful.",
                    textStyle = FluentAliasTokens.TypographyTokens.Title1
                )
            }

            Divider(
                color = FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke2].value(
                    themeMode = FluentTheme.themeMode
                )
            )

            Column (
                verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100)),
            ) {
                val uriHandler = LocalUriHandler.current
                val listItemToken = object : ListItemTokens () {
                    @Composable
                    override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
                        return StateBrush(
                            rest = SolidColor(
                                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            ),
                            pressed = SolidColor(
                                FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2Pressed].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        )
                    }
                }

                ListItem.SectionHeader(
                    title = "More",
                    enableChevron = false,
                    listItemTokens = listItemToken
                )
                ListItem.Item(
                    text = "Github Repo",
                    trailingAccessoryView = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_link_24_regular),
                            contentDescription = "Fluent Link",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                FluentTheme.themeMode
                            ),
                            modifier = Modifier.clickable (
                                onClick = {
                                    uriHandler.openUri("https://github.com/microsoft/fluentui-android")
                                }
                            )
                        )
                    },
                    listItemTokens = listItemToken,
                    onClick = {
                        uriHandler.openUri("https://github.com/microsoft/fluentui-android")
                    }
                )
                ListItem.Item(text = "Your Feedback", listItemTokens = listItemToken)
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100)),
                modifier = Modifier.padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size100))
            ) {
                Label(
                    text = "Theme",
                    textStyle = FluentAliasTokens.TypographyTokens.Caption1Strong
                )

                val controlTokens = ControlTokens()
                val horizontalScrollState = rememberScrollState()
                Row (
                    horizontalArrangement = Arrangement.spacedBy(
                        FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size60),
                        Alignment.CenterHorizontally
                    ),

                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
                        contentDescription = "Theme1" ,
                        tint = AliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        modifier = Modifier.clickable (
                            onClick = {
                                FluentTheme.updateAliasTokens(AliasTokens())
                            }
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
                        contentDescription = "Theme2" ,
                        tint = OneNoteAliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80 ],
                        modifier = Modifier.clickable (
                            onClick = {
                                FluentTheme.updateAliasTokens(OneNoteAliasTokens())
                            }
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
                        contentDescription = "Theme3" ,
                        tint = WordAliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80 ],
                        modifier = Modifier.clickable (
                            onClick = {
                                FluentTheme.updateAliasTokens(WordAliasTokens())
                            }
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
                        contentDescription = "Theme4" ,
                        tint = ExcelAliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80 ],
                        modifier = Modifier.clickable (
                            onClick = {
                                FluentTheme.updateAliasTokens(ExcelAliasTokens())
                            }
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
                        contentDescription = "Theme5" ,
                        tint = PowerPointAliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80 ],
                        modifier = Modifier.clickable (
                            onClick = {
                                FluentTheme.updateAliasTokens(PowerPointAliasTokens())
                            }
                        )
                    )

                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
                        contentDescription = "Theme5" ,
                        tint = M365AliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80 ],
                        modifier = Modifier.clickable (
                            onClick = {
                                FluentTheme.updateAliasTokens(M365AliasTokens())
                            }
                        )
                    )

//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_fluent_circle_28_filled),
//                        contentDescription = "Theme7" ,
//                        tint = AliasTokens().brandColor[FluentAliasTokens.BrandColorTokens.Color80],
//                        modifier = Modifier.clickable (
//                            onClick = {
//                                FluentTheme.updateControlTokens(
//                                    controlTokens.updateToken(
//                                        ControlTokens.ControlType.AppBar,
//                                        MyAppBarToken()
//                                    ).updateToken(
//                                        ControlTokens.ControlType.FloatingActionButton,
//                                        MyFABToken()
//                                    )
//                                )
//                            }
//                        )
//                    )
                }
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

                var searchModeEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
                val enableButtonBar: Boolean by rememberSaveable { mutableStateOf(true) }
                var selectedComponents by rememberSaveable { mutableStateOf(Components.V2.selected) }
                var filteredDemoList by remember { mutableStateOf(V2DEMO.toMutableList()) }
                var showDialog by remember { mutableStateOf(false) }

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
                                    selectedComponents = Components.V1.selected
                                    filteredDemoList = V1DEMO.toMutableList()
                                }
                            )
                        )
                        buttonBarList.add(
                            PillMetaData(
                                text = "V2 Components",
                                enabled = true,
                                onClick = {
                                    selectedComponents = Components.V2.selected
                                    filteredDemoList = V2DEMO.toMutableList()
                                }
                            )
                        )

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
                            rightAccessoryView = {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_fluent_search_24_regular),
                                    contentDescription = "Search Icon",
                                    modifier = Modifier
                                        .padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
                                        .clickable(
                                            onClick = {
                                                searchModeEnabled = true
                                            }
                                        ),
                                    tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                        FluentTheme.themeMode
                                    )
                                )
                            },
                            searchBar = if (searchModeEnabled) {
                                {
                                    SearchBar(
                                        onValueChange = { userInput, _ ->
                                            val demo = if(selectedComponents == 0) {V1DEMO.toMutableList()} else {V2DEMO.toMutableList()}
                                            //val demo = DEMOS.toMutableList()
                                            filteredDemoList = if (userInput.isEmpty()) {
                                                demo
                                            } else {
                                                demo.filter {
                                                    it.title.lowercase(Locale.getDefault())
                                                        .contains(userInput.lowercase(Locale.getDefault()))
                                                }.toMutableList()
                                            }
                                        },
                                        navigationIconCallback = { searchModeEnabled = false },
                                        focusByDefault = true
                                    )
                                }
                            } else null,

                            bottomBar = if (enableButtonBar) {
                                {
                                    PillTabs(
                                        metadataList = buttonBarList,
                                        selectedIndex = selectedComponents,
                                    )
                                }
                            } else null,

                            appTitleDelta = appTitleDelta
                        )
                    },

                    floatingActionButton = {
                        FloatingActionButton(
                            icon = ImageVector.vectorResource(id = R.drawable.ic_fluent_info_16_regular),
                            onClick = {
                                showDialog = !showDialog
                            }
                        )
                        if(showDialog) {
                            Dialog (
                                dismissOnBackPress = true,
                                dismissOnClickedOutside = true,
                                onDismiss = { showDialog = !showDialog },
                                content = {
                                    val scrollState = rememberScrollState()
                                    Label (
                                        modifier = Modifier
                                            .verticalScroll(scrollState)
                                            .padding(
                                                all = FluentGlobalTokens.size(
                                                    FluentGlobalTokens.SizeTokens.Size160
                                                )
                                            )
                                            .background(
                                                color = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                                                    FluentTheme.themeMode
                                                )
                                            ),
                                        text = application.assets.open("dogfood-release-notes.txt").bufferedReader().use {
                                            it.readText() },
                                        textStyle = FluentAliasTokens.TypographyTokens.Body2
                                    )
                                }
                            )
                        }
                    }
                )


                {
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
                                        val intent =
                                            Intent(this@V2DemoListActivity, it.demoClass.java)
                                        intent.putExtra(DemoActivity.DEMO_ID, it.id)
                                        this@V2DemoListActivity.startActivity(intent)
                                    },
                                    trailingAccessoryView = {
                                        if(it.isNew) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Badge (text = "New", badgeType = BadgeType.List) }

                                        if(it.isModified) {
                                            Spacer(modifier = Modifier.width(8.dp))
                                            Badge (text = "Modified", badgeType = BadgeType.List)}
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
