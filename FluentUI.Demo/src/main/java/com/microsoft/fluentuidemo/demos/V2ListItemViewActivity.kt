package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material.icons.rounded.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.controls.Button
import com.microsoft.fluentui.controls.CheckBox
import com.microsoft.fluentui.controls.ToggleSwitch
import com.microsoft.fluentui.listitem.OneLine
import com.microsoft.fluentui.listitem.SectionHeader
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.GlobalTokens.NeutralColorTokens.Grey96
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

    Box(
        Modifier
            .fillMaxSize()
            .background(FluentTheme.globalTokens.neutralColor[Grey96])){
        LazyColumn( ){
            item {
                Box{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clip(RoundedCornerShape(10.dp))) {
                        sectionHeaderCreation()
                    }
                }
            }
            item {
                Box{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)
                        .clip(RoundedCornerShape(10.dp))) {
                        singleIconList()
                        threeButtonList()
                        randomList()
                        singleLineList()
                    }
                }
            }

        }
    }




}

@Composable
fun sectionHeaderCreation(){
    SectionHeader.ListItem(title = "PinnedList", accessoryIcon = arrowRightView(), list = sampleList())
}
@Composable
fun sampleList(): @Composable (() -> Unit){
    return {
        Box(
            Modifier
                .fillMaxSize()){
            Column() {
                singleIconList()
                threeButtonList()
            }
        }
    }
}
@Composable
fun singleIconList() {
        OneLine.ListItem(text ="Text", leftIconAccessoryView = singleIconLeftView(), rightAccessoryView = checkboxRightView(), onClick ={

        })
}
@Composable
fun threeButtonList() {
    OneLine.ListItem(text = "", leftButtonAccessoryView = threeButtonLeftView(), rightAccessoryView = rightViewOverFlowMenu(), onClick ={})
}
@Composable
fun singleLineList() {
    OneLine.ListItem(text="Text", leftIconAccessoryView = leftView(), rightAccessoryView = rightViewOverFlowMenu(), onClick ={})
}
@Composable
fun multiLineList() {
    OneLine.ListItem(text="Text", leftIconAccessoryView = leftView(), rightAccessoryView = rightViewOverFlowMenu(), onClick ={})
}
@Composable
fun overFlowButtonList() {
    OneLine.ListItem(text = "Text", leftIconAccessoryView = leftViewRowless(), rightAccessoryView = rightViewOverFlowMenu(), onClick ={})
}

@Composable
fun randomList(){
    OneLine.ListItem(text ="", leftButtonAccessoryView = threeButtonLeftView(), rightAccessoryView = radioButtonRightView(), onClick ={})
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun googleList(){
    androidx.compose.material.ListItem(icon = leftViewRowless(), trailing = rightViewOverFlowMenu(), text = {})
}

@Composable
fun singleIconLeftView(): @Composable (() -> Unit) {

    return {
        Icon(Icons.Outlined.Email, contentDescription = "Email")
    }
}
@Composable
fun checkboxRightView(): @Composable (() -> Unit){
    var checked by remember { mutableStateOf(true) }
    return{
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text("Value")
            CheckBox(enabled = checked, checked = !checked, onCheckedChanged = {
                checked = !it
                if (checked) {
                } else {
                }
            })
        }

    }
}
@Composable
fun leftView(): @Composable (() -> Unit) {

    return {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Rounded.Email, contentDescription = "thumbup")
            Icon(Icons.Outlined.AccountBox, contentDescription = "box")
            Icon(Icons.Outlined.Send, contentDescription = "send")
        }

    }
}
@Composable
fun rightViewOverFlowMenu(): @Composable (() -> Unit){
    return{
        FluentTheme{
            var checked by remember { mutableStateOf(true) }
            var enabled by remember { mutableStateOf(false) }
            Row {
                ToggleSwitch(enabled, checked, {
                    checked = it
                    if (checked) {
                    } else {
                    }
                })
            }
        }

    }
}
@Composable
fun threeButtonLeftView(): @Composable (() -> Unit)? {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    return {
        LazyRow(Modifier.widthIn(min = 30.dp, max = 200.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            item{
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
            item{
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
            item{
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
}
@Composable
fun leftViewRowless(): @Composable (() -> Unit) {

    return {
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(Icons.Outlined.ThumbUp, contentDescription = "thumbup")
            Icon(Icons.Outlined.AccountBox, contentDescription = "box")
            Icon(Icons.Outlined.Send, contentDescription = "send")
        }
    }
}
@Composable
fun arrowRightView(): @Composable (() -> Unit){
    return{
        Icon(Icons.Outlined.ArrowForward, contentDescription = "arrow")
    }
}

@Composable
fun radioButtonRightView(): @Composable (() -> Unit){
    return{
        RadioButton(selected = true, onClick = { })
    }
}

