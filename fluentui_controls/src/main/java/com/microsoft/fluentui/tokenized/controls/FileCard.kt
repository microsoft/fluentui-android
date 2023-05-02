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
import androidx.compose.ui.platform.testTag
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

/**
 * Cards are flexible containers that group related content and actions together. They reveal more information upon interaction.
 * A File card is generally used to refer a document. The card is made of a preview image on the top and some text below.
 *
 * @param text Primary text of the Card
 * @param subText Secondary text of the card, usually a small description
 * @param modifier Modifier for the card
 * @param interactionSource Optional interaction source
 * @param onClick onClick for the card
 * @param previewImageVector Optional previewImage for the card
 * @param previewImageDrawable Optional previewImage for the card
 * @param textIcon Optional textIcon.
 * @param actionOverflowIcon Optional actionOverflowIcon. If not provided there will be no button on top of the preview Image.
 * @param fileCardTokens Optional tokens for customizing the card
 */
@Composable
fun FileCard(
    text: String,
    subText: String,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    onClick: (() -> Unit)? = null,
    previewImageVector: ImageVector? = null,
    @DrawableRes previewImageDrawable: Int? = null,
    textIcon: ImageVector,
    actionOverflowIcon: FluentIcon? = null,
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
    val iconTextSpacing = token.iconTextSpacing(fileCardInfo = fileCardInfo)
    val textSubTextSpacing = token.textSubTextSpacing(fileCardInfo = fileCardInfo)
    val actionOverflowPadding = token.actionOverflowPadding(fileCardInfo = fileCardInfo)
    val textContainerPadding = token.textContainerPadding(fileCardInfo = fileCardInfo)
    BasicCard(
        modifier = modifier
    ) {
        Box(contentAlignment = Alignment.Center) {
            Column(
                modifier = Modifier
                    .requiredWidth(IntrinsicSize.Min)
                    .testTag("Card")
                    .then(
                        if (onClick != null) Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = rememberRipple(),
                            enabled = true,
                            onClick = onClick,
                            role = Role.Button
                        ) else Modifier
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (previewImageDrawable != null) {
                    Image(
                        painterResource(id = previewImageDrawable), contentDescription = null
                    )
                } else if (previewImageVector != null) {
                    Image(
                        imageVector = previewImageVector,
                        contentDescription = null
                    )
                }
                Row(
                    modifier = Modifier.padding(
                        textContainerPadding
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(iconSize),
                        icon = FluentIcon(textIcon),
                        tint = iconColor
                    )
                    Spacer(modifier = Modifier.width(iconTextSpacing))
                    Column {
                        BasicText(
                            text = text,
                            style = textTypography.merge(TextStyle(color = textColor))
                        )
                        Spacer(modifier = Modifier
                            .fillMaxWidth()
                            .height(textSubTextSpacing))
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
                        .padding(actionOverflowPadding)
                        .clip(RoundedCornerShape(actionOverflowCornerRadius))
                        .background(actionOverFlowBackgroundColor)
                        .clickable(
                            interactionSource = MutableInteractionSource(),
                            indication = rememberRipple(),
                            enabled = true,
                            onClick = actionOverflowIcon.onClick ?: {},
                            role = Role.Button
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