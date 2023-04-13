package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FloatingActionButton
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.navigation.TabBar
import com.microsoft.fluentui.tokenized.navigation.TabData
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.notification.NotificationResult
import com.microsoft.fluentui.tokenized.notification.Snackbar
import com.microsoft.fluentui.tokenized.notification.SnackbarState
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import com.microsoft.fluentuidemo.icons.ListItemIcons
import com.microsoft.fluentuidemo.icons.listitemicons.Folder40
import com.microsoft.fluentuidemo.util.invokeToast
import kotlinx.coroutines.launch

class V2ScaffoldActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    //Tag for Test
    private val TOP_BAR = "TopBar"
    private val BOTTOM_BAR = "BottomBar"
    private val SNACKBAR = "SnackBar"
    private val DRAWER = "Drawer"
    private val FLOATING_ACTION_BUTTON = "FAB"
    private val MAIN_CONTENT = "Main Content"
    private val DRAWER_BUTTON = "Drawer Button"
    private val SNACKBAR_BUTTON = "Snackbar Button"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context = this
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(LayoutInflater.from(container.context), container,true)
        v2ActivityComposeBinding.composeHere.setContent {
            var selectedIndex by rememberSaveable { mutableStateOf(0) }
            var showHomeBadge by rememberSaveable { mutableStateOf(true) }
            val tabDataList = arrayListOf(
                TabData(
                    title = resources.getString(R.string.tabBar_home),
                    icon = Icons.Outlined.Home,
                    selectedIcon = Icons.Filled.Home,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_home), context)
                        selectedIndex = 0
                        showHomeBadge = false
                    },
                    badge = { if (selectedIndex == 0 && showHomeBadge) Badge() }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_mail),
                    icon = Icons.Outlined.Email,
                    selectedIcon = Icons.Filled.Email,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_mail), context)
                        selectedIndex = 1
                    },
                    badge = { Badge("123+", badgeType = BadgeType.Character) }
                ),
                TabData(
                    title = resources.getString(R.string.tabBar_settings),
                    icon = Icons.Outlined.Settings,
                    selectedIcon = Icons.Filled.Settings,
                    onClick = {
                        invokeToast(resources.getString(R.string.tabBar_settings), context)
                        selectedIndex = 2
                    }
                )
            )
            var fabState by rememberSaveable { mutableStateOf(FABState.Expanded) }
            val snackbarState by remember { mutableStateOf(SnackbarState()) }

            FluentTheme {
                Scaffold(
                    topBar = {
                        AppBar(
                            title = resources.getString(R.string.scaffold),
                            appBarSize = AppBarSize.Large,
                            modifier = Modifier.testTag(TOP_BAR)
                        )
                    },
                    bottomBar = {
                        TabBar(
                            tabDataList = tabDataList,
                            selectedIndex = selectedIndex,
                            modifier = Modifier.testTag(BOTTOM_BAR)
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            text = resources.getString(R.string.scaffold_fab),
                            icon = Icons.Filled.Create,
                            state = fabState,
                            onClick = {
                                val toastText: String
                                if (fabState == FABState.Expanded) {
                                    toastText = resources.getString(R.string.scaffold_fab_collapsed)
                                    fabState = FABState.Collapsed
                                } else {
                                    toastText = resources.getString(R.string.scaffold_fab_collapsed)
                                    fabState = FABState.Expanded
                                }
                                invokeToast(
                                    toastText,
                                    this
                                )
                            },
                            modifier = Modifier.testTag(FLOATING_ACTION_BUTTON)
                        )
                    },
                    snackbar = {
                        Snackbar(
                            snackbarState = snackbarState,
                            modifier = Modifier.testTag(SNACKBAR)
                        )
                    }
                ) {
                    Box(
                        Modifier
                            .padding(it)
                            .testTag(MAIN_CONTENT)
                    ) {
                        GetContent(context, snackbarState)
                    }
                }
            }
        }
    }

    @Composable
    private fun GetContent(context: Context, snackbarState: SnackbarState? = null) {
        val size = remember { mutableStateOf(5) }
        val drawerState = rememberDrawerState()
        val scope = rememberCoroutineScope()
        Column {
            Drawer(
                modifier = Modifier.testTag(DRAWER),
                drawerState = drawerState,
                drawerContent = { CreateList(size = 20, context = context) }
            )
            Row(
                modifier = Modifier.padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = context.resources.getString(R.string.scaffold_refresh_list),
                    onClick = { size.value = (40 * Math.random()).toInt() })

                Button(
                    modifier = Modifier.testTag(DRAWER_BUTTON),
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = context.resources.getString(R.string.scaffold_open_drawer),
                    onClick = { scope.launch { drawerState.open() } })

                if (snackbarState != null) {
                    val snackBarTitle =
                        LocalContext.current.resources.getString(R.string.fluentui_title)
                    val actionButtonString =
                        LocalContext.current.resources.getString(R.string.fluentui_action_button)
                    val dismissedString =
                        LocalContext.current.resources.getString(R.string.fluentui_dismissed)
                    val pressedString =
                        LocalContext.current.resources.getString(R.string.fluentui_button_pressed)
                    val timeoutString =
                        LocalContext.current.resources.getString(R.string.fluentui_timeout)
                    var displayString: String = ""
                    Button(
                        modifier = Modifier.testTag(SNACKBAR_BUTTON),
                        style = ButtonStyle.OutlinedButton,
                        size = ButtonSize.Medium,
                        text = context.resources.getString(R.string.fluentui_show_snackbar),
                        onClick = {
                            scope.launch {
                                val result: NotificationResult = snackbarState.showSnackbar(
                                    message = snackBarTitle,
                                    style = SnackbarStyle.Contrast,
                                    icon = FluentIcon(Icons.Outlined.ShoppingCart),
                                    actionText = actionButtonString
                                )

                                when (result) {
                                    NotificationResult.TIMEOUT ->
                                        displayString = timeoutString

                                    NotificationResult.CLICKED ->
                                        displayString = pressedString

                                    NotificationResult.DISMISSED ->
                                        displayString = dismissedString
                                }
                                invokeToast(displayString, context)
                            }
                        }
                    )
                }
            }
            CreateList(size.value, context)
        }
    }

    @Composable
    fun CreateList(size: Int, context: Context) {
        LazyColumn()
        {
            repeat(size) {
                item {
                    ListItem.Item(text = context.resources.getString(R.string.common_list, it),
                        onClick = {
                            invokeToast(
                                context.resources.getString(R.string.common_list, it),
                                context
                            )
                        },
                        leadingAccessoryView = {
                            Image(
                                ListItemIcons.Folder40,
                                context.resources.getString(R.string.common_folder)
                            )
                        })
                }
            }
        }
    }

}

