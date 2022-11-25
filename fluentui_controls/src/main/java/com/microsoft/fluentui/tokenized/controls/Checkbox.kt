package com.microsoft.fluentui.tokenized.controls

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.triStateToggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.CheckBoxInfo
import com.microsoft.fluentui.theme.token.controlTokens.CheckBoxTokens

val LocalCheckBoxTokens = compositionLocalOf { CheckBoxTokens() }
val LocalCheckBoxInfo = compositionLocalOf { CheckBoxInfo() }

@Composable
fun CheckBox(
    onCheckedChanged: ((Boolean) -> Unit)?,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    checked: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    checkBoxToken: CheckBoxTokens? = null
) {

    val token = checkBoxToken
        ?: FluentTheme.controlTokens.tokens[ControlType.CheckBox] as CheckBoxTokens

    CompositionLocalProvider(
        LocalCheckBoxTokens provides token,
        LocalCheckBoxInfo provides CheckBoxInfo(checked)
    ) {
        val toggleModifier =
            if (onCheckedChanged != null) {
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
            } else {
                modifier
            }

        val backgroundColor: Color = backgroundColor(
            getCheckBoxToken(), getCheckBoxInfo(),
            enabled, checked, interactionSource
        )
        val iconColor: Color = iconColor(
            getCheckBoxToken(), getCheckBoxInfo(),
            enabled, checked, interactionSource
        )
        val shape: Shape = RoundedCornerShape(getCheckBoxToken().fixedBorderRadius)

        val borders: List<BorderStroke> =
            borderStroke(getCheckBoxToken(), getCheckBoxInfo(), enabled, checked, interactionSource)
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
                    .size(getCheckBoxToken().fixedSize)
                    .clip(shape)
                    .background(backgroundColor)
                    .then(borderModifier)
                    .then(toggleModifier)
            )
            AnimatedVisibility(checked, enter = fadeIn(), exit = fadeOut()) {
                Icon(
                    Icons.Filled.Done,
                    "Done",
                    modifier = Modifier.size(getCheckBoxToken().fixedIconSize),
                    tint = iconColor
                )
            }
        }

    }
}

@Composable
fun getCheckBoxToken(): CheckBoxTokens {
    return LocalCheckBoxTokens.current
}

@Composable
fun getCheckBoxInfo(): CheckBoxInfo {
    return LocalCheckBoxInfo.current
}