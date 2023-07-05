package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.foundation.text.appendInlineContent
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheet
import com.microsoft.fluentui.tokenized.bottomsheet.BottomSheetValue
import com.microsoft.fluentui.tokenized.bottomsheet.rememberBottomSheetState
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.Citation
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class V2CitationActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        v2ActivityComposeBinding.composeHere.setContent {
            FluentTheme {
                CreateCitationUI()
            }
        }
    }

    @Composable
    private fun CreateCitationUI() {
        val text1 =
            "Mona said that Summit Center project is set to start pre-construction planning and site preparation for the new arena in Atlanta April 2023. "
        val text2 =
            " Over the month you’ve had lots of emails and meetings with Mona. You’ve discussed project updates, timelines, and deadlines across 5 emails. "
        val text3 = " You agreed on a number of ways to promote the project."
        val textStyle =
            FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1]
        val textColor =
            FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        val textSpanStyle = SpanStyle(
            fontWeight = textStyle.fontWeight,
            fontSize = textStyle.fontSize,
            color = textColor
        )
        var hidden by remember { mutableStateOf(true) }
        var citation1Highlight by remember { mutableStateOf(false) }
        val bottomSheetState = rememberBottomSheetState(BottomSheetValue.Hidden)

        val scope = rememberCoroutineScope()
        val annotatedText = buildAnnotatedString {
            append(
                AnnotatedString(
                    text1, spanStyle = textSpanStyle.plus(
                        if (citation1Highlight) {
                            SpanStyle(background = Color.Yellow)
                        } else {
                            SpanStyle()
                        }
                    )
                )
            )
            appendInlineContent("citation1")
            append(AnnotatedString(text2, spanStyle = textSpanStyle))
            appendInlineContent("citations")
            append(AnnotatedString(text3, spanStyle = textSpanStyle))
            appendInlineContent("citation2")
        }
        val inlineContent = mapOf(
            Pair(
                "citation1",
                InlineTextContent(
                    Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) {
                    Citation(text = "1", onClick = {
                        hidden = false
                        citation1Highlight = true
                        scope.launch {
                            delay(3000)
                            citation1Highlight = false
                        }
                        scope.launch { bottomSheetState.show() }
                    })
                }),
            Pair(
                "citations",
                InlineTextContent(
                    Placeholder(
                        width = 96.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) {
                    Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                        Citation(text = "2", onClick = {
                            hidden = false
                            scope.launch { bottomSheetState.show() }
                        })
                        Citation(text = "3", onClick = {
                            hidden = false
                            scope.launch { bottomSheetState.show() }
                        })
                        Citation(text = "4", onClick = {
                            hidden = false
                            scope.launch { bottomSheetState.show() }
                        })
                        Citation(text = "5", onClick = {
                            hidden = false
                            scope.launch { bottomSheetState.show() }
                        })
                        Citation(text = "6", onClick = {
                            hidden = false
                            scope.launch { bottomSheetState.show() }
                        })
                    }
                }),
            Pair(
                "citation2",
                InlineTextContent(
                    Placeholder(
                        width = 16.sp,
                        height = 16.sp,
                        placeholderVerticalAlign = PlaceholderVerticalAlign.TextCenter
                    )
                ) {
                    Citation(text = "7", onClick = {
                        hidden = false
                        scope.launch { bottomSheetState.show() }
                    })
                })

        )
        BottomSheet(
            sheetContent = { bottomSheetOnCitation() },
            expandable = true,
            peekHeight = 250.dp,
            showHandle = true,
            sheetState = bottomSheetState,
            slideOver = true
        ) {
            Box(Modifier.padding(all = 24.dp)) {
                BasicText(text = annotatedText, inlineContent = inlineContent)
            }
        }
    }

    @Composable
    private fun bottomSheetOnCitation() {
        Column {
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "1") },
                text = "Reference",
                border = BorderType.Bottom
            )
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "2") },
                text = "Reference",
                border = BorderType.Bottom
            )
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "3") },
                text = "Reference",
                border = BorderType.Bottom
            )
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "4") },
                text = "Reference",
                border = BorderType.Bottom
            )
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "5") },
                text = "Reference",
                border = BorderType.Bottom
            )
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "6") },
                text = "Reference",
                border = BorderType.Bottom
            )
            ListItem.Item(
                leadingAccessoryContent = { Citation(text = "7") },
                text = "Reference",
                border = BorderType.Bottom
            )
        }
    }
}