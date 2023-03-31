package com.microsoft.fluentui.tokenized.controls

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.RadioButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.RadioButtonTokens

val LocalRadioButtonTokens = compositionLocalOf { RadioButtonTokens() }
val LocalRadioButtonInfo = compositionLocalOf { RadioButtonInfo() }

/**
 * API to create a Radio Button. A Radio selection lets user choose one option out of all values.
 * This API provides a single instance of radio button and not a group.
 *
 * @param onClick OnClick method to be invoked when clicked on the radio button
 * @param modifier Optional modifier for Radio Button
 * @param enabled Boolean for enabling/disabling Radio Button. Default: [true]
 * @param selected Boolean for setting selected state in radio Button. Default: [false]
 * @param interactionSource Interaction Source for user gesture management.
 * @param radioButtonToken Tokens for customizing Radio buttons
 */
@Composable
fun RadioButton(
    onClick: (() -> Unit),
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    radioButtonToken: RadioButtonTokens? = null
) {
    val token = radioButtonToken
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.RadioButton] as RadioButtonTokens

    CompositionLocalProvider(
        LocalRadioButtonTokens provides token,
        LocalRadioButtonInfo provides RadioButtonInfo(selected)
    ) {
        val dotRadius = animateDpAsState(
            targetValue = if (selected) getRadioButtonTokens().innerCircleRadius else 0.dp,
            animationSpec = tween(durationMillis = 100)
        )

        val selectableModifier = modifier.selectable(
            selected = selected,
            enabled = enabled,
            onClick = onClick,
            role = Role.RadioButton,
            interactionSource = interactionSource,
            indication = rememberRipple(
                bounded = false,
                radius = 24.dp
            )
        )

        val outerStrokeColor =
            getRadioButtonTokens().backgroundColor(radioButtonInfo = getRadioButtonInfo())
                .getColorByState(
                    enabled = enabled,
                    selected = selected,
                    interactionSource = interactionSource
                )
        val innerColor = getRadioButtonTokens().iconColor(radioButtonInfo = getRadioButtonInfo())
            .getColorByState(
                enabled = enabled,
                selected = selected,
                interactionSource = interactionSource
            )

        val outerRadius = getRadioButtonTokens().outerCircleRadius
        val strokeWidth = getRadioButtonTokens().strokeWidthInwards

        Canvas(
            modifier = Modifier
                .then(selectableModifier)
                .size(24.dp)
                .wrapContentSize(Alignment.Center)
                .semantics { contentDescription = "RadioButton" }
        ) {
            drawCircle(
                outerStrokeColor,
                (outerRadius - (strokeWidth / 2)).toPx(),
                style = Stroke(1.5.dp.toPx())
            )

            if (dotRadius.value > 0.dp) {
                drawCircle(innerColor, (dotRadius.value).toPx(), style = Fill)
            }
        }
    }
}


@Composable
fun getRadioButtonTokens(): RadioButtonTokens {
    return LocalRadioButtonTokens.current
}

@Composable
fun getRadioButtonInfo(): RadioButtonInfo {
    return LocalRadioButtonInfo.current
}
