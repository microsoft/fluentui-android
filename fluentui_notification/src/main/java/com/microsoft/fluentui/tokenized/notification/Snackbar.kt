package com.microsoft.fluentui.tokenized.snackbar

import androidx.compose.animation.core.*
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.SnackbarDuration
import com.microsoft.fluentui.tokenized.controls.Button
import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.delay
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.coroutines.resume

class SnackbarMetadata(
    val message: String,
    val enableDismiss: Boolean,
    val icon: FluentIcon?,
    val subTitle: String?,
    val actionText: String?,
    val duration: SnackbarDuration,
    val continuation: CancellableContinuation<SnackbarResult>
) {
    fun clicked() {
        if (continuation.isActive) continuation.resume(SnackbarResult.CLICKED)
    }

    fun dismiss() {
        if (continuation.isActive) continuation.resume(SnackbarResult.DISMISSED)
    }

    fun timedout() {
        if (continuation.isActive) continuation.resume(SnackbarResult.TIMEOUT)
    }
}

enum class SnackbarResult {
    TIMEOUT,
    DISMISSED,
    CLICKED
}

class SnackbarQueue {
    private val mutex = Mutex()

    var currentSnackbar by mutableStateOf<SnackbarMetadata?>(null)

    suspend fun enqueue(
        message: String,
        enableDismiss: Boolean = false,
        icon: FluentIcon? = null,
        subTitle: String? = null,
        actionText: String? = null,
        duration: SnackbarDuration = SnackbarDuration.SHORT
    ): SnackbarResult {
        mutex.withLock {
            try {
                return suspendCancellableCoroutine { it ->
                    currentSnackbar = SnackbarMetadata(message, enableDismiss, icon, subTitle, actionText, duration, it)
                }
            } finally {
                currentSnackbar = null
                delay(75)
            }
        }
    }
}

@Composable
fun Snackbar(snackBarQueue: SnackbarQueue) {
    val metadata: SnackbarMetadata = snackBarQueue.currentSnackbar ?: return

    LaunchedEffect(metadata) {
        delay(metadata.duration.convertToMillis())
        metadata.timedout()
    }

    val alpha = remember { Animatable(0F) }
    val scale = remember { Animatable(0.8F) }

    LaunchedEffect(metadata) {
        alpha.animateTo(
            1F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }
    LaunchedEffect(metadata) {
        scale.animateTo(
            1F,
            animationSpec = tween(
                easing = FastOutSlowInEasing,
                durationMillis = 150,
            )
        )
    }

    Row(
        Modifier
            .padding(horizontal = 16.dp)
            .defaultMinSize(minHeight = 52.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.DarkGray)
            .graphicsLayer(scaleX = scale.value, scaleY = scale.value, alpha = alpha.value),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if(metadata.icon != null && metadata.icon.isIconAvailable()) {
            Box(
                modifier = Modifier
                    .then(if(metadata.icon.onClick != null) {
                        Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = rememberRipple(),
                            enabled = true,
                            role = Role.Image,
                            onClick = metadata.icon.onClick!!
                        )} else Modifier)
            ) {
                Icon(metadata.icon,
                    modifier = Modifier
                        .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                        .size(24.dp),
                    tint = Color.White)
            }
        }

        if(metadata.subTitle.isNullOrBlank()) {
            Text(
                text = metadata.message,
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 16.dp, top = 12.dp, bottom = 12.dp),
                color = Color.White
            )
        } else {
            Column(Modifier
                .weight(1F)
                .padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
            ) {
                Text(
                    text = metadata.message,
                    color = Color.White
                )
                Text(
                    text = metadata.subTitle,
                    color = Color.White
                )
            }
        }

        if (metadata.actionText != null) {
            Button(
                onClick = { metadata.clicked() },
                modifier = Modifier.then(
                    if(!metadata.enableDismiss)
                        Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
                    else
                        Modifier.padding(start = 16.dp, top = 12.dp, bottom = 12.dp)
                ),
                text = metadata.actionText,
                style = ButtonStyle.TextButton,
                size = ButtonSize.Small
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
            ) {
                androidx.compose.material.Icon(
                    Icons.Filled.Close,
                    "Dismiss",
                    modifier = Modifier
                        .padding(start = 12.dp, top = 12.dp, bottom = 12.dp, end = 16.dp)
                        .size(20.dp),
                    tint = Color.White)
            }
        }
    }
}
