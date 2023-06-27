package com.microsoft.fluentuidemo

import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs

enum class Tokens {
    GlobalTokens,
    AliasTokens
}

class V2DesignTokens : V2DemoActivity() {
    private var buttonBarList = mutableListOf<PillMetaData>()
    private lateinit var selectedTokens: MutableState<Tokens>
    private lateinit var tokensList: MutableState< Map<String, Array<out Enum<*>>>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setButtonBar {
            val globalTokensList = mapOf (
                "Neutral Color Tokens" to FluentGlobalTokens.NeutralColorTokens.values(),
                "Font Size Tokens" to FluentGlobalTokens.FontSizeTokens.values(),
                "Line Height Tokens" to FluentGlobalTokens.LineHeightTokens.values(),
                "Font Weight Tokens" to FluentGlobalTokens.FontWeightTokens.values(),
                "Icon Size Tokens" to FluentGlobalTokens.IconSizeTokens.values(),
                "Size Tokens" to FluentGlobalTokens.SizeTokens.values(),
                "ShadowTokens" to FluentGlobalTokens.ShadowTokens.values(),
                "CornerRadiusTokens" to FluentGlobalTokens.CornerRadiusTokens.values(),
                "StrokeWidthTokens" to FluentGlobalTokens.StrokeWidthTokens.values(),
                "SharedColorSets" to FluentGlobalTokens.SharedColorSets.values(),
                "SharedColorsTokens" to FluentGlobalTokens.SharedColorsTokens.values(),
            )

            val aliasTokensList = mapOf (
                "Brand Color Tokens" to FluentAliasTokens.BrandColorTokens.values(),
                "Neutral Background Color Tokens" to FluentAliasTokens.NeutralBackgroundColorTokens.values(),
                "NeutralForegroundColorTokens" to FluentAliasTokens.NeutralForegroundColorTokens.values(),
                "NeutralStrokeColorTokens" to FluentAliasTokens.NeutralStrokeColorTokens.values(),
                "BrandBackgroundColorTokens" to FluentAliasTokens.BrandBackgroundColorTokens.values(),
                "BrandForegroundColorTokens" to FluentAliasTokens.BrandForegroundColorTokens.values(),
                "BrandStrokeColorTokens" to FluentAliasTokens.BrandStrokeColorTokens.values(),
                "ErrorAndStatusColorTokens" to FluentAliasTokens.ErrorAndStatusColorTokens.values(),
                "PresenceColorTokens" to FluentAliasTokens.PresenceColorTokens.values(),
                "TypographyTokens" to FluentAliasTokens.TypographyTokens.values(),
            )

            selectedTokens = remember { mutableStateOf(Tokens.GlobalTokens) }
            tokensList = remember { mutableStateOf(globalTokensList.toMutableMap()) }
            buttonBarList = listOf (
                PillMetaData(
                    text = "Global Tokens",
                    enabled = true,
                    onClick = {
                        selectedTokens.value = Tokens.GlobalTokens
                        tokensList.value = globalTokensList.toMutableMap()
                    }
                ),
                PillMetaData(
                    text = "Alias Tokens",
                    enabled = true,
                    onClick = {
                        selectedTokens.value = Tokens.AliasTokens
                        tokensList.value = aliasTokensList.toMutableMap()
                    }
                )
            ) as MutableList<PillMetaData>

            PillTabs(
                style = appThemeStyle.value,
                metadataList = buttonBarList,
                selectedIndex = selectedTokens.value.ordinal,
            )
        }

        setActivityContent {
            LazyColumn(
                Modifier.fillMaxSize()
            ) {
                tokensList.value.forEach { (tokenName, tokenValue) ->
                    item{
                        ListItem.SectionHeader(
                            title = tokenName,
                            enableContentOpenCloseTransition = true,
                            chevronOrientation = ChevronOrientation(90f, 0f)
                        ) {
                            Column {
                                tokenValue.forEach {
                                    ListItem.Item(text = "$it")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

