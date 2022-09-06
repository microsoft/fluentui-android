package com.microsoft.fluentui.tokenized.contextualcommandbar

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.microsoft.fluentui.icons.CCBIcons
import com.microsoft.fluentui.icons.ccbicons.Keyboarddismiss
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.ContextualCommandBarTokens
import kotlinx.coroutines.launch
import kotlin.math.max

val LocalContextualCommandBarTokens = compositionLocalOf { ContextualCommandBarTokens() }

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
 * @param actionButtonIcon ImageVector for The Action Button Icon
 * @param actionButtonOnClick OnCLick Functionality for the Action Button
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
    actionButtonIcon: ImageVector = CCBIcons.Keyboarddismiss,
    actionButtonOnClick: (() -> Unit)? = null,
    contextualCommandBarToken: ContextualCommandBarTokens? = null
) {

    val token = contextualCommandBarToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ContextualCommandBar] as ContextualCommandBarTokens

    CompositionLocalProvider(LocalContextualCommandBarTokens provides token) {
        val groupBorderRadius = getContextualCommandBarTokens().groupBorderRadius()
        val itemBorderRadius = getContextualCommandBarTokens().itemBorderRadius()

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
                .background(getContextualCommandBarTokens().contextualCommandBarBackgroundColor())
        ) {
            val (KeyboardDismiss, Content) = createRefs()

            val contentPaddingWithKD =
                -(getContextualCommandBarTokens().actionButtonGradientWidth() + getContextualCommandBarTokens().buttonPadding())

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
                .padding(getContextualCommandBarTokens().buttonPadding()),
                state = lazyListState) {

                var itemIndex = 0
                for (commandGroup in groups) {
                    for (item in commandGroup.items) {
                        val key = itemIndex.toString()
                        item(key) {
                            val shape = if (commandGroup.items.size == 1)
                                soloItemShape
                            else if (item == commandGroup.items.first())
                                startShape
                            else if (item == commandGroup.items.last())
                                endShape
                            else
                                defaultShape

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

                            val focusStroke = getContextualCommandBarTokens().focusStroke()
                            var focusedBorderModifier: Modifier = Modifier
                            for (borderStroke in focusStroke) {
                                focusedBorderModifier =
                                    focusedBorderModifier.border(borderStroke, shape)
                            }

                            Row(
                                modifier = Modifier.height(IntrinsicSize.Min),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Spacing before first element if KD is at start.
                                if (actionButtonPosition == ActionButtonPosition.Start &&
                                    item == commandGroup.items.first() && commandGroup == groups.first()
                                )
                                    Spacer(
                                        Modifier
                                            .requiredWidth(12.dp)
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                    )

                                Box(Modifier
                                    .onFocusEvent { focusState ->
                                        if (focusState.isFocused) {
                                            scope.launch {
                                                lazyListState.animateScrollToItem(
                                                    max(
                                                        0,
                                                        key.toInt() - 2
                                                    )
                                                )
                                            }
                                        }
                                    }
                                    .defaultMinSize(minWidth = getContextualCommandBarTokens().buttonMinWidth())
                                    .height(IntrinsicSize.Min)
                                    .clip(shape)
                                    .background(
                                        getColorByState(
                                            stateData = getContextualCommandBarTokens().buttonBackgroundColor(),
                                            enabled = item.enabled,
                                            selected = item.selected,
                                            interactionSource = interactionSource
                                        ),
                                        shape = shape
                                    )
                                    .then(clickableModifier)
                                    .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                                    .semantics {
                                        contentDescription =
                                            item.label + if (item.selected) "Selected" else ""
                                    },
                                    contentAlignment = Alignment.Center
                                ) {
                                    val foregroundColor = getColorByState(
                                        stateData = getContextualCommandBarTokens().iconColor(),
                                        enabled = item.enabled,
                                        selected = item.selected,
                                        interactionSource = interactionSource
                                    )

                                    val contentPadding: PaddingValues =
                                        if (commandGroup.items.size == 1)
                                            PaddingValues(
                                                top = getContextualCommandBarTokens().iconVerticalPadding(),
                                                bottom = getContextualCommandBarTokens().iconVerticalPadding(),
                                                start = getContextualCommandBarTokens().groupIconHorizontalPadding(),
                                                end = getContextualCommandBarTokens().groupIconHorizontalPadding()
                                            )
                                        else if (item == commandGroup.items.first())
                                            PaddingValues(
                                                top = getContextualCommandBarTokens().iconVerticalPadding(),
                                                bottom = getContextualCommandBarTokens().iconVerticalPadding(),
                                                start = getContextualCommandBarTokens().groupIconHorizontalPadding(),
                                                end = getContextualCommandBarTokens().itemIconHorizontalPadding()
                                            )
                                        else if (item == commandGroup.items.last())
                                            PaddingValues(
                                                top = getContextualCommandBarTokens().iconVerticalPadding(),
                                                bottom = getContextualCommandBarTokens().iconVerticalPadding(),
                                                start = getContextualCommandBarTokens().itemIconHorizontalPadding(),
                                                end = getContextualCommandBarTokens().groupIconHorizontalPadding()
                                            )
                                        else
                                            PaddingValues(
                                                top = getContextualCommandBarTokens().iconVerticalPadding(),
                                                bottom = getContextualCommandBarTokens().iconVerticalPadding(),
                                                start = getContextualCommandBarTokens().itemIconHorizontalPadding(),
                                                end = getContextualCommandBarTokens().itemIconHorizontalPadding()
                                            )

                                    if (item.icon != null)
                                        Icon(
                                            item.icon,
                                            null,
                                            Modifier
                                                .padding(contentPadding)
                                                .requiredSize(getContextualCommandBarTokens().iconSize().size),
                                            tint = foregroundColor
                                        )
                                    else {
                                        val fontInfo = getContextualCommandBarTokens().textSize()
                                        Box(
                                            modifier = Modifier
                                                .padding(contentPadding)
                                                .requiredHeight(getContextualCommandBarTokens().iconSize().size),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                item.label,
                                                modifier = Modifier
                                                    .clearAndSetSemantics { },
                                                fontSize = fontInfo.fontSize.size,
                                                lineHeight = fontInfo.fontSize.lineHeight,
                                                fontWeight = fontInfo.weight,
                                                color = foregroundColor,
                                                overflow = TextOverflow.Ellipsis,
                                            )
                                        }
                                    }
                                }
                                if (item != commandGroup.items.last())
                                    Spacer(
                                        Modifier
                                            .requiredWidth(getContextualCommandBarTokens().buttonSpacing())
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                    )
                                else if (commandGroup != groups.last())
                                    Spacer(
                                        Modifier
                                            .requiredWidth(getContextualCommandBarTokens().groupSpacing())
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                    )

                                // Spacing after last element if KD is at end.
                                if (actionButtonPosition == ActionButtonPosition.End &&
                                    item == commandGroup.items.last() && commandGroup == groups.last()
                                )
                                    Spacer(
                                        Modifier
                                            .requiredWidth(12.dp)
                                            .fillMaxHeight()
                                            .background(Color.Transparent)
                                    )
                            }
                        }
                        itemIndex += 1
                    }
                }
            }

            if (actionButtonPosition != ActionButtonPosition.None) {
                val keyboardController = LocalSoftwareKeyboardController.current
                val keyboardDismiss: (() -> Unit) = { keyboardController?.hide() }
                val actionButtonClickable = Modifier.clickable(
                    enabled = true,
                    onClick = actionButtonOnClick ?: keyboardDismiss,
                    role = Role.Button,
                    onClickLabel = "Keyboard Dismiss",
                    indication = rememberRipple(),
                    interactionSource = remember { MutableInteractionSource() }
                )

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
                                .requiredWidth(getContextualCommandBarTokens().actionButtonGradientWidth())
                                .fillMaxHeight()
                                .background(
                                    Brush.horizontalGradient(
                                        getContextualCommandBarTokens().actionButtonGradient(),
                                        startX = 0.0F,
                                        endX = Float.POSITIVE_INFINITY
                                    )
                                )
                        )
                    Icon(
                        imageVector = actionButtonIcon,
                        contentDescription = "Dismiss",
                        modifier = Modifier
                            .then(actionButtonClickable)
                            .fillMaxHeight()
                            .background(getContextualCommandBarTokens().actionButtonBackgroundColor())
                            .padding(getContextualCommandBarTokens().actionButtonIconPadding()),
                        tint = getContextualCommandBarTokens().actionButtonIconColor()
                    )
                    if (actionButtonPosition == ActionButtonPosition.Start)
                        Spacer(
                            modifier = Modifier
                                .requiredWidth(getContextualCommandBarTokens().actionButtonGradientWidth())
                                .fillMaxHeight()
                                .background(
                                    Brush.horizontalGradient(
                                        getContextualCommandBarTokens().actionButtonGradient(),
                                        startX = Float.POSITIVE_INFINITY,
                                        endX = 0.0F
                                    )
                                )
                        )
                }
            }
        }
    }
}

@Composable
fun getContextualCommandBarTokens(): ContextualCommandBarTokens {
    return LocalContextualCommandBarTokens.current
}

data class CommandGroup(
    val groupName: String,
    val items: List<CommandItem>
)

data class CommandItem(
    val label: String,
    val onClick: (() -> Unit),
    val enabled: Boolean = true,
    val selected: Boolean = false,
    val icon: ImageVector? = null,
    val onLongClick: (() -> Unit)? = null
)

@Composable
fun getColorByState(
    stateData: StateColor,
    enabled: Boolean,
    selected: Boolean,
    interactionSource: InteractionSource
): Color {
    if (enabled) {
        if (selected)
            return stateData.selected

        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return stateData.pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (selected && isFocused)
            return stateData.selectedFocused
        else if (isFocused)
            return stateData.focused

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return stateData.focused

        return stateData.rest
    } else
        return stateData.disabled
}