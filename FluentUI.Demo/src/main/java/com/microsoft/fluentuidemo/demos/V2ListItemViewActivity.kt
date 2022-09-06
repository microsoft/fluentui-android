package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.GlobalTokens.NeutralColorTokens.Grey96
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.launch
import com.microsoft.fluentui.listitem.ListItem
import com.microsoft.fluentui.persona.AvatarSize
import com.microsoft.fluentui.persona.AvatarView
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Subtle
import kotlinx.android.synthetic.main.activity_avatar_view.*

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

    var show by remember { mutableStateOf(false) }
    var show2 by remember { mutableStateOf(true) }
    Box(
        Modifier
            .fillMaxSize()){
        LazyColumn( ){
            item {
                Box(){
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {
                        Text(text = "Section Header")
                        ListItem.SectionHeader(style = Subtle, title = "PinnedListPinnedListPinnedListPinnedListPinnedListPinnedListPinnedList", accessoryIcon = arrowView(), rightAccessory = overFlowMenuView(), actionTitle = "Action", onClick = {show = !show}, actionOnClick = {show = !show})
                        AnimatedVisibility(visible = show){
                            Box(
                                Modifier
                                    .fillMaxWidth(), contentAlignment = Alignment.Center){
                                Column {
                                    multiIconList()
                                    threeButtonList()
                                    threeLineTextList()
                                }
                            }
                        }
                    }
                }
            }
            item {
                Box{
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(15.dp)) {
                        Text(text = "One Line List")
                        multiIconList()
                        threeButtonList()
                        randomList()
                        singleLineList()
                        multiLineList()
                    }
                }
            }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text(text = "Multi Line List")
                    threeLineTextList()
                    twoLineTextList()
                }
            }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text(text = "Section Description")
                    oneLineDescription(onClick = {show2 = !show2})
                    if(show2){
                        twoLineDescription()
                        threeLineDescription()
                    }
                }
            }
            item{
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .padding(15.dp)) {
                    Text(text = "Avatar Carousel")
                    Row() {
                        avatarCarousel()
                        avatarCarousel()
                        avatarCarousel()
                        avatarCarousel()
                        avatarCarousel()
                        avatarCarousel()
                    }
                }
            }

        }
    }
}



@Composable
fun avatar(): @Composable (() -> Unit){
    return {
        Row{
            Icon(Icons.Outlined.AccountBox, contentDescription = "box")
        }
    }
}

@Composable
fun avatarCarousel(){
    ListItem.AvatarCarousel(firstName = "Lorem", lastName = "Ipsum", avatar = avatar(), size = AvatarCarouselSize.Large)
}

@Composable
fun oneLineDescription(onClick: (() -> Unit)?){
    ListItem.SectionDescription(action = true, iconAccessory = radioButtonView(), onActionClick = onClick, border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development.")
}

@Composable
fun twoLineDescription(){
    ListItem.SectionDescription(border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development. It controls the development process in the tasks of compilation")
}

@Composable
fun threeLineDescription(){
    ListItem.SectionDescription(action = true, border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development. It controls the development process in the tasks of compilation It controls the development process in the tasks of compilation")
}

@Composable
fun sectionHeaderCreation(show:Boolean){
    ListItem.SectionHeader(title = "PinnedList", accessoryIcon = arrowView(), rightAccessory = overFlowMenuView(), actionTitle = "Action", onClick = {})
}
@Composable
fun sampleList(): @Composable (() -> Unit){
    return {
            Column {
                multiIconList()
                threeButtonList()
                threeLineTextList()
            }

    }
}
var modifier = Modifier.border(width = 1.dp, color = Color(0xFFE0E0E0))

@Composable
fun multiIconList() {
        ListItem.OneLine(text ="Text", secondarySubTextMaxLines = 2, border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = radioButtonView(), rightAccessoryView = checkboxRightView(), onClick ={})
}
@Composable
fun threeButtonList() {
    ListItem.OneLine(text = "Text", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = threeButtonView(), rightAccessoryView = overFlowMenuView(), onClick ={})
}
@Composable
fun singleLineList() {
    ListItem.OneLine(text="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, rightAccessoryView = overFlowMenuView(), onClick ={})
}
@Composable
fun multiLineList() {
    ListItem.OneLine(text="Text", rightAccessoryView = threeIconsView(), onClick ={})
}
@Composable
fun overFlowButtonList() {
    ListItem.OneLine(text = "Text", border = BorderType.Bottom, borderInset = BorderInset.Large, rightAccessoryView = overFlowMenuView(), onClick ={})
}

@Composable
fun randomList(){
    ListItem.OneLine(text ="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = leftView(), rightAccessoryView = arrowView(), onClick ={})
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun googleList(){
    androidx.compose.material.ListItem(icon = threeIconsView(), trailing = overFlowMenuView(), text = {})
}
@Composable
fun threeLineTextList() {
    ListItem.OneLine(text="TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextxtTextTextTextTextTextxtTextTextTextTextTextxtTextTextTextTextText", secondarySubTextMaxLines = 2, subText = "subtext", secondarySubText = "secondarysecondarysecondarysecondarysecondarysecondarysecondary", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = multiIconLeftView(), rightAccessoryView = threeIconsView(), onClick ={})
}
@Composable
fun twoLineTextList() {
    ListItem.OneLine(text="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, subText = "subtext", rightAccessoryView = threeIconsView(), onClick ={})
}
@Composable
fun multiIconLeftView(): @Composable (() -> Unit) {

    return {
        Row() {
            Icon(Icons.Outlined.Email, contentDescription = "Email")
            Icon(Icons.Outlined.Email, contentDescription = "Email")
        }

    }
}
@Composable
fun checkboxRightView(): @Composable (() -> Unit){
    var checked by remember { mutableStateOf(true) }
    return{
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = CenterVertically) {
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
fun overFlowMenuView(): @Composable (() -> Unit){
    return{
        FluentTheme{
            var checked by remember { mutableStateOf(true) }
            var enabled by remember { mutableStateOf(false) }
            Row {
            }
        }

    }
}
@Composable
fun singleButton(): @Composable () -> Unit{
    return {
        Button(
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text",
            onClick = { }
        )
    }
}
@Composable
fun threeButtonView(): @Composable (() -> Unit)? {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    return {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                size = ButtonSize.Small,
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
                size = ButtonSize.Small,
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
                size = ButtonSize.Small,
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
@Composable
fun threeIconsView(): @Composable (() -> Unit) {

    return {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Icon(Icons.Outlined.ThumbUp, contentDescription = "thumbup")
            Icon(Icons.Outlined.AccountBox, contentDescription = "box")
            Icon(Icons.Outlined.Send, contentDescription = "send")
        }
    }
}
@Composable
fun arrowView(): @Composable (() -> Unit){
    return{
        Icon(Icons.Outlined.ArrowForward, contentDescription = "arrow")
    }
}

@Composable
fun radioButtonView(): @Composable (() -> Unit){
    return{
        Row() {
            RadioButton(onClick = { /*TODO*/ })
        }

    }
}

