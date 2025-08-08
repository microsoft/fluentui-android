package com.microsoft.fluentui.tokenized.notification

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.util.clickableWithTooltip
import com.microsoft.fluentui.util.dpToPx
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class StackableSnackbarBehavior : AnimationBehavior() {
    override suspend fun onShowAnimation() {
        animationVariables.offsetX = Animatable(100f)
        coroutineScope {
            launch { // move the entire stack up, pass offset Y in each card
                animationVariables.offsetY = Animatable(0f)
                animationVariables.offsetY.animateTo(
                    targetValue = -100f,
                    animationSpec = tween(
                        easing = LinearEasing,
                        durationMillis = 200,
                    )
                )
            }
            launch {
                animationVariables.alpha.animateTo(
                    targetValue = 1F,
                    animationSpec = tween(
                        easing = LinearEasing,
                        durationMillis = 200,
                    )
                )
            }

//            launch {
//                animationVariables.offsetX.animateTo(
//                    targetValue = 0f,
//                    animationSpec = tween(
//                        easing = LinearEasing,
//                        durationMillis = 200,
//                    )
//                )
//            }
        }
    }

    override suspend fun onDismissAnimation() {
        animationVariables.offsetX.animateTo(
            0f,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
        animationVariables.alpha.animateTo(
            0F,
            animationSpec = tween(
                easing = LinearEasing,
                durationMillis = 150,
            )
        )
    }
}
@Composable
fun Modifier.swipeToDismissFromStack(
    animationVariables: AnimationVariables,
    scope: CoroutineScope,
    onDismiss: () -> Unit,
): Modifier {
    val configuration = LocalConfiguration.current
    val dismissThreshold =
        dpToPx(configuration.screenWidthDp.dp) * 0.33f  // One-third of screen width
    return this.pointerInput(Unit) {
        detectHorizontalDragGestures(
            onDragEnd = {
                if (animationVariables.offsetX.value < -dismissThreshold) {
                    scope.launch {
                        onDismiss()
                    }
                } else {
                    scope.launch {
                        animationVariables.offsetX.animateTo(
                            0f,
                            animationSpec = tween(300)
                        )
                    }
                }
            },
            onHorizontalDrag = { _, dragAmount ->
                scope.launch {
                    animationVariables.offsetX.snapTo(animationVariables.offsetX.value + dragAmount)
                }
            }
        )
    }
}
@Composable
fun StackableSnackbar(){
    var enableDialog by remember{ mutableStateOf(false) }
    Column() {

        if (!enableDialog) {
            SingleSnackbarTile()
            SingleSnackbarTile()
            SingleSnackbarTile()
        } else {
            val popupProperties = PopupProperties(
                focusable = true
            )
            Popup(
                onDismissRequest = {
                    enableDialog = false
                }
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                    BasicCard(modifier = Modifier.padding(10.dp)) {
                        BasicText("This is a Stackable Snackbar")
                    }
                }
            }
        }
    }
}


@Composable
private fun SingleSnackbarTile(){
    var enableDialog by remember{ mutableStateOf(false) }
    var stackableSnackbarBehavior: StackableSnackbarBehavior = remember {
        StackableSnackbarBehavior()
    }
    var animationVariables = stackableSnackbarBehavior.animationVariables
    val scope = rememberCoroutineScope()
    var isShown by remember { mutableStateOf(false) }
    Button(
        onClick = {
            scope.launch {
                if(isShown) {
                    stackableSnackbarBehavior.onShowAnimation()
                }
                else{
                    stackableSnackbarBehavior.onDismissAnimation()
                }
            }
            isShown = !isShown
        }
    )
    if(isShown) {
        BasicCard(
            modifier = Modifier.padding(10.dp).graphicsLayer(
                scaleX = animationVariables.scale.value,
                scaleY = animationVariables.scale.value,
                alpha = animationVariables.alpha.value,
                translationX = animationVariables.offsetX.value,
                translationY = animationVariables.offsetY.value
            ).swipeToDismissFromStack(
                animationVariables = animationVariables,
                scope = scope,
                onDismiss = {
                    isShown = false
                }
            ).clickableWithTooltip(
                onClick = {
                    // enableDialog = true

                },
                tooltipText = "Snackbar clicked",
                tooltipEnabled = true
            )
        ) {
            BasicText("Click here to show Stackable Snackbar")
        }
        Spacer(modifier = Modifier.height(10.dp))
    }
}