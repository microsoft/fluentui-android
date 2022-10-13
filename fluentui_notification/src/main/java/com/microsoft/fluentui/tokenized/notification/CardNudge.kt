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
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.compose.FractionalThreshold
import com.microsoft.fluentui.compose.rememberSwipeableState
import com.microsoft.fluentui.compose.swipeable
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
import androidx.compose.material.Icon as MaterialIcon

private val LocalCardNudgeTokens = compositionLocalOf { CardNudgeTokens() }
private val LocalCardNudgeInfo = compositionLocalOf { CardNudgeInfo() }

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

enum class SwipeGesture {
    NONE,
    LEFT,
    RIGHT
}

@Composable
fun CardNudge(
    metadata: CardNudgeMetaData,
    modifier: Modifier = Modifier,
    outlineMode: Boolean = false,
    cardNudgeTokens: CardNudgeTokens? = null
) {
    val token = cardNudgeTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.CardNudge] as CardNudgeTokens

    CompositionLocalProvider(
        LocalCardNudgeTokens provides token,
        LocalCardNudgeInfo provides CardNudgeInfo()
    ) {
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
                        getCardNudgeTokens().backgroundColor(getCardNudgeInfo()),
                        shape
                    )
                    .then(
                        if (outlineMode)
                            Modifier.border(
                                getCardNudgeTokens().borderSize(getCardNudgeInfo()),
                                getCardNudgeTokens().strokeColor(getCardNudgeInfo()),
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
                    ),
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
                            .size(getCardNudgeTokens().leftIconBackgroundSize(getCardNudgeInfo()))
                            .background(
                                getCardNudgeTokens().iconBackgroundColor(getCardNudgeInfo()),
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
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            metadata.icon,
                            modifier = Modifier
                                .size(getCardNudgeTokens().leftIconSize(getCardNudgeInfo())),
                            tint = getCardNudgeTokens().iconColor(getCardNudgeInfo())
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 16.dp)
                ) {
                    Text(
                        metadata.message,
                        style = getCardNudgeTokens().titleTypography(getCardNudgeInfo()),
                    )
                    if (!metadata.subTitle.isNullOrBlank() || !metadata.accentText.isNullOrBlank() || metadata.accentIcon != null) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (metadata.accentIcon != null) {
                                Box(
                                    modifier = Modifier.size(12.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        metadata.accentIcon,
                                        modifier = Modifier.size(8.dp),
                                        tint = getCardNudgeTokens().accentColor(getCardNudgeInfo())
                                    )
                                }
                            }

                            if (!metadata.accentText.isNullOrBlank()) {
                                Text(
                                    metadata.accentText,
                                    style = getCardNudgeTokens().accentTypography(getCardNudgeInfo())
                                )
                            }

                            if (!metadata.subTitle.isNullOrBlank()) {
                                Text(
                                    metadata.subTitle,
                                    style = getCardNudgeTokens().subtitleTypography(getCardNudgeInfo())
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.width(4.dp))
                }

                if (metadata.actionMetaData != null) {
                    PillButton(
                        metadata.actionMetaData,
                        pillButtonTokens = object : PillButtonTokens() {
                            @Composable
                            override fun backgroundColor(pillButtonInfo: PillButtonInfo): StateColor {
                                return StateColor(
                                    rest = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(),
                                    pressed = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value(),
                                    focused = aliasTokens.brandBackgroundColor[AliasTokens.BrandBackgroundColorTokens.BrandBackgroundTint].value()
                                )
                            }

                            @Composable
                            override fun textColor(pillButtonInfo: PillButtonInfo): StateColor {
                                return StateColor(
                                    rest = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(),
                                    pressed = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value(),
                                    focused = aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForegroundTint].value()
                                )
                            }

                            @Composable
                            override fun fontStyle(pillButtonInfo: PillButtonInfo): TextStyle {
                                return aliasTokens.typography[AliasTokens.TypographyTokens.Body2Strong]
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(12.dp))
                }

                if (metadata.dismissOnClick != null) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(),
                                enabled = true,
                                role = Role.Image,
                                onClickLabel = "Dismiss",
                                onClick = metadata.dismissOnClick
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        MaterialIcon(
                            Icons.Filled.Close,
                            "Dismiss",
                            modifier = Modifier
                                .size(getCardNudgeTokens().dismissIconSize(getCardNudgeInfo())),
                            tint = getCardNudgeTokens().iconColor(getCardNudgeInfo())
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getCardNudgeTokens(): CardNudgeTokens {
    return LocalCardNudgeTokens.current
}

@Composable
private fun getCardNudgeInfo(): CardNudgeInfo {
    return LocalCardNudgeInfo.current
}