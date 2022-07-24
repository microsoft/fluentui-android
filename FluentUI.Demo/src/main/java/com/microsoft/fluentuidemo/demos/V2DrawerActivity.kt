package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.drawer.compose.BehaviorType
import com.microsoft.fluentui.drawer.compose.Drawer
import com.microsoft.fluentui.drawer.compose.rememberDrawerState
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.launch


class V2DrawerActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                CreateActivityUI()
            }
        }
    }
}

@Composable
private fun CreateActivityUI() {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
                text = "Text on primary surface",
                fontSize = 40.sp,
                color = Color.Green
        )
        LazyColumn {
            item { DrawerInDrawer() }
            item { BottomDrawer() }
            item { LeftDrawer() }
            item { RightDrawer() }
            item { TopDrawer() }
            item {
                Text(
                        text = "Text on primary surface",
                        fontSize = 40.sp,
                        color = Color.Green
                )
            }
        }
    }
}

@Composable
fun TopDrawer() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }
    ScreenUI(
            open,
            text = "Click to open Top drawer",
            height = 20.dp
    )
    Drawer(
            drawerState = drawerState,
            drawerContent = getDrawerContent(
                    close = close
            ),
            behaviorType = BehaviorType.TOP,
            expandable = true
    )
}

@Composable
fun BottomDrawer() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }

    ScreenUI(
            onClick = open,
            text = "Click to open Bottom drawer",
            height = 20.dp
    )

    Drawer(
            drawerState = drawerState,
            drawerContent = getDrawerContent(
                    close = close
            ),
            behaviorType = BehaviorType.BOTTOM,
            expandable = true
    )
}

@Composable
fun LeftDrawer() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }

    ScreenUI(
            onClick = open,
            text = "Click to open Left Drawer",
            height = 20.dp
    )
    Drawer(
            drawerState = drawerState,
            drawerContent = getDrawerContent(
                    close = close
            ),
            behaviorType = BehaviorType.LEFT,
    )
}

@Composable
fun RightDrawer() {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }

    ScreenUI(
            onClick = open,
            text = "Click to open Right Drawer",
            height = 20.dp
    )
    Drawer(
            drawerState = drawerState,
            drawerContent = getDrawerContent(
                    close = close
            ),
            behaviorType = BehaviorType.RIGHT,
    )
}

@Composable
private fun DrawerInDrawer() {
    val scopeB = rememberCoroutineScope()
    val drawerStateB = rememberDrawerState()
    ScreenUI(
            onClick = {
                scopeB.launch {
                    drawerStateB.open()
                }
            },
            text = "Click to open drawer",
            height = 20.dp
    )
    Drawer(
            drawerState = drawerStateB,
            drawerContent = getDrawerInDrawerContent(
                    close = {
                        scopeB.launch {
                            drawerStateB.close()
                        }
                    }
            ),
            expandable = false
    )
}

@Composable
fun ScreenUI(
        onClick: () -> Unit,
        text: String,
        height: Dp,
) {
    Column(
            modifier = Modifier
                    .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(height))
        Button(
                onClick = onClick
        ) {
            Text(text = text)
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun getDrawerContent(
        close: () -> Unit
): @Composable (() -> Unit) {
    return {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                    modifier = Modifier
                            .padding(top = 16.dp)
                            .shadow(
                                    50.dp,
                                    shape = RoundedCornerShape(10),
                                    ambientColor = Color.Red,
                                    spotColor = Color.Green
                            ),

                    onClick = { close() },
                    content = { Text("Close Drawer") }
            )

            ListItem(
                    text = { Text("Single Item") },
                    icon = {
                        Icon(
                                Icons.Default.Favorite,
                                contentDescription = "Localized description",
                                tint = Color.Green
                        )
                    }
            )

            LazyColumn(Modifier.fillMaxHeight()) {
                items(25) {
                    ListItem(
                            text = { Text("Item $it") },
                            icon = {
                                Icon(
                                        Icons.Default.Favorite,
                                        contentDescription = "Localized description",
                                        tint = Color.Green
                                )
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun getDrawerInDrawerContent(
        close: () -> Unit
): @Composable (() -> Unit) {
    return {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(
                    modifier = Modifier
                            .padding(top = 16.dp)
                            .shadow(
                                    50.dp,
                                    shape = RoundedCornerShape(10),
                                    ambientColor = Color.Red,
                                    spotColor = Color.Green
                            ),

                    onClick = { close() },
                    content = { Text("Close Drawer") }
            )

            val scopeB = rememberCoroutineScope()
            val drawerStateB = rememberDrawerState()

            ScreenUI(
                    onClick = {
                        scopeB.launch {
                            drawerStateB.open()
                        }
                    },
                    text = "Click to open bottom drawer",
                    height = 20.dp
            )
            Drawer(
                    drawerState = drawerStateB,
                    drawerContent = getDrawerContent(
                            close = {
                                scopeB.launch {
                                    drawerStateB.close()
                                }
                            }
                    ),
                    expandable = true
            )
        }
    }
}
