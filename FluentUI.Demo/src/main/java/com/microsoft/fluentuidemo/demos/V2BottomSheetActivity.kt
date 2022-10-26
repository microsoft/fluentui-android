package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.persona.PersonaListView
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheet
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetValue
import com.microsoft.fluentui.tokenized.bottomsheet.rememberBottomSheetState
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.util.createPersonaList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2BottomSheetActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeHere = findViewById<ComposeView>(R.id.compose_here)

        composeHere.setContent {
            FluentTheme {
                CreateActivityUI()
            }
        }
    }
}

@Composable
private fun CreateActivityUI() {
    var showHandleState by remember { mutableStateOf(true) }

    var expandableState by remember { mutableStateOf(true) }

    var peekHeightState by remember { mutableStateOf(110.dp) }

    var toggleBottomSheetContent by remember { mutableStateOf(true) }

    var hidden by remember { mutableStateOf(true) }

    val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Shown)

    val scope = rememberCoroutineScope()

    hidden = !bottomSheetState.isVisible

    var sheetContentState by remember { mutableStateOf(content1) }
    BottomSheet(
        sheetContent = sheetContentState,
        expandable = expandableState,
        peekHeight = peekHeightState,
        showHandle = showHandleState,
        sheetState = bottomSheetState
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
                com.microsoft.fluentui.tokenized.controls.Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = "Show",
                    enabled = hidden,
                    onClick = {
                        hidden = false
                        scope.launch { bottomSheetState.show() }
                    }
                )

                com.microsoft.fluentui.tokenized.controls.Button(
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
                com.microsoft.fluentui.tokenized.controls.Button(
                    style = ButtonStyle.OutlinedButton,
                    size = ButtonSize.Medium,
                    text = "Hide",
                    enabled = !hidden,
                    onClick = {
                        hidden = true
                        scope.launch { bottomSheetState.hide() }
                    }
                )

                com.microsoft.fluentui.tokenized.controls.Button(
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
                Text(
                    text = "Expandable",
                    modifier = Modifier.weight(1F),
                    color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        themeMode = ThemeMode.Auto
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
                Text(
                    text = "Show Handle",
                    modifier = Modifier.weight(1F),
                    color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        themeMode = ThemeMode.Auto
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
                Text(
                    text = "Peek Height is restricted to half of screen size.",
                    modifier = Modifier.weight(1F)
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Peek Height $peekHeightState",
                    modifier = Modifier.weight(1F),
                    color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                        themeMode = ThemeMode.Auto
                    )
                )
                com.microsoft.fluentui.tokenized.controls.Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    text = "+ 8 dp",
                    enabled = !hidden,
                    onClick = { peekHeightState += 8.dp })

                com.microsoft.fluentui.tokenized.controls.Button(
                    style = ButtonStyle.Button,
                    size = ButtonSize.Medium,
                    text = "- 8 dp",
                    enabled = !hidden && (peekHeightState > 0.dp),
                    onClick = { peekHeightState -= 8.dp })
            }

            com.microsoft.fluentui.tokenized.controls.Button(
                style = ButtonStyle.OutlinedButton,
                size = ButtonSize.Medium,
                text = "Toggle BottomSheet Content",
                onClick = {
                    toggleBottomSheetContent = !toggleBottomSheetContent
                    sheetContentState = if (toggleBottomSheetContent) {
                        content1
                    } else {
                        content2
                    }
                }
            )

            com.microsoft.fluentui.tokenized.controls.Button(
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
                Text(text = stringResource(R.string.large_scrollable_text))
            }
        }
    }
}

val content1: @Composable () -> Unit = {
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
            (view as PersonaListView).personas = personaList
            view
        }
    ) {}
}

val content2: @Composable () -> Unit = {
    val no = remember { mutableStateOf(0) }
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            com.microsoft.fluentui.tokenized.controls.Button(
                style = ButtonStyle.Button,
                size = ButtonSize.Medium,
                text = "Click to create random size list",
                onClick = { no.value = (20 * Math.random()).toInt() })
        }

        repeat(no.value) {
            item {
                Spacer(Modifier.height(10.dp))
                Text("list item $it")
            }
        }
    }
}

