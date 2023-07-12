package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.SuggestionPromptInfo
import com.microsoft.fluentui.theme.token.controlTokens.SuggestionPromptTokens

data class Suggestions(
    val text: String,
    val icon: FluentIcon? = null,
    val maxLines: Int = Int.MAX_VALUE,
    val onClick: (() -> Unit)? = null
)

//Tags for testing
private const val ACTION_ICON = "Action Icon"


/**
 * API to render suggestions in a horizontally scrollable manner with provision of
 * an Action Icon Button, which can be used for any onClick Behavior. Suggestions are
 * equi-height boxes which may or may not be single line in nature.
 *
 * @param suggestions [MutableList] which encapsulates the [Suggestions] data.
 * @param modifier Optional Modifier for SuggestionPrompt
 * @param actionIcon Icon with onClick Property of type [FluentIcon]
 * @param suggestionPromptToken Tokens to override default [SuggestionPromptTokens]
 */
@Composable
fun SuggestionPrompt(
    suggestions: MutableList<Suggestions>,
    modifier: Modifier = Modifier,
    actionIcon: FluentIcon? = null,
    suggestionPromptToken: SuggestionPromptTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = suggestionPromptToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SuggestionPrompt] as SuggestionPromptTokens
    val suggestionPromptInfo = SuggestionPromptInfo()

    Row(
        modifier = modifier
            .horizontalScroll(rememberScrollState())
            .requiredHeight(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Spacer(Modifier.width(16.dp))
        if (actionIcon?.isIconAvailable() == true) {
            val actionInteractionSource = remember { MutableInteractionSource() }

            Box(
                Modifier
                    .requiredSize(40.dp)
                    .clip(CircleShape)
                    .background(token.backgroundBrush(suggestionPromptInfo).getBrushByState(
                        enabled = true,
                        selected = false,
                        interactionSource = actionInteractionSource
                    ), CircleShape)
                    .then(
                        if (actionIcon.onClick != null) {
                            Modifier.clickable(
                                indication = rememberRipple(),
                                interactionSource = actionInteractionSource,
                                enabled = true,
                                onClickLabel = actionIcon.contentDescription,
                                role = Role.Button,
                                onClick = actionIcon.onClick!!
                            )
                        } else Modifier
                    )
                    .applyAllBorders(
                        token
                            .borderStroke(suggestionPromptInfo)
                            .getBorderStrokeByState(
                                enabled = true,
                                selected = false,
                                interactionSource = actionInteractionSource
                            ), CircleShape
                    ), contentAlignment = Alignment.Center
            ) {
                Icon(
                    actionIcon.value(),
                    contentDescription = actionIcon.contentDescription,
                    modifier = Modifier.testTag(ACTION_ICON),
                    tint = token.actionIconColor(suggestionPromptInfo)
                )
            }
        }
        for (suggestion in suggestions) {
            val suggestedString = suggestion.text
            if (suggestedString.isNotBlank()) {
                val suggestionShape = RoundedCornerShape(token.cornerRadius(suggestionPromptInfo))
                val suggestionInteractionSource = remember { MutableInteractionSource() }

                Box(
                    Modifier
                        .fillMaxHeight()
                        .widthIn(0.dp, token.maxWidth(suggestionPromptInfo))
                        .clip(suggestionShape)
                        .background(token.backgroundBrush(suggestionPromptInfo).getBrushByState(
                            enabled = true,
                            selected = false,
                            interactionSource = suggestionInteractionSource
                        ), suggestionShape)
                        .then(
                            if (suggestion.onClick != null)
                                Modifier.clickable(
                                    indication = rememberRipple(),
                                    interactionSource = suggestionInteractionSource,
                                    enabled = true,
                                    onClickLabel = null,
                                    role = Role.Button,
                                    onClick = suggestion.onClick
                                ) else Modifier
                        )
                        .applyAllBorders(
                            token
                                .borderStroke(suggestionPromptInfo)
                                .getBorderStrokeByState(
                                    enabled = true,
                                    selected = false,
                                    interactionSource = suggestionInteractionSource
                                ), suggestionShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        modifier = Modifier
                            .padding(token.contentPadding(suggestionPromptInfo)),
                        horizontalArrangement = Arrangement.spacedBy(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size40)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if(suggestion.icon?.isIconAvailable() == true) {
                            Icon(
                                suggestion.icon,
                                modifier = Modifier.size(token.imageSize(suggestionPromptInfo)),
                                tint = token.textColor(suggestionPromptInfo)
                            )
                        }

                        BasicText(
                            text = suggestedString,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = suggestion.maxLines,
                            softWrap = true,
                            style = token.typography(suggestionPromptInfo).merge(
                                TextStyle(
                                    color = token.textColor(suggestionPromptInfo),
                                )
                            )
                        )
                    }
                }
            }
        }
    }
}

private fun Modifier.applyAllBorders(borderStrokes: List<BorderStroke>, shape: Shape): Modifier {
    var borderModifier: Modifier = Modifier
    for (borderStroke in borderStrokes) {
        borderModifier = borderModifier.border(borderStroke, shape)
    }
    return this.then(borderModifier)
}