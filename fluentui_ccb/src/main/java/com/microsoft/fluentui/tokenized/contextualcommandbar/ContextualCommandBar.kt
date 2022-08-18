package com.microsoft.fluentui.tokenized.contextualcommandbar

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalComposeUiApi::class)
@Composable
fun ContextualCommandBar(
    groups: List<CommandGroup>,
    modifier: Modifier = Modifier,
    keyboardDismissEnabled: Boolean = true,
    keyboardDismissAtStart: Boolean = false,
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
                .requiredHeight(getContextualCommandBarTokens().contextualCommandBarHeight())
                .fillMaxWidth()
                .background(getContextualCommandBarTokens().contextualCommandBarBackgroundColor())
        ) {
            val (KeyboardDismiss, Content) = createRefs()

            val contentPaddingWithKD =
                (getContextualCommandBarTokens().keyboardDismissGradientWidth() + 8.dp) * -1
            LazyRow(modifier = Modifier
                .focusable(enabled = false)
                .constrainAs(Content) {
                    if (keyboardDismissEnabled && keyboardDismissAtStart) {
                        start.linkTo(KeyboardDismiss.end, margin = contentPaddingWithKD)
                        end.linkTo(parent.end)
                    } else if (keyboardDismissEnabled && !keyboardDismissAtStart) {
                        start.linkTo(parent.start)
                        end.linkTo(KeyboardDismiss.start, margin = contentPaddingWithKD)
                    } else {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.fillToConstraints
                }
                .padding(8.dp),
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
                                onClickLabel = item.label,
                                onLongClick = item.onLongClick,
                                role = Role.Button,
                                interactionSource = interactionSource,
                                indication = null
                            )

                            val focusStroke = getContextualCommandBarTokens().focusStroke()
                            var focusedBorderModifier: Modifier = Modifier
                            for (borderStroke in focusStroke) {
                                focusedBorderModifier =
                                    focusedBorderModifier.border(borderStroke, shape)
                            }

                            Row(verticalAlignment = Alignment.CenterVertically) {
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
                                    .then(clickableModifier)
                                    .then(if (interactionSource.collectIsFocusedAsState().value || interactionSource.collectIsHoveredAsState().value) focusedBorderModifier else Modifier)
                                    .fillMaxHeight()
                                    .clip(shape)
                                    .background(
                                        getColorByState(
                                            stateData = getContextualCommandBarTokens().buttonBackgroundColor(),
                                            enabled = item.enabled,
                                            selected = item.selected,
                                            interactionSource = interactionSource
                                        ),
                                        shape = shape
                                    ),
                                    contentAlignment = Alignment.Center) {
                                    val foregroundColor = getColorByState(
                                        stateData = getContextualCommandBarTokens().iconColor(),
                                        enabled = item.enabled,
                                        selected = item.selected,
                                        interactionSource = interactionSource
                                    )
                                    if (item.icon != null)
                                        Icon(
                                            item.icon,
                                            item.label,
                                            Modifier.padding(getContextualCommandBarTokens().iconPadding()),
                                            tint = foregroundColor
                                        )
                                    else
                                        Text(
                                            item.label,
                                            modifier = Modifier.padding(
                                                getContextualCommandBarTokens().iconPadding()
                                            ),
                                            color = foregroundColor,
                                            overflow = TextOverflow.Ellipsis,
                                            maxLines = 1
                                        )
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
                            }
                        }
                        itemIndex += 1
                    }
                }
            }

            if (keyboardDismissEnabled) {
                val keyboardController = LocalSoftwareKeyboardController.current
                val keyboardDismissClickable = Modifier.clickable(
                    enabled = true,
                    onClick = { keyboardController?.hide() },
                    role = Role.Button,
                    onClickLabel = "Keyboard Dismiss",
                    indication = LocalIndication.current,
                    interactionSource = remember { MutableInteractionSource() }
                )

                Row(Modifier.constrainAs(KeyboardDismiss) {
                    if (keyboardDismissAtStart) {
                        start.linkTo(parent.start)
                    } else {
                        end.linkTo(parent.end)
                    }
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }, verticalAlignment = Alignment.CenterVertically) {
                    if (!keyboardDismissAtStart)
                        Spacer(
                            modifier = Modifier
                                .requiredWidth(getContextualCommandBarTokens().keyboardDismissGradientWidth())
                                .fillMaxHeight()
                                .background(
                                    Brush.horizontalGradient(
                                        getContextualCommandBarTokens().keyboardDismissGradient(),
                                        startX = 0.0F,
                                        endX = Float.POSITIVE_INFINITY
                                    )
                                )
                        )
                    Icon(
                        imageVector = CCBIcons.Keyboarddismiss,
                        contentDescription = "Dismiss",
                        modifier = Modifier
                            .then(keyboardDismissClickable)
                            .fillMaxHeight()
                            .background(getContextualCommandBarTokens().keyboardDismissBackgroundColor())
                            .padding(getContextualCommandBarTokens().keyBoardDismissIconPadding()),
                        tint = getContextualCommandBarTokens().keyboardDismissIconColor()
                    )
                    if (keyboardDismissAtStart)
                        Spacer(
                            modifier = Modifier
                                .requiredWidth(getContextualCommandBarTokens().keyboardDismissGradientWidth())
                                .fillMaxHeight()
                                .background(
                                    Brush.horizontalGradient(
                                        getContextualCommandBarTokens().keyboardDismissGradient(),
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