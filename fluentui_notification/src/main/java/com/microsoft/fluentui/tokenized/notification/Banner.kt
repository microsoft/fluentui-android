package com.microsoft.fluentui.tokenized.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.BannerInfo
import com.microsoft.fluentui.theme.token.controlTokens.BannerTokens

/**
 * A Banner displays a prominent message and related actions for users to address it. Banners are dismissed by the user.
 * Banners are used to inform users about important changes or persistent conditions. They are non-modal, meaning users can either interact or ignore them, and continue with their task.
 *
 * @param text The text to be displayed in the banner.
 * @param modifier The modifier to be applied to the component.
 * @param isTextCentered Whether the text should be centered or not. Text is centered when there is no leading icon, action button, or accessory buttons.
 * @param leadingIcon The icon to be displayed before the text.
 * @param actionButtonOnClick The callback to be invoked when the action button is clicked.
 * @param actionButtonText The text to be displayed on the action button. ActionButtonIcon will be replaced when this is set.
 * @param actionButtonIcon The icon to be displayed on the action button. ActionButtonText should not be set to display the icon.
 * @param accessoryTextButton1 The text to be displayed on the first accessory button.
 * @param accessoryTextButton2 The text to be displayed on the second accessory button.
 * @param accessoryTextButton1OnClick The callback to be invoked when the first accessory button is clicked.
 * @param accessoryTextButton2OnClick The callback to be invoked when the second accessory button is clicked.
 * @param bannerTokens The tokens to be used to customize the component.
 */
@Composable
fun Banner(
    text: String,
    modifier: Modifier = Modifier,
    isTextCentered: Boolean = false,
    leadingIcon: FluentIcon? = null,
    actionButtonOnClick: (() -> Unit)? = null,
    actionButtonText: String? = null,
    actionButtonIcon: FluentIcon? = null,
    accessoryTextButton1: String? = null,
    accessoryTextButton2: String? = null,
    accessoryTextButton1OnClick: (() -> Unit)? = null,
    accessoryTextButton2OnClick: (() -> Unit)? = null,
    bannerTokens: BannerTokens? = null,
) {
    val tokens = bannerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BannerControlType] as BannerTokens
    val actionButton1Enabled = accessoryTextButton1 != null && accessoryTextButton1OnClick != null
    val actionButton2Enabled = accessoryTextButton2 != null && accessoryTextButton2OnClick != null
    val bannerInfo = BannerInfo(actionButton1Enabled || actionButton2Enabled)
    var centerText = false
    if (isTextCentered && leadingIcon == null && actionButtonIcon == null && actionButtonText == null && !actionButton1Enabled && !actionButton2Enabled) {
        centerText = true
    }
    Box(
        modifier = modifier
            .background(tokens.backgroundColor(bannerInfo = bannerInfo))
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(tokens.padding(bannerInfo = bannerInfo)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                if (leadingIcon != null) {
                    Icon(
                        icon = leadingIcon,
                        tint = tokens.leadingIconColor(bannerInfo = bannerInfo),
                        modifier = Modifier.size(
                            tokens.leadingIconSize(
                                bannerInfo = bannerInfo
                            )
                        )
                    )
                }
                Spacer(modifier = Modifier.width(tokens.leadingIconAndTextSpacing(bannerInfo = bannerInfo)))
                BasicText(
                    modifier = Modifier.weight(1f, !centerText),
                    text = text,
                    style = TextStyle(color = tokens.textColor(bannerInfo = bannerInfo)).merge(
                        tokens.textTypography(
                            bannerInfo = bannerInfo
                        )
                    )
                )
                Spacer(modifier = Modifier.width(tokens.textAndActionButtonSpacing(bannerInfo = bannerInfo)))
                if (actionButtonOnClick != null) {
                    if (actionButtonText != null) {
                        ClickableText(
                            text = AnnotatedString(actionButtonText),
                            onClick = { actionButtonOnClick() },
                            style = TextStyle(color = tokens.actionButtonColor(bannerInfo = bannerInfo)).merge(
                                tokens.actionButtonTextTypography(
                                    bannerInfo = bannerInfo
                                )
                            )
                        )
                    } else if (actionButtonIcon != null) {
                        Icon(
                            icon = actionButtonIcon,
                            modifier = Modifier
                                .clickable(onClick = { actionButtonOnClick() })
                                .size(tokens.actionIconSize(bannerInfo = bannerInfo)),
                            tint = tokens.actionIconColor(
                                bannerInfo = bannerInfo
                            )
                        )
                    }
                }
            }
            if (actionButton1Enabled || actionButton2Enabled) {
                Spacer(modifier = Modifier.height(tokens.textAndAccessoryButtonSpacing(bannerInfo = bannerInfo)))
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                if (actionButton1Enabled) {
                    ClickableText(
                        text = AnnotatedString(accessoryTextButton1!!),
                        style = TextStyle(color = tokens.actionButtonColor(bannerInfo = bannerInfo)).merge(
                            tokens.actionButtonTextTypography(
                                bannerInfo = bannerInfo
                            )
                        ),
                        onClick = {
                            if (accessoryTextButton1OnClick != null) {
                                accessoryTextButton1OnClick()
                            }
                        })
                    Spacer(modifier = Modifier.width(tokens.accessoryActionButtonsSpacing(bannerInfo = bannerInfo)))
                }

                if (actionButton2Enabled) {
                    ClickableText(
                        text = AnnotatedString(accessoryTextButton2!!),
                        style = TextStyle(color = tokens.actionButtonColor(bannerInfo = bannerInfo)).merge(
                            tokens.actionButtonTextTypography(
                                bannerInfo = bannerInfo
                            )
                        ),
                        onClick = {
                            if (accessoryTextButton2OnClick != null) {
                                accessoryTextButton2OnClick()
                            }
                        })
                }
            }
        }
    }

}