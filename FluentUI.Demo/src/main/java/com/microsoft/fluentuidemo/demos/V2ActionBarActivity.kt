package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.Demo
import com.microsoft.fluentuidemo.DemoActivity.Companion.DEMO_ID
import com.microsoft.fluentuidemo.Navigation
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.demos.actionbar.V2ActionBarDemoActivity

const val ACTION_BAR_TOP_RADIO = "actionBarTopRadio"
const val ACTION_BAR_BOTTOM_RADIO = "actionBarBottomRadio"
const val ACTION_BAR_BASIC_TYPE_RADIO = "actionBarBasicTypeRadio"
const val ACTION_BAR_ICON_TYPE_RADIO = "actionBarIconTypeRadio"
const val ACTION_BAR_CAROUSEL_TYPE_RADIO = "actionBarCarouselTypeRadio"

class V2ActionBarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-37"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-35"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setActivityContent {
            val actionBarPos = listOf(0, 1)
            val actionBarType = listOf(0, 1, 2)
            var selectedActionBarPos by rememberSaveable { mutableStateOf(actionBarPos[0]) }
            var selectedActionBarType by rememberSaveable { mutableStateOf(actionBarType[0]) }

            Column {
                ListItem.Header(title = resources.getString(R.string.actionbar_position_heading))
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    ActionBarRow(
                        text = R.string.actionbar_position_top_radio_label,
                        testTag = ACTION_BAR_TOP_RADIO,
                        selected = selectedActionBarPos == actionBarPos[0],
                        onClick = { selectedActionBarPos = actionBarPos[0] }
                    )
                    ActionBarRow(
                        text = R.string.actionbar_position_bottom_radio_label,
                        testTag = ACTION_BAR_BOTTOM_RADIO,
                        selected = selectedActionBarPos == actionBarPos[1],
                        onClick = { selectedActionBarPos = actionBarPos[1] }
                    )
                }
                ListItem.Header(title = resources.getString(R.string.actionbar_type_heading))
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    ActionBarRow(
                        text = R.string.actionbar_basic_radio_label,
                        testTag = ACTION_BAR_BASIC_TYPE_RADIO,
                        selected = selectedActionBarType == actionBarType[0],
                        onClick = { selectedActionBarType = actionBarType[0] }
                    )
                    ActionBarRow(
                        text = R.string.actionbar_icon_radio_label,
                        testTag = ACTION_BAR_ICON_TYPE_RADIO,
                        selected = selectedActionBarType == actionBarType[1],
                        onClick = { selectedActionBarType = actionBarType[1] }
                    )

                    ActionBarRow(
                        text = R.string.actionbar_carousel_radio_label,
                        testTag = ACTION_BAR_CAROUSEL_TYPE_RADIO,
                        selected = selectedActionBarType == actionBarType[2],
                        onClick = { selectedActionBarType = actionBarType[2] }
                    )
                }

                Button(
                    text = resources.getString(R.string.actionbar_start_button),
                    onClick = {
                        val demo = Demo("DEMOACTIONBAR", V2ActionBarDemoActivity::class)
                        val packageContext = this@V2ActionBarActivity
                        Navigation.forwardNavigation(
                            packageContext,
                            demo.demoClass.java,
                            Pair(DEMO_ID, demo.id),
                            Pair(DEMO_TITLE, demo.title),
                            Pair("ACTION_BAR_TYPE", selectedActionBarType),
                            Pair("ACTION_BAR_POSITION", selectedActionBarPos)
                        )
                    },
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

    @Composable
    fun ActionBarRow(
        text: Int,
        testTag: String,
        selected: Boolean,
        onClick: () -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicText(
                text = resources.getString(text),
                modifier = Modifier.weight(1F),
                style = TextStyle(
                    color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        themeMode = ThemeMode.Auto
                    )
                )
            )
            RadioButton(
                modifier = Modifier.testTag(testTag),
                selected = selected,
                onClick = onClick
            )
        }
    }
}