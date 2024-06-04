package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.background
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
 * A Basic card is a card with an empty container and with radius and basic elevation or outline depending on the cardType.
 *
 * @param modifier Modifier for the card which is applied first in order
 * @param cardType defines the card type, whether Elevated or Outlined
 * @param basicCardTokens Optional tokens for customizing the card
 * @param content Content for the card
 */
@Composable
fun BorderlessBasicCard(
    modifier: Modifier = Modifier,
    cardType: CardType = CardType.Elevated,
    basicCardTokens: BasicCardTokens? = null,
    content: @Composable () -> Unit
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = basicCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BasicCardControlType] as BasicCardTokens
    val cardInfo = BasicCardInfo(cardType)
    val cornerRadius = token.cornerRadius(basicCardInfo = cardInfo)
    val backgroundBrush = token.backgroundBrush(basicCardInfo = cardInfo)
    val elevation = token.elevation(basicCardInfo = cardInfo)
    val shape = RoundedCornerShape(cornerRadius)
    Box(
        modifier = modifier
            .shadow(elevation, shape, false)
            .background(
                backgroundBrush, shape
            )
            .clip(shape)
    ) {
        content()
    }
}