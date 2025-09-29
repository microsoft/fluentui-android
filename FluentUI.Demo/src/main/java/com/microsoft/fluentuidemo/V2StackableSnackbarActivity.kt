package com.microsoft.fluentuidemo.demos

import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.notification.Scrim
import com.microsoft.fluentui.tokenized.notification.SnackBarItemModel
import com.microsoft.fluentui.tokenized.notification.SnackBarStack
import com.microsoft.fluentui.tokenized.notification.SnackBarStackConfig
import com.microsoft.fluentui.tokenized.notification.rememberSnackBarStackState
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class V2StackableSnackbarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-36"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-34"

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        setActivityContent {
            SnackBarStackDemoLayout(context = context)
        }
    }
}

@Composable
fun BackgroundContent(context: V2StackableSnackbarActivity) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        repeat(5) { index ->
            Button(
                onClick = {
                    Toast.makeText(context, "Button #$index pressed", Toast.LENGTH_SHORT).show()
                },
                text = "Button #$index",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            )
            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(24.dp))

        repeat(20) { index ->
            BasicText(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}

@Composable
fun SnackBarStackDemoLayout(context: V2StackableSnackbarActivity) {
    Box() {
        val stackState = rememberSnackBarStackState(
            maxExpandedSize = 10,
            maxCollapsedSize = 5
        )
        var counter by rememberSaveable { mutableIntStateOf(0) }
        val scope = rememberCoroutineScope()
        BackgroundContent(context)
        Scrim(
            isActivated = stackState.expanded && stackState.sizeVisible() > 0,
            onDismiss = {}
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {

            SnackBarStack(
                state = stackState,
                snackBarStackConfig = SnackBarStackConfig(
                    snackbarGapWhenExpanded = 10.dp
                )
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                Button(onClick = {
                    val id = counter++

                    stackState.addSnackbar(
                        SnackBarItemModel(
                            id = id.toString(),
                            message = "Snackbar #$id",
                            actionText = "Expand",
                            trailingIcon = FluentIcon(
                                Icons.Default.Close,
                                Icons.Default.Close,
                                contentDescription = "Close",
                                onClick = {
                                    scope.launch {
                                        stackState.removeSnackbarByIdWithAnimation(
                                            id.toString(),
                                            showLastHiddenSnackbarOnRemove = true
                                        )
                                    }
                                }
                            ),
                            onActionTextClicked = {
                                stackState.toggleExpandedState()
                            })
                    )
                }, text = "Add Snackbar")

                Spacer(modifier = Modifier.width(12.dp))

                Button(onClick = {
                    scope.launch {
                        stackState.hideFront()
                        delay(300)
                        stackState.removeFront()
                        stackState.showBack()
                    }
                }, text = "Remove latest")

                Spacer(modifier = Modifier.width(12.dp))
                Button(onClick = {
                    scope.launch {
                        val id = counter++
                        for (i in 0..15) {
                            stackState.addSnackbar(
                                SnackBarItemModel(
                                    id = "$id-$i",
                                    message = "Snackbar #$id-$i".repeat(i + 4),
                                    actionText = "Expand",
                                    trailingIcon = FluentIcon(
                                        Icons.Default.Close,
                                        Icons.Default.Close,
                                        contentDescription = "Close",
                                        onClick = {
                                            scope.launch {
                                                stackState.removeSnackbarByIdWithAnimation(
                                                    "$id-$i",
                                                    showLastHiddenSnackbarOnRemove = true
                                                )
                                            }
                                        }
                                    ),
                                    onActionTextClicked = {
                                        stackState.toggleExpandedState()
                                    })
                            )
                            delay(2000)
                        }
                    }
                }, text = "Keep Adding")
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}