package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.tokenized.acrylicpane.AcrylicPane
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Office
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.drawer.DrawerValue
import com.microsoft.fluentui.tokenized.drawer.rememberBottomDrawerState
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.CustomizedSearchBarTokens
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.getDemoAppString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2AcrylicPaneActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-18" //TODO: Update this URL
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-18"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            CreateAcrylicPaneActivityUI(this)
        }
    }
}

@Composable
fun CreateAcrylicPaneActivityUI(
    context: Context
){
    var acrylicPaneSizeFraction by rememberSaveable { mutableFloatStateOf(0.5F) }
    var acrylicPaneStyle by rememberSaveable { mutableStateOf(FluentStyle.Neutral) }

    AcrylicPane(
        paneHeight = (acrylicPaneSizeFraction * 500).toInt().dp,
        acrylicPaneStyle = acrylicPaneStyle,
        component = { acrylicPaneContent(context = context) },
        backgroundContent = {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()).fillMaxWidth().padding(10.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(Modifier.height(300.dp))
                ListItem.Header(
                    title = "Acrylic Pane Size",
                    titleMaxLines = 2,
                    modifier = Modifier
                        .clearAndSetSemantics {
                            this.contentDescription = "Acrylic Pane Size"
                        },
                )
                Slider(
                    value = acrylicPaneSizeFraction,
                    onValueChange = { acrylicPaneSizeFraction = it },
                    valueRange = 0F..1F,
                    colors = SliderDefaults.colors(
                        thumbColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                            FluentTheme.themeMode
                        ),
                        activeTrackColor = FluentTheme.aliasTokens.brandColor[FluentAliasTokens.BrandColorTokens.Color80],
                        inactiveTrackColor = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background3].value(
                            FluentTheme.themeMode
                        ),
                        disabledThumbColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            FluentTheme.themeMode
                        ),
                        disabledActiveTrackColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            FluentTheme.themeMode
                        ),
                        disabledInactiveTrackColor = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                            FluentTheme.themeMode
                        )
                    ),
                    steps = 9
                )
                ListItem.Header(
                    title = "Acrylic Pane Theme",
                    titleMaxLines = 2,
                    modifier = Modifier
                        .clearAndSetSemantics {
                            this.contentDescription = "Acrylic Pane Theme"
                        },
                )
                var checkBoxSelectedValues = List(2) { rememberSaveable { mutableStateOf(false) } }
                var acrylicPaneStyles = listOf(
                    FluentStyle.Neutral,
                    FluentStyle.Brand
                )
                for (i in 0..1) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 10.dp, vertical = 3.dp)
                    ) {
                        Text(text = "Theme $i")
                        Spacer(modifier = Modifier.width(320.dp))
                        RadioButton(
                            onClick = {
                                selectRadioGroupButton(i, checkBoxSelectedValues)
                                acrylicPaneStyle = acrylicPaneStyles[i]
                            },
                            selected = checkBoxSelectedValues[i].value
                        )
                    }
                }
                ListItem.Header(
                    title = "Test Bottom Drawer",
                    titleMaxLines = 2,
                    modifier = Modifier
                        .clearAndSetSemantics {
                            this.contentDescription = "Test Bottom Drawer"
                        },
                )
                showBottomDrawer()
                ListItem.Header(
                    title = "Scroll Test",
                    titleMaxLines = 2,
                    modifier = Modifier
                        .clearAndSetSemantics {
                            this.contentDescription = "Test Bottom Drawer"
                        },
                )
                repeat(40) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                        modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 5.dp)
                    ) {
                        Text(text = "Text $it", fontSize = 14.sp)
                    }
                }
            }
        }
    )
}

@Composable
fun showBottomDrawer(){
    val scope = rememberCoroutineScope()

    val drawerState = rememberBottomDrawerState(initialValue = DrawerValue.Closed, expandable = true, skipOpenState = false)

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
    var selectedContent by rememberSaveable { mutableStateOf(ContentType.FULL_SCREEN_SCROLLABLE_CONTENT) }
    val drawerContent = getAndroidViewAsContent(selectedContent)
    var maxLandscapeWidthFraction by rememberSaveable { mutableFloatStateOf(1F) }
    var preventDismissalOnScrimClick by rememberSaveable { mutableStateOf(false) }
    com.microsoft.fluentui.tokenized.drawer.BottomDrawer(
        drawerState = drawerState,
        drawerContent = { drawerContent(close) },
        scrimVisible = true,
        slideOver = true,
        showHandle = true,
        enableSwipeDismiss = true,
        maxLandscapeWidthFraction = maxLandscapeWidthFraction,
        preventDismissalOnScrimClick = preventDismissalOnScrimClick
    )
}

@Composable
fun acrylicPaneContent(context: Context){
    val scope = rememberCoroutineScope()

    val microphonePressedString = getDemoAppString(DemoAppStrings.MicrophonePressed)
    val rightViewPressedString = getDemoAppString(DemoAppStrings.RightViewPressed)
    val keyboardSearchPressedString = getDemoAppString(DemoAppStrings.KeyboardSearchPressed)
    var loading by rememberSaveable { mutableStateOf(false) }
    val keyboardController = LocalSoftwareKeyboardController.current
    var autoCorrectEnabled: Boolean by rememberSaveable { mutableStateOf(false) }
    var enableMicrophoneCallback: Boolean by rememberSaveable { mutableStateOf(true) }
    var searchBarStyle: FluentStyle by rememberSaveable { mutableStateOf(FluentStyle.Brand) }
    var displayRightAccessory: Boolean by rememberSaveable { mutableStateOf(true) }
    var selectedPeople: Person? by rememberSaveable { mutableStateOf(null) }
    val showCustomizedAppBar = false
    Column {
        Spacer(modifier = Modifier.height(80.dp))
        Row(Modifier.height(5.dp).padding(20.dp)) {
            SearchBar(
                onValueChange = { query, selectedPerson ->
                    scope.launch {
                        loading = true
                        delay(2000)
                        loading = false
                    }
                },
                style = searchBarStyle,
                loading = loading,
                selectedPerson = selectedPeople,
                microphoneCallback = if (enableMicrophoneCallback) {
                    {
                        Toast.makeText(context, microphonePressedString, Toast.LENGTH_SHORT)
                            .show()
                    }
                } else null,
                keyboardOptions = KeyboardOptions(
                    autoCorrect = autoCorrectEnabled,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        Toast.makeText(
                            context,
                            keyboardSearchPressedString,
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        keyboardController?.hide()
                    }
                ),
                rightAccessoryIcon = if (displayRightAccessory) {
                    FluentIcon(
                        SearchBarIcons.Office,
                        contentDescription = "Office",
                        onClick = {
                            Toast.makeText(
                                context,
                                rightViewPressedString,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    )
                } else null,
                searchBarTokens = if (showCustomizedAppBar) {
                    CustomizedSearchBarTokens
                } else null,
                modifier = if (showCustomizedAppBar) Modifier.requiredHeight(60.dp) else Modifier
            )
        }
    }
}
