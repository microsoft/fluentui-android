package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LocalAbsoluteElevation
import androidx.compose.material.LocalElevationOverlay
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.semantics.Role
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardInfo
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardTokens
import com.microsoft.fluentui.theme.token.controlTokens.CardType

@Composable
fun BasicCard(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    basicCardTokens: BasicCardTokens? = null,
    content: @Composable () -> Unit
) {
    val token = basicCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BasicCard] as BasicCardTokens
    val basicCardInfo = BasicCardInfo(CardType.Elevated)
    val cornerRadius = token.cornerRadius(basicCardInfo = basicCardInfo)
    val backgroundColor = token.backgroundColor(basicCardInfo = basicCardInfo)
    val elevation = token.elevation(basicCardInfo = basicCardInfo)
    val borderColor = token.borderColor(basicCardInfo = basicCardInfo)
    val borderStrokeWidth = token.borderStrokeWidth(basicCardInfo = basicCardInfo)
    val shape = RoundedCornerShape(cornerRadius)
    val absoluteElevation = LocalAbsoluteElevation.current + elevation
    val elevationOverlay = LocalElevationOverlay.current
    val elevatedBackgroundColor =
        elevationOverlay?.apply(color = backgroundColor, elevation = absoluteElevation)
            ?: backgroundColor
    Box(
        modifier = modifier
            .shadow(elevation, shape, false)
            .background(
                elevatedBackgroundColor,
                shape
            )
            .border(
                borderStrokeWidth, borderColor, shape
            )
            .clip(shape)
            .then(
                if (onClick != null) Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = rememberRipple(),
                    enabled = true,
                    onClick = onClick,
                    role = Role.Button
                ) else Modifier
            ),
    ) {
        content()
    }
}