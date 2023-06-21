package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.persona.PersonaListView
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheet
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetState
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetValue
import com.microsoft.fluentui.tokenized.bottomsheet.rememberBottomSheetState
import com.microsoft.fluentui.tokenized.contentBuilder.ItemData
import com.microsoft.fluentui.tokenized.contentBuilder.ListContentBuilder
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.createPersonaList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2BottomSheetActivity : V2DemoActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
                CreateActivityUI()
        }
    }
}

@Composable
private fun CreateActivityUI() {
    var showHandleState by remember { mutableStateOf(true) }

    var expandableState by remember { mutableStateOf(true) }

    var slideOverState by remember { mutableStateOf(true) }

    var peekHeightState by remember { mutableStateOf(110.dp) }

    var hidden by remember { mutableStateOf(true) }

    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Shown)

    val scope = rememberCoroutineScope()

    hidden = !bottomSheetState.isVisible

    val context = LocalContext.current
    val contentByListContentBuilder = ListContentBuilder()
        .addHorizontalList(getSingleLineList(context), "Default: Wrapped")
        .addDivider()
        .addHorizontalList(getSingleLineList(context), "Fixed width", fixedWidth = true)
        .addDivider()
        .addVerticalGrid(
            getSingleLineList(context),
            "Vertical Grid",
            3
        )
        .addDivider()
        .addVerticalGrid(
            getSingleLineList(context),
            "Vertical Grid: Equidistant",
            3,
            true
        )
        .addVerticalList(getDoubleLineList(context), "Double Line List")
        .addVerticalList(
            getSingleLineList(context),
            "Single Line List"
        )
        .getContent()
    var sheetContentState by remember { mutableStateOf(contentByListContentBuilder) }
    val content = listOf(0, 1, 2)
    val selectedOption = remember { mutableStateOf(content[0]) }

    BottomSheet(
        sheetContent = sheetContentState,
        expandable = expandableState,
        peekHeight = peekHeightState,
        showHandle = showHandleState,
        sheetState = bottomSheetState,
        slideOver = slideOverState
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = "Show",
                    enabled = hidden,
                    onClick = {
                        hidden = false
                        scope.launch { bottomSheetState.show() }
                    }
                )

                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = "Jump Show",
                    enabled = hidden,
                    onClick = {
                        hidden = false
                        scope.launch {
                            bottomSheetState.show()
                            for (x in 1..9) {
                                delay(17)
                                peekHeightState += x.dp
                            }

                            for (x in 1..9) {
                                delay(17)
                                peekHeightState -= x.dp
                            }

                        }
                    }
                )
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = "Hide",
                    enabled = !hidden,
                    onClick = {
                        hidden = true
                        scope.launch { bottomSheetState.hide() }
                    }
                )

                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = "Expand",
                    enabled = !hidden && expandableState,
                    onClick = {
                        scope.launch { bottomSheetState.expand() }
                    }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicText(
                    text = "Expandable",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )
                ToggleSwitch(checkedState = expandableState,
                    onValueChange = { expandableState = it }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicText(
                    text = "Show Handle",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )
                ToggleSwitch(checkedState = showHandleState,
                    onValueChange = { showHandleState = it }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicText(
                    text = "Slide Over",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )
                ToggleSwitch(checkedState = slideOverState,
                    onValueChange = { slideOverState = it }
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicText(
                    text = "Peek Height $peekHeightState",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )
                Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    text = "+ 8 dp",
                    enabled = !hidden,
                    onClick = { peekHeightState += 8.dp })

                Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    text = "- 8 dp",
                    enabled = !hidden && (peekHeightState > 0.dp),
                    onClick = { peekHeightState -= 8.dp })
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                BasicText(
                    text = "Note: When 'Slide Over' is On then Peek Height max limit is restricted to half of screen size. When 'Slide Over' is off then bottomSheet height does not vary with content height. It either open at peek height or expand to fullest or hide at bottom",
                    modifier = Modifier.weight(1F)
                )
            }


            Row {
                BasicText(
                    text = "Select SheetContent",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )
            }

            Row {
                BasicText(
                    text = "From ItemListContentBuilder",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )

                RadioButton(
                    selected = (selectedOption.value == content[0]),
                    onClick = {
                        selectedOption.value = content[0]
                        sheetContentState = contentByListContentBuilder
                    }
                )
            }
            Row {
                BasicText(
                    text = "Using AndroidView",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )

                RadioButton(
                    selected = (selectedOption.value == content[1]),
                    onClick = {
                        selectedOption.value = content[1]
                        sheetContentState = content1(bottomSheetState)
                    }
                )
            }
            Row {
                BasicText(
                    text = "Compose Content",
                    modifier = Modifier.weight(1F),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )

                RadioButton(
                    selected = (selectedOption.value == content[2]),
                    onClick = {
                        selectedOption.value = content[2]
                        sheetContentState = content2(bottomSheetState)
                    }
                )
            }

            Button(
                style = ButtonStyle.OutlinedButton,
                size = ButtonSize.Medium,
                enabled = !hidden,
                text = "Jump to indicate more content",
                onClick = {
                    scope.launch {
                        for (x in 1..9) {
                            delay(17)
                            peekHeightState += x.dp
                        }
                        for (x in 1..9) {
                            delay(17)
                            peekHeightState -= x.dp
                        }
                    }
                }
            )

            val nestedScrollConnection = remember {
                object : NestedScrollConnection {
                    override fun onPreScroll(
                        available: Offset,
                        source: NestedScrollSource
                    ): Offset {
                        val delta = available.y
                        hidden = if (delta < 0) {
                            scope.launch { bottomSheetState.hide() }
                            true
                        } else {
                            scope.launch { bottomSheetState.show() }
                            false
                        }
                        return Offset.Zero
                    }
                }
            }

            Box(
                modifier = Modifier
                    .nestedScroll(nestedScrollConnection)
                    .verticalScroll(
                        rememberScrollState()
                    )

            )
            {
                BasicText(
                    text = context.resources.getString(R.string.large_scrollable_text),
                    style = TextStyle(
                        color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            themeMode = ThemeMode.Auto
                        )
                    )
                )
            }
        }
    }
}

fun content1(bottomSheetState: BottomSheetState): @Composable () -> Unit = {
    lateinit var context: Context
    val scope = rememberCoroutineScope()
    val state = rememberScrollState()
    AndroidView(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(state),
        factory = {
            context = it
            val view = it.activity!!.layoutInflater.inflate(
                R.layout.demo_drawer_content,
                null
            )!!.rootView
            val personaList = createPersonaList(context)
            (view as PersonaListView).personas = personaList
            view
        }
    ) {
        if (bottomSheetState.currentValue == BottomSheetValue.Shown) {
            scope.launch {
                state.animateScrollTo(0)
            }
        }
    }
}

fun content2(bottomSheetState: BottomSheetState): @Composable () -> Unit = {
    val no = remember { mutableStateOf(0) }
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            Button(
                style = ButtonStyle.Button,
                size = ButtonSize.Medium,
                text = "Click to create random size list",
                onClick = { no.value = (40 * Math.random()).toInt() })
        }

        repeat(no.value) {
            item {
                Spacer(Modifier.height(10.dp))
                BasicText("list item $it")
            }
        }
    }

    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = bottomSheetState.currentValue) {
        if (bottomSheetState.currentValue == BottomSheetValue.Shown) {
            scope.launch {
                lazyListState.animateScrollToItem(0)
            }
        }
    }
}

fun getSingleLineList(context: Context): List<ItemData> {
    return arrayListOf(
        ItemData(icon = Icons.Outlined.Email, title = "Email", onClick = {}),
        ItemData(
            icon = Icons.Outlined.ArrowBack,
            title = context.resources.getString(R.string.bottom_sheet_item_reply_title),
            onClick = {}
        ),
        ItemData(
            icon = Icons.Outlined.ArrowForward,
            title = context.resources.getString(R.string.bottom_sheet_item_forward_title),
            onClick = {},
            enabled = false
        ),
        ItemData(icon = Icons.Outlined.Favorite, title = "Favorite", onClick = {}, enabled = false),
        ItemData(icon = Icons.Outlined.Info, title = "Long Info text", onClick = {}),
        ItemData(icon = Icons.Outlined.Menu, title = "Menu", onClick = {}),
        ItemData(icon = Icons.Outlined.Share, title = "Share", onClick = {}),
        ItemData(
            icon = Icons.Outlined.Delete,
            title = context.resources.getString(R.string.bottom_sheet_item_delete_title),
            onClick = {})
    )
}

fun getDoubleLineList(context: Context): List<ItemData> {
    return arrayListOf(
        ItemData(
            icon = Icons.Outlined.Person,
            title = context.resources.getString(R.string.bottom_sheet_item_camera_title),
            subTitle = context.resources.getString(R.string.bottom_sheet_item_camera_subtitle),
            onClick = {}),
        ItemData(
            icon = Icons.Outlined.List,
            title = context.resources.getString(R.string.bottom_sheet_item_gallery_title),
            subTitle = context.resources.getString(R.string.bottom_sheet_item_gallery_subtitle),
            onClick = {},
            enabled = false
        ),
        ItemData(
            icon = Icons.Outlined.Settings,
            title = context.resources.getString(R.string.bottom_sheet_item_manage_title),
            subTitle = context.resources.getString(R.string.bottom_sheet_item_manage_subtitle),
            onClick = {}),
        ItemData(
            icon = Icons.Outlined.Face,
            title = context.resources.getString(R.string.bottom_sheet_item_videos_title),
            subTitle = context.resources.getString(R.string.bottom_sheet_item_videos_subtitle),
            onClick = {},
            enabled = false
        )
    )
}