package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.TweenSpec
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.FractionalThreshold
import com.microsoft.fluentui.compose.rememberSwipeableState
import com.microsoft.fluentui.compose.swipeable
import com.microsoft.fluentui.notification.R
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.controlTokens.CardNudgeInfo
import com.microsoft.fluentui.theme.token.controlTokens.CardNudgeTokens
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.PillButtonTokens
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillButton
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import kotlin.math.roundToInt

// TAGS FOR TESTING
private const val CARD_NUDGE = "CardNudge"
private const val ICON = "ICON"
private const val ACCENT_ICON = "Accent Icon"
private const val ACCENT_TEXT = "Accent Text"
private const val SUBTITLE = "Subtitle"
private const val ACTION_BUTTON = "Action Button"
private const val DISMISS_BUTTON = "Dismiss Button"

class CardNudgeMetaData(
    val message: String,
    val dismissOnClick: (() -> Unit)? = null,
    val icon: FluentIcon? = null,
    val subTitle: String? = null,
    val accentText: String? = null,
    val accentIcon: FluentIcon? = null,
    val actionMetaData: PillMetaData? = null,
    val leftSwipeGesture: (() -> Unit)? = null,
    val rightSwipeGesture: (() -> Unit)? = null
)

private enum class SwipeGesture {
    NONE,
    LEFT,
    RIGHT
}

/**
 * A card nudge is a short message that helps people discover what they can do in an app.
 * It appears at the top of a screen, beneath the navigation bar, and pushes all other content below it.
 * Card nudges are helpful for sending reminders and recommendations, but they don’t necessarily relates
 * to someone’s current task.
 *
 * @param metadata [CardNudgeMetaData] storing the information for the Nudge to be displayed.
 * @param modifier Optional Modifier to be applied to CardNudge.
 * @param outlineMode Boolean for enabling outline on CardNudge. Default: [false]
 * @param cardNudgeTokens Optional Tokens for customizing CardNudge.
 */
@Composable
fun CardNudge(
    metadata: CardNudgeMetaData,
    modifier: Modifier = Modifier,
    outlineMode: Boolean = false,
    cardNudgeTokens: CardNudgeTokens? = null
) {
    val token = cardNudgeTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CardNudge] as CardNudgeTokens

    val cardNudgeInfo = CardNudgeInfo()
    val animationSpec = TweenSpec<Float>(durationMillis = 300)
    val state = rememberSwipeableState(initialValue = SwipeGesture.NONE, animationSpec) { true }

    BoxWithConstraints {
        val maxWidth = constraints.maxWidth.toFloat()
        val shape = RoundedCornerShape(12.dp)

        Row(
            modifier
                .fillMaxWidth()
                .offset { IntOffset(state.offset.value.roundToInt(), 0) }
                .background(
                    token.backgroundBrush(cardNudgeInfo),
                    shape
                )
                .then(
                    if (outlineMode)
                        Modifier.border(
                            token.borderSize(cardNudgeInfo),
                            token.borderStrokeColor(cardNudgeInfo),
                            shape
                        )
                    else
                        Modifier
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .swipeable(
                    state,
                    anchors = mapOf(
                        -maxWidth to SwipeGesture.LEFT,
                        0F to SwipeGesture.NONE,
                        maxWidth to SwipeGesture.RIGHT
                    ),
                    thresholds = { _, _ -> FractionalThreshold(0.3F) },
                    orientation = Orientation.Horizontal,
                )
                .testTag(CARD_NUDGE),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LaunchedEffect(state.currentValue) {
                if (state.currentValue == SwipeGesture.RIGHT) {
                    metadata.rightSwipeGesture?.invoke()
                    state.animateTo(SwipeGesture.NONE)
                } else if (state.currentValue == SwipeGesture.LEFT) {
                    metadata.leftSwipeGesture?.invoke()
                    state.animateTo(SwipeGesture.NONE)
                }
            }

            if (metadata.icon != null && metadata.icon.isIconAvailable()) {
                Box(
                    modifier = Modifier
                        .size(token.leftIconBackgroundSize(cardNudgeInfo))
                        .background(
                            token.iconBackgroundBrush(cardNudgeInfo),
                            CircleShape
                        )
                        .then(
                            if (metadata.icon.onClick != null) {
                                Modifier.clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(),
                                    enabled = true,
                                    role = Role.Image,
                                    onClick = metadata.icon.onClick!!
                                )
                            } else Modifier
                        )
                        .testTag(ICON),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        metadata.icon,
                        modifier = Modifier
                            .size(token.leftIconSize(cardNudgeInfo)),
                        tint = token.iconColor(cardNudgeInfo)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 16.dp)
            ) {
                BasicText(
                    metadata.message,
                    style = token.titleTypography(cardNudgeInfo),
                )
                if (!metadata.subTitle.isNullOrBlank() || !metadata.accentText.isNullOrBlank() || metadata.accentIcon != null) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (metadata.accentIcon != null) {
                            Box(
                                modifier = Modifier
                                    .size(12.dp)
                                    .testTag(ACCENT_ICON),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    metadata.accentIcon,
                                    tint = token.accentColor(cardNudgeInfo)
                                )
                            }
                        }

                        if (!metadata.accentText.isNullOrBlank()) {
                            BasicText(
                                metadata.accentText,
                                style = token.accentTypography(cardNudgeInfo),
                                modifier = Modifier.testTag(ACCENT_TEXT)
                            )
                        }

                        if (!metadata.subTitle.isNullOrBlank()) {
                            BasicText(
                                metadata.subTitle,
                                style = token.subtitleTypography(cardNudgeInfo),
                                modifier = Modifier.testTag(SUBTITLE)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.width(4.dp))
            }

            if (metadata.actionMetaData != null) {
                PillButton(
                    metadata.actionMetaData,
                    modifier = Modifier.testTag(ACTION_BUTTON),
                    pillButtonTokens = object : PillButtonTokens() {
                        @Composable
                        override fun backgroundBrush(pillButtonInfo: PillButtonInfo): StateBrush {
                            return StateBrush(
                                rest = SolidColor(aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value()),
                                pressed = SolidColor(aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value()),
                                focused = SolidColor(aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value())
                            )
                        }

                        @Composable
                        override fun textColor(pillButtonInfo: PillButtonInfo): StateColor {
                            return StateColor(
                                rest = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(),
                                pressed = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(),
                                focused = aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
                            )
                        }

                        @Composable
                        override fun typography(pillButtonInfo: PillButtonInfo): TextStyle {
                            return aliasTokens.typography[FluentAliasTokens.TypographyTokens.Body2Strong]
                        }
                    }
                )

                Spacer(modifier = Modifier.width(12.dp))
            }

            if (metadata.dismissOnClick != null) {
                Icon(
                    Icons.Filled.Close,
                    LocalContext.current.resources.getString(R.string.fluentui_dismiss_button),
                    modifier = Modifier
                        .padding(10.dp)
                        .size(token.dismissIconSize(cardNudgeInfo))
                        .testTag(DISMISS_BUTTON),
                    tint = token.iconColor(cardNudgeInfo),
                    onClick = metadata.dismissOnClick
                )
            }
        }
    }
}