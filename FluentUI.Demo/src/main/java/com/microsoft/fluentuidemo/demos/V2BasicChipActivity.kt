package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.BasicChipInfo
import com.microsoft.fluentui.theme.token.controlTokens.BasicChipTokens
import com.microsoft.fluentui.tokenized.controls.BasicChip
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentuidemo.V2DemoActivity

class V2BasicChipActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-13"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-13"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateBasicChipActivityUI()
        }
    }

    @Composable
    private fun CreateBasicChipActivityUI() {
        var selectedList = rememberSaveable(
            saver = listSaver(
                save = { stateList ->
                    if (stateList.isNotEmpty()) {
                        val first = stateList.first()
                        if (!canBeSaved(first)) {
                            throw IllegalStateException("${first::class} cannot be saved. By default only types which can be stored in the Bundle class can be saved.")
                        }
                    }
                    stateList.toList()
                },
                restore = { it.toMutableStateList() }
            )) {
            mutableStateListOf(
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false,
                false
            )
        }

        class CustomChipToken1 : BasicChipTokens() {
            @Composable
            override fun backgroundBrush(basicChipInfo: BasicChipInfo): StateBrush {
                return StateBrush(
                    rest = SolidColor(
                        FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            themeMode = FluentTheme.themeMode
                        )
                    ),
                    selected = SolidColor(
                        FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                            themeMode = FluentTheme.themeMode
                        )
                    )
                )
            }

            @Composable
            override fun textColor(basicChipInfo: BasicChipInfo): StateColor {
                return StateColor(
                    rest = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    selected = FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1Selected].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            }
        }

        class CustomChipToken2 : BasicChipTokens() {
            @Composable
            override fun backgroundBrush(basicChipInfo: BasicChipInfo): StateBrush {
                return StateBrush(
                    rest = SolidColor(
                        FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(
                            themeMode = FluentTheme.themeMode
                        )
                    ),
                    selected = SolidColor(
                        FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value(
                            themeMode = FluentTheme.themeMode
                        )
                    ),
                    disabled = SolidColor(
                        FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                            themeMode = FluentTheme.themeMode
                        )
                    )
                )
            }

            @Composable
            override fun textColor(basicChipInfo: BasicChipInfo): StateColor {
                return StateColor(
                    rest = FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    selected = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                        themeMode = FluentTheme.themeMode
                    ),
                    disabled = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundDisable1].value(
                        themeMode = FluentTheme.themeMode
                    )
                )
            }
        }
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Label(text = "Basic Chip", textStyle = FluentAliasTokens.TypographyTokens.Body1Strong)
            Row(horizontalArrangement = spacedBy(8.dp)) {
                BasicChip(
                    label = "Word",
                    selected = selectedList[0],
                    onClick = { selectedList[0] = !selectedList[0] })
                BasicChip(
                    label = "Excel",
                    selected = selectedList[1],
                    onClick = { selectedList[1] = !selectedList[1] })
                BasicChip(
                    label = "PowerPoint",
                    selected = selectedList[2],
                    onClick = { selectedList[2] = !selectedList[2] })
                BasicChip(
                    label = "PDF",
                    selected = selectedList[3],
                    onClick = { selectedList[3] = !selectedList[3] })
            }
            Label(text = "Chip States", textStyle = FluentAliasTokens.TypographyTokens.Body1Strong)
            Row(horizontalArrangement = spacedBy(8.dp)) {
                BasicChip(
                    label = "Enabled", onClick = {})
                BasicChip(
                    label = "Selected",
                    selected = true, onClick = {})
                BasicChip(
                    label = "Disabled",
                    enabled = false
                )
                BasicChip(
                    label = "Static"
                )
            }
            Label(
                text = "Time (Custom Color)",
                textStyle = FluentAliasTokens.TypographyTokens.Body1Strong
            )
            Row(horizontalArrangement = spacedBy(8.dp)) {
                BasicChip(
                    basicChipTokens = CustomChipToken2(),
                    label = "Today",
                    selected = selectedList[4],
                    onClick = { selectedList[4] = !selectedList[4] })
                BasicChip(
                    basicChipTokens = CustomChipToken2(),
                    label = "Yesterday",
                    selected = selectedList[5],
                    onClick = { selectedList[5] = !selectedList[5] })
                BasicChip(
                    basicChipTokens = CustomChipToken2(),
                    label = "Last Week",
                    selected = selectedList[6],
                    onClick = { selectedList[6] = !selectedList[6] })
            }
            Label(
                text = "Custom UI Chips",
                textStyle = FluentAliasTokens.TypographyTokens.Body1Strong
            )
            Row(horizontalArrangement = spacedBy(8.dp)) {
                BasicChip(
                    label = "Label",
                    leadingAccessory = { Icon(FluentIcon(Icons.Outlined.ShoppingCart)) },
                    selected = selectedList[7],
                    onClick = { selectedList[7] = !selectedList[7] })
                BasicChip(
                    label = "Label",
                    trailingAccessory = { Icon(FluentIcon(Icons.Outlined.Add)) },
                    selected = selectedList[8],
                    onClick = { selectedList[8] = !selectedList[8] })
            }
            Label(
                text = "Custom Color Chips",
                textStyle = FluentAliasTokens.TypographyTokens.Body1Strong
            )
            Row(horizontalArrangement = spacedBy(8.dp)) {
                BasicChip(
                    label = "Label",
                    selected = selectedList[9],
                    onClick = { selectedList[9] = !selectedList[9] },
                    basicChipTokens = CustomChipToken1()
                )
            }
        }
    }
}