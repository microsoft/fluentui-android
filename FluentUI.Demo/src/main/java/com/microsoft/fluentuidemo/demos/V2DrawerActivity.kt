package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.persona.PersonaListView
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.createPersonaList
import kotlinx.coroutines.launch


class V2DrawerActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeView = findViewById<ComposeView>(R.id.compose_here)

        composeView.setContent {
            FluentTheme {
                CreateActivityUI()
            }
        }
    }
}

enum class ContentType {
    FULL_PAGE_SCROLLABLE_CONTENT,
    EXPANDABLE_SIZE_CONTENT,
    WRAPPED_SIZE_CONTENT
}

@Composable
private fun CreateActivityUI() {
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Bottom Drawer",
                    BehaviorType.BOTTOM,
                    getDrawerContent()
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Left Drawer",
                    BehaviorType.LEFT,
                    getDrawerContent()
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Right Drawer",
                    BehaviorType.RIGHT,
                    getDrawerContent()
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Top Drawer",
                    BehaviorType.TOP,
                    getDrawerContent()
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Fixed Drawer",
                    BehaviorType.BOTTOM,
                    getDrawerContent(),
                    expandable = false
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show No Fade Drawer",
                    BehaviorType.BOTTOM,
                    getDrawerContent(),
                    expandable = false,
                    enableScrim = false
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Content Wrapped Expanded Bottom Drawer",
                    BehaviorType.BOTTOM,
                    getDrawerContent(contentType = ContentType.EXPANDABLE_SIZE_CONTENT)
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Content Wrapped Bottom Drawer",
                    BehaviorType.BOTTOM,
                    getDrawerContent(contentType = ContentType.WRAPPED_SIZE_CONTENT)
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Content Wrapped Top Drawer",
                    BehaviorType.TOP,
                    getDrawerContent(contentType = ContentType.WRAPPED_SIZE_CONTENT)
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Bottom Outer Drawer",
                    BehaviorType.BOTTOM,
                    getDrawerInDrawerContent()
            )
        }
        item {
            CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
                    "Show Left Outer Drawer",
                    BehaviorType.LEFT,
                    getDrawerInDrawerContent()
            )
        }
    }
}

@Composable
private fun CreateDrawerWithButtonOnPrimarySurfaceToInvokeIt(
        primaryScreenButtonText: String,
        behaviorType: BehaviorType,
        drawerContent: @Composable ((() -> Unit) -> Unit),
        expandable: Boolean = true,
        enableScrim: Boolean = true
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState()
    val open: () -> Unit = {
        scope.launch { drawerState.open() }
    }
    val close: () -> Unit = {
        scope.launch { drawerState.close() }
    }
    PrimarySurfaceContent(
            open,
            text = primaryScreenButtonText
    )
    Drawer(
            drawerState = drawerState,
            drawerContent = { drawerContent(close) },
            behaviorType = behaviorType,
            expandable = expandable,
            scrimVisible = enableScrim
    )
}

@Composable
private fun PrimarySurfaceContent(
        onClick: () -> Unit,
        text: String,
        height: Dp = 20.dp,
) {
    Column(
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(height))
        com.microsoft.fluentui.tokenized.controls.Button(
                style = ButtonStyle.Button,
                size = ButtonSize.Medium,
                text = text,
                onClick = onClick
        )
    }
}

@Composable
private fun getDrawerContent(
        contentType: ContentType = ContentType.FULL_PAGE_SCROLLABLE_CONTENT
): @Composable ((close: () -> Unit) -> Unit) {
    return { _ ->
        lateinit var context: Context
        AndroidView(
                modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                factory = {
                    context = it
                    val view = it.activity!!.layoutInflater.inflate(
                            R.layout.demo_drawer_content,
                            null
                    )!!.rootView
                    val personaList = createPersonaList(context)
                    (view as PersonaListView).personas = when (contentType) {
                        ContentType.FULL_PAGE_SCROLLABLE_CONTENT -> personaList
                        ContentType.EXPANDABLE_SIZE_CONTENT -> personaList.take(7) as ArrayList<IPersona>
                        ContentType.WRAPPED_SIZE_CONTENT -> personaList.take(2) as ArrayList<IPersona>
                    }
                    view
                }
        ) {}
    }
}

@Composable
private fun getDrawerInDrawerContent(sideDrawer: Boolean = false): @Composable ((() -> Unit) -> Unit) {
    return { close ->
        Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = if (sideDrawer) Modifier.width(250.dp) else Modifier
        ) {
            com.microsoft.fluentui.tokenized.controls.Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    text = "Close Drawer",
                    onClick = close
            )

            val scopeB = rememberCoroutineScope()
            val drawerStateB = rememberDrawerState()

            //Button on Outer Drawer Surface
            PrimarySurfaceContent(
                    onClick = {
                        scopeB.launch {
                            drawerStateB.open()
                        }
                    },
                    text = "Show Inner Drawer"
            )
            Drawer(
                    drawerState = drawerStateB,
                    drawerContent = {
                        getDrawerContent()()
                        {
                            scopeB.launch {
                                drawerStateB.close()
                            }
                        }

                    },
                    expandable = true
            )
        }
    }
}