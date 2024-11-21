package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.DrawerV2
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
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-19"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-19"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateActivityUI()
        }
    }
}

enum class ContentType {
    FULL_SCREEN_SCROLLABLE_CONTENT,
    EXPANDABLE_SIZE_CONTENT,
    WRAPPED_SIZE_CONTENT
}

@Composable
fun navDrawerEntry(text: String, subText: String, @DrawableRes id: Int){
    NavigationDrawerItem(
        label = {
            Column(modifier = Modifier.fillMaxWidth()) {
                //Spacer(modifier = Modifier.height(10.dp))
                Text(text = text, fontSize = 15.sp)
                Spacer(modifier = Modifier.height(2.dp))
                Text(text = subText, fontSize = 13.sp, color = Color.Gray)
            } },
        selected = false,
        onClick = { /*TODO*/ },
        icon = {
            Image(
                painterResource(id), contentDescription = "Drawer Item Icon", modifier = Modifier.width(42.dp).clip(
                    CircleShape
                ))
        },
        modifier = Modifier.padding(vertical = 2.dp, horizontal = 4.dp),
        colors = NavigationDrawerItemDefaults.colors(
            unselectedContainerColor = Color.White,
        )
    )
    HorizontalDivider(modifier = Modifier.padding(horizontal = 30.dp).offset(x = 40.dp))
}

@Composable
private fun rememberSaveableCheckboxes(count: Int): List<MutableState<Boolean>> {
    return List(count) { rememberSaveable { mutableStateOf(false) } }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CreateActivityUI() {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val rightDrawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    var showRightDrawer by rememberSaveable { mutableStateOf(false) }

    CompositionLocalProvider(if (showRightDrawer) LocalLayoutDirection provides LayoutDirection.Rtl else LocalLayoutDirection provides LayoutDirection.Ltr) {
        ModalNavigationDrawer(
            drawerContent = {
                CompositionLocalProvider(if (showRightDrawer) LocalLayoutDirection provides LayoutDirection.Rtl else LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalDrawerSheet {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                            TopAppBar(
                                title = {
                                    Text(
                                        "People",
                                        modifier = Modifier.padding(horizontal = 10.dp),
                                        fontSize = 18.sp
                                    )
                                },
                                colors = TopAppBarColors(
                                    Color(0xff0f6cbd),
                                    Color(0xff0f6cbd),
                                    Color(0xff0f6cbd),
                                    Color.White,
                                    Color(0xff0f6cbd)
                                )
                            )
                            Column(
                                modifier = Modifier.fillMaxSize().background(Color.White)
                                    .verticalScroll(
                                        rememberScrollState()
                                    )
                            ) {
                                navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                                navDrawerEntry(
                                    "Amanda Brady",
                                    "Manager",
                                    R.drawable.avatar_amanda_brady
                                )
                                navDrawerEntry(
                                    "Cecil Folk",
                                    "Manager",
                                    R.drawable.avatar_cecil_folk
                                )
                                navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                                navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                                navDrawerEntry(
                                    "Amanda Brady",
                                    "Manager",
                                    R.drawable.avatar_amanda_brady
                                )
                                navDrawerEntry(
                                    "Cecil Folk",
                                    "Manager",
                                    R.drawable.avatar_cecil_folk
                                )
                                navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                                navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                                navDrawerEntry(
                                    "Amanda Brady",
                                    "Manager",
                                    R.drawable.avatar_amanda_brady
                                )
                                navDrawerEntry(
                                    "Cecil Folk",
                                    "Manager",
                                    R.drawable.avatar_cecil_folk
                                )
                                navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                                navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                                navDrawerEntry(
                                    "Amanda Brady",
                                    "Manager",
                                    R.drawable.avatar_amanda_brady
                                )
                                navDrawerEntry(
                                    "Cecil Folk",
                                    "Manager",
                                    R.drawable.avatar_cecil_folk
                                )
                                navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                                navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                                navDrawerEntry(
                                    "Amanda Brady",
                                    "Manager",
                                    R.drawable.avatar_amanda_brady
                                )
                                navDrawerEntry(
                                    "Cecil Folk",
                                    "Manager",
                                    R.drawable.avatar_cecil_folk
                                )
                                navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                            }
                        }
                    }
                }
            },
            drawerState = rightDrawerState,
            modifier = Modifier
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        TopAppBar(
                            title = { Text("Material3 Based Drawer POC", modifier = Modifier.padding(horizontal = 30.dp)) },
                            colors = TopAppBarColors(Color(0xff0f6cbd), Color(0xff0f6cbd), Color(0xff0f6cbd),Color.White,Color(0xff0f6cbd))
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text("Drawer Behaviour Menu", fontSize = 14.sp, textAlign = TextAlign.Start, color = Color.Gray, modifier = Modifier.padding(start = 10.dp))
                        var checkBoxSelectedValues = rememberSaveableCheckboxes(3)
                        DropdownMenuItem(
                            text = { Text("Slide from Left") },
                            onClick = {
                                checkBoxSelectedValues[0].value = true
                                checkBoxSelectedValues[1].value = false
                                checkBoxSelectedValues[2].value = false
                                showBottomSheet = false
                                if (rightDrawerState.isOpen && showRightDrawer) {
                                    scope.launch {
                                        rightDrawerState.close()
                                        showRightDrawer = false
                                        delay(50)
                                        rightDrawerState.open()
                                    }
                                } else if (rightDrawerState.isClosed) {
                                    showRightDrawer = false
                                    scope.launch {
                                        delay(50)
                                        rightDrawerState.open()
                                    }
                                }
                            },
                            trailingIcon = {
                                androidx.compose.material3.RadioButton(
                                    selected = checkBoxSelectedValues[0].value,
                                    onClick = {
                                        checkBoxSelectedValues[0].value = true
                                        checkBoxSelectedValues[1].value = false
                                        checkBoxSelectedValues[2].value = false
                                        showBottomSheet = false
                                        if (rightDrawerState.isOpen && showRightDrawer) {
                                            scope.launch {
                                                rightDrawerState.close()
                                                showRightDrawer = false
                                                rightDrawerState.open()
                                            }
                                        } else if (rightDrawerState.isClosed) {
                                            showRightDrawer = false
                                            scope.launch {
                                                delay(50)
                                                rightDrawerState.open()
                                            }
                                        }
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xff0f6cbd)
                                    )
                                )
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("Slide from Right") },
                            onClick = {
                                checkBoxSelectedValues[0].value = false
                                checkBoxSelectedValues[1].value = true
                                checkBoxSelectedValues[2].value = false
                                showBottomSheet = false
                                if (rightDrawerState.isOpen && !showRightDrawer) {
                                    scope.launch {
                                        rightDrawerState.close()
                                        showRightDrawer = true
                                        rightDrawerState.open()
                                    }
                                } else if (rightDrawerState.isClosed) {
                                    showRightDrawer = true
                                    scope.launch {
                                        delay(50)
                                        rightDrawerState.open()
                                    }
                                }
                            },
                            trailingIcon = {
                                androidx.compose.material3.RadioButton(
                                    selected = checkBoxSelectedValues[1].value,
                                    onClick = {
                                        checkBoxSelectedValues[0].value = false
                                        checkBoxSelectedValues[1].value = true
                                        checkBoxSelectedValues[2].value = false
                                        showBottomSheet = false
                                        if (rightDrawerState.isOpen && !showRightDrawer) {
                                            scope.launch {
                                                rightDrawerState.close()
                                                showRightDrawer = true
                                                rightDrawerState.open()
                                            }
                                        } else if (rightDrawerState.isClosed) {
                                            showRightDrawer = true
                                            scope.launch {
                                                delay(50)
                                                rightDrawerState.open()
                                            }
                                        }
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xff0f6cbd)
                                    )
                                )
                            },
                        )
                        DropdownMenuItem(
                            text = { Text("Slide from Bottom") },
                            onClick = {
                                checkBoxSelectedValues[0].value = false
                                checkBoxSelectedValues[1].value = false
                                checkBoxSelectedValues[2].value = true
                                if (rightDrawerState.isOpen) {
                                    scope.launch {
                                        rightDrawerState.close()
                                        showBottomSheet = true
                                    }
                                } else {
                                    showBottomSheet = true
                                }
                            },
                            trailingIcon = {
                                androidx.compose.material3.RadioButton(
                                    selected = checkBoxSelectedValues[2].value,
                                    onClick = {
                                        checkBoxSelectedValues[0].value = false
                                        checkBoxSelectedValues[1].value = false
                                        checkBoxSelectedValues[2].value = true
                                        if (rightDrawerState.isOpen) {
                                            scope.launch {
                                                rightDrawerState.close()
                                                showBottomSheet = true
                                            }
                                        } else {
                                            showBottomSheet = true
                                        }
                                    },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = Color(0xff0f6cbd)
                                    )
                                )
                            },
                        )
                    }
                }
            }
        }

    }
    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            modifier = Modifier.fillMaxSize(),
            containerColor = Color(0xFFFFFFFF),
        ) {
            Box(modifier = Modifier.fillMaxSize().background(Color.White)) {
                Column(
                    modifier = Modifier.fillMaxSize().background(Color.White)
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                    navDrawerEntry(
                        "Amanda Brady",
                        "Manager",
                        R.drawable.avatar_amanda_brady
                    )
                    navDrawerEntry(
                        "Cecil Folk",
                        "Manager",
                        R.drawable.avatar_cecil_folk
                    )
                    navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                    navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                    navDrawerEntry(
                        "Amanda Brady",
                        "Manager",
                        R.drawable.avatar_amanda_brady
                    )
                    navDrawerEntry(
                        "Cecil Folk",
                        "Manager",
                        R.drawable.avatar_cecil_folk
                    )
                    navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                    navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                    navDrawerEntry(
                        "Amanda Brady",
                        "Manager",
                        R.drawable.avatar_amanda_brady
                    )
                    navDrawerEntry(
                        "Cecil Folk",
                        "Manager",
                        R.drawable.avatar_cecil_folk
                    )
                    navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                    navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                    navDrawerEntry(
                        "Amanda Brady",
                        "Manager",
                        R.drawable.avatar_amanda_brady
                    )
                    navDrawerEntry(
                        "Cecil Folk",
                        "Manager",
                        R.drawable.avatar_cecil_folk
                    )
                    navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                    navDrawerEntry("Mona Kane", "Manager", R.drawable.avatar_mona_kane)
                    navDrawerEntry(
                        "Amanda Brady",
                        "Manager",
                        R.drawable.avatar_amanda_brady
                    )
                    navDrawerEntry(
                        "Cecil Folk",
                        "Manager",
                        R.drawable.avatar_cecil_folk
                    )
                    navDrawerEntry("Erik Nason", "Manager", R.drawable.avatar_erik_nason)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun showDrawer(){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        ModalNavigationDrawer(
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    ModalDrawerSheet {
                        Text("Drawer title", modifier = Modifier.padding(16.dp))
                        HorizontalDivider()
                        NavigationDrawerItem(
                            label = { Text(text = "Drawer Item") },
                            selected = false,
                            onClick = { /*TODO*/ }
                        )
                        // ...other drawer items
                    }
                }
            },
            drawerState = rememberDrawerState(DrawerValue.Open),
        ) {
        }
    }
    Text("Hello, world!")
//    ModalBottomSheet(content = {
//        Text("Modal Bottom Sheet")
//    }, onDismissRequest = { }, sheetState = rememberModalBottomSheetState(true))
}