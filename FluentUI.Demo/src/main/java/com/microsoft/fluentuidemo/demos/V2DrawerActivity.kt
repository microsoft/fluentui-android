package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.getDrawerAsContent
import com.microsoft.fluentuidemo.util.getDynamicListGeneratorAsContent
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class V2DrawerActivity : V2DemoActivity() {
    @Composable
    fun MyScreenWithDrawer() {
        val drawerState = rememberDrawerState()
        val scope = rememberCoroutineScope()
        val isLeftDrawer = true // Or false for a right drawer

        val density = LocalDensity.current
        val edgeSwipeWidth = 20.dp // Define the width of the edge touch area
        val edgeSwipePx = with(density) { edgeSwipeWidth.toPx() }

        // To track if an edge swipe is in progress
        var isEdgeSwiping by remember { mutableStateOf(false) }
        // To get screen dimensions for right drawer edge detection
        var screenWidthPx by remember { mutableStateOf(0f) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { screenWidthPx = it.width.toFloat() }
                .pointerInput(drawerState, isLeftDrawer, screenWidthPx) { // Keys for recomposition if they change
                    detectHorizontalDragGestures(
                        onDragStart = { touch ->
                            if (drawerState.isClosed) { // Only start if drawer is closed
                                val initialX = touch.x
                                val canStartEdgeSwipe = if (isLeftDrawer) {
                                    initialX < edgeSwipePx
                                } else {
                                    initialX > screenWidthPx - edgeSwipePx
                                }

                                if (canStartEdgeSwipe) {
                                    isEdgeSwiping = true
                                    // Enable the drawer: this triggers composition, measurement, and anchor setup.
                                    // It's important that HorizontalDrawer gets a chance to set its anchors.
                                    scope.launch {
                                        drawerState.enable = true
                                        // Wait for anchors to be filled.
                                        // SwipeableState (which DrawerState extends) sets anchorsFilled = true
                                        // after processNewAnchors. This usually happens in a LaunchedEffect.
                                        // A short delay or polling drawerState.anchorsFilled can work here.
                                        // For a more robust solution, you might use snapshotFlow { drawerState.anchorsFilled }.
                                        var attempts = 0
                                        while (!drawerState.anchorsFilled && attempts < 15) { // Timeout after ~240ms
                                            delay(16) // Wait for approx one frame
                                            attempts++
                                        }
                                    }
                                }
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            if (isEdgeSwiping && drawerState.anchorsFilled) {
                                scope.launch {
                                    drawerState.performDrag(dragAmount)
                                }
                                change.consume()
                            } else if (isEdgeSwiping && !drawerState.anchorsFilled) {
                                // If anchors are still not filled, consume the event to prevent other gestures,
                                // but don't drag the drawer yet.
                                change.consume()
                            }
                        },
                        onDragEnd = {
                            if (isEdgeSwiping) {
                                scope.launch {
                                    // Perform fling with 0f velocity if you haven't tracked it.
                                    // SwipeableState will then settle based on the current position and thresholds.
                                    drawerState.performFling(0f)
                                    // If performFling results in the drawer being closed,
                                    // DrawerState's close() method will set enable = false.
                                }
                                isEdgeSwiping = false
                            }
                        },
                        onDragCancel = {
                            if (isEdgeSwiping) {
                                scope.launch {
                                    drawerState.performFling(0f) // Settle even on cancel
                                }
                                isEdgeSwiping = false
                            }
                        }
                    )
                }
        ) {
            MainContentBehindDrawer() // Your main screen content
        }
        val close: () -> Unit = {
            scope.launch {
                drawerState.close()
            }
        }
        // FluentUI Drawer
        if (drawerState.enable) {
            com.microsoft.fluentui.tokenized.drawer.Drawer(
                drawerState = drawerState,
                behaviorType = if (isLeftDrawer) BehaviorType.LEFT_SLIDE_OVER else BehaviorType.RIGHT_SLIDE_OVER,
                drawerContent = {
                    //getAndroidViewAsContent(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT)(close)
                    Column(
                        modifier = Modifier
                            .fillMaxHeight() // Ensure drawer has a defined size
                            .then(if (isLeftDrawer) Modifier.width(280.dp) else Modifier.width(280.dp)) // Example width
                            .background(Color.Blue) // Use your theme colors
                            .padding(16.dp)
                    ) {
                        Text("Drawer Content Area")
                        getDrawerAsContent()(close)
                    }
                }
                // Prevent dismissal on scrim click if you want the edge swipe to be the primary way to close when partially open
                // preventDismissalOnScrimClick = isEdgeSwiping
            )
        }
    }

    @Composable
    fun MainContentBehindDrawer(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Main Screen Content")
            Text("Swipe from the edge to open the drawer.")
            for(i in 1..100) {
                Text("Item $i")
            }
        }
    }
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-19"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-19"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            MyScreenWithDrawer()
        }
    }
}

enum class ContentType {
    FULL_SCREEN_SCROLLABLE_CONTENT,
    EXPANDABLE_SIZE_CONTENT,
    WRAPPED_SIZE_CONTENT
}

@Composable
private fun CreateActivityUI() {
    var scrimVisible by rememberSaveable { mutableStateOf(true) }
    var dynamicSizeContent by rememberSaveable { mutableStateOf(false) }
    var nestedDrawerContent by rememberSaveable { mutableStateOf(false) }
    var listContent by rememberSaveable { mutableStateOf(true) }
    var preventDismissalOnScrimClick by rememberSaveable { mutableStateOf(false) }
    var selectedContent by rememberSaveable { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    var selectedBehaviorType by rememberSaveable { mutableStateOf(BehaviorType.BOTTOM_SLIDE_OVER) }
    var relativeToParentAnchor by rememberSaveable {
        mutableStateOf(
            false
        )
    }
    var offsetX by rememberSaveable { mutableIntStateOf(0) }
    var offsetY by rememberSaveable { mutableIntStateOf(0) }
    Column {
        if (relativeToParentAnchor) {
            Row(
                Modifier
                    .width(500.dp)
                    .height(100.dp)
                    .border(width = 2.dp, color = Color.Red),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Label(
                    text = "Random composable. Drawer starts from below",
                    textStyle = FluentAliasTokens.TypographyTokens.Body1Strong
                )
            }
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                selectedBehaviorType,
                if (listContent)
                    getAndroidViewAsContent(selectedContent)
                else if (nestedDrawerContent) {
                    getDrawerAsContent()
                } else {
                    getDynamicListGeneratorAsContent()
                },
                scrimVisible = scrimVisible,
                IntOffset(offsetX, offsetY),
                preventDismissalOnScrimClick = preventDismissalOnScrimClick
            )
            LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                item {
                    ListItem.Header(title = stringResource(id = R.string.drawer_select_drawer_type))
                    ListItem.Item(text = stringResource(id = R.string.drawer_top),
                        subText = stringResource(id = R.string.drawer_top_description),
                        subTextMaxLines = Int.MAX_VALUE,
                        onClick = { selectedBehaviorType = BehaviorType.TOP },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedBehaviorType = BehaviorType.TOP
                                },
                                selected = selectedBehaviorType == BehaviorType.TOP
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_bottom),
                        subText = stringResource(id = R.string.drawer_bottom_description),
                        subTextMaxLines = Int.MAX_VALUE,
                        onClick = { selectedBehaviorType = BehaviorType.BOTTOM },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedBehaviorType = BehaviorType.BOTTOM
                                },
                                selected = selectedBehaviorType == BehaviorType.BOTTOM
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_left_slide_over),
                        subText = stringResource(id = R.string.drawer_left_slide_over_description),
                        subTextMaxLines = Int.MAX_VALUE,
                        onClick = { selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER
                                },
                                selected = selectedBehaviorType == BehaviorType.LEFT_SLIDE_OVER
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_right_slide_over),
                        subText = stringResource(id = R.string.drawer_right_slide_over_description),
                        subTextMaxLines = Int.MAX_VALUE,
                        onClick = { selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER
                                },
                                selected = selectedBehaviorType == BehaviorType.RIGHT_SLIDE_OVER
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_bottom_slide_over),
                        subText = stringResource(id = R.string.drawer_bottom_slide_over_description),
                        subTextMaxLines = Int.MAX_VALUE,
                        onClick = { selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER
                                },
                                selected = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER
                            )
                        }
                    )
                }
                item {
                    val preventDismissalOnScrimClickText =
                        stringResource(id = R.string.prevent_scrim_click_dismissal)
                    ListItem.Header(title = preventDismissalOnScrimClickText,
                        modifier = Modifier
                            .toggleable(
                                value = preventDismissalOnScrimClick,
                                role = Role.Switch,
                                onValueChange = {
                                    preventDismissalOnScrimClick = !preventDismissalOnScrimClick
                                }
                            )
                            .clearAndSetSemantics {
                                this.contentDescription = preventDismissalOnScrimClickText
                            },
                        trailingAccessoryContent = {
                            ToggleSwitch(
                                onValueChange = {
                                    preventDismissalOnScrimClick = !preventDismissalOnScrimClick
                                },
                                checkedState = preventDismissalOnScrimClick
                            )
                        }
                    )
                }
                item {
                    val scrimVisibleText = stringResource(id = R.string.drawer_scrim_visible)
                    ListItem.Header(title = scrimVisibleText, modifier = Modifier
                        .toggleable(
                            value = scrimVisible,
                            role = Role.Switch,
                            onValueChange = { scrimVisible = !scrimVisible }
                        )
                        .clearAndSetSemantics {
                            this.contentDescription = scrimVisibleText
                        }, trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = { scrimVisible = !scrimVisible },
                            checkedState = scrimVisible
                        )
                    }
                    )
                }
                item {
                    ListItem.Header(title = "Offset: X $offsetX.dp",
                        modifier = Modifier.fillMaxWidth(),
                        trailingAccessoryContent = {
                            Row {
                                Button(
                                    style = ButtonStyle.Button,
                                    size = ButtonSize.Medium,
                                    text = "+ 10 dp",
                                    enabled = true,
                                    onClick = { offsetX += 10 })
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    style = ButtonStyle.Button,
                                    size = ButtonSize.Medium,
                                    text = "- 10 dp",
                                    enabled = true,
                                    onClick = { offsetX -= 10 })
                            }
                        }
                    )
                }
                item {
                    ListItem.Header(title = "Offset: Y $offsetY.dp",
                        modifier = Modifier.fillMaxWidth(),
                        trailingAccessoryContent = {
                            Row {
                                Button(
                                    style = ButtonStyle.Button,
                                    size = ButtonSize.Medium,
                                    text = "+ 10 dp",
                                    enabled = true,
                                    onClick = { offsetY += 10 })
                                Spacer(modifier = Modifier.width(10.dp))
                                Button(
                                    style = ButtonStyle.Button,
                                    size = ButtonSize.Medium,
                                    text = "- 10 dp",
                                    enabled = true,
                                    onClick = { offsetY -= 10 })
                            }
                        }
                    )
                }

                item {
                    ListItem.Header(title = stringResource(id = R.string.drawer_select_drawer_content))
                    ListItem.Item(text = stringResource(id = R.string.drawer_full_screen_size_scrollable_content),
                        onClick = {
                            selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                            listContent = true
                            nestedDrawerContent = false
                            dynamicSizeContent = false
                        },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedContent = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
                                    listContent = true
                                    nestedDrawerContent = false
                                    dynamicSizeContent = false
                                },
                                selected = selectedContent == ContentType.FULL_SCREEN_SCROLLABLE_CONTENT && listContent
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_more_than_half_screen_content),
                        onClick = {
                            selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                            listContent = true
                            nestedDrawerContent = false
                            dynamicSizeContent = false
                        },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedContent = ContentType.EXPANDABLE_SIZE_CONTENT
                                    listContent = true
                                    nestedDrawerContent = false
                                    dynamicSizeContent = false
                                },
                                selected = selectedContent == ContentType.EXPANDABLE_SIZE_CONTENT && listContent
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_less_than_half_screen_content),
                        onClick = {
                            selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                            listContent = true
                            dynamicSizeContent = false
                            nestedDrawerContent = false
                        },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    selectedContent = ContentType.WRAPPED_SIZE_CONTENT
                                    listContent = true
                                    dynamicSizeContent = false
                                    nestedDrawerContent = false
                                },
                                selected = selectedContent == ContentType.WRAPPED_SIZE_CONTENT && listContent
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_dynamic_size_content),
                        onClick = {
                            dynamicSizeContent = true
                            nestedDrawerContent = false
                            listContent = false
                        },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    dynamicSizeContent = true
                                    nestedDrawerContent = false
                                    listContent = false
                                },
                                selected = dynamicSizeContent
                            )
                        }
                    )
                    ListItem.Item(text = stringResource(id = R.string.drawer_nested_drawer_content),
                        onClick = {
                            nestedDrawerContent = true
                            dynamicSizeContent = false
                            listContent = false
                        },
                        trailingAccessoryContent = {
                            RadioButton(
                                onClick = {
                                    nestedDrawerContent = true
                                    dynamicSizeContent = false
                                    listContent = false
                                },
                                selected = nestedDrawerContent
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
    behaviorType: BehaviorType,
    drawerContent: @Composable ((() -> Unit) -> Unit),
    scrimVisible: Boolean = true,
    offset: IntOffset = IntOffset.Zero,
    preventDismissalOnScrimClick: Boolean
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val expand: () -> Unit = {
        scope.launch { drawerState.expand() }
    }
    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }
    Row {
        PrimarySurfaceContent(
            open,
            text = stringResource(id = R.string.drawer_open)
        )
        Spacer(modifier = Modifier.width(10.dp))
        PrimarySurfaceContent(
            expand,
            text = stringResource(id = R.string.drawer_expand)
        )
    }

    Drawer(
        drawerState = drawerState,
        offset = offset,
        drawerContent = { drawerContent(close) },
        behaviorType = behaviorType,
        scrimVisible = scrimVisible,
        preventDismissalOnScrimClick = preventDismissalOnScrimClick
    )
}