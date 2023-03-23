package com.microsoft.fluentui.tokenized.notification

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.Role
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
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume
import androidx.compose.material.Icon as MaterialIcon

private val LocalSnackBarTokens = compositionLocalOf { SnackBarTokens() }
private val LocalSnackBarInfo = compositionLocalOf { SnackBarInfo(SnackbarStyle.Neutral) }

// TAGS FOR TESTING
private val SNACKBAR = "Snackbar"
private val ICON = "ICON"
private val SUBTITLE = "Subtitle"
private val ACTION_BUTTON = "Action Button"
private val DISMISS_BUTTON = "Dismiss Button"

class SnackbarMetadata(
    val message: String,
    val style: SnackbarStyle,
    val enableDismiss: Boolean,
    val icon: FluentIcon?,
    val subTitle: String?,
    val actionText: String?,
    duration: NotificationDuration,
    val continuation: CancellableContinuation<NotificationResult>
) : NotificationMetadata {
    override var notificationDuration: NotificationDuration = duration

    override fun clicked() {
        if (continuation.isActive) continuation.resume(NotificationResult.CLICKED)
    }

    override fun dismiss() {
        if (continuation.isActive) continuation.resume(NotificationResult.DISMISSED)
    }

    override fun timedout() {
        if (continuation.isActive) continuation.resume(NotificationResult.TIMEOUT)
    }
}

class SnackbarQueue {
    private val mutex = Mutex()

    var currentSnackbar by mutableStateOf<SnackbarMetadata?>(null)

    suspend fun enqueue(
        message: String,
        style: SnackbarStyle = SnackbarStyle.Neutral,
        enableDismiss: Boolean = false,
        icon: FluentIcon? = null,
        subTitle: String? = null,
        actionText: String? = null,
        duration: NotificationDuration = NotificationDuration.SHORT
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
                        it
                    )
                }
            } finally {
                currentSnackbar = null
                delay(75)
            }
        }
    }
}

@Composable
fun Snackbar(
    snackBarQueue: SnackbarQueue,
    modifier: Modifier = Modifier,
    snackbarTokens: SnackBarTokens? = null
) {
    val metadata: SnackbarMetadata = snackBarQueue.currentSnackbar ?: return

    val token = snackbarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.Snackbar] as SnackBarTokens

    CompositionLocalProvider(
        LocalSnackBarTokens provides token,
        LocalSnackBarInfo provides SnackBarInfo(metadata.style, !metadata.subTitle.isNullOrBlank())
    ) {
        NotificationContainer(notificationMetadata = metadata) { alpha, scale ->
            Row(
                modifier
                    .graphicsLayer(scaleX = scale.value, scaleY = scale.value, alpha = alpha.value)
                    .padding(horizontal = 16.dp)
                    .defaultMinSize(minHeight = 52.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(getSnackBarTokens().backgroundColor(getSnackBarInfo()))
                    .testTag(SNACKBAR),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (metadata.icon != null && metadata.icon.isIconAvailable()) {
                    Box(
                        modifier = Modifier
                            .testTag(ICON)
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
                                .size(getSnackBarTokens().leftIconSize(getSnackBarInfo())),
                            tint = getSnackBarTokens().iconColor(getSnackBarInfo())
                        )
                    }
                }

                if (metadata.subTitle.isNullOrBlank()) {
                    Text(
                        text = metadata.message,
                        modifier = Modifier
                            .weight(1F)
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
                        style = getSnackBarTokens().titleTypography(getSnackBarInfo())
                    )
                } else {
                    Column(
                        Modifier
                            .weight(1F)
                            .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                    ) {
                        Text(
                            text = metadata.message,
                            style = getSnackBarTokens().titleTypography(getSnackBarInfo())
                        )
                        Text(
                            text = metadata.subTitle,
                            style = getSnackBarTokens().subtitleTypography(getSnackBarInfo()),
                            modifier = Modifier.testTag(SUBTITLE)
                        )
                    }
                }

                if (metadata.actionText != null) {
                    Button(
                        onClick = { metadata.clicked() },
                        modifier = Modifier
                            .testTag(ACTION_BUTTON)
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
                                    rest = getSnackBarTokens().iconColor(getSnackBarInfo()),
                                    pressed = getSnackBarTokens().iconColor(getSnackBarInfo()),
                                    focused = getSnackBarTokens().iconColor(getSnackBarInfo()),
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
                                onClick = { metadata.dismiss() }
                            )
                            .testTag(DISMISS_BUTTON)
                    ) {
                        MaterialIcon(
                            Icons.Filled.Close,
                            "Dismiss",
                            modifier = Modifier
                                .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 16.dp)
                                .size(getSnackBarTokens().dismissIconSize(getSnackBarInfo())),
                            tint = getSnackBarTokens().iconColor(getSnackBarInfo())
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun getSnackBarTokens(): SnackBarTokens {
    return LocalSnackBarTokens.current
}

@Composable
private fun getSnackBarInfo(): SnackBarInfo {
    return LocalSnackBarInfo.current
}
