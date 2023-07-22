package com.microsoft.fluentuidemo.util

import android.app.Activity
import android.view.LayoutInflater
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.persona.IPersona
import com.microsoft.fluentui.persona.PersonaListView
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.drawer.Drawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentui.util.activity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.demos.ContentType
import kotlinx.coroutines.launch

@Composable
fun PrimarySurfaceContent(
    onClick: () -> Unit,
    text: String,
    height: Dp = 20.dp,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(height))
        Button(
            style = ButtonStyle.Button,
            size = ButtonSize.Medium,
            text = text,
            onClick = onClick
        )
    }
}

@Composable
fun getDynamicListGeneratorAsContent(): @Composable ((close: () -> Unit) -> Unit) {
    return { _ ->
        val no = remember { mutableStateOf(0) }
        LazyColumn(
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
                    Label(
                        text = "Item $it",
                        textStyle = FluentAliasTokens.TypographyTokens.Caption1
                    )
                }
            }
        }
    }
}

@Composable
fun getAndroidViewAsContent(
    contentType: ContentType = ContentType.FULL_SCREEN_SCROLLABLE_CONTENT
): @Composable ((close: () -> Unit) -> Unit) {
    return { _ ->
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            factory = {
                val view = LayoutInflater.from(it as Activity).inflate(
                    R.layout.demo_drawer_content,
                    null
                ).rootView
                val personaList = createPersonaList(it)
                (view as PersonaListView).personas = when (contentType) {
                    ContentType.FULL_SCREEN_SCROLLABLE_CONTENT -> personaList
                    ContentType.EXPANDABLE_SIZE_CONTENT -> personaList.take(9) as ArrayList<IPersona>
                    ContentType.WRAPPED_SIZE_CONTENT -> personaList.take(2) as ArrayList<IPersona>
                }
                view
            }
        ) {}
    }
}

@Composable
fun getDrawerAsContent(): @Composable ((close: () -> Unit) -> Unit) {
    return { close ->
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 8.dp)
        ) {
            Button(
                style = ButtonStyle.Button,
                size = ButtonSize.Medium,
                text = stringResource(id = R.string.drawer_close),
                onClick = close
            )

            val scopeB = rememberCoroutineScope()
            val drawerStateB = rememberDrawerState()

            var selectedBehaviorType by remember { mutableStateOf(BehaviorType.BOTTOM_SLIDE_OVER) }
            Label(
                text = stringResource(id = R.string.drawer_select_drawer_type),
                textStyle = FluentAliasTokens.TypographyTokens.Title1
            )
            val topText = stringResource(id = R.string.drawer_top)
            val bottomText = stringResource(id = R.string.drawer_bottom)
            val leftSlideOverText = stringResource(id = R.string.drawer_left_slide_over)
            val rightSlideOverText = stringResource(id = R.string.drawer_right_slide_over)
            val bottomSlideOverText = stringResource(id = R.string.drawer_bottom_slide_over)
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.TOP }
                .clearAndSetSemantics { contentDescription = topText }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.TOP
                    },
                    selected = selectedBehaviorType == BehaviorType.TOP
                )
                Label(text = topText, textStyle = FluentAliasTokens.TypographyTokens.Caption1)
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.BOTTOM }
                .clearAndSetSemantics { contentDescription = bottomText }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.BOTTOM
                    },
                    selected = selectedBehaviorType == BehaviorType.BOTTOM
                )
                Label(text = bottomText, textStyle = FluentAliasTokens.TypographyTokens.Caption1)
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER }
                .clearAndSetSemantics { contentDescription = leftSlideOverText }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.LEFT_SLIDE_OVER
                    },
                    selected = selectedBehaviorType == BehaviorType.LEFT_SLIDE_OVER
                )
                Label(
                    text = leftSlideOverText,
                    textStyle = FluentAliasTokens.TypographyTokens.Caption1
                )
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER }
                .clearAndSetSemantics { contentDescription = rightSlideOverText }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.RIGHT_SLIDE_OVER
                    },
                    selected = selectedBehaviorType == BehaviorType.RIGHT_SLIDE_OVER
                )
                Label(
                    text = rightSlideOverText,
                    textStyle = FluentAliasTokens.TypographyTokens.Caption1
                )
            }
            Row(modifier = Modifier
                .clickable { selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER }
                .clearAndSetSemantics { contentDescription = bottomSlideOverText }
            ) {
                RadioButton(
                    onClick = {
                        selectedBehaviorType = BehaviorType.BOTTOM_SLIDE_OVER
                    },
                    selected = selectedBehaviorType == BehaviorType.BOTTOM_SLIDE_OVER
                )
                Label(
                    text = bottomSlideOverText,
                    textStyle = FluentAliasTokens.TypographyTokens.Caption1
                )
            }

            //Button on Outer Drawer Surface
            PrimarySurfaceContent(
                onClick = {
                    scopeB.launch {
                        drawerStateB.open()
                    }
                },
                text = stringResource(id = R.string.drawer_open)
            )
            Drawer(
                drawerState = drawerStateB,
                behaviorType = selectedBehaviorType,
                drawerContent = {
                    getAndroidViewAsContent()()
                    {
                        scopeB.launch {
                            drawerStateB.close()
                        }
                    }

                }
            )
        }
    }
}