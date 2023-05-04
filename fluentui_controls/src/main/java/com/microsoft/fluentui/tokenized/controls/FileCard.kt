package com.microsoft.fluentui.tokenized.controls

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.FileCardInfo
import com.microsoft.fluentui.theme.token.controlTokens.FileCardTokens

@Composable
fun FileCard(
    text: String,
    subText: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
    previewImageVector: ImageVector? = null,
    @DrawableRes previewImageDrawable: Int? = null,
    textIcon: FluentIcon,
    actionOverflowIcon: FluentIcon? = null,
    actionOverflowIconOnClick: (() -> Unit)? = null,
    fileCardTokens: FileCardTokens? = null
) {
    val token = fileCardTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.FileCard] as FileCardTokens
    val isPreviewAvailable = !(previewImageDrawable == null && previewImageVector == null)
    val fileCardInfo = FileCardInfo(isPreviewAvailable)
    val textColor = token.textColor(fileCardInfo = fileCardInfo)
    val subTextColor = token.subTextColor(fileCardInfo = fileCardInfo)
    val iconColor = token.iconColor(fileCardInfo = fileCardInfo)
    val actionOverFlowBackgroundColor =
        token.actionOverFlowBackgroundColor(fileCardInfo = fileCardInfo)
    val actionOverFlowIconColor = token.actionOverFlowIconColor(fileCardInfo = fileCardInfo)
    val iconSize = token.iconSize(fileCardInfo = fileCardInfo)
    val textTypography = token.textTypography(fileCardInfo = fileCardInfo)
    val subTextTypography = token.subTextTypography(fileCardInfo = fileCardInfo)
    val actionOverflowCornerRadius = token.actionOverflowCornerRadius(fileCardInfo = fileCardInfo)
    val actionOverflowIconSize = token.actionOverflowIconSize(fileCardInfo = fileCardInfo)
    val leadIconPadding = token.leadIconPadding(fileCardInfo = fileCardInfo)
    val iconTextPadding = token.iconTextPadding(fileCardInfo = fileCardInfo)
    val textSubTextPadding = token.textSubTextPadding(fileCardInfo = fileCardInfo)
    val actionOverflowPadding = token.actionOverflowPadding(fileCardInfo = fileCardInfo)
    val textVerticalPadding = token.textVerticalPadding(fileCardInfo = fileCardInfo)
    BasicCard(
        modifier = modifier,
        onClick = onClick
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier.requiredWidth(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (previewImageDrawable != null) {
                    Image(
                        painterResource(id = previewImageDrawable), contentDescription = ""
                    )
                } else if (previewImageVector != null) {
                    Image(
                        imageVector = previewImageVector,
                        contentDescription = ""
                    )
                }
                Row(
                    modifier = Modifier.padding(
                        start = leadIconPadding,
                        end = leadIconPadding,
                        top = textVerticalPadding.calculateTopPadding(),
                        bottom = textVerticalPadding.calculateBottomPadding()
                    ), verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(modifier = Modifier.size(iconSize), icon = textIcon, tint = iconColor)
                    Column(
                        modifier = Modifier.padding(start = iconTextPadding),
                        verticalArrangement = Arrangement.spacedBy(textSubTextPadding)
                    ) {
                        BasicText(
                            text = text,
                            style = textTypography.merge(TextStyle(color = textColor))
                        )
                        BasicText(
                            text = subText,
                            style = subTextTypography.merge(TextStyle(color = subTextColor))
                        )
                    }
                }
            }
            if (isPreviewAvailable && actionOverflowIcon != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(end = actionOverflowPadding, top = actionOverflowPadding)
                        .clip(RoundedCornerShape(actionOverflowCornerRadius))
                        .background(actionOverFlowBackgroundColor)
                        .then(
                            if (actionOverflowIconOnClick != null) Modifier.clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(),
                                enabled = true,
                                onClick = actionOverflowIconOnClick,
                                role = Role.Button
                            ) else Modifier
                        )
                        .semantics(true, properties = {
                            role = Role.Button
                        })
                ) {
                    Icon(
                        modifier = Modifier.size(actionOverflowIconSize),
                        icon = actionOverflowIcon,
                        tint = actionOverFlowIconColor,
                    )
                }
            }
        }
    }
}