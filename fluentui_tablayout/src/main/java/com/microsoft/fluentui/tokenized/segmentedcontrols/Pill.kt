package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tablayout.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.PillBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import kotlinx.coroutines.launch
import kotlin.math.max

data class PillMetaData(
    var text: String,
    var onClick: (() -> Unit),
    var icon: ImageVector? = null,
    var enabled: Boolean = true,
    var selected: Boolean = false
)

val LocalPillButtonTokens = compositionLocalOf { PillButtonTokens() }
val LocalPillButtonInfo = compositionLocalOf { PillButtonInfo() }
val LocalPillBarTokens = compositionLocalOf { PillBarTokens() }
val LocalPillBarInfo = compositionLocalOf { PillBarInfo() }

@Composable
fun PillButton(
    pillMetaData: PillMetaData,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    badge: (@Composable () -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    pillButtonTokens: PillButtonTokens? = null
) {
    val token = pillButtonTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PillButton] as PillButtonTokens

    CompositionLocalProvider(
        LocalPillButtonTokens provides token,
        LocalPillButtonInfo provides PillButtonInfo(
            style,
            pillMetaData.enabled,
            pillMetaData.selected
        )

    ) {
        val shape = RoundedCornerShape(50)
        val scaleBox = remember { Animatable(1.0F) }

        LaunchedEffect(key1 = pillMetaData.selected) {
            if (pillMetaData.selected) {
                launch {
                    scaleBox.animateTo(
                        targetValue = 0.95F,
                        animationSpec = tween(
                            durationMillis = 50
                        )
                    )
                    scaleBox.animateTo(
                        targetValue = 1.0F,
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioLowBouncy,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }

        val backgroundColor by animateColorAsState(
            targetValue = getPillButtonTokens().backgroundColor(pillButtonInfo = getPillButtonInfo())
                .getColorByState(
                    enabled = pillMetaData.enabled,
                    selected = pillMetaData.selected,
                    interactionSource = interactionSource
                ),
            animationSpec = tween(200)
        )
        val iconColor =
            getPillButtonTokens().iconColor(pillButtonInfo = getPillButtonInfo()).getColorByState(
                enabled = pillMetaData.enabled,
                selected = pillMetaData.selected,
                interactionSource = interactionSource
            )
        val textColor =
            getPillButtonTokens().textColor(pillButtonInfo = getPillButtonInfo()).getColorByState(
                enabled = pillMetaData.enabled,
                selected = pillMetaData.selected,
                interactionSource = interactionSource
            )

        val font = getPillButtonTokens().font(getPillButtonInfo())

        val focusStroke = getPillButtonTokens().focusStroke(getPillButtonInfo())
        var focusedBorderModifier: Modifier = Modifier
        for (borderStroke in focusStroke) {
            focusedBorderModifier =
                focusedBorderModifier.border(borderStroke, shape)
        }

        val clickAndSemanticsModifier = modifier.clickable(
            interactionSource = interactionSource,
            indication = rememberRipple(bounded = false, radius = 24.dp),
            enabled = pillMetaData.enabled,
            onClickLabel = null,
            role = Role.Button,
            onClick = pillMetaData.onClick
        )

        val selectedString = if (pillMetaData.selected)
            LocalContext.current.resources.getString(R.string.fluentui_selected)
        else
            LocalContext.current.resources.getString(R.string.fluentui_not_selected)

        val enabledString = if (pillMetaData.enabled)
            LocalContext.current.resources.getString(R.string.fluentui_enabled)
        else
            LocalContext.current.resources.getString(R.string.fluentui_disabled)

        Box(
            modifier
                .scale(scaleBox.value)
                .requiredHeight(32.dp)
                .clip(shape)
                .background(backgroundColor, shape)
                .then(clickAndSemanticsModifier)
                .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                .semantics(true) {
                    contentDescription =
                        "${pillMetaData.text} $selectedString $enabledString"
                },
            contentAlignment = Alignment.Center
        ) {
            Row {
                if (pillMetaData.icon != null) {
                    Icon(
                        pillMetaData.icon!!,
                        pillMetaData.text,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clearAndSetSemantics { },
                        tint = iconColor
                    )
                } else {
                    Text(
                        pillMetaData.text,
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 6.dp)
                            .clearAndSetSemantics { },
                        color = textColor,
                        fontSize = font.fontSize.size,
                        fontWeight = font.weight,
                        lineHeight = font.fontSize.lineHeight,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (badge != null) {
                    // TODO: Add logic to display badge
                    Surface(content = badge)
                }
            }
        }
    }
}

/**
 * API to create Bar of Pill button. The PillBar control is a linear set of two or more PillButton, each of which functions as a mutually exclusive button.
 * PillBar are commonly used as filter for search results.
 *
 * @param metadataList
 * @param modifier
 * @param style
 * @param showBackground
 * @param pillButtonTokens
 * @param pillBarTokens
 */
@Composable
fun PillBar(
    metadataList: MutableList<PillMetaData>,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    showBackground: Boolean = false,
    pillButtonTokens: PillButtonTokens? = null,
    pillBarTokens: PillBarTokens? = null
) {
    if (metadataList.size == 0)
        return

    val token = pillBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PillBar] as PillBarTokens

    CompositionLocalProvider(
        LocalPillBarTokens provides token,
        LocalPillBarInfo provides PillBarInfo(style)
    ) {
        val lazyListState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        LazyRow(
            modifier = modifier
                .background(if (showBackground) getPillBarTokens().background(getPillBarInfo()) else Color.Unspecified)
                .focusable(enabled = false),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            state = lazyListState
        ) {
            metadataList.forEachIndexed { index, pillMetadata ->
                item(index.toString()) {
                    PillButton(
                        pillMetadata,
                        modifier = Modifier.onFocusEvent { focusState ->
                            if (focusState.isFocused) {
                                scope.launch {
                                    lazyListState.animateScrollToItem(
                                        max(0, index - 2)
                                    )
                                }
                            }
                        }, style = style, pillButtonTokens = pillButtonTokens
                    )
                }
            }
        }
    }
}

@Composable
fun getPillButtonTokens(): PillButtonTokens {
    return LocalPillButtonTokens.current
}

@Composable
fun getPillButtonInfo(): PillButtonInfo {
    return LocalPillButtonInfo.current
}

@Composable
fun getPillBarTokens(): PillBarTokens {
    return LocalPillBarTokens.current
}

@Composable
fun getPillBarInfo(): PillBarInfo {
    return LocalPillBarInfo.current
}
