package com.microsoft.fluentui.tokenized.contextualcommandbar

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.microsoft.fluentui.ccb.R
import com.microsoft.fluentui.icons.CCBIcons
import com.microsoft.fluentui.icons.ccbicons.Keyboarddismiss
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.ContextualCommandBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.ContextualCommandBarTokens
import kotlinx.coroutines.launch
import kotlin.math.max

enum class ActionButtonPosition {
    None,
    Start,
    End
}

/**
 * Generate a Contextual Command Bar. This is a horizontally scrollable bar which is made up of [CommandGroup].
 * Command group internally is a group of [CommandItem] which can be used to create onClick and onLongClick based buttons.
 * It also consist of an optional icon based action button which can be placed at either side of the CCB.
 * If enabled, by default this button acts as a Keyboard Dismiss button.
 *
 * @param groups List of Groups to be created in a context
 * @param modifier Optional Modifier for CCB
 * @param actionButtonPosition Enum to specify if we will have an Action Button and its position
 * @param actionButtonIcon FluentIcon for The Action Button Icon
 * @param scrollable Boolean value to specify if CCB has fixed or infinite width(Scrollable).
 *                      Use false to create a fixed non scrollable CCB. Command groups widths will adhere to the weights set in [CommandGroup] weight parameter.
 *                      Use true to have a scrollable CCB. [CommandGroup] weight parameter is ignored
 * @param selectionStroke List of BorderStroke to be applied on selected button in CCB
 * @param contextualCommandBarToken Token to provide appearance values to Avatar
 */
@OptIn(
    //Used for:
    //     Combined Clickable            Soft Keyboard Controller
    ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class
)
@Composable
fun ContextualCommandBar(
    groups: List<CommandGroup>,
    modifier: Modifier = Modifier,
    actionButtonPosition: ActionButtonPosition = ActionButtonPosition.End,
    actionButtonIcon: FluentIcon = FluentIcon(
        CCBIcons.Keyboarddismiss,
        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_dismiss)
    ),
    scrollable: Boolean = true,
    selectionStroke: List<BorderStroke>? = null,
    contextualCommandBarToken: ContextualCommandBarTokens? = null
) {

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = contextualCommandBarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ContextualCommandBarControlType] as ContextualCommandBarTokens

    val contextualCommandBarInfo = ContextualCommandBarInfo()
    val groupBorderRadius =
        token.groupBorderRadius(contextualCommandBarInfo)
    val itemBorderRadius =
        token.itemBorderRadius(contextualCommandBarInfo)

    val soloItemShape = RoundedCornerShape(groupBorderRadius)
    val startShape = RoundedCornerShape(
        topStart = groupBorderRadius,
        bottomStart = groupBorderRadius,
        topEnd = itemBorderRadius,
        bottomEnd = itemBorderRadius
    )
    val defaultShape = RoundedCornerShape(itemBorderRadius)
    val endShape = RoundedCornerShape(
        topEnd = groupBorderRadius,
        bottomEnd = groupBorderRadius,
        topStart = itemBorderRadius,
        bottomStart = itemBorderRadius
    )
    val focusStroke = token.focusStroke(
        contextualCommandBarInfo
    )
    val showSelectionBorderStroke = selectionStroke != null
    var focusedBorderModifier: Modifier = Modifier
    var selectedBorderModifier: Modifier = Modifier

    val selectedString = LocalContext.current.resources.getString(R.string.fluentui_selected)

    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    ConstraintLayout(
        modifier = modifier
            .focusable(enabled = false)
            .fillMaxWidth()
            .background(
                token.contextualCommandBarBackgroundBrush(
                    contextualCommandBarInfo
                )
            )
    ) {
        val (KeyboardDismiss, Content) = createRefs()
        val contentPaddingWithActionButton =
            -(token.actionButtonGradientWidth(
                contextualCommandBarInfo
            ) + token.buttonPadding(contextualCommandBarInfo))
        if (scrollable) {
            LazyRow(modifier = Modifier
                .focusable(enabled = false)
                .constrainAs(Content) {
                    when (actionButtonPosition) {
                        ActionButtonPosition.Start -> {
                            start.linkTo(
                                KeyboardDismiss.end,
                                margin = contentPaddingWithActionButton
                            )
                            end.linkTo(parent.end)
                        }

                        ActionButtonPosition.End -> {
                            start.linkTo(parent.start)
                            end.linkTo(
                                KeyboardDismiss.start,
                                margin = contentPaddingWithActionButton
                            )
                        }

                        ActionButtonPosition.None -> {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    }
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .padding(
                    token.buttonPadding(
                        contextualCommandBarInfo
                    )
                ), state = lazyListState) {

                var itemKey = 0
                for ((index, commandGroup) in groups.withIndex()) {
                    for ((itemIndex, item) in commandGroup.items.withIndex()) {
                        val key = itemKey.toString()
                        item(key) {
                            val shape = if (commandGroup.items.size == 1) soloItemShape
                            else if (item == commandGroup.items.first()) startShape
                            else if (item == commandGroup.items.last()) endShape
                            else defaultShape

                            val interactionSource: MutableInteractionSource =
                                remember { MutableInteractionSource() }
                            val clickableModifier = Modifier.combinedClickable(
                                enabled = item.enabled,
                                onClick = item.onClick,
                                onClickLabel = null,
                                onLongClick = item.onLongClick,
                                role = Role.Button,
                                interactionSource = interactionSource,
                                indication = rememberRipple()
                            )

                            focusedBorderModifier = Modifier
                            for (borderStroke in focusStroke) {
                                focusedBorderModifier =
                                    focusedBorderModifier.border(borderStroke, shape)
                            }
                            selectedBorderModifier = Modifier
                            if(showSelectionBorderStroke){
                                for (borderStroke in selectionStroke!!) {
                                    selectedBorderModifier =
                                        selectedBorderModifier.border(borderStroke, shape)
                                }
                            }

                            Row(
                                modifier = Modifier.height(IntrinsicSize.Min),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    Modifier
                                        .onFocusEvent { focusState ->
                                            if (focusState.isFocused) {
                                                scope.launch {
                                                    lazyListState.animateScrollToItem(
                                                        max(
                                                            0, key.toInt() - 2
                                                        )
                                                    )
                                                }
                                            }
                                        }
                                        .defaultMinSize(
                                            minWidth = token.buttonMinWidth(
                                                contextualCommandBarInfo
                                            )
                                        )
                                        .height(IntrinsicSize.Min)
                                        .clip(shape)
                                        .background(
                                            token
                                                .buttonBackgroundBrush(
                                                    contextualCommandBarInfo
                                                )
                                                .getBrushByState(
                                                    enabled = item.enabled,
                                                    selected = item.selected,
                                                    interactionSource = interactionSource
                                                ), shape = shape
                                        )
                                        .then(clickableModifier)
                                        .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                                        .semantics {
                                            contentDescription =
                                                item.label +
                                                        if (item.selected)
                                                            selectedString
                                                        else ""
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    CommandItemComposable(
                                        token,
                                        contextualCommandBarInfo,
                                        item,
                                        interactionSource,
                                        commandGroup
                                    )
                                }
                                if (itemIndex != commandGroup.items.size - 1) {
                                    Spacer(
                                        Modifier
                                            .requiredWidth(
                                                token.buttonSpacing(
                                                    contextualCommandBarInfo
                                                )
                                            )
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                    )
                                } else if (index != groups.size - 1) {
                                    Spacer(
                                        Modifier
                                            .requiredWidth(
                                                token.groupSpacing(
                                                    contextualCommandBarInfo
                                                )
                                            )
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                    )
                                }
                            }

                        }
                        itemKey += 1
                    }
                }
            }
        } else {
            Row(modifier = Modifier
                .constrainAs(Content) {
                    when (actionButtonPosition) {
                        ActionButtonPosition.Start -> {
                            start.linkTo(
                                KeyboardDismiss.end,
                                margin = contentPaddingWithActionButton
                            )
                            end.linkTo(parent.end)
                        }

                        ActionButtonPosition.End -> {
                            start.linkTo(parent.start)
                            end.linkTo(
                                KeyboardDismiss.start,
                                margin = contentPaddingWithActionButton
                            )
                        }

                        ActionButtonPosition.None -> {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    }
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .fillMaxWidth()
                .padding(
                    token.buttonPadding(
                        contextualCommandBarInfo
                    )
                )) {
                for ((index, commandGroup) in groups.withIndex()) {
                    for ((itemIndex, item) in commandGroup.items.withIndex()) {
                        val shape = if (commandGroup.items.size == 1) soloItemShape
                        else if (item == commandGroup.items.first()) startShape
                        else if (item == commandGroup.items.last()) endShape
                        else defaultShape

                        val interactionSource: MutableInteractionSource =
                            remember { MutableInteractionSource() }
                        val clickableModifier = Modifier.combinedClickable(
                            enabled = item.enabled,
                            onClick = item.onClick,
                            onClickLabel = null,
                            onLongClick = item.onLongClick,
                            role = Role.Button,
                            interactionSource = interactionSource,
                            indication = rememberRipple()
                        )

                        focusedBorderModifier = Modifier
                        for (borderStroke in focusStroke) {
                            focusedBorderModifier =
                                focusedBorderModifier.border(borderStroke, shape)
                        }
                        selectedBorderModifier = Modifier
                        if(showSelectionBorderStroke){
                            for (borderStroke in selectionStroke!!) {
                                selectedBorderModifier =
                                    selectedBorderModifier.border(borderStroke, shape)
                            }
                        }
                        Row(
                            modifier = Modifier
                                .height(IntrinsicSize.Min)
                                .weight(commandGroup.weight),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                Modifier
                                    .height(IntrinsicSize.Min)
                                    .fillMaxWidth()
                                    .clip(shape)
                                    .background(
                                        token
                                            .buttonBackgroundBrush(
                                                contextualCommandBarInfo
                                            )
                                            .getBrushByState(
                                                enabled = item.enabled,
                                                selected = item.selected,
                                                interactionSource = interactionSource
                                            ), shape = shape
                                    )
                                    .then(clickableModifier)
                                    .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                                    .then(
                                        if (commandGroup.items[itemIndex].selected)
                                            selectedBorderModifier
                                        else Modifier
                                    )
                                    .semantics {
                                        contentDescription =
                                            item.label + if (item.selected) selectedString else ""
                                    }, contentAlignment = Alignment.Center
                            ) {
                                CommandItemComposable(
                                    token,
                                    contextualCommandBarInfo,
                                    item = item,
                                    interactionSource = interactionSource,
                                    commandGroup = commandGroup
                                )
                            }

                        }
                        if (itemIndex != commandGroup.items.size - 1) {
                            Spacer(
                                Modifier
                                    .requiredWidth(
                                        token.buttonSpacing(
                                            contextualCommandBarInfo
                                        )
                                    )
                                    .background(Color.Transparent)
                            )
                        } else if (index != groups.size - 1) {
                            Spacer(
                                Modifier
                                    .requiredWidth(
                                        token.groupSpacing(
                                            contextualCommandBarInfo
                                        )
                                    )
                                    .background(Color.Transparent)
                            )
                        }
                    }
                }
            }
        }
        if (actionButtonPosition != ActionButtonPosition.None) {
            val keyboardController = LocalSoftwareKeyboardController.current
            val keyboardDismiss: (() -> Unit) = { keyboardController?.hide() }
            val actionButtonClickable = Modifier.clickable(enabled = true,
                onClick = actionButtonIcon.onClick ?: keyboardDismiss,
                role = Role.Button,
                onClickLabel = actionButtonIcon.contentDescription,
                indication = rememberRipple(),
                interactionSource = remember { MutableInteractionSource() })

            val isRtl: Boolean = LocalLayoutDirection.current == LayoutDirection.Rtl

            Row(
                Modifier
                    .height(IntrinsicSize.Min)
                    .constrainAs(KeyboardDismiss) {
                        if (actionButtonPosition == ActionButtonPosition.Start) {
                            start.linkTo(parent.start)
                        } else {
                            end.linkTo(parent.end)
                        }
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }, verticalAlignment = Alignment.CenterVertically
            ) {
                if (actionButtonPosition == ActionButtonPosition.End)
                    Spacer(
                        modifier = Modifier
                            .requiredWidth(
                                token.actionButtonGradientWidth(
                                    contextualCommandBarInfo
                                )
                            )
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    token.actionButtonGradient(
                                        contextualCommandBarInfo
                                    ),
                                    startX = if (!isRtl) 0.0F else Float.POSITIVE_INFINITY,
                                    endX = if (!isRtl) Float.POSITIVE_INFINITY else 0.0F
                                )
                            )
                    )
                Icon(
                    actionButtonIcon,
                    modifier = Modifier
                        .then(actionButtonClickable)
                        .background(
                            token.actionButtonBackgroundBrush(
                                contextualCommandBarInfo
                            )
                        )
                        .padding(
                            token.actionButtonIconPadding(
                                contextualCommandBarInfo
                            )
                        ),
                    tint = token.actionButtonIconColor(
                        contextualCommandBarInfo
                    )
                )
                if (actionButtonPosition == ActionButtonPosition.Start)
                    Spacer(
                        modifier = Modifier
                            .requiredWidth(
                                token.actionButtonGradientWidth(
                                    contextualCommandBarInfo
                                )
                            )
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    token.actionButtonGradient(
                                        contextualCommandBarInfo
                                    ),
                                    startX = if (!isRtl) Float.POSITIVE_INFINITY else 0F,
                                    endX = if (!isRtl) 0F else Float.POSITIVE_INFINITY
                                )
                            )
                    )
            }
        }
    }
}

@Composable
private fun CommandItemComposable(
    token: ContextualCommandBarTokens,
    contextualCommandBarInfo: ContextualCommandBarInfo,
    item: CommandItem,
    interactionSource: MutableInteractionSource,
    commandGroup: CommandGroup
) {
    val foregroundColor = token.iconColor(
        contextualCommandBarInfo
    ).getColorByState(
        enabled = item.enabled, selected = item.selected, interactionSource = interactionSource
    )

    val contentPadding: PaddingValues = if (commandGroup.items.size == 1) {
        PaddingValues(
            top = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), bottom = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), start = token.groupIconHorizontalPadding(
                contextualCommandBarInfo
            ), end = token.groupIconHorizontalPadding(
                contextualCommandBarInfo
            )
        )
    } else if (item == commandGroup.items.first()) {
        PaddingValues(
            top = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), bottom = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), start = token.groupIconHorizontalPadding(
                contextualCommandBarInfo
            ), end = token.itemIconHorizontalPadding(
                contextualCommandBarInfo
            )
        )
    } else if (item == commandGroup.items.last()) {
        PaddingValues(
            top = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), bottom = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), start = token.itemIconHorizontalPadding(
                contextualCommandBarInfo
            ), end = token.groupIconHorizontalPadding(
                contextualCommandBarInfo
            )
        )
    } else {
        PaddingValues(
            top = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), bottom = token.iconVerticalPadding(
                contextualCommandBarInfo
            ), start = token.itemIconHorizontalPadding(
                contextualCommandBarInfo
            ), end = token.itemIconHorizontalPadding(
                contextualCommandBarInfo
            )
        )
    }
    if (item.icon != null) {
        Icon(
            item.icon,
            Modifier
                .padding(contentPadding)
                .requiredSize(
                    token.iconSize(
                        contextualCommandBarInfo
                    )
                ), tint = foregroundColor
        )
    } else {
        val fontTypography = token.typography(
            contextualCommandBarInfo
        )
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .requiredHeight(
                    token.iconSize(
                        contextualCommandBarInfo
                    )
                ), contentAlignment = Alignment.Center
        ) {
            BasicText(
                item.label,
                modifier = Modifier.clearAndSetSemantics { },
                style = fontTypography.merge(
                    TextStyle(color = foregroundColor)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

data class CommandGroup(
    val groupName: String,
    val items: List<CommandItem>,
    val weight: Float = 1f
)

data class CommandItem(
    val label: String,
    val onClick: (() -> Unit),
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val icon: FluentIcon? = null,
    val onLongClick: (() -> Unit)? = null
)