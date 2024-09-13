package com.microsoft.fluentui.tokenized.segmentedcontrols

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.stateDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tablayout.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.PillBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import com.microsoft.fluentui.util.dpToPx
import kotlinx.coroutines.launch
import kotlin.math.max

/**
 * Pill Meta Data defines meta data for  a pill.
 * @param text: the text that's displayed on the Pill
 * @param onClick: onClick callback for defining the click action on the pill
 * @param icon: icon that's displayed on the Pill.
 * @param enabled: to make pill disabled or enabled for click interactions
 * @param notificationDot: boolean which defines whether to show a notification dot or not on the pill.
 * @param calloutSelectionState: boolean which let's the user define if "selected" or "not selected" state of pill must be announced for the accessibility purposes
 * @param semanticContentName: Pill name that must be announced for accessibility purposes, if it's null then @param text is used for accessibility announcement
 */
data class PillMetaData(
    var text: String? = null,
    var onClick: (() -> Unit),
    var icon: ImageVector? = null,
    var enabled: Boolean = true,
    var selected: Boolean = false,
    var notificationDot: Boolean = false,
    var calloutSelectionState: Boolean = true,
    var semanticContentName: String? = null,
)

/**
 * API to create Pill shaped Button which will further be used in tabs and bars.
 *
 * @param pillMetaData Metadata for a single pill. Type: [PillMetaData]
 * @param modifier Optional Modifier to customize the design and behaviour of pill button
 * @param style Color Scheme of pill shaped button. Default: [FluentStyle.Neutral]
 * @param interactionSource Interaction Source Object to handle gestures.
 * @param pillButtonTokens Tokens to customize the design of pill button.
 */
@Composable
fun PillButton(
    pillMetaData: PillMetaData,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    pillButtonTokens: PillButtonTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = pillButtonTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PillButtonControlType] as PillButtonTokens
    val pillButtonInfo = PillButtonInfo(
        style,
        pillMetaData.enabled,
        pillMetaData.selected
    )
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

    val backgroundColor = token.backgroundBrush(pillButtonInfo = pillButtonInfo)
        .getBrushByState(
            enabled = pillMetaData.enabled,
            selected = pillMetaData.selected,
            interactionSource = interactionSource
        )
    val iconColor =
        token.iconColor(pillButtonInfo = pillButtonInfo).getColorByState(
            enabled = pillMetaData.enabled,
            selected = pillMetaData.selected,
            interactionSource = interactionSource
        )
    val textColor =
        token.textColor(pillButtonInfo = pillButtonInfo).getColorByState(
            enabled = pillMetaData.enabled,
            selected = pillMetaData.selected,
            interactionSource = interactionSource
        )

    val fontStyle = token.typography(pillButtonInfo)

    val focusStroke = token.focusStroke(pillButtonInfo)
    var focusedBorderModifier: Modifier = Modifier
    for (borderStroke in focusStroke) {
        focusedBorderModifier =
            focusedBorderModifier.border(borderStroke, shape)
    }

    val clickAndSemanticsModifier = modifier.clickable(
        interactionSource = interactionSource,
        indication = rememberRipple(),
        enabled = pillMetaData.enabled,
        onClickLabel = null,
        role = Role.Button,
        onClick = pillMetaData.onClick
    )

    val selectedString = if(!pillMetaData.calloutSelectionState)  ""
    else if (pillMetaData.selected)
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
            .defaultMinSize(minHeight = token.minHeight(pillButtonInfo))
            .clip(shape)
            .background(backgroundColor, shape)
            .then(clickAndSemanticsModifier)
            .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
            .padding(vertical = token.verticalPadding(pillButtonInfo))
            .semantics(true) {
                contentDescription =
                    if (pillMetaData.enabled) "${pillMetaData.semanticContentName ?: pillMetaData.text ?:  ""} $selectedString"
                    else "${pillMetaData.semanticContentName ?: pillMetaData.text ?: ""} $enabledString"
            },
        contentAlignment = Alignment.Center
    ) {
        Row(Modifier.width(IntrinsicSize.Max)) {
            Spacer(Modifier.requiredWidth(token.horizontalMargin(pillButtonInfo = pillButtonInfo)))
            if (pillMetaData.icon != null) {
                Spacer(Modifier.requiredWidth(token.iconSpace(pillButtonInfo = pillButtonInfo)))
                Icon(
                    pillMetaData.icon!!,
                    pillMetaData.text,
                    modifier = Modifier
                        .size(token.iconSize(pillButtonInfo))
                        .clearAndSetSemantics { },
                    tint = iconColor
                )
                if(pillMetaData.text != null){
                    Spacer(Modifier.requiredWidth(token.iconSpace(pillButtonInfo = pillButtonInfo)))
                }
            }
            if(pillMetaData.text != null){

                BasicText(
                    pillMetaData.text!!,
                    modifier = Modifier
                        .weight(1F)
                        .clearAndSetSemantics { },
                    style = fontStyle.merge(
                        TextStyle(color = textColor)
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            if (pillMetaData.notificationDot) {
                val notificationDotColor: Color =
                    token.notificationDotColor(pillButtonInfo)
                        .getColorByState(
                            enabled = pillMetaData.enabled,
                            selected = pillMetaData.selected,
                            interactionSource = interactionSource
                        )
                Spacer(Modifier.requiredWidth(FluentGlobalTokens.SizeTokens.Size20.value))
                Canvas(
                    modifier = Modifier
                        .padding(top = 2.dp, bottom = 12.dp)
                        .sizeIn(minWidth = 6.dp, minHeight = 6.dp)
                ) {
                    drawCircle(
                        color = notificationDotColor, style = Fill, radius = dpToPx(3.dp)
                    )
                }
                if (pillMetaData.icon != null)
                    Spacer(Modifier.requiredWidth(FluentGlobalTokens.SizeTokens.Size100.value))
                else
                    Spacer(Modifier.requiredWidth(FluentGlobalTokens.SizeTokens.Size80.value))
            } else {
                Spacer(Modifier.requiredWidth(token.horizontalMargin(pillButtonInfo = pillButtonInfo)))
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
 * @param pillAlignment
 * @param pillButtonTokens
 * @param pillBarTokens
 */
@Composable
fun PillBar(
    metadataList: MutableList<PillMetaData>,
    modifier: Modifier = Modifier,
    style: FluentStyle = FluentStyle.Neutral,
    showBackground: Boolean = false,
    pillAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    pillButtonTokens: PillButtonTokens? = null,
    pillBarTokens: PillBarTokens? = null
) {
    if (metadataList.size == 0)
        return

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = pillBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.PillBarControlType] as PillBarTokens

    val pillBarInfo = PillBarInfo(style)
    val padding = token.padding(pillBarInfo = pillBarInfo)
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val positionString: String = LocalContext.current.resources.getString(R.string.position_string)
    LazyRow(
        modifier = modifier
            .fillMaxWidth()
            .background(if (showBackground) token.backgroundBrush(pillBarInfo) else SolidColor(Color.Unspecified))
            .focusable(enabled = false),
        contentPadding = PaddingValues(padding),
        horizontalArrangement = Arrangement.spacedBy(8.dp, pillAlignment),
        state = lazyListState
    ) {
        metadataList.forEachIndexed { index, pillMetadata ->
            item(index.toString()) {
                PillButton(
                    pillMetadata,
                    modifier = Modifier
                        .onFocusEvent { focusState ->
                            if (focusState.isFocused) {
                                scope.launch {
                                    lazyListState.animateScrollToItem(
                                        max(0, index - 2)
                                    )
                                }
                            }
                        }
                        .semantics(mergeDescendants = true) {
                            stateDescription =
                                if (metadataList.size > 1) positionString.format(
                                    index + 1,
                                    metadataList.size
                                ) else ""
                        },
                    style = style, pillButtonTokens = pillButtonTokens
                )
            }
        }
    }
}
