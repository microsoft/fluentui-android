package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import kotlinx.coroutines.delay

interface NotificationMetadata {
    fun clicked()

    fun dismiss()

    fun timedOut()
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

    fun convertToMillis(
        hasIcon: Boolean,
        hasAction: Boolean,
        accessibilityManager: AccessibilityManager?
    ): Long {
        val actualDuration = when (this) {
            INDEFINITE -> Long.MAX_VALUE
            LONG -> 10000L
            SHORT -> 4000L
        }

        if (accessibilityManager == null)
            return actualDuration

        return accessibilityManager.calculateRecommendedTimeoutMillis(
            actualDuration,
            containsIcons = true,
            containsText = hasIcon,
            containsControls = hasAction
        )
    }
}

@Composable
internal fun NotificationContainer(
    notificationMetadata: NotificationMetadata,
    hasIcon: Boolean,
    hasAction: Boolean,
    duration: NotificationDuration,
    content: @Composable (
        (
        Animatable<Float, AnimationVector1D>,
        Animatable<Float, AnimationVector1D>
    ) -> Unit
    )
) {
    val accessibilityManager = LocalAccessibilityManager.current
    LaunchedEffect(notificationMetadata) {
        delay(
            duration.convertToMillis(
                hasIcon = hasIcon,
                hasAction = hasAction,
                accessibilityManager = accessibilityManager
            )
        )
        notificationMetadata.timedOut()
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