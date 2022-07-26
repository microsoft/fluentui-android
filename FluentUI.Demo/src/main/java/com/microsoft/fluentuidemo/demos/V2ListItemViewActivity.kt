package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.controls.Button
import com.microsoft.fluentui.controls.CheckBox
import com.microsoft.fluentui.controls.ToggleSwitch
import com.microsoft.fluentui.listitem.ListItem
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
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
    Column(modifier = Modifier
        .fillMaxWidth()) {

        singleIconList()
        threeButtonList()
        singleLineList()
        multiLineList()
        overFlowButtonList()
        googleList()
    }
}

@Composable
fun singleIconList() {
        ListItem(title="Text", leftAccessoryView = singleIconLeftView(), rightAccessoryView = checkboxRightView())
}
@Composable
fun threeButtonList() {
    ListItem(leftAccessoryView = threeButtonLeftView(), rightAccessoryView = rightViewOverFlowMenu())
}
@Composable
fun singleLineList() {
    ListItem(title="LIST", leftAccessoryView = leftView(), rightAccessoryView = rightViewOverFlowMenu())
}
@Composable
fun multiLineList() {
    ListItem(title="LIST", subTitle = " long list of text", leftAccessoryView = leftView(), rightAccessoryView = rightViewOverFlowMenu())
}
@Composable
fun overFlowButtonList() {
    ListItem(leftAccessoryView = leftView(), rightAccessoryView = rightViewOverFlowMenu())
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun googleList(){
    androidx.compose.material.ListItem(icon = threeButtonLeftView(), text = checkboxRightView(), trailing = checkboxRightView())
}

@Composable
fun singleIconLeftView(): @Composable (() -> Unit) {

    return {
        Row() {
            Icon(Icons.Outlined.Email, contentDescription = "Email")
        }
    }
}
@Composable
fun checkboxRightView(): @Composable (() -> Unit){
    return{
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)){
            Text("Value")
            CheckBox(onCheckedChanged = {})
        }
    }
}
@Composable
fun leftView(): @Composable (() -> Unit) {

    return {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Outlined.ThumbUp, contentDescription = "thumbup")
            Icon(Icons.Outlined.AccountBox, contentDescription = "box")
            Icon(Icons.Outlined.Send, contentDescription = "send")
        }
    }
}
@Composable
fun rightViewOverFlowMenu(): @Composable (() -> Unit){
    return{
        Row {
            ToggleSwitch()
        }
    }
}
@Composable
fun threeButtonLeftView(): @Composable (() -> Unit)? {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    return {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Button(
                style = ButtonStyle.OutlinedButton,
                text = "Text",
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "This is your message"
                        )
                    }
                }
            )
            Button(
                style = ButtonStyle.OutlinedButton,
                text = "Text",
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "This is your message"
                        )
                    }
                }
            )
            Button(
                style = ButtonStyle.OutlinedButton,
                text = "Text",
                onClick = {
                    coroutineScope.launch {
                        scaffoldState.snackbarHostState.showSnackbar(
                            message = "This is your message"
                        )
                    }
                }
            )
        }
    }
}

