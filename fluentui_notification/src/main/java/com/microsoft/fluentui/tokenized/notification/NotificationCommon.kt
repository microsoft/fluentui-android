package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay

interface NotificationMetadata {
    var notificationDuration: NotificationDuration

    fun clicked()

    fun dismiss()

    fun timedout()
}

enum class NotificationResult {
    TIMEOUT,
    DISMISSED,
    CLICKED
}

enum class NotificationDuration {
    SHORT,
    LONG,
    INDEFINITE;

    fun convertToMillis(): Long {
        return when (this) {
            INDEFINITE -> Long.MAX_VALUE
            LONG -> 10000L
            SHORT -> 4000L
        }
    }
}

@Composable
internal fun NotificationContainer(
    notificationMetadata: NotificationMetadata,
    content: @Composable (
        (
        Animatable<Float, AnimationVector1D>,
        Animatable<Float, AnimationVector1D>
    ) -> Unit
    )
) {
    LaunchedEffect(notificationMetadata) {
        delay(notificationMetadata.notificationDuration.convertToMillis())
        notificationMetadata.timedout()
    }

    val alpha = remember { Animatable(0F) }
    val scale = remember { Animatable(0.8F) }

    LaunchedEffect(notificationMetadata) {
        alpha.animateTo(
            1F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }
    LaunchedEffect(notificationMetadata) {
        scale.animateTo(
            1F,
            animationSpec = tween(
                easing = FastOutSlowInEasing,
                durationMillis = 150,
            )
        )
    }

    content(alpha, scale)

}