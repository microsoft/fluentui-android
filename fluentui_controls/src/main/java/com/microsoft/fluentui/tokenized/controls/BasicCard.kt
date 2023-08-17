package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardInfo
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardTokens
import com.microsoft.fluentui.theme.token.controlTokens.CardType

/**
 * Cards are flexible containers that group related content and actions together. They reveal more information upon interaction.
 * A Basic card is a card with an empty container and basic elevation and radius.
 *
 * @param modifier Modifier for the card
 * @param basicCardTokens Optional tokens for customizing the card
 * @param content Content for the card
 */
@Composable
fun BasicCard(
    modifier: Modifier = Modifier,
    basicCardTokens: BasicCardTokens? = null,
    content: @Composable () -> Unit
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = basicCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BasicCardControlType] as BasicCardTokens
    val basicCardInfo = BasicCardInfo(CardType.Elevated)
    val cornerRadius = token.cornerRadius(basicCardInfo = basicCardInfo)
    val backgroundBrush = token.backgroundBrush(basicCardInfo = basicCardInfo)
    val elevation = token.elevation(basicCardInfo = basicCardInfo)
    val borderColor = token.borderColor(basicCardInfo = basicCardInfo)
    val borderStrokeWidth = token.borderStrokeWidth(basicCardInfo = basicCardInfo)
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .shadow(elevation, shape, false)
            .background(
                backgroundBrush, shape
            )
            .border(
                borderStrokeWidth, borderColor, shape
            )
            .clip(shape)
    ) {
        content()
    }
}