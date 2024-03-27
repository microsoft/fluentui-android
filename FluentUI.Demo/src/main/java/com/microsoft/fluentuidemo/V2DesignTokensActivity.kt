package com.microsoft.fluentuidemo

import android.os.Bundle
import androidx.compose.foundation.ExperimentalFoundationApi
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
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.strokeWidth
import com.microsoft.fluentui.theme.token.FluentGlobalTokens.size
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

class V2DesignTokensActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val designTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Design-Tokens#overview"
    override val globalTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Design-Tokens#global-tokens"
    override val aliasTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Design-Tokens#alias-token"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Design-Tokens#control-token"

    private fun previewShadowToken(selectedToken: FluentGlobalTokens.ShadowTokens): BasicCardTokens {
        return object : BasicCardTokens() {
            @Composable
            override fun elevation(basicCardInfo: BasicCardInfo): Dp {
                return when (basicCardInfo.cardType) {
                    CardType.Elevated -> FluentGlobalTokens.ShadowTokens.valueOf(selectedToken.name).value
                    CardType.Outlined -> 0.dp
                }
            }
        }
    }

    private fun previewCornerRadiusToken(selectedToken: FluentGlobalTokens.CornerRadiusTokens): BasicCardTokens {
        return object : BasicCardTokens() {
            @Composable
            override fun cornerRadius(basicCardInfo: BasicCardInfo): Dp {
                return FluentGlobalTokens.CornerRadiusTokens.valueOf(selectedToken.name).value
            }
        }

    }

    private fun previewStrokeWidthToken(selectedToken: FluentGlobalTokens.StrokeWidthTokens): BasicCardTokens {
        return object : BasicCardTokens() {
            @Composable
            override fun borderStrokeWidth(basicCardInfo: BasicCardInfo): Dp {
                return FluentGlobalTokens.StrokeWidthTokens.valueOf(selectedToken.name).value
            }
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun PreviewToken(tokenName: Int, tokensList: Array<out Enum<*>>) {
        val tabsList = mutableListOf<PillMetaData>()
        var selectedTokenIndex by remember { mutableStateOf(0) }
        tokensList.forEach {
            tabsList.add(
                PillMetaData(
                    text = "$it",
                    onClick = { selectedTokenIndex = tokensList.indexOf(it) }
                )
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BasicCard(
                modifier = Modifier
                    .size(width = 180.dp, height = 130.dp)
                    .padding(bottom = FluentGlobalTokens.SizeTokens.Size160.value),
                basicCardTokens = if (tokenName == R.string.shadow_tokens) previewShadowToken(
                    tokensList[selectedTokenIndex] as FluentGlobalTokens.ShadowTokens
                )
                else if (tokenName == R.string.corner_radius_tokens) previewCornerRadiusToken(
                    tokensList[selectedTokenIndex] as FluentGlobalTokens.CornerRadiusTokens
                )
                else if (tokenName == R.string.stroke_width_tokens) previewStrokeWidthToken(
                    tokensList[selectedTokenIndex] as FluentGlobalTokens.StrokeWidthTokens
                ) else null
            ) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    SetPreviewContent(tokenName, tokensList[selectedTokenIndex])
                }
            }

            PillTabs(
                metadataList = tabsList,
                scrollable = true,
                selectedIndex = selectedTokenIndex
            )
        }
    }

    @Composable
    fun SetPreviewContent(tokenName: Int, selectedToken: Any) {
        val textColor: Color = FluentColor(light = Color.Black, dark = Color.White).value()
        when (tokenName) {
            R.string.font_size_tokens ->
                Text(
                    text = stringResource(id = R.string.sample_text),
                    color = textColor,
                    fontSize = FluentGlobalTokens.FontSizeTokens.valueOf((selectedToken as FluentGlobalTokens.FontSizeTokens).name).value
                )

            R.string.line_height_tokens ->
                Column {
                    Text(
                        text = "Text\nText",
                        color = textColor,
                        lineHeight = FluentGlobalTokens.LineHeightTokens.valueOf((selectedToken as FluentGlobalTokens.LineHeightTokens).name).value
                    )
                }

            R.string.font_weight_tokens ->
                Text(
                    text = stringResource(id = R.string.sample_text),
                    color = textColor,
                    fontSize = FluentGlobalTokens.FontSizeTokens.Size600.value,
                    fontWeight = FluentGlobalTokens.FontWeightTokens.valueOf((selectedToken as FluentGlobalTokens.FontWeightTokens).name).value
                )

            R.string.icon_size_tokens ->
                Icon(
                    painter = painterResource(id = R.drawable.ic_fluent_home_24_regular),
                    contentDescription = stringResource(id = R.string.sample_icon),
                    tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                        FluentTheme.themeMode
                    ),
                    modifier = Modifier.size(FluentGlobalTokens.IconSizeTokens.valueOf((selectedToken as FluentGlobalTokens.IconSizeTokens).name).value)
                )

            R.string.size_tokens ->
                Row(horizontalArrangement = Arrangement.spacedBy(FluentGlobalTokens.SizeTokens.valueOf((selectedToken as FluentGlobalTokens.SizeTokens).name).value)) {
                    Text(
                        text = stringResource(id = R.string.sample_text),
                        color = textColor,
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size600.value,
                    )

                    Text(
                        text = stringResource(id = R.string.sample_text),
                        color = textColor,
                        fontSize = FluentGlobalTokens.FontSizeTokens.Size600.value,
                    )
                }

            R.string.typography_tokens -> Label(
                text = stringResource(id = R.string.sample_text),
                textStyle = selectedToken as FluentAliasTokens.TypographyTokens
            )
        }
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var buttonBarList: MutableList<PillMetaData>
        lateinit var selectedTokens: MutableState<Tokens>
        lateinit var tokensMap: MutableState<Map<Int, Pair<Array<out Enum<*>>, Any>>>
        setBottomAppBar {
            val globalTokensMap = mapOf(
                R.string.neutral_color_tokens to Pair(
                    FluentGlobalTokens.NeutralColorTokens.values(),
                    ::neutralColor
                ),
                R.string.font_size_tokens to Pair(
                    FluentGlobalTokens.FontSizeTokens.values(),
                    ::fontSize
                ),
                R.string.line_height_tokens to Pair(
                    FluentGlobalTokens.LineHeightTokens.values(),
                    ::lineHeight
                ),
                R.string.font_weight_tokens to Pair(
                    FluentGlobalTokens.FontWeightTokens.values(),
                    ::fontWeight
                ),
                R.string.icon_size_tokens to Pair(
                    FluentGlobalTokens.IconSizeTokens.values(),
                    ::iconSize
                ),
                R.string.size_tokens to Pair(
                    FluentGlobalTokens.SizeTokens.values(),
                    ::size
                ),
                R.string.shadow_tokens to Pair(
                    FluentGlobalTokens.ShadowTokens.values(),
                    ::elevation
                ),
                R.string.corner_radius_tokens to Pair(
                    FluentGlobalTokens.CornerRadiusTokens.values(),
                    ::cornerRadius
                ),
                R.string.stroke_width_tokens to Pair(
                    FluentGlobalTokens.StrokeWidthTokens.values(),
                    ::strokeWidth
                ),
            )

            val aliasTokensMap = mapOf(
                R.string.brand_color_tokens to Pair(
                    FluentAliasTokens.BrandColorTokens.values(),
                    FluentTheme.aliasTokens.brandColor
                ),
                R.string.neutral_background_color_tokens to Pair(
                    FluentAliasTokens.NeutralBackgroundColorTokens.values(),
                    FluentTheme.aliasTokens.neutralBackgroundColor
                ),
                R.string.neutral_foreground_color_tokens to Pair(
                    FluentAliasTokens.NeutralForegroundColorTokens.values(),
                    FluentTheme.aliasTokens.neutralForegroundColor
                ),
                R.string.neutral_stroke_color_tokens to Pair(
                    FluentAliasTokens.NeutralStrokeColorTokens.values(),
                    FluentTheme.aliasTokens.neutralStrokeColor
                ),
                R.string.brand_background_color_tokens to Pair(
                    FluentAliasTokens.BrandBackgroundColorTokens.values(),
                    FluentTheme.aliasTokens.brandBackgroundColor
                ),
                R.string.brand_foreground_color_tokens to Pair(
                    FluentAliasTokens.BrandForegroundColorTokens.values(),
                    FluentTheme.aliasTokens.brandForegroundColor
                ),
                R.string.brand_stroke_color_tokens to Pair(
                    FluentAliasTokens.BrandStrokeColorTokens.values(),
                    FluentTheme.aliasTokens.brandStroke
                ),
                R.string.error_and_status_color_tokens to Pair(
                    FluentAliasTokens.ErrorAndStatusColorTokens.values(),
                    FluentTheme.aliasTokens.errorAndStatusColor
                ),
                R.string.presence_tokens to Pair(
                    FluentAliasTokens.PresenceColorTokens.values(),
                    FluentTheme.aliasTokens.presenceColor
                ),
                R.string.typography_tokens to Pair(
                    FluentAliasTokens.TypographyTokens.values(),
                    FluentTheme.aliasTokens.typography
                )
            )

            selectedTokens = remember { mutableStateOf(Tokens.GlobalTokens) }
            tokensMap = remember { mutableStateOf(globalTokensMap.toMutableMap()) }
            buttonBarList = listOf(
                PillMetaData(
                    text = stringResource(id = R.string.global_tokens),
                    enabled = true,
                    onClick = {
                        selectedTokens.value = Tokens.GlobalTokens
                        tokensMap.value = globalTokensMap.toMutableMap()
                    }
                ),
                PillMetaData(
                    text = stringResource(id = R.string.alias_tokens),
                    enabled = true,
                    onClick = {
                        selectedTokens.value = Tokens.AliasTokens
                        tokensMap.value = aliasTokensMap.toMutableMap()
                    }
                )
            ) as MutableList<PillMetaData>

            PillTabs(
                style = AppThemeViewModel.appThemeStyle.value,
                metadataList = buttonBarList,
                selectedIndex = selectedTokens.value.ordinal,
            )
        }

        setActivityContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                tokensMap.value.forEach { (tokenType, tokenGetter) ->
                    ListItem.SectionHeader(
                        title = stringResource(id = tokenType),
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f),
                    ) {
                        if (stringResource(id = tokenType).contains(stringResource(id = R.string.color))) {
                            Column(
                                modifier = Modifier
                                    .height(256.dp)
                                    .verticalScroll(rememberScrollState())
                            ) {

                                tokenGetter.first.forEach {
                                    ListItem.Item(
                                        text = "$it",
                                        trailingAccessoryContent = {
                                            when (tokenType) {
                                                R.string.neutral_color_tokens ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.neutral_color_tokens),
                                                        tint = (tokenGetter.second as (Enum<*>) -> Color).invoke(
                                                            it
                                                        )
                                                    )

                                                R.string.brand_color_tokens ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.brand_color_tokens),
                                                        tint = FluentTheme.aliasTokens.brandColor[it as FluentAliasTokens.BrandColorTokens]
                                                    )

                                                R.string.neutral_background_color_tokens, R.string.neutral_foreground_color_tokens, R.string.neutral_stroke_color_tokens, R.string.error_and_status_color_tokens, R.string.presence_tokens ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = "",
                                                        tint = (tokenGetter.second as TokenSet<Enum<*>, FluentColor>)[it].value()
                                                    )

                                                R.string.brand_background_color_tokens ->
                                                    if ((tokenGetter.second as TokenSet<Enum<*>, FluentColor>)[it].value() == Color.Unspecified) {
                                                        Label(
                                                            text = stringResource(id = R.string.unspecified),
                                                            textStyle = FluentAliasTokens.TypographyTokens.Caption1
                                                        )
                                                    } else {
                                                        Icon(
                                                            painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                            contentDescription = stringResource(id = R.string.brand_background_color_tokens),
                                                            tint = FluentTheme.aliasTokens.brandBackgroundColor[it as FluentAliasTokens.BrandBackgroundColorTokens].value()
                                                        )
                                                    }

                                                R.string.brand_foreground_color_tokens ->
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.ic_fluent_square_24_filled),
                                                        contentDescription = stringResource(id = R.string.brand_foreground_color_tokens),
                                                        tint = FluentTheme.aliasTokens.brandForegroundColor[it as FluentAliasTokens.BrandForegroundColorTokens].value()
                                                    )

                                                R.string.brand_stroke_color_tokens ->
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
                        } else PreviewToken(tokenType, tokenGetter.first)
                    }
                }
            }
        }
    }
}