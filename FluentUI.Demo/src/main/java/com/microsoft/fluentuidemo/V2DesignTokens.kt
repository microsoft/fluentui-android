package com.microsoft.fluentuidemo

import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.cornerRadius
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.elevation
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.fontSize
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.fontWeight
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.iconSize
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.lineHeight
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.neutralColor
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.size
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.strokeWidth
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.TokenSet
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardInfo
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardTokens
import com.microsoft.fluentui.theme.token.controlTokens.CardType
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillTabs

enum class Tokens {
    GlobalTokens,
    AliasTokens
}

class V2DesignTokens : V2DemoActivity() {
    private fun previewShadowToken(selectedToken: FluentGlobalTokens.ShadowTokens): BasicCardTokens {
        return object : BasicCardTokens() {
            @Composable
            override fun elevation(basicCardInfo: BasicCardInfo): Dp {
                return when (basicCardInfo.cardType) {
                    CardType.Elevated -> elevation(selectedToken)
                    CardType.Outlined -> 0.dp
                }
            }
        }
    }

    private fun previewCornerRadiusToken(selectedToken: FluentGlobalTokens.CornerRadiusTokens): BasicCardTokens {
        return object : BasicCardTokens() {
            @Composable
            override fun cornerRadius(basicCardInfo: BasicCardInfo): Dp {
                return cornerRadius(selectedToken)
            }
        }

    }

    private fun previewStrokeWidthToken(selectedToken: FluentGlobalTokens.StrokeWidthTokens): BasicCardTokens {
        return object : BasicCardTokens() {
            @Composable
            override fun borderStrokeWidth(basicCardInfo: BasicCardInfo): Dp {
                return strokeWidth(selectedToken)
            }
        }
    }

    @Composable
    fun PreviewToken(tokenName: String, tokensList: Array<out Enum<*>>) {
        val tabsList = mutableListOf<PillMetaData>()
        var selectedToken by remember { mutableStateOf(0) }
        tokensList.forEach {
            tabsList.add(
                PillMetaData(
                    text = "$it",
                    onClick = { selectedToken = tokensList.indexOf(it) }
                )
            )
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            BasicCard(
                modifier = Modifier
                    .size(width = 180.dp, height = 130.dp)
                    .padding(bottom = size(FluentGlobalTokens.SizeTokens.Size160)),
                basicCardTokens = if (tokenName == "Shadow Tokens") previewShadowToken(tokensList[selectedToken] as FluentGlobalTokens.ShadowTokens)
                else if (tokenName == "Corner Radius Tokens") previewCornerRadiusToken(tokensList[selectedToken] as FluentGlobalTokens.CornerRadiusTokens)
                else if (tokenName == "Stroke Width Tokens") previewStrokeWidthToken(tokensList[selectedToken] as FluentGlobalTokens.StrokeWidthTokens) else null
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    SetPreviewContent(tokenName, tokensList[selectedToken])
                }
            }

            PillTabs(metadataList = tabsList, scrollable = true, selectedIndex = selectedToken)
        }
    }

    @Composable
    fun SetPreviewContent(tokenName: String, selectedToken: Any) {
        val textColor: Color = FluentColor(light = Color.Black, dark = Color.White).value()
        when (tokenName) {
            "Font Size Tokens" ->
                Text(
                    text = "Text",
                    color = textColor,
                    fontSize = fontSize(selectedToken as FluentGlobalTokens.FontSizeTokens)
                )

            "Line Height Tokens" ->
                Column {
                    Text(
                        text = "Text\nText",
                        color = textColor,
                        lineHeight = lineHeight(selectedToken as FluentGlobalTokens.LineHeightTokens)
                    )
                }


            "Font Weight Tokens" ->
                Text(
                    text = "Text",
                    color = textColor,
                    fontSize = fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                    fontWeight = fontWeight(selectedToken as FluentGlobalTokens.FontWeightTokens)
                )

            "Icon Size Tokens" ->
                Icon(
                    painter = painterResource(id = R.drawable.ic_fluent_home_24_regular),
                    contentDescription = "Sample Icon",
                    tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        FluentTheme.themeMode
                    ),
                    modifier = Modifier.size(iconSize(selectedToken as FluentGlobalTokens.IconSizeTokens))
                )

            "Size Tokens" ->
                Row(horizontalArrangement = Arrangement.spacedBy(size(selectedToken as FluentGlobalTokens.SizeTokens))) {
                    Text(
                        text = "Text",
                        color = textColor,
                        fontSize = fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                    )

                    Text(
                        text = "Text",
                        color = textColor,
                        fontSize = fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                    )
                }

            "Typography Tokens" -> Label(
                text = "Text",
                textStyle = selectedToken as FluentAliasTokens.TypographyTokens
            )
        }
    }

    private var buttonBarList = mutableListOf<PillMetaData>()
    private lateinit var selectedTokens: MutableState<Tokens>
    private lateinit var tokensList: MutableState<Map<String, Pair<Array<out Enum<*>>, Any>>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBottomBar {
            val globalTokensList = mapOf(
                "Neutral Color Tokens" to Pair(
                    FluentGlobalTokens.NeutralColorTokens.values(),
                    ::neutralColor
                ),
                "Font Size Tokens" to Pair(FluentGlobalTokens.FontSizeTokens.values(), ::fontSize),
                "Line Height Tokens" to Pair(
                    FluentGlobalTokens.LineHeightTokens.values(),
                    ::lineHeight
                ),
                "Font Weight Tokens" to Pair(
                    FluentGlobalTokens.FontWeightTokens.values(),
                    ::fontWeight
                ),
                "Icon Size Tokens" to Pair(FluentGlobalTokens.IconSizeTokens.values(), ::iconSize),
                "Size Tokens" to Pair(FluentGlobalTokens.SizeTokens.values(), ::size),
                "Shadow Tokens" to Pair(FluentGlobalTokens.ShadowTokens.values(), ::elevation),
                "Corner Radius Tokens" to Pair(
                    FluentGlobalTokens.CornerRadiusTokens.values(),
                    ::cornerRadius
                ),
                "Stroke Width Tokens" to Pair(
                    FluentGlobalTokens.StrokeWidthTokens.values(),
                    ::strokeWidth
                ),
            )

            val aliasTokensList = mapOf(
                "Brand Color Tokens" to Pair(
                    FluentAliasTokens.BrandColorTokens.values(),
                    FluentTheme.aliasTokens.brandColor
                ),
                "Neutral Background Color Tokens" to Pair(
                    FluentAliasTokens.NeutralBackgroundColorTokens.values(),
                    FluentTheme.aliasTokens.neutralBackgroundColor
                ),
                "Neutral Foreground Color Tokens" to Pair(
                    FluentAliasTokens.NeutralForegroundColorTokens.values(),
                    FluentTheme.aliasTokens.neutralForegroundColor
                ),
                "Neutral Stroke Color Tokens" to Pair(
                    FluentAliasTokens.NeutralStrokeColorTokens.values(),
                    FluentTheme.aliasTokens.neutralStrokeColor
                ),
                "Brand Background Color Tokens" to Pair(
                    FluentAliasTokens.BrandBackgroundColorTokens.values(),
                    FluentTheme.aliasTokens.brandBackgroundColor
                ),
                "Brand Foreground Color Tokens" to Pair(
                    FluentAliasTokens.BrandForegroundColorTokens.values(),
                    FluentTheme.aliasTokens.brandForegroundColor
                ),
                "Brand Stroke Color Tokens" to Pair(
                    FluentAliasTokens.BrandStrokeColorTokens.values(),
                    FluentTheme.aliasTokens.brandStroke
                ),
                "Error And Status Color Tokens" to Pair(
                    FluentAliasTokens.ErrorAndStatusColorTokens.values(),
                    FluentTheme.aliasTokens.errorAndStatusColor
                ),
                "Presence Color Tokens" to Pair(
                    FluentAliasTokens.PresenceColorTokens.values(),
                    FluentTheme.aliasTokens.presenceColor
                ),
                "Typography Tokens" to Pair(
                    FluentAliasTokens.TypographyTokens.values(),
                    FluentTheme.aliasTokens.typography
                )
            )

            selectedTokens = remember { mutableStateOf(Tokens.GlobalTokens) }
            tokensList = remember { mutableStateOf(globalTokensList.toMutableMap()) }
            buttonBarList = listOf(
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
                style = AppTheme.appThemeStyle.value,
                metadataList = buttonBarList,
                selectedIndex = selectedTokens.value.ordinal,
            )
        }

        setActivityContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                tokensList.value.forEach { (tokenName, tokenValue) ->
                    ListItem.SectionHeader(
                        title = tokenName,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f)
                    ) {
                        if (tokenName.contains("Color")) {
                            Column(
                                modifier = Modifier
                                    .height(256.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {

                                tokenValue.first.forEach {
                                    ListItem.Item(
                                        text = "$it",
                                        trailingAccessoryContent = {
                                            when (tokenName) {
                                                "Neutral Color Tokens" ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = (tokenValue.second as (Enum<*>) -> Color).invoke(
                                                            it
                                                        )
                                                    )

                                                "Brand Color Tokens" ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = FluentTheme.aliasTokens.brandColor[it as FluentAliasTokens.BrandColorTokens]
                                                    )

                                                "Neutral Background Color Tokens", "Neutral Foreground Color Tokens", "Neutral Stroke Color Tokens", "Error And Status Color Tokens", "Presence Color Tokens" ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = (tokenValue.second as TokenSet<Enum<*>, FluentColor>)[it].value()
                                                    )

                                                "Brand Background Color Tokens" ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = FluentTheme.aliasTokens.brandBackgroundColor[it as FluentAliasTokens.BrandBackgroundColorTokens].value()
                                                    )

                                                "Brand Foreground Color Tokens" ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = FluentTheme.aliasTokens.brandForegroundColor[it as FluentAliasTokens.BrandForegroundColorTokens].value()
                                                    )

                                                "Brand Stroke Color Tokens" ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = FluentTheme.aliasTokens.brandStroke[it as FluentAliasTokens.BrandStrokeColorTokens].value()
                                                    )
                                            }
                                        }
                                    )
                                }
                            }
                        } else PreviewToken(tokenName, tokenValue.first)
                    }
                }
            }
        }

        setBottomSheetContent {
            val mUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#avatar"
            AndroidView(factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    webViewClient = WebViewClient()
                    loadUrl(mUrl)
                }
            }, update = {
                it.loadUrl(mUrl)
            })
        }
    }
}