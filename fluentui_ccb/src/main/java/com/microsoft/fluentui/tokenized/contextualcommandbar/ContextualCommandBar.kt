package com.microsoft.fluentui.tokenized.contextualcommandbar

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
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

private val LocalContextualCommandBarTokens = compositionLocalOf { ContextualCommandBarTokens() }
private val LocalContextualCommandBarInfo = compositionLocalOf { ContextualCommandBarInfo() }

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
 * @param actionButtonOnClick OnCLick Functionality for the Action Button
 * @param scrollable Optional boolean to specify if CCB has fixed or infinite width(Scrollable).
 *                      Use false to create a fixed non scrollable CCB. Command groups widths will adhere to the weights set in [CommandGroup] weight parameter.
 *                      Use true to have a scrollable CCB. [CommandGroup] weight parameter is ignored
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
        CCBIcons.Keyboarddismiss, contentDescription = "Dismiss"
    ),
    actionButtonOnClick: (() -> Unit)? = null,
    scrollable: Boolean = true,
    contextualCommandBarToken: ContextualCommandBarTokens? = null
) {

    val token = contextualCommandBarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ContextualCommandBar] as ContextualCommandBarTokens

    CompositionLocalProvider(
        LocalContextualCommandBarTokens provides token,
        LocalContextualCommandBarInfo provides ContextualCommandBarInfo()
    ) {
        val groupBorderRadius =
            getContextualCommandBarTokens().groupBorderRadius(getContextualCommandBarInfo())
        val itemBorderRadius =
            getContextualCommandBarTokens().itemBorderRadius(getContextualCommandBarInfo())

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

        val lazyListState = rememberLazyListState()
        val scope = rememberCoroutineScope()

        ConstraintLayout(
            modifier = modifier
                .focusable(enabled = false)
                .fillMaxWidth()
                .background(
                    getContextualCommandBarTokens().contextualCommandBarBackgroundColor(
                        getContextualCommandBarInfo()
                    )
                )
        ) {
            val (KeyboardDismiss, Content) = createRefs()
            val contentPaddingWithKD = -(getContextualCommandBarTokens().actionButtonGradientWidth(
                getContextualCommandBarInfo()
            ) + getContextualCommandBarTokens().buttonPadding(getContextualCommandBarInfo()))
            if (scrollable) {
                LazyRow(modifier = Modifier
                    .focusable(enabled = false)
                    .constrainAs(Content) {
                        when (actionButtonPosition) {
                            ActionButtonPosition.Start -> {
                                start.linkTo(KeyboardDismiss.end, margin = contentPaddingWithKD)
                                end.linkTo(parent.end)
                            }
                            ActionButtonPosition.End -> {
                                start.linkTo(parent.start)
                                end.linkTo(KeyboardDismiss.start, margin = contentPaddingWithKD)
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
                        getContextualCommandBarTokens().buttonPadding(
                            getContextualCommandBarInfo()
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

                                val focusStroke = getContextualCommandBarTokens().focusStroke(
                                    getContextualCommandBarInfo()
                                )
                                var focusedBorderModifier: Modifier = Modifier
                                for (borderStroke in focusStroke) {
                                    focusedBorderModifier =
                                        focusedBorderModifier.border(borderStroke, shape)
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
                                                minWidth = getContextualCommandBarTokens().buttonMinWidth(
                                                    getContextualCommandBarInfo()
                                                )
                                            )
                                            .height(IntrinsicSize.Min)
                                            .clip(shape)
                                            .background(
                                                getContextualCommandBarTokens()
                                                    .buttonBackgroundColor(
                                                        getContextualCommandBarInfo()
                                                    )
                                                    .getColorByState(
                                                        enabled = item.enabled,
                                                        selected = item.selected,
                                                        interactionSource = interactionSource
                                                    ), shape = shape
                                            )
                                            .then(clickableModifier)
                                            .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                                            .semantics {
                                                contentDescription =
                                                    item.label + if (item.selected) "Selected" else ""
                                            }, contentAlignment = Alignment.Center
                                    ) {
                                        CommandItemComposable(item, interactionSource, commandGroup)
                                    }
                                    if (itemIndex != commandGroup.items.size - 1) {
                                        Spacer(
                                            Modifier
                                                .requiredWidth(
                                                    getContextualCommandBarTokens().buttonSpacing(
                                                        getContextualCommandBarInfo()
                                                    )
                                                )
                                                .fillMaxHeight()
                                                .background(Color.Transparent)
                                        )
                                    } else if (index != groups.size - 1) {
                                        Spacer(
                                            Modifier
                                                .requiredWidth(
                                                    getContextualCommandBarTokens().groupSpacing(
                                                        getContextualCommandBarInfo()
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
                                start.linkTo(KeyboardDismiss.end, margin = contentPaddingWithKD)
                                end.linkTo(parent.end)
                            }
                            ActionButtonPosition.End -> {
                                start.linkTo(parent.start)
                                end.linkTo(KeyboardDismiss.start, margin = contentPaddingWithKD)
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
                        getContextualCommandBarTokens().buttonPadding(
                            getContextualCommandBarInfo()
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

                            val focusStroke = getContextualCommandBarTokens().focusStroke(
                                getContextualCommandBarInfo()
                            )
                            var focusedBorderModifier: Modifier = Modifier
                            for (borderStroke in focusStroke) {
                                focusedBorderModifier =
                                    focusedBorderModifier.border(borderStroke, shape)
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
                                            getContextualCommandBarTokens()
                                                .buttonBackgroundColor(
                                                    getContextualCommandBarInfo()
                                                )
                                                .getColorByState(
                                                    enabled = item.enabled,
                                                    selected = item.selected,
                                                    interactionSource = interactionSource
                                                ), shape = shape
                                        )
                                        .then(clickableModifier)
                                        .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                                        .semantics {
                                            contentDescription =
                                                item.label + if (item.selected) "Selected" else ""
                                        }, contentAlignment = Alignment.Center
                                ) {
                                    CommandItemComposable(
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
                                            getContextualCommandBarTokens().buttonSpacing(
                                                getContextualCommandBarInfo()
                                            )
                                        )
                                        .background(Color.Transparent)
                                )
                            } else if (index != groups.size - 1) {
                                Spacer(
                                    Modifier
                                        .requiredWidth(
                                            getContextualCommandBarTokens().groupSpacing(
                                                getContextualCommandBarInfo()
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
                    onClick = actionButtonOnClick ?: keyboardDismiss,
                    role = Role.Button,
                    onClickLabel = "Keyboard Dismiss",
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() })

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
                    if (actionButtonPosition == ActionButtonPosition.End) Spacer(
                        modifier = Modifier
                            .requiredWidth(
                                getContextualCommandBarTokens().actionButtonGradientWidth(
                                    getContextualCommandBarInfo()
                                )
                            )
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    getContextualCommandBarTokens().actionButtonGradient(
                                        getContextualCommandBarInfo()
                                    ), startX = 0.0F, endX = Float.POSITIVE_INFINITY
                                )
                            )
                    )
                    Icon(
                        actionButtonIcon,
                        modifier = Modifier
                            .then(actionButtonClickable)
                            .background(
                                getContextualCommandBarTokens().actionButtonBackgroundColor(
                                    getContextualCommandBarInfo()
                                )
                            )
                            .padding(
                                getContextualCommandBarTokens().actionButtonIconPadding(
                                    getContextualCommandBarInfo()
                                )
                            ),
                        tint = getContextualCommandBarTokens().actionButtonIconColor(
                            getContextualCommandBarInfo()
                        )
                    )
                    if (actionButtonPosition == ActionButtonPosition.Start) Spacer(
                        modifier = Modifier
                            .requiredWidth(
                                getContextualCommandBarTokens().actionButtonGradientWidth(
                                    getContextualCommandBarInfo()
                                )
                            )
                            .fillMaxHeight()
                            .background(
                                Brush.horizontalGradient(
                                    getContextualCommandBarTokens().actionButtonGradient(
                                        getContextualCommandBarInfo()
                                    ), startX = Float.POSITIVE_INFINITY, endX = 0.0F
                                )
                            )
                    )
                }
            }
        }
    }
}

@Composable
private fun CommandItemComposable(
    item: CommandItem, interactionSource: MutableInteractionSource, commandGroup: CommandGroup
) {
    val foregroundColor = getContextualCommandBarTokens().iconColor(
        getContextualCommandBarInfo()
    ).getColorByState(
        enabled = item.enabled, selected = item.selected, interactionSource = interactionSource
    )

    val contentPadding: PaddingValues = if (commandGroup.items.size == 1) {
        PaddingValues(
            top = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), bottom = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), start = getContextualCommandBarTokens().groupIconHorizontalPadding(
                getContextualCommandBarInfo()
            ), end = getContextualCommandBarTokens().groupIconHorizontalPadding(
                getContextualCommandBarInfo()
            )
        )
    } else if (item == commandGroup.items.first()) {
        PaddingValues(
            top = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), bottom = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), start = getContextualCommandBarTokens().groupIconHorizontalPadding(
                getContextualCommandBarInfo()
            ), end = getContextualCommandBarTokens().itemIconHorizontalPadding(
                getContextualCommandBarInfo()
            )
        )
    } else if (item == commandGroup.items.last()) {
        PaddingValues(
            top = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), bottom = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), start = getContextualCommandBarTokens().itemIconHorizontalPadding(
                getContextualCommandBarInfo()
            ), end = getContextualCommandBarTokens().groupIconHorizontalPadding(
                getContextualCommandBarInfo()
            )
        )
    } else {
        PaddingValues(
            top = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), bottom = getContextualCommandBarTokens().iconVerticalPadding(
                getContextualCommandBarInfo()
            ), start = getContextualCommandBarTokens().itemIconHorizontalPadding(
                getContextualCommandBarInfo()
            ), end = getContextualCommandBarTokens().itemIconHorizontalPadding(
                getContextualCommandBarInfo()
            )
        )
    }
    if (item.icon != null) {
        Icon(
            item.icon,
            Modifier
                .padding(contentPadding)
                .requiredSize(
                    getContextualCommandBarTokens().iconSize(
                        getContextualCommandBarInfo()
                    )
                ), tint = foregroundColor
        )
    } else {
        val fontInfo = getContextualCommandBarTokens().typography(
            getContextualCommandBarInfo()
        )
        Box(
            modifier = Modifier
                .padding(contentPadding)
                .requiredHeight(
                    getContextualCommandBarTokens().iconSize(
                        getContextualCommandBarInfo()
                    )
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                item.label,
                modifier = Modifier.clearAndSetSemantics { },
                fontSize = fontInfo.fontSize.size,
                lineHeight = fontInfo.fontSize.lineHeight,
                fontWeight = fontInfo.weight,
                color = foregroundColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}

@Composable
private fun getContextualCommandBarTokens(): ContextualCommandBarTokens {
    return LocalContextualCommandBarTokens.current
}

@Composable
private fun getContextualCommandBarInfo(): ContextualCommandBarInfo {
    return LocalContextualCommandBarInfo.current
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