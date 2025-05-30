package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.AccessibilityManager
import androidx.compose.ui.platform.LocalAccessibilityManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay

interface NotificationMetadata {
    fun clicked(scope: CoroutineScope? = null)

    fun dismiss(scope: CoroutineScope? = null )

    fun timedOut(scope: CoroutineScope? = null)
}

open class AnimationVariables {
    open var alpha = Animatable(0F)
    open var scale = Animatable(0.8F)
    open var offsetX = Animatable(0f)
    open var offsetY = Animatable(0f)
}

open class AnimationBehavior {
    open var animationVariables: AnimationVariables = AnimationVariables()

    open suspend fun onShowAnimation() {
        animationVariables.alpha.animateTo(
            1F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
        animationVariables.scale.animateTo(
            1F,
            animationSpec = tween(
                easing = FastOutSlowInEasing,
                durationMillis = 150,
            )
        )
    }

    open suspend fun onClickAnimation() {
        // fade out
        animationVariables.alpha.animateTo(
            0F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }

    open suspend fun onDismissAnimation() {
        // fade out
        animationVariables.alpha.animateTo(
            0F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }

    open suspend fun onTimeoutAnimation() {
        // fade out
        animationVariables.alpha.animateTo(
            0F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }
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
    animationBehavior: AnimationBehavior,
    scope: CoroutineScope,
    content: @Composable ((animationVariables: AnimationVariables) -> Unit)
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
        notificationMetadata.timedOut(scope)
    }

    LaunchedEffect(notificationMetadata) {
        animationBehavior.onShowAnimation()
    }

    content(animationBehavior.animationVariables)
}