package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.TypographyTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.LabelInfo
import com.microsoft.fluentui.theme.token.controlTokens.LabelTokens

/**
 * A standard UI label consisting of a simple text.
 * @param text Label text
 * @param textStyle Text styling for the text. Default [TypographyTokens.Display]
 * @param colorStyle Color styling for the text. Default [ColorStyle.Primary]
 * @param modifier Optional modifier for Label
 * @param labelTokens Option tokens for label styling
 */
@Composable
fun Label(
    text: String,
    textStyle: TypographyTokens = TypographyTokens.Display,
    colorStyle: ColorStyle = ColorStyle.Primary,
    modifier: Modifier = Modifier,
    labelTokens: LabelTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val tokens = labelTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Label] as LabelTokens
    val labelInfo = LabelInfo(textStyle, colorStyle)
    val textStyle = tokens.typography(labelInfo = labelInfo)
    val textColor = tokens.textColor(labelInfo = labelInfo)
    BasicText(
        modifier = modifier,
        text = text,
        style = textStyle.merge(TextStyle(color = textColor))
    )
}