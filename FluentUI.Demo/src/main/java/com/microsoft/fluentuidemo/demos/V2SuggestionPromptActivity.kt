package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.SuggestionPrompt
import com.microsoft.fluentui.tokenized.controls.Suggestions
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.getDemoAppString

class V2SuggestionPromptActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Tags used for testing
        val MODIFIABLE_PARAMETER_SECTION = "Modifiable Parameters"
        val ACTION_ICON_PARAM = "Action Icon Param"

        val context = this
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )

        v2ActivityComposeBinding.composeHere.setContent {
            var singleLine by remember { mutableStateOf(false) }
            var actionIcon by remember { mutableStateOf(true) }
            val onClickLambda: ((String) -> Unit) = { it ->
                Toast.makeText(
                    context,
                    it,
                    Toast.LENGTH_SHORT
                ).show()
            }

            FluentTheme {
                Column {
                    ListItem.SectionHeader(
                        title = getDemoAppString(DemoAppStrings.ModifiableParameters),
                        enableChevron = true,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f),
                        modifier = Modifier.testTag(MODIFIABLE_PARAMETER_SECTION)
                    ) {
                        LazyColumn(Modifier.fillMaxHeight(0.5F)) {
                            item {
                                ListItem.Item(
                                    text = "Single Line",
                                    subText = if (singleLine)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                singleLine = it
                                            },
                                            checkedState = singleLine
                                        )
                                    }
                                )
                            }

                            item {
                                ListItem.Item(
                                    text = "Action Icon",
                                    subText = if (actionIcon)
                                        LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                    else
                                        LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                    trailingAccessoryView = {
                                        ToggleSwitch(
                                            onValueChange = {
                                                actionIcon = it
                                            },
                                            checkedState = actionIcon,
                                            modifier = Modifier.testTag(ACTION_ICON_PARAM)
                                        )
                                    }
                                )
                            }
                        }
                    }
                    SuggestionPrompt(
                        suggestions = mutableListOf(
                            Suggestions(
                                "Suggestion 1",
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                onClick = { onClickLambda("Suggestion Clicked") }
                            ),
                            Suggestions(
                                "Suggestion 2. This is a very very long suggestion. Should be wrapped properly.",
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                onClick = { onClickLambda("Suggestion Clicked") }
                            ),
                            Suggestions(
                                "Suggestion 3. Small Suggestion",
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                onClick = { onClickLambda("Suggestion Clicked") }
                            ),
                            Suggestions(
                                "Suggestion 4",
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                onClick = { onClickLambda("Suggestion Clicked") }
                            ),
                            Suggestions(
                                "Suggestion 5",
                                maxLines = if (singleLine) 1 else Int.MAX_VALUE,
                                onClick = { onClickLambda("Suggestion Clicked") }
                            ),
                        ),
                        actionIcon = if (actionIcon) {
                            FluentIcon(
                                Icons.Filled.Refresh,
                                contentDescription = "Refresh Suggestions",
                                onClick = { onClickLambda("Reloading") }
                            )
                        } else null
                    )
                }
            }
        }
    }
}