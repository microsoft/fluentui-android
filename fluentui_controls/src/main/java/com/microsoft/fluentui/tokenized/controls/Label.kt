package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.*

/**
 * A standard UI label consisting of a simple text. Design format [TypographyTokens.Display]
 */
@Composable
fun Label(
    text: String,
    textStyle: TextType = TextType.Display,
    colorStyle: ColorStyle = ColorStyle.Primary,
    modifier: Modifier = Modifier,
    labelTokens: LabelTokens? = null
) {
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