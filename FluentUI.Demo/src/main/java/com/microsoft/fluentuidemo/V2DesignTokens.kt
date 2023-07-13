package com.microsoft.fluentuidemo

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
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
                basicCardTokens = if (tokenName == stringResource(id = R.string.shadow_tokens)) previewShadowToken(
                    tokensList[selectedToken] as FluentGlobalTokens.ShadowTokens
                )
                else if (tokenName == stringResource(id = R.string.corner_radius_tokens)) previewCornerRadiusToken(
                    tokensList[selectedToken] as FluentGlobalTokens.CornerRadiusTokens
                )
                else if (tokenName == stringResource(id = R.string.stroke_width_tokens)) previewStrokeWidthToken(
                    tokensList[selectedToken] as FluentGlobalTokens.StrokeWidthTokens
                ) else null
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
            stringResource(id = R.string.font_size_tokens) ->
                Text(
                    text = stringResource(id = R.string.sample_text),
                    color = textColor,
                    fontSize = fontSize(selectedToken as FluentGlobalTokens.FontSizeTokens)
                )

            stringResource(id = R.string.line_height_tokens) ->
                Column {
                    Text(
                        text = "Text\nText",
                        color = textColor,
                        lineHeight = lineHeight(selectedToken as FluentGlobalTokens.LineHeightTokens)
                    )
                }

            stringResource(id = R.string.font_weight_tokens) ->
                Text(
                    text = stringResource(id = R.string.sample_text),
                    color = textColor,
                    fontSize = fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                    fontWeight = fontWeight(selectedToken as FluentGlobalTokens.FontWeightTokens)
                )

            stringResource(id = R.string.icon_size_tokens) ->
                Icon(
                    painter = painterResource(id = R.drawable.ic_fluent_home_24_regular),
                    contentDescription = stringResource(id = R.string.sample_icon),
                    tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        FluentTheme.themeMode
                    ),
                    modifier = Modifier.size(iconSize(selectedToken as FluentGlobalTokens.IconSizeTokens))
                )

            stringResource(id = R.string.size_tokens) ->
                Row(horizontalArrangement = Arrangement.spacedBy(size(selectedToken as FluentGlobalTokens.SizeTokens))) {
                    Text(
                        text = stringResource(id = R.string.sample_text),
                        color = textColor,
                        fontSize = fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                    )

                    Text(
                        text = stringResource(id = R.string.sample_text),
                        color = textColor,
                        fontSize = fontSize(FluentGlobalTokens.FontSizeTokens.Size600),
                    )
                }

            stringResource(id = R.string.typography_tokens) -> Label(
                text = stringResource(id = R.string.sample_text),
                textStyle = selectedToken as FluentAliasTokens.TypographyTokens
            )
        }
    }

    private var buttonBarList = mutableListOf<PillMetaData>()
    private lateinit var selectedTokens: MutableState<Tokens>
    private lateinit var tokensList: MutableState<Map<String, Pair<Array<out Enum<*>>, Any>>>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setBottomAppBar {
            val globalTokensList = mapOf(
                stringResource(id = R.string.neutral_color_tokens) to Pair(
                    FluentGlobalTokens.NeutralColorTokens.values(),
                    ::neutralColor
                ),
                stringResource(id = R.string.font_size_tokens) to Pair(
                    FluentGlobalTokens.FontSizeTokens.values(),
                    ::fontSize
                ),
                stringResource(id = R.string.line_height_tokens) to Pair(
                    FluentGlobalTokens.LineHeightTokens.values(),
                    ::lineHeight
                ),
                stringResource(id = R.string.font_weight_tokens) to Pair(
                    FluentGlobalTokens.FontWeightTokens.values(),
                    ::fontWeight
                ),
                stringResource(id = R.string.icon_size_tokens) to Pair(
                    FluentGlobalTokens.IconSizeTokens.values(),
                    ::iconSize
                ),
                stringResource(id = R.string.size_tokens) to Pair(
                    FluentGlobalTokens.SizeTokens.values(),
                    ::size
                ),
                stringResource(id = R.string.shadow_tokens) to Pair(
                    FluentGlobalTokens.ShadowTokens.values(),
                    ::elevation
                ),
                stringResource(id = R.string.corner_radius_tokens) to Pair(
                    FluentGlobalTokens.CornerRadiusTokens.values(),
                    ::cornerRadius
                ),
                stringResource(id = R.string.stroke_width_tokens) to Pair(
                    FluentGlobalTokens.StrokeWidthTokens.values(),
                    ::strokeWidth
                ),
            )

            val aliasTokensList = mapOf(
                stringResource(id = R.string.brand_color_tokens) to Pair(
                    FluentAliasTokens.BrandColorTokens.values(),
                    FluentTheme.aliasTokens.brandColor
                ),
                stringResource(id = R.string.neutral_background_color_tokens) to Pair(
                    FluentAliasTokens.NeutralBackgroundColorTokens.values(),
                    FluentTheme.aliasTokens.neutralBackgroundColor
                ),
                stringResource(id = R.string.neutral_foreground_color_tokens) to Pair(
                    FluentAliasTokens.NeutralForegroundColorTokens.values(),
                    FluentTheme.aliasTokens.neutralForegroundColor
                ),
                stringResource(id = R.string.neutral_stroke_color_tokens) to Pair(
                    FluentAliasTokens.NeutralStrokeColorTokens.values(),
                    FluentTheme.aliasTokens.neutralStrokeColor
                ),
                stringResource(id = R.string.brand_background_color_tokens) to Pair(
                    FluentAliasTokens.BrandBackgroundColorTokens.values(),
                    FluentTheme.aliasTokens.brandBackgroundColor
                ),
                stringResource(id = R.string.brand_foreground_color_tokens) to Pair(
                    FluentAliasTokens.BrandForegroundColorTokens.values(),
                    FluentTheme.aliasTokens.brandForegroundColor
                ),
                stringResource(id = R.string.brand_stroke_color_tokens) to Pair(
                    FluentAliasTokens.BrandStrokeColorTokens.values(),
                    FluentTheme.aliasTokens.brandStroke
                ),
                stringResource(id = R.string.error_and_status_color_tokens) to Pair(
                    FluentAliasTokens.ErrorAndStatusColorTokens.values(),
                    FluentTheme.aliasTokens.errorAndStatusColor
                ),
                stringResource(id = R.string.presence_tokens) to Pair(
                    FluentAliasTokens.PresenceColorTokens.values(),
                    FluentTheme.aliasTokens.presenceColor
                ),
                stringResource(id = R.string.typography_tokens) to Pair(
                    FluentAliasTokens.TypographyTokens.values(),
                    FluentTheme.aliasTokens.typography
                )
            )

            selectedTokens = remember { mutableStateOf(Tokens.GlobalTokens) }
            tokensList = remember { mutableStateOf(globalTokensList.toMutableMap()) }
            buttonBarList = listOf(
                PillMetaData(
                    text = stringResource(id = R.string.global_tokens),
                    enabled = true,
                    onClick = {
                        selectedTokens.value = Tokens.GlobalTokens
                        tokensList.value = globalTokensList.toMutableMap()
                    }
                ),
                PillMetaData(
                    text = stringResource(id = R.string.alias_tokens),
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
                        if (tokenName.contains(stringResource(id = R.string.color))) {
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
                                                stringResource(id = R.string.neutral_color_tokens) ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.neutral_color_tokens),
                                                        tint = (tokenValue.second as (Enum<*>) -> Color).invoke(
                                                            it
                                                        )
                                                    )

                                                stringResource(id = R.string.brand_color_tokens) ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.brand_color_tokens),
                                                        tint = FluentTheme.aliasTokens.brandColor[it as FluentAliasTokens.BrandColorTokens]
                                                    )

                                                stringResource(id = R.string.neutral_background_color_tokens), stringResource(
                                                    id = R.string.neutral_foreground_color_tokens
                                                ), stringResource(id = R.string.neutral_stroke_color_tokens), stringResource(
                                                    id = R.string.error_and_status_color_tokens
                                                ), stringResource(id = R.string.presence_tokens) ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = (tokenValue.second as TokenSet<Enum<*>, FluentColor>)[it].value()
                                                    )

                                                stringResource(id = R.string.brand_background_color_tokens) ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.brand_background_color_tokens),
                                                        tint = FluentTheme.aliasTokens.brandBackgroundColor[it as FluentAliasTokens.BrandBackgroundColorTokens].value()
                                                    )

                                                stringResource(id = R.string.brand_foreground_color_tokens) ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.brand_foreground_color_tokens),
                                                        tint = FluentTheme.aliasTokens.brandForegroundColor[it as FluentAliasTokens.BrandForegroundColorTokens].value()
                                                    )

                                                stringResource(id = R.string.brand_stroke_color_tokens) ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.brand_stroke_color_tokens),
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
            val mUrl = "https://github.com/microsoft/fluentui-android/wiki/Design-Tokens#overview"
            BottomSheetWebView(mUrl)
        }
    }
}