package com.microsoft.fluentui.tokenized.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.CheckBoxInfo
import com.microsoft.fluentui.theme.token.controlTokens.CheckBoxTokens

/**
 * API to create a checkbox. A checkbox is a type of button that lets the user choose between two opposite states,
 * actions, or values. A selected checkbox is considered on when it contains a checkmark and off when
 * it's empty. A checkbox is almost always followed by a title unless it appears in a checklist.
 *
 * @param onCheckedChanged Function to be invoked when checked state changes
 * @param modifier Optional Modifier for CheckBox
 * @param enabled Boolean for enabling/disabling CheckBox. Default: [true]
 * @param checked Boolean for checked state of control. Default: [false]
 * @param interactionSource Interaction source for User gesture Management.
 * @param checkBoxToken Tokens for customizing CheckBox design.
 */
@Composable
fun CheckBox(
    onCheckedChanged: ((Boolean) -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    checkBoxToken: CheckBoxTokens? = null
) {

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = checkBoxToken
        ?: FluentTheme.controlTokens.tokens[ControlType.CheckBoxControlType] as CheckBoxTokens
    val checkBoxInfo = CheckBoxInfo(checked)
    val toggleModifier =
        modifier.triStateToggleable(
            state = ToggleableState(checked),
            enabled = enabled,
            onClick = { onCheckedChanged(!checked) },
            role = Role.Checkbox,
            interactionSource = interactionSource,
            indication = rememberRipple(
                bounded = false,
                radius = 24.dp
            )
        )

    val backgroundColor: Brush =
        token.backgroundBrush(checkBoxInfo = checkBoxInfo).getBrushByState(
            enabled = enabled,
            selected = checked,
            interactionSource = interactionSource
        )
    val iconColor: Color =
        token.iconColor(checkBoxInfo = checkBoxInfo).getColorByState(
            enabled = enabled,
            selected = checked,
            interactionSource = interactionSource
        )
    val shape: Shape = RoundedCornerShape(token.fixedBorderRadius)

    val borders: List<BorderStroke> =
        token.borderStroke(checkBoxInfo = checkBoxInfo)
            .getBorderStrokeByState(
                enabled = enabled,
                selected = checked,
                interactionSource = interactionSource
            )
    var borderModifier: Modifier = Modifier
    var borderWidth = 0.dp
    for (border in borders) {
        borderWidth += border.width
        borderModifier = borderModifier.border(borderWidth, border.brush, shape)
    }

    Box(
        modifier = Modifier.indication(interactionSource, null),
        contentAlignment = Alignment.Center
    ) {
        Spacer(
            modifier = Modifier
                .size(token.fixedSize)
                .clip(shape)
                .background(backgroundColor)
                .then(borderModifier)
                .then(toggleModifier)
        )
        AnimatedVisibility(checked, enter = fadeIn(), exit = fadeOut()) {
            Icon(
                Icons.Filled.Done,
                null,
                modifier = Modifier
                    .size(token.fixedIconSize)
                    .focusable(false)
                    .clearAndSetSemantics {},
                tint = iconColor
            )
        }
    }
}