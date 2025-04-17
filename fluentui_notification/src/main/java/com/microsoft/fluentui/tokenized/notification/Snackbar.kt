package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.tokenized.controls.Button
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

// TAGS FOR TESTING
const val SNACK_BAR = "Fluent Snack bar"
const val SNACK_BAR_ICON = "Fluent Snack bar Icon"
const val SNACK_BAR_SUBTITLE = "Fluent Snack bar Subtitle"
const val SNACK_BAR_ACTION_BUTTON = "Fluent Snack bar Action Button"
const val SNACK_BAR_DISMISS_BUTTON = "Fluent Snack bar Dismiss Button"

/**
 * SnackbarMetadata is a data class that holds the metadata for a Snackbar.
 * It contains information such as the message, style, icon, action text, and duration.
 *
 * @param message The message to be displayed in the Snackbar.
 * @param style The style of the Snackbar.
 * @param enableDismiss Whether the Snackbar can be dismissed by the user.
 * @param icon The icon to be displayed in the Snackbar.
 * @param subTitle The subtitle to be displayed in the Snackbar.
 * @param actionText The text for the action button in the Snackbar.
 * @param duration The duration for which the Snackbar will be displayed.
 * @param continuation A cancellable continuation for handling user interactions with the Snackbar.
 * @param animationBehavior The animation behavior for the Snackbar.
 */

class SnackbarMetadata(
    val message: String,
    val style: SnackbarStyle,
    val enableDismiss: Boolean,
    val icon: FluentIcon?,
    val subTitle: String?,
    val actionText: String?,
    val duration: NotificationDuration,
    private val continuation: CancellableContinuation<NotificationResult>,
    val animationBehavior: AnimationBehavior
) : NotificationMetadata {

    override suspend fun clicked() {
        try {
            animationBehavior?.onClickAnimation()
            if (continuation.isActive) continuation.resume(NotificationResult.CLICKED)
        } catch (e: Exception) {
            // This can happen if there is a race condition b/w two events. In that case, we ignore the second event.
        }
    }

    override suspend fun dismiss() {
        try {
            animationBehavior?.onDismissAnimation()
            if (continuation.isActive) continuation.resume(NotificationResult.DISMISSED)
        } catch (e: Exception) {
            // This can happen if there is a race condition b/w two events. In that case, we ignore the second event.
        }
    }

    override suspend fun timedOut() {
        try {
            animationBehavior?.onTimeoutAnimation()
            if (continuation.isActive) continuation.resume(NotificationResult.TIMEOUT)
        } catch (e: Exception) {
            // This can happen if there is a race condition b/w two events. In that case, we ignore the second event.
        }
    }
}

class SnackbarState {
    private val mutex = Mutex()

    var currentSnackbar by mutableStateOf<SnackbarMetadata?>(null)

    suspend fun showSnackbar(
        message: String,
        style: SnackbarStyle = SnackbarStyle.Neutral,
        enableDismiss: Boolean = false,
        icon: FluentIcon? = null,
        subTitle: String? = null,
        actionText: String? = null,
        duration: NotificationDuration = NotificationDuration.SHORT,
        animationBehavior: AnimationBehavior = AnimationBehavior()
    ): NotificationResult {
        mutex.withLock {
            try {
                return suspendCancellableCoroutine { it ->
                    currentSnackbar = SnackbarMetadata(
                        message,
                        style,
                        enableDismiss,
                        icon,
                        subTitle,
                        actionText,
                        duration,
                        it,
                        animationBehavior
                    )
                }
            } finally {
                currentSnackbar = null
                delay(75)
            }
        }
    }
}

/**
 * Snackbar are transient Notification control used to deliver information which can be timedout or
 * can be cleared by user pressing the CTA or dismiss icon. Snackbar is rendered using [SnackbarMetadata]
 * which saves all the information about it. Snackbar shows one message at a time and uses a [SnackbarState]
 * to save all the requests. Multiple styles of Snackbar are supported using [SnackbarStyle].
 *
 * @param snackbarState Queue to store all the Notification requests.
 * @param modifier Optional modifier to be applied to Snackbar.
 * @param snackbarTokens Optional Tokens to redesign Snackbar.
 */
@Composable
fun Snackbar(
    snackbarState: SnackbarState,
    modifier: Modifier = Modifier,
    snackbarTokens: SnackBarTokens? = null
) {
    val metadata: SnackbarMetadata = snackbarState.currentSnackbar ?: return
    val scope = rememberCoroutineScope()

    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.
    val token = snackbarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.SnackbarControlType] as SnackBarTokens

    val snackBarInfo = SnackBarInfo(metadata.style, !metadata.subTitle.isNullOrBlank())
    var textPaddingValues =
        if (metadata.actionText == null && !metadata.enableDismiss) PaddingValues(
            start = 16.dp,
            top = 12.dp,
            bottom = 12.dp,
            end = 16.dp
        ) else PaddingValues(start = 16.dp, top = 12.dp, bottom = 12.dp)

    NotificationContainer(
        notificationMetadata = metadata,
        hasIcon = metadata.icon != null,
        hasAction = metadata.actionText != null,
        duration = metadata.duration,
        animationBehavior = metadata.animationBehavior,
    ) { animationVariables ->
        Row(
            modifier
                .graphicsLayer(
                    scaleX = animationVariables.scale.value,
                    scaleY = animationVariables.scale.value,
                    alpha = animationVariables.alpha.value,
                    translationX = animationVariables.offsetX.value,
                    translationY = animationVariables.offsetY.value
                )
                .padding(horizontal = 16.dp)
                .defaultMinSize(minHeight = 52.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(token.backgroundBrush(snackBarInfo))
                .semantics {
                    liveRegion = LiveRegionMode.Polite
                }
                .testTag(SNACK_BAR),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (metadata.icon != null && metadata.icon.isIconAvailable()) {
                Box(
                    modifier = Modifier
                        .testTag(SNACK_BAR_ICON)
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
                ) {
                    Icon(
                        metadata.icon,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                            .size(token.leftIconSize(snackBarInfo)),
                        tint = token.iconColor(snackBarInfo)
                    )
                }
            }
            Column(
                Modifier
                    .weight(1F)
                    .padding(textPaddingValues)
            ) {
                BasicText(
                    text = metadata.message,
                    style = token.titleTypography(snackBarInfo)
                )
                if (!metadata.subTitle.isNullOrBlank()) {
                    BasicText(
                        text = metadata.subTitle,
                        style = token.subtitleTypography(snackBarInfo),
                        modifier = Modifier.testTag(SNACK_BAR_SUBTITLE)
                    )
                }

            }

            if (metadata.actionText != null) {
                Button(
                    onClick = {
                        scope.launch {
                            metadata.clicked()
                        }
                    },
                    modifier = Modifier
                        .testTag(SNACK_BAR_ACTION_BUTTON)
                        .then(
                            if (!metadata.enableDismiss)
                                Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                            else
                                Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                        ),
                    text = metadata.actionText,
                    style = ButtonStyle.TextButton,
                    size = ButtonSize.Small,
                    buttonTokens = object : ButtonTokens() {
                        @Composable
                        override fun textColor(buttonInfo: ButtonInfo): StateColor {
                            return StateColor(
                                rest = token.iconColor(snackBarInfo),
                                pressed = token.iconColor(snackBarInfo),
                                focused = token.iconColor(snackBarInfo),
                            )
                        }
                    }
                )
            }

            if (metadata.enableDismiss) {
                Box(
                    modifier = Modifier
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            enabled = true,
                            role = Role.Image,
                            onClickLabel = "Dismiss",
                            onClick = {
                                scope.launch {
                                    metadata.dismiss()
                                }
                            }
                        )
                        .testTag(SNACK_BAR_DISMISS_BUTTON)
                ) {
                    Icon(
                        Icons.Filled.Close,
                        "Dismiss",
                        modifier = Modifier
                            .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 16.dp)
                            .size(token.dismissIconSize(snackBarInfo)),
                        tint = token.iconColor(snackBarInfo)
                    )
                }
            }
        }
    }
}
