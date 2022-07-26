package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.controls.Button
import com.microsoft.fluentui.listitem.ListItem
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.launch

class V2ListItemViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                CreateActivityUI()
            }
        }
    }
}
@Composable
private fun CreateActivityUI() {
    Column(modifier = Modifier.fillMaxWidth().padding(20.dp)) {
        Text(
            text = "List",
            fontSize = 25.sp,
            color = Color.Green
        )
        singleLineList()
        multiLineList()
    }
}

@Composable
fun singleLineList() {
        ListItem(title="LIST", subTitle = "subTitle", leftAccessoryView = leftView(), rightAccessoryView = rightView())
}
@Composable
fun multiLineList() {
    ListItem(title="LIST", subTitle = " long list of text", leftAccessoryView = leftView(), rightAccessoryView = rightView())
}


@Composable
fun leftView(): @Composable (() -> Unit) {
    val scaffoldState = rememberScaffoldState() // this contains the `SnackbarHostState`
    val coroutineScope = rememberCoroutineScope()
    return {
        Row() {
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(
                        50.dp,
                        shape = RoundedCornerShape(10),
                        ambientColor = Color.Red,
                        spotColor = Color.Green
                    ),

                onClick = {
                    coroutineScope.launch {
                        val snackbarResult = scaffoldState.snackbarHostState.showSnackbar(
                            message = "This is your message",
                            actionLabel = "Do something."
                        )
                        when (snackbarResult) {
                            SnackbarResult.Dismissed -> Log.d("SnackbarDemo", "Dismissed")
                            SnackbarResult.ActionPerformed -> Log.d("SnackbarDemo", "Snackbar's button clicked")
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun rightView(): @Composable (() -> Unit)? {
    return {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(
                        50.dp,
                        shape = RoundedCornerShape(10),
                        ambientColor = Color.Red,
                        spotColor = Color.Green
                    ),

                onClick = { }
            )
            Button(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .shadow(
                        50.dp,
                        shape = RoundedCornerShape(10),
                        ambientColor = Color.Red,
                        spotColor = Color.Green
                    ),

                onClick = { }
            )
        }
    }
}

