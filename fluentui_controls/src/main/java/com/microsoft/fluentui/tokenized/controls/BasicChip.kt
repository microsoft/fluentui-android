package com.microsoft.fluentui.tokenized.controls

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.controls.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BasicChipInfo
import com.microsoft.fluentui.theme.token.controlTokens.BasicChipTokens

/**
 * [BasicChip] is a compact representations of entities(most commonly, people)that can be typed in, deleted or dragged easily
 * @param label Label for the chip
 * @param modifier Modifier for the chip
 * @param enabled Whether chip is enabled or disabled. Enabled by default.
 * @param selected Whether chip is selected or unselected. Unselected by default.
 * @param leadingAccessory Leading accessory for the chip
 * @param trailingAccessory Trailing accessory for the chip
 * @param onClick onClick action for chip
 * @param interactionSource Optional interactionSource
 * @param basicChipTokens Optional tokens for chip
 */
@Composable
fun BasicChip(
    label: String,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    selected: Boolean = false,
    leadingAccessory: @Composable (() -> Unit)? = null,
    trailingAccessory: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    basicChipTokens: BasicChipTokens? = null
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = basicChipTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BasicChip] as BasicChipTokens
    val basicChipInfo = BasicChipInfo()
    val backgroundColor =
        token.backgroundBrush(basicChipInfo)
            .getBrushByState(
                enabled = enabled, selected = selected, interactionSource = interactionSource
            )
    val textColor = token.textColor(basicChipInfo)
        .getColorByState(
            enabled = enabled, selected = selected, interactionSource = interactionSource
        )
    val typography = token.typography(basicChipInfo)
    val padding =
        token.padding(basicChipInfo)
    val cornerRadius =
        token.cornerRadius(basicChipInfo)
    val horizontalSpacing = token.horizontalSpacing(basicChipInfo)
    val selectedString = if (selected)
        LocalContext.current.resources.getString(R.string.fluentui_selected)
    else
        LocalContext.current.resources.getString(R.string.fluentui_not_selected)

    val enabledString = if (enabled)
        LocalContext.current.resources.getString(R.string.fluentui_enabled)
    else
        LocalContext.current.resources.getString(R.string.fluentui_disabled)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(backgroundColor)
            .then(if(onClick!=null){
                Modifier.clickable(
                    enabled = enabled,
                    onClick = onClick,
                    interactionSource = interactionSource,
                    indication = rememberRipple()
                )
            }else{
                Modifier
            })
            .clearAndSetSemantics {
                this.contentDescription = "$label $selectedString $enabledString"
            }
    )
    {
        Row(
            Modifier
                .padding(
                    padding
                ),
            horizontalArrangement = Arrangement.spacedBy(horizontalSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (leadingAccessory != null) {
                leadingAccessory()
            }
            BasicText(
                text = label,
                style = typography.merge(TextStyle(color = textColor))
            )
            if (trailingAccessory != null) {
                trailingAccessory()
            }
        }
    }
}