/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */
package com.microsoft.fluentui.compose

import androidx.compose.animation.core.snap
import androidx.compose.runtime.MonotonicFrameClock
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [28])
class NestedScrollConnectionTest {
    @Test
    fun postFlingDoesNotConsumeHorizontalVelocity() = withFrames {
        val swipeable = swipeableState()
        val anchored = anchoredState()
        val connections = listOf(
            swipeable.PreUpPostDownNestedScrollConnection,
            swipeable.PostDownNestedScrollConnection,
            swipeable.NonDismissiblePostDownNestedScrollConnection,
            anchored.PreUpPostDownNestedScrollConnection,
            anchored.PostDownNestedScrollConnection,
            anchored.NonDismissiblePostDownNestedScrollConnection
        )
        for (connection in connections) {
            assertEquals(Velocity.Zero, connection.onPostFling(Velocity.Zero, Velocity(500f, 0f)))
            assertEquals(Velocity(0f, 100f), connection.onPostFling(Velocity.Zero, Velocity(500f, 100f)))
        }
    }

    @Test
    fun preFlingConsumesOnlyVerticalVelocity() = withFrames {
        val connections = listOf<NestedScrollConnection>(
            swipeableState().PreUpPostDownNestedScrollConnection,
            swipeableState().NonDismissiblePreUpPostDownNestedScrollConnection,
            anchoredState().PreUpPostDownNestedScrollConnection,
            anchoredState().NonDismissiblePreUpPostDownNestedScrollConnection
        )
        for (connection in connections) {
            assertEquals(Velocity(0f, -1000f), connection.onPreFling(Velocity(500f, -1000f)))
        }
    }

    private suspend fun swipeableState(): SwipeableState<Int> {
        val state = SwipeableState(initialValue = 1, animationSpec = snap())
        val anchors = mapOf(0f to 0, 100f to 1)
        state.ensureInit(anchors)
        state.anchors = anchors
        state.processNewAnchors(emptyMap(), anchors)
        return state
    }

    private fun anchoredState() = AnchoredDraggableState(
        initialValue = 1,
        anchors = DraggableAnchors {
            0 at 0f
            1 at 100f
        },
        positionalThreshold = { it / 2 },
        velocityThreshold = { 125f },
        animationSpec = snap()
    )

    private fun withFrames(block: suspend () -> Unit) {
        val clock = object : MonotonicFrameClock {
            private var timeNanos = 0L
            override suspend fun <R> withFrameNanos(onFrame: (Long) -> R): R {
                timeNanos += 16_000_000L
                return onFrame(timeNanos)
            }
        }
        runBlocking(clock) { block() }
    }
}
