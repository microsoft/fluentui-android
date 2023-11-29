package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs
import com.microsoft.fluentuidemo.V2DemoActivity

class V2LabelActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-20"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-20"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateLabelUI()
        }
    }

    @Composable
    private fun CreateLabelUI() {
        var colorStyle by rememberSaveable { mutableStateOf(ColorStyle.Primary) }
        var selectedTab by rememberSaveable { mutableStateOf(0) }
        var isBackgroundChange by rememberSaveable { mutableStateOf(false) }
        var tabsList: MutableList<PillMetaData> = mutableListOf()
        tabsList.add(
            PillMetaData(
                text = "Primary",
                onClick = {
                    colorStyle = ColorStyle.Primary
                    selectedTab = 0
                    isBackgroundChange = false
                }
            )
        )
        tabsList.add(
            PillMetaData(
                text = "Secondary",
                onClick = {
                    colorStyle = ColorStyle.Secondary
                    selectedTab = 1
                    isBackgroundChange = false
                }
            )
        )
        tabsList.add(
            PillMetaData(
                text = "White",
                onClick = {
                    colorStyle = ColorStyle.White
                    isBackgroundChange = true
                    selectedTab = 2
                }
            )
        )
        tabsList.add(
            PillMetaData(
                text = "Brand",
                onClick = {
                    colorStyle = ColorStyle.Brand
                    selectedTab = 3
                    isBackgroundChange = false
                }
            )
        )
        tabsList.add(
            PillMetaData(
                text = "Error",
                onClick = {
                    colorStyle = ColorStyle.Error
                    selectedTab = 4
                    isBackgroundChange = false
                }
            )
        )
        Column(
            modifier = Modifier
                .then(
                    if (isBackgroundChange) {
                        Modifier.background(Color.Black)
                    } else {
                        Modifier
                    }
                )
                .padding(top = 16.dp)
        ) {
            PillTabs(metadataList = tabsList, scrollable = true, selectedIndex = selectedTab)
            Column(
                modifier = Modifier
                    .padding(all = 16.dp)
                    .horizontalScroll(
                        rememberScrollState()
                    )
                    .verticalScroll(
                        rememberScrollState()
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Label(
                    text = "Display is Regular 60sp",
                    textStyle = TypographyTokens.Display,
                    colorStyle = colorStyle
                )
                Label(
                    text = "LargeTitle is Regular 34sp",
                    textStyle = TypographyTokens.LargeTitle,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Title1 is Bold 24sp",
                    textStyle = TypographyTokens.Title1,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Title2 is Medium 20sp",
                    textStyle = TypographyTokens.Title2,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Title3 is Medium is 18sp",
                    textStyle = TypographyTokens.Title3,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Body1Strong is SemiBold 16sp",
                    textStyle = TypographyTokens.Body1Strong,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Body1 is Regular 16sp",
                    textStyle = TypographyTokens.Body1,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Body2Strong is Medium 14sp",
                    textStyle = TypographyTokens.Body2Strong,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Body2 is Regular 14sp",
                    textStyle = TypographyTokens.Body2,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Caption1Strong is Medium 13sp",
                    textStyle = TypographyTokens.Caption1Strong,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Caption1 is Regular 13sp",
                    textStyle = TypographyTokens.Caption1,
                    colorStyle = colorStyle
                )
                Label(
                    text = "Caption2 is Regular 12sp",
                    textStyle = TypographyTokens.Caption2,
                    colorStyle = colorStyle
                )
            }
        }
    }
}