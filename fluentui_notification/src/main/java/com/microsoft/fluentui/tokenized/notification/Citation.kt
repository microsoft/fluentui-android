package com.microsoft.fluentui.tokenized.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.CitationInfo
import com.microsoft.fluentui.theme.token.controlTokens.CitationTokens

/**
 * A citation is used to refer a certain text or a quotation from another source.
 * It contains a single text value, usually a number.
 * @param text text for the citation. Usually a number
 * @param onClick onClick method for citation. Usually to provide the reference information
 * @param modifier Optional modifier for the citation
 * @param citationTokens Optional tokens to customize appearance
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun Citation(
    text: String,
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    citationTokens: CitationTokens? = null
) {
    val tokens = citationTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Citation] as CitationTokens
    val citationInfo = CitationInfo()
    val backgroundBrush = tokens.backgroundBrush(citationInfo = citationInfo)
    val cornerRadius = tokens.cornerRadius(citationInfo = citationInfo)
    val borderWidth = tokens.borderStrokeWidth(citationInfo = citationInfo)
    val borderBrush = tokens.borderBrush(citationInfo = citationInfo)
    val textColor = tokens.textColor(citationInfo = citationInfo)
    val typography = tokens.textTypography(citationInfo = citationInfo)
    val textPadding = tokens.textPadding(citationInfo = citationInfo)
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .border(borderWidth, borderBrush, shape)
            .background(backgroundBrush, shape)
            .clip(shape)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        enabled = true,
                        onClickLabel = null,
                        role = Role.Button,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            )
    ) {
        BasicText(
            modifier = Modifier.padding(textPadding),
            text = text,
            style = typography.merge(
                TextStyle(
                    platformStyle = PlatformTextStyle(
                        includeFontPadding = false
                    ), color = textColor
                )
            )
        )
    }
}