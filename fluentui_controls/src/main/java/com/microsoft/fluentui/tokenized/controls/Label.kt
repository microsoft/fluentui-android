package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
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
 * @param textColorOverride Optional override for text color. Default is null. When set colorStyle is ignored. This param is deprecated and will be removed in future releases. use [style] to override text color
 * @param style Optional style for the text
 * @param overflow TextOverflow value. Default is [TextOverflow.Clip]
 * @param softWrap Whether the text should wrap when it reaches the end of the line. Default is true
 * @param maxLines Maximum number of lines to display. Default is Int.MAX_VALUE
 * @param minLines Minimum number of lines to display. Default is 1
 * @param modifier Optional modifier for Label
 * @param labelTokens Option tokens for label styling
 */
@Composable
fun Label(
    text: String,
    textStyle: TypographyTokens = TypographyTokens.Display,
    colorStyle: ColorStyle = ColorStyle.Primary,
    textColorOverride: Color? = null,
    style: TextStyle? = null,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    modifier: Modifier = Modifier,
    labelTokens: LabelTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val tokens = labelTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.LabelControlType] as LabelTokens
    val labelInfo = LabelInfo(textStyle, colorStyle)
    val textStyle = tokens.typography(labelInfo = labelInfo)
    val textColor = textColorOverride ?: tokens.textColor(labelInfo = labelInfo)
    BasicText(
        modifier = modifier,
        text = text,
        style = textStyle.merge(TextStyle(color = textColor)).merge(style),
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        minLines = minLines
    )
}