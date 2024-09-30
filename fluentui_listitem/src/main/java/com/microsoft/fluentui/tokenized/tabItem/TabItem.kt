package com.microsoft.fluentui.tokenized.tabItem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.TabItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.TabItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.TabTextAlignment

@Composable
fun TabItem(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    accessory: (@Composable () -> Unit)?,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    textAlignment: TabTextAlignment = TabTextAlignment.VERTICAL,
    enabled: Boolean = true,
    selected: Boolean = false,
    fixedWidth: Boolean = false,
    showIndicator: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    tabItemTokens: TabItemTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token =
        tabItemTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.TabItemControlType] as TabItemTokens

    val tabItemInfo = TabItemInfo(textAlignment, style)
    val textColor by animateColorAsState(
        token.textColor(tabItemInfo = tabItemInfo).getColorByState(
            enabled = enabled,
            selected = selected,
            interactionSource = interactionSource
        ),
        animationSpec = tween(durationMillis = 300)
    )
    val iconColor by animateColorAsState (
        targetValue = token.iconColor(tabItemInfo = tabItemInfo).getColorByState(
            enabled = enabled,
            selected = selected,
            interactionSource = interactionSource
        ),
        animationSpec = tween(durationMillis = 300)
    )
    val padding = token.padding(tabItemInfo = tabItemInfo)
    val backgroundColor = token.backgroundBrush(tabItemInfo = tabItemInfo).getBrushByState(
        enabled = enabled, selected = selected, interactionSource = interactionSource
    )
    val rippleColor = token.rippleColor(tabItemInfo = tabItemInfo)
    val clickableModifier = Modifier
        .clickable(
            interactionSource = interactionSource,
            indication = if (showIndicator) null else rememberRipple(color = rippleColor),
            onClickLabel = null,
            enabled = enabled,
            onClick = onClick,
            role = Role.Tab,
        )

    val widthModifier = if (fixedWidth) {
        Modifier.width(token.width(tabItemInfo = tabItemInfo))
    } else {
        Modifier
    }
    val iconContent: @Composable () -> Unit = {
        Icon(
            imageVector = icon,
            modifier = Modifier.size(if (textAlignment == TabTextAlignment.NO_TEXT) 28.dp else 24.dp),
            contentDescription = if (textAlignment == TabTextAlignment.NO_TEXT) title else null,
            tint = iconColor
        )
    }

    if (textAlignment == TabTextAlignment.HORIZONTAL) {
        ConstraintLayout(
            modifier = modifier
                .then(clickableModifier)
                .then(
                    if(showIndicator)
                        Modifier.background(backgroundColor)
                    else
                        Modifier
                )
                .padding(padding)
                .then(widthModifier)
        )
        {
            val (iconConstrain, textConstrain, badgeConstrain) = createRefs()

            Box(modifier = Modifier.constrainAs(iconConstrain) {
                start.linkTo(parent.start)
                end.linkTo(textConstrain.start)
            }
            ) {
                iconContent()
            }

            BasicText(
                text = title,
                modifier = Modifier
                    .constrainAs(textConstrain) {
                        start.linkTo(iconConstrain.end)
                        end.linkTo(badgeConstrain.start)
                        width = Dimension.preferredWrapContent
                    }
                    .padding(start = 8.dp),
                style = TextStyle(color = textColor, textAlign = TextAlign.Center),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )

            if (accessory != null) {
                Box(modifier = Modifier
                    .constrainAs(badgeConstrain) {
                        start.linkTo(textConstrain.end)
                        end.linkTo(parent.end)
                    }
                ) {
                    accessory()
                }
            }
            createHorizontalChain(
                iconConstrain,
                textConstrain,
                badgeConstrain,
                chainStyle = ChainStyle.Packed
            )
        }
    } else {
        val badgeWithIcon: @Composable () -> Unit = {
            Layout(
                {
                    Box(
                        modifier = Modifier.layoutId("anchor"),
                        contentAlignment = Alignment.Center
                    ) {
                        iconContent()
                    }

                    Box(modifier = Modifier.layoutId("badge")) {
                        if (accessory != null)
                            accessory()
                    }

                }
            ) { measurables, constraints ->
                val badgePlaceable = measurables.first { it.layoutId == "badge" }.measure(
                    // Measure with loose constraints for height as we don't want the text to take up more
                    // space than it needs.
                    constraints.copy(minHeight = 0)
                )

                val anchorPlaceable =
                    measurables.first { it.layoutId == "anchor" }.measure(constraints)

                // Use the width of the badge to infer whether it has any content (based on radius used
                // in [Badge]) and determine its horizontal offset.
                val hasContent = badgePlaceable.width > 16.dp.roundToPx()
                val contentOffset = if (hasContent) -2.dp.roundToPx() else 0
                val badgeHorizontalOffset = -anchorPlaceable.width / 2 + contentOffset
                val badgeVerticalOffset = (-4).dp

                val totalWidth = anchorPlaceable.width
                val totalHeight = anchorPlaceable.height

                layout(
                    totalWidth,
                    totalHeight
                ) {

                    anchorPlaceable.placeRelative(0, 0)
                    val badgeX = anchorPlaceable.width + badgeHorizontalOffset
                    val badgeY = badgeVerticalOffset.roundToPx()
                    badgePlaceable.placeRelative(badgeX, badgeY)
                }
            }
        }

        val indicatorCornerRadiusSize = FluentGlobalTokens.CornerRadiusTokens.CornerRadiusCircle.value
        val indicatorWidth = FluentGlobalTokens.SizeTokens.Size160.value
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier
                .then(clickableModifier)
                .then(
                    if(showIndicator)
                        Modifier.background(backgroundColor)
                    else
                        Modifier
                )
                .padding(padding)
                .then(widthModifier)
        ) {
            badgeWithIcon()

            val textTypography = token.textTypography(tabItemInfo = tabItemInfo)
            var fontSize = remember { mutableStateOf(textTypography.fontSize) }
            var textStyle by remember(textColor) {
                mutableStateOf(
                    textTypography.merge(TextStyle(color = textColor, fontSize = fontSize.value))
                )
            }

            if (textAlignment == TabTextAlignment.VERTICAL) {
                Spacer(modifier = Modifier.height(2.dp))
                BasicText(
                    text = title,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { textLayoutResult ->
                        if (textLayoutResult.didOverflowHeight) {
                            textStyle.fontSize
                            fontSize.value *= 0.9
                            textStyle = textStyle.copy(fontSize = fontSize.value)
                        }
                    }
                )
            }
            if(showIndicator){
                Spacer(modifier = Modifier.height(3.5.dp))
                AnimatedVisibility(
                    visible = selected,
                    enter = fadeIn(animationSpec = tween(durationMillis = 300))+ expandHorizontally(animationSpec = tween(durationMillis = 300)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 300))+ shrinkHorizontally(animationSpec = tween(durationMillis = 300)),
                )
                {
                    Box(
                        modifier = Modifier
                            .height(3.dp)
                            .width(indicatorWidth)
                            .background(shape = RoundedCornerShape(indicatorCornerRadiusSize), color = textColor)
                            .clip(RoundedCornerShape(indicatorCornerRadiusSize))
                    )
                }
            }
        }
    }
}