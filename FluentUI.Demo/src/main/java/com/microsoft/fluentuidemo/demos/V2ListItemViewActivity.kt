package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.launch
import com.microsoft.fluentui.listitem.ListItem
import com.microsoft.fluentui.theme.token.FontSize
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Standard
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Subtle
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Bottom
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R.drawable

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
            .fillMaxSize().background(Color.LightGray)){
        LazyColumn( ){
            item {
                Box(modifier.padding(8.dp).background(Color.White)){
                    Column(modifier = Modifier
                        .fillMaxWidth()) {
                        Text(text = "Section Header Standard", modifier = modifier.fillMaxWidth().padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                        ListItem.SectionHeader(style = Standard, title = "Pinned channels 1", rightAccessory = overFlowMenuView(), content = { sectionHeaderContent() }, actionTitle = "Action", actionOnClick = {show = !show})
                    }
                }
            }
            item {
                Box(){
                    Column(modifier = Modifier
                        .fillMaxWidth().padding(8.dp).background(Color.White)) {
                        Text(text = "Section Header Standard No chevron", modifier = modifier.fillMaxWidth().padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                        ListItem.SectionHeader(style = Standard, title = "Pinned channels 2", enableChevron = false, rightAccessory = overFlowMenuView(), actionTitle = "Action", content = { sectionHeaderContent() }, actionOnClick = {show = !show})
                    }
                }
            }
            item {
                Box(){
                    Column(modifier = Modifier
                        .fillMaxWidth().padding(8.dp).background(Color.White)) {
                        Text(text = "Section Header Subtle", modifier = modifier.fillMaxWidth().padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                        ListItem.SectionHeader(style = Subtle, title = "Pinned channels 3", rightAccessory = overFlowMenuView(), actionTitle = "Action", content = { sectionHeaderContent() }, actionOnClick = {show = !show})
                    }
                }
            }
            item {
                Box(){
                    Column(modifier = Modifier
                        .fillMaxWidth().padding(8.dp).background(Color.White)) {
                        Text(text = "Section Header Subtle No chevron", modifier = modifier.fillMaxWidth().padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                        ListItem.SectionHeader(style = Subtle, title = "Pinned channels 4", enableChevron = false, rightAccessory = overFlowMenuView(), actionTitle = "Action", content = { sectionHeaderContent() }, actionOnClick = {show = !show})
                    }
                }
            }
            item {
                Box{
                    Column(modifier = Modifier
                        .fillMaxWidth().padding(8.dp).background(Color.White)) {
                        Text(text = "One Line List", modifier = modifier.padding(8.dp).fillMaxWidth(), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
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
                    .fillMaxWidth().padding(8.dp).background(Color.White)) {
                    Text(text = "Multi Line List", modifier = modifier.padding(8.dp).fillMaxWidth(), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                    threeLineTextList()
                    twoLineTextList()
                    twoLineAvatarTextList()
                }
            }
            item {
                Column(modifier = Modifier
                    .fillMaxWidth().padding(8.dp).background(Color.White)) {
                    Text(text = "Section Description", modifier = modifier.padding(8.dp).fillMaxWidth(), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
                    oneLineDescription(onClick = {show2 = !show2})
                    if(show2){
                        twoLineDescription()
                        threeLineDescription()
                    }
                }
            }
//            item{
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(15.dp)) {
//                    Text(text = "Avatar Carousel")
//                    Row() {
//                        avatarCarousel()
//                        avatarCarousel()
//                        avatarCarousel()
//                        avatarCarousel()
//                        avatarCarousel()
//                        avatarCarousel()
//                    }
//                }
//            }

        }
    }
}

//@Composable
//fun avatarCarousel(){
//    val person = Person("Allan", "Munger",
//        image = drawable.avatar_allan_munger)
//    ListItem.AvatarCarousel(person = person, size = AvatarCarouselSize.Large)
//}

@Composable
fun sectionHeaderContent(){
    return Box(
        Modifier
            .fillMaxWidth(), contentAlignment = Alignment.Center){
        Column {
            multiIconList()
            threeButtonList()
            threeLineTextList()
        }
    }
}

@Composable
fun oneLineDescription(onClick: (() -> Unit)?){
    ListItem.SectionDescription(action = true, iconAccessory = radioButtonView(), onActionClick = onClick, border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development.")
}

@Composable
fun twoLineDescription(){
    ListItem.SectionDescription(border = BorderType.Bottom, borderInset = BorderInset.XXXXXXLarge, descriptionPlacement = Bottom, description = "Gradle is a build automation tool for multi-language software development. It controls the development process")
}

@Composable
fun threeLineDescription(){
    ListItem.SectionDescription(action = true, border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development. ")
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
    ListItem.OneLine(text="TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextxtTextTextTextTextTextxtTextTextTextTextTextxtTextTextTextTextText", secondarySubTextMaxLines = 2, subText = "subtext", secondarySubText = "secondarysecondarysecondarysecondarysecondarysecondarysecondary", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = folderIconLeftView(), rightAccessoryView = threeIconsView(), onClick ={})
}
@Composable
fun twoLineTextList() {
    ListItem.OneLine(text="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, subText = "subtext", rightAccessoryView = threeIconsView(), onClick ={})
}
@Composable
fun twoLineAvatarTextList() {
    ListItem.OneLine(text="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, subText = "subtext", leftAccessoryView = avatarIconLeftView(), onClick ={})
}
@Composable
fun folderIconLeftView(): @Composable (() -> Unit) {

    return {
        Row() {
            Icon(painter = painterResource(id = drawable.ic_folder_24_regular), contentDescription = "Email")
        }

    }
}
fun avatarIconLeftView(): @Composable (() -> Unit) {

    return {
        var person = Person(firstName = "Kelly", image = R.drawable.avatar_amanda_brady)
        Row() {
            Avatar(person)
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
            Icon(painter = painterResource(id = drawable.ic_camera_24_regular), contentDescription = "thumbup")
            Icon(painter = painterResource(id = R.drawable.ic_sync_24_filled), contentDescription = "box")
            Icon(painter = painterResource(id = R.drawable.ic_clock_24_filled), contentDescription = "send")
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
                RadioButton(onClick = { /*TODO*/ })
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
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Icon(painter = painterResource(id = drawable.ic_camera_24_regular), contentDescription = "thumbup")
            Icon(painter = painterResource(id = R.drawable.ic_sync_24_filled), contentDescription = "box")
            Icon(painter = painterResource(id = R.drawable.ic_clock_24_filled), contentDescription = "send")
        }
    }
}
@Composable
fun arrowView(): @Composable (() -> Unit){
    return{

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

