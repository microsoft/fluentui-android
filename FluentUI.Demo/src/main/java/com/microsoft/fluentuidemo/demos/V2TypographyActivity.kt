package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.AppThemeViewModel
import com.microsoft.fluentuidemo.AppThemeViewModel.currentTokens
import com.microsoft.fluentuidemo.AppThemeViewModel.heightList
import com.microsoft.fluentuidemo.AppThemeViewModel.selectedheightlist
import com.microsoft.fluentuidemo.AppThemeViewModel.selectedsizelist
import com.microsoft.fluentuidemo.AppThemeViewModel.selectedtrackinglist
import com.microsoft.fluentuidemo.AppThemeViewModel.selectedweightList
import com.microsoft.fluentuidemo.AppThemeViewModel.sizeList
import com.microsoft.fluentuidemo.AppThemeViewModel.trackingList
import com.microsoft.fluentuidemo.AppThemeViewModel.weightList
import com.microsoft.fluentuidemo.V2DemoActivity

enum class TypographyParameters {
    WEIGHT,
    SIZE,
    TRACKING,
    HEIG
}

class V2TypographyActivity : V2DemoActivity() {
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
            Column (
                Modifier.verticalScroll(rememberScrollState())
            ) {
                ListItem.Header(title = "Weight")
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    OptionRow(
                        text = "Fluent",
                        selected = selectedweightList.value == weightList[0],
                        onClick = {
                            selectedweightList.value = weightList[0]
                            useFluentTypography(TypographyParameters.WEIGHT)
                        }
                    )
                    OptionRow(
                        text = "Material",
                        selected = selectedweightList.value == weightList[1],
                        onClick = {
                            selectedweightList.value = weightList[1]
                            useMaterialTypography(TypographyParameters.WEIGHT)
                        }
                    )
                }
                ListItem.Header(title = "Size")
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    OptionRow(
                        text = "Fluent",
                        selected = selectedsizelist.value == sizeList[0],
                        onClick = { selectedsizelist.value = sizeList[0]
                            useFluentTypography(TypographyParameters.SIZE)
                        }
                    )
                    OptionRow(
                        text = "Material",
                        selected = selectedsizelist.value == sizeList[1],
                        onClick = { selectedsizelist.value = sizeList[1]
                            useMaterialTypography(TypographyParameters.SIZE)
                        }
                    )
                }
                ListItem.Header(title = "Tracking")
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    OptionRow(
                        text = "Fluent",
                        selected = selectedtrackinglist.value == trackingList[0],
                        onClick = { selectedtrackinglist.value = trackingList[0]
                            useFluentTypography(TypographyParameters.TRACKING)
                        }
                    )
                    OptionRow(
                        text = "Material",
                        selected = selectedtrackinglist.value == trackingList[1],
                        onClick = { selectedtrackinglist.value = trackingList[1]
                            useMaterialTypography(TypographyParameters.TRACKING)
                        }
                    )
                }
                ListItem.Header(title = "Line Height")
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    OptionRow(
                        text = "Fluent",
                        selected = selectedheightlist.value == heightList[0],
                        onClick = { selectedheightlist.value = heightList[0]
                            useFluentTypography(TypographyParameters.HEIGHT)
                        }
                    )
                    OptionRow(
                        text = "Material",
                        selected = selectedheightlist.value == heightList[1],
                        onClick = { selectedheightlist.value = heightList[1]
                            useMaterialTypography(TypographyParameters.HEIGHT)
                        }
                    )
                }

                ListItem.Header(title = "Test Text")
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Display],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.LargeTitle],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title3],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1Strong],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body1],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1Strong],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption1],
                    modifier = Modifier.padding(16.dp)
                )
                Text(
                    text = "Display Text\nDisplay Text",
                    style = FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Caption2],
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
    @Composable
    fun OptionRow(
        text: String,
        selected: Boolean,
        onClick: () -> Unit
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            BasicText(
                text = text,
                modifier = Modifier.weight(1F),
                style = TextStyle(
                    color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                        themeMode = ThemeMode.Auto
                    )
                )
            )
            RadioButton(
                selected = selected,
                onClick = onClick
            )
        }
    }

    private fun updateProperty(
        parameter: TypographyParameters,
        isMaterial: Boolean = false
    ){
        for (currentTextType in FluentAliasTokens.TypographyTokens.values())
        {
            val existingValues = currentTokens.typography.get(currentTextType)
            val newValues = TextStyle(
                fontSize = if(parameter != TypographyParameters.SIZE) {existingValues.fontSize} else { if( isMaterial) { AppThemeViewModel.materialTypographyTokens.typography.get(currentTextType).fontSize } else { AppThemeViewModel.fluentTypographyTokens.typography.get(currentTextType).fontSize } },
                lineHeight = if(parameter != TypographyParameters.HEIGHT) {existingValues.lineHeight} else { if( isMaterial) { AppThemeViewModel.materialTypographyTokens.typography.get(currentTextType).lineHeight } else { AppThemeViewModel.fluentTypographyTokens.typography.get(currentTextType).lineHeight } },
                fontWeight = if(parameter != TypographyParameters.WEIGHT) {existingValues.fontWeight} else { if( isMaterial) { AppThemeViewModel.materialTypographyTokens.typography.get(currentTextType).fontWeight } else { AppThemeViewModel.fluentTypographyTokens.typography.get(currentTextType).fontWeight } },
                letterSpacing = if(parameter != TypographyParameters.TRACKING) {existingValues.letterSpacing} else { if( isMaterial) { AppThemeViewModel.materialTypographyTokens.typography.get(currentTextType).letterSpacing } else { AppThemeViewModel.fluentTypographyTokens.typography.get(currentTextType).letterSpacing } },
            )
            currentTokens.typography.set(currentTextType, newValues)
        }
        FluentTheme.updateAliasTokens(currentTokens)
    }
    private fun useMaterialTypography(
        parameter: TypographyParameters
    ) {
        when (parameter) {
            TypographyParameters.WEIGHT ->
            {
                updateProperty(TypographyParameters.WEIGHT, true)
            }
            TypographyParameters.SIZE ->
            {
                updateProperty(TypographyParameters.SIZE, true)
            }
            TypographyParameters.TRACKING ->
            {
                updateProperty(TypographyParameters.TRACKING, true)
            }
            TypographyParameters.HEIGHT ->
            {
                updateProperty(TypographyParameters.HEIGHT, true)
            }
            else -> {

            }
        }
    }

    private fun useFluentTypography(
        parameter: TypographyParameters
    ) {
        when (parameter) {
            TypographyParameters.WEIGHT ->
            {
                updateProperty(TypographyParameters.WEIGHT, false)
            }
            TypographyParameters.SIZE ->
            {
                updateProperty(TypographyParameters.SIZE, false)
            }
            TypographyParameters.TRACKING ->
            {
                updateProperty(TypographyParameters.TRACKING, false)
            }
            TypographyParameters.HEIGHT ->
            {
                updateProperty(TypographyParameters.HEIGHT, false)
            }
            else -> {

            }
        }
    }
}