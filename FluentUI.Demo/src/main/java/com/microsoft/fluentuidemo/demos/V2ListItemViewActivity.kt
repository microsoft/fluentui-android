package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentui.listitem.ListItem
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Large
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Medium
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Small
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.XXLarge
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R.drawable
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.persona.AvatarGroup
import com.microsoft.fluentui.tokenized.persona.Group

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
                createActivityUI()
            }
        }
    }
}

const val sampleText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
const val primaryText = "Title, primary text"
const val secondaryText = "Subtitle, secondary text"
const val tertiaryText = "Footer, tertiary text"

@Composable
fun createActivityUI(){
    Box(
        Modifier
            .fillMaxSize()){
        LazyColumn( ){
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "One-Line list", color = Color(0xFF2886DE), )
                ListItem.Item(text = primaryText, leftAccessoryView = { leftViewFolderIcon24() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, leftAccessoryView = { leftViewFolderIcon24() }, rightAccessoryView = { rightViewButton(ButtonSize.Small) }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, leftAccessoryView = { leftViewAvatar(Small) }, rightAccessoryView = { rightViewText(text = "Value")}, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Two-Line list", color = Color(0xFF2886DE))
                ListItem.Item(text = primaryText, subText = secondaryText, leftAccessoryView = { leftViewFolderIcon40() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, subText = secondaryText, leftAccessoryView = { leftViewFolderIcon40() }, rightAccessoryView = { rightViewButton(ButtonSize.Small) }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, subText = secondaryText, leftAccessoryView = { leftViewAvatar(Large) }, rightAccessoryView = { rightViewText(text = "Value")}, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Three-Line list", color = Color(0xFF2886DE))
                ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, leftAccessoryView = { leftViewFolderIcon40() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, leftAccessoryView = { leftViewFolderIcon40() }, rightAccessoryView = { rightViewButton(ButtonSize.Small) }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, leftAccessoryView = { leftViewAvatar(Large) }, rightAccessoryView = { rightViewText(text = "Value")}, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Text Only", color = Color(0xFF2886DE))
                ListItem.Item(text = primaryText, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, subText = secondaryText, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Wrapped text list", color = Color(0xFF2886DE))
                ListItem.Item(text = sampleText, textMaxLines = 4, leftAccessoryView = { leftViewFolderIcon40() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = sampleText, subText = sampleText, textMaxLines = 4, subTextMaxLines = 4, leftAccessoryView = { leftViewFolderIcon40() }, rightAccessoryView = { rightViewButton(ButtonSize.Small) }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = sampleText, subText = sampleText, secondarySubText = sampleText, textMaxLines = 4, subTextMaxLines = 4, secondarySubTextMaxLines = 4, leftAccessoryView = { leftViewAvatar(Large) }, rightAccessoryView = { rightViewText(text = "Value")}, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp), text = "Section Headers", color = Color(0xFF2886DE))
                ListItem.SectionHeader(title = "One-Line list with accessory views", enableChevron = true, content = { oneLineListAccessoryViewContent() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.SectionHeader(title = "Two-Line list with accessory views", enableChevron = true, actionTitle = "Action", actionOnClick = {}, content = { twoLineListAccessoryViewContent() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.SectionHeader(title = "Three-Line list with accessory views", enableChevron = true, content = { oneLineListAccessoryViewContent() }, border = BorderType.Bottom, borderInset = XXLarge)
            }
        }
    }
}

@Composable
fun oneLineListAccessoryViewContent(){
    return Column() {
        ListItem.Item(text = "Text", leftAccessoryView = { leftViewFolderIcon24() }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(leftAccessoryView = { leftViewThreeButton() }, text = "", border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", leftAccessoryView = { leftViewAvatar(size = Small) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "", leftAccessoryView = { leftViewThreeIcon()}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", leftAccessoryView = { leftViewFolderIcon24() }, rightAccessoryView = { rightViewAvatarStack(Small)}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", leftAccessoryView = { leftViewFolderIcon24() }, rightAccessoryView = { rightViewCheckbox()}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", leftAccessoryView = { leftViewThreeButton() }, rightAccessoryView = { rightViewToggle() }, border = BorderType.Bottom, borderInset = XXLarge)
    }
}
@Composable
fun twoLineListAccessoryViewContent(){
    return Column() {
        ListItem.Item(text = "Text", subText = "Subtext", leftAccessoryView = { leftViewFolderIcon40() }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", leftAccessoryView = { leftViewAvatar(size = Large) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", subText = "Subtext", leftAccessoryView = { leftViewFolderIcon40()}, rightAccessoryView = { rightViewAvatarStack(Large)}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", subText = "Subtext", leftAccessoryView = { leftViewFolderIcon40() }, rightAccessoryView = { rightViewButton(ButtonSize.Small)}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", subText = "Subtext", leftAccessoryView = { leftViewFolderIcon40() }, rightAccessoryView = { rightViewToggle()}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "Text", subText = "Subtext", leftAccessoryView = { leftViewThreeButton() }, rightAccessoryView = { rightViewText("Value") }, border = BorderType.Bottom, borderInset = XXLarge)
    }
}

fun getPersonsList(): List<Person> {
    var person1 = Person(firstName = "Allan", image = R.drawable.avatar_allan_munger)
    var person2 = Person(firstName = "Amanda", image = R.drawable.avatar_amanda_brady)
    var person3 = Person(firstName = "Ashley", image = R.drawable.avatar_ashley_mccarthy)
    return listOf<Person>(person1, person2, person3)
}
fun groupAvatar(): Group {
    return Group(getPersonsList())
}
@Composable
fun rightViewAvatarStack(size:AvatarSize){
    return AvatarGroup(group = groupAvatar(), size = size)
}
@Composable
fun leftViewThreeIcon(){
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(painter = painterResource(id = R.drawable.ic_fluent_flag_24_regular), contentDescription = "Flag")
        Icon(painter = painterResource(id = R.drawable.ic_fluent_reply_24_regular), contentDescription = "Reply")
        Icon(painter = painterResource(id = R.drawable.ic_fluent_forward_24_regular), contentDescription = "Florward")
    }
}
@Composable
fun rightViewToggle(){
    return ToggleSwitch()
}
@Composable
fun rightViewCheckbox(){
    return CheckBox(onCheckedChanged = {})
}
@Composable
fun leftViewThreeButton(){
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
    }
}
@Composable
fun leftViewFolderIcon24() {
    return Icon(painter = painterResource(id = drawable.ic_folder_24_regular), contentDescription = "Folder")
}
@Composable
fun leftViewFolderIcon40() {
    return Icon(painter = painterResource(id = drawable.ic_folder_open_40), contentDescription = "Folder")
}
@Composable
fun rightViewButton(size:ButtonSize) {
    return Button(text = "Text", onClick = { /*TODO*/ }, size = size, style = ButtonStyle.OutlinedButton)
}
@Composable
fun leftViewAvatar(size: AvatarSize) {
    var person = Person(firstName = "", lastName = "",  image = R.drawable.avatar_amanda_brady)
    return Avatar(person = person, size = size)
}
@Composable
fun rightViewText(text:String) {
    return Text(text = text)
}




































//
//
//
//@Composable
//private fun CreateActivityUwI() {
//
//    var show by remember { mutableStateOf(false) }
//    var show2 by remember { mutableStateOf(true) }
//    Box(
//        Modifier
//            .fillMaxSize()
//            .background(Color.LightGray)){
//        LazyColumn( ){
//            item {
//                Box(
//                    modifier
//                        .padding(8.dp)
//                        .background(Color.White)){
//                    Column(modifier = Modifier
//                        .fillMaxWidth()) {
//                        Text(text = "Section Header Standard", modifier = modifier
//                            .fillMaxWidth()
//                            .padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                        ListItem.SectionHeader(style = Standard, title = "Pinned channels 1", rightAccessory = overFlowMenuView(), content = { sectionHeaderContent() }, actionTitle = "Action", actionOnClick = {show = !show})
//                    }
//                }
//            }
//            item {
//                Box(){
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .background(Color.White)) {
//                        Text(text = "Section Header Standard No chevron", modifier = modifier
//                            .fillMaxWidth()
//                            .padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                        ListItem.SectionHeader(style = Standard, title = "Pinned channels 2", enableChevron = false, rightAccessory = overFlowMenuView(), actionTitle = "Action", content = { sectionHeaderContent() }, actionOnClick = {show = !show})
//                    }
//                }
//            }
//            item {
//                Box(){
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .background(Color.White)) {
//                        Text(text = "Section Header Subtle", modifier = modifier
//                            .fillMaxWidth()
//                            .padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                        ListItem.SectionHeader(style = Subtle, title = "Pinned channels 3", rightAccessory = overFlowMenuView(), actionTitle = "Action", content = { sectionHeaderContent() }, actionOnClick = {show = !show})
//                    }
//                }
//            }
//            item {
//                Box(){
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .background(Color.White)) {
//                        Text(text = "Section Header Subtle No chevron", modifier = modifier
//                            .fillMaxWidth()
//                            .padding(8.dp), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                        ListItem.SectionHeader(style = Subtle, title = "Pinned channels 4", enableChevron = false, rightAccessory = overFlowMenuView(), actionTitle = "Action", content = { sectionHeaderContent() }, actionOnClick = {show = !show})
//                    }
//                }
//            }
//            item {
//                Box{
//                    Column(modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                        .background(Color.White)) {
//                        Text(text = "One Line List", modifier = modifier
//                            .padding(8.dp)
//                            .fillMaxWidth(), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                        oneLineList()
//                        multiIconList()
//                        threeButtonList()
//                        randomList()
//                        singleLineList()
//                        multiLineList()
//                    }
//                }
//            }
//            item {
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//                    .background(Color.White)) {
//                    Text(text = "Multi Line List", modifier = modifier
//                        .padding(8.dp)
//                        .fillMaxWidth(), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                    threeLineTextList()
//                    twoLineTextList()
//                    twoLineAvatarTextList()
//                }
//            }
//            item {
//                Column(modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(8.dp)
//                    .background(Color.White)) {
//                    Text(text = "Section Description", modifier = modifier
//                        .padding(8.dp)
//                        .fillMaxWidth(), fontWeight = FontWeight.ExtraBold, fontSize = 24.sp)
//                    oneLineDescription(onClick = {show2 = !show2})
//                    if(show2){
//                        twoLineDescription()
//                        threeLineDescription()
//                    }
//                }
//            }
////            item{
////                Column(modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(15.dp)) {
////                    Text(text = "Avatar Carousel")
////                    Row() {
////                        avatarCarousel()
////                        avatarCarousel()
////                        avatarCarousel()
////                        avatarCarousel()
////                        avatarCarousel()
////                        avatarCarousel()
////                    }
////                }
////            }
//
//        }
//    }
//}
//
////@Composable
////fun avatarCarousel(){
////    val person = Person("Allan", "Munger",
////        image = drawable.avatar_allan_munger)
////    ListItem.AvatarCarousel(person = person, size = AvatarCarouselSize.Large)
////}
//
//@Composable
//fun sectionHeaderContent(){
//    return Box(
//        Modifier
//            .fillMaxWidth(), contentAlignment = Alignment.Center){
//        Column {
//            multiIconList()
//            threeButtonList()
//            threeLineTextList()
//        }
//    }
//}
//
//@Composable
//fun oneLineDescription(onClick: (() -> Unit)?){
//    ListItem.SectionDescription(action = true, iconAccessory = radioButtonView(), onActionClick = onClick, border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development.")
//}
//
//@Composable
//fun twoLineDescription(){
//    ListItem.SectionDescription(border = BorderType.Bottom, borderInset = BorderInset.XXXXXXLarge, descriptionPlacement = Bottom, description = "Gradle is a build automation tool for multi-language software development. It controls the development process")
//}
//
//@Composable
//fun threeLineDescription(){
//    ListItem.SectionDescription(action = true, border = BorderType.Bottom, description = "Gradle is a build automation tool for multi-language software development.")
//}
//
//@Composable
//fun sampleList(): @Composable (() -> Unit){
//    return {
//            Column {
//                multiIconList()
//                threeButtonList()
//                threeLineTextList()
//            }
//
//    }
//}
//var modifier = Modifier.border(width = 1.dp, color = Color(0xFFE0E0E0))
//
//@Composable
//fun multiIconList() {
//        ListItem.OneLine(text ="Text", secondarySubTextMaxLines = 2, border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = radioButtonView(), rightAccessoryView = checkboxRightView(), onClick ={})
//}
//@Composable
//fun threeButtonList() {
//    ListItem.OneLine(text = "Text", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = threeButtonView(), rightAccessoryView = overFlowMenuView(), onClick ={})
//}
//@Composable
//fun oneLineList() {
//    ListItem.OneLine(text ="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = leftViewFolderIcon())
//}
//@Composable
//fun singleLineList() {
//    ListItem.OneLine(text ="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, rightAccessoryView = overFlowMenuView(), onClick ={})
//}
//@Composable
//fun multiLineList() {
//    ListItem.OneLine(text ="Text", rightAccessoryView = threeIconsView(), onClick ={})
//}
//@Composable
//fun overFlowButtonList() {
//    ListItem.OneLine(text = "Text", border = BorderType.Bottom, borderInset = BorderInset.Large, rightAccessoryView = overFlowMenuView(), onClick ={})
//}
//
//@Composable
//fun randomList(){
//    ListItem.OneLine(text ="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = leftView(), rightAccessoryView = arrowView(), onClick ={})
//}
//
//@OptIn(ExperimentalMaterialApi::class)
//@Composable
//fun googleList(){
//    androidx.compose.material.ListItem(icon = threeIconsView(), trailing = overFlowMenuView(), text = {})
//}
//@Composable
//fun threeLineTextList() {
//    ListItem.OneLine(text ="TextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextTextxtTextTextTextTextTextxtTextTextTextTextTextxtTextTextTextTextText", secondarySubTextMaxLines = 2, subText = "subtext", secondarySubText = "secondarysecondarysecondarysecondarysecondarysecondarysecondary", border = BorderType.Bottom, borderInset = BorderInset.Large, leftAccessoryView = leftViewFolderIcon(), rightAccessoryView = threeIconsView(), onClick ={})
//}
//@Composable
//fun twoLineTextList() {
//    ListItem.OneLine(text ="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, subText = "subtext", rightAccessoryView = threeIconsView(), onClick ={})
//}
//@Composable
//fun twoLineAvatarTextList() {
//    ListItem.OneLine(text ="Text", border = BorderType.Bottom, borderInset = BorderInset.Large, subText = "subtext", leftAccessoryView = avatarIconLeftView(), onClick ={})
//}
//fun avatarIconLeftView(): @Composable (() -> Unit) {
//
//    return {
//        var person = Person(firstName = "Kelly", image = R.drawable.avatar_amanda_brady)
//        Row() {
//            Avatar(person)
//        }
//
//    }
//}
//@Composable
//fun checkboxRightView(): @Composable (() -> Unit){
//    var checked by remember { mutableStateOf(true) }
//    return{
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = CenterVertically) {
//            Text("Value")
//            CheckBox(enabled = checked, checked = !checked, onCheckedChanged = {
//                checked = !it
//                if (checked) {
//                } else {
//                }
//            })
//        }
//
//    }
//}
//@Composable
//fun leftView(): @Composable (() -> Unit) {
//
//    return {
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            Icon(modifier = modifier.clickable {  }, painter = painterResource(id = drawable.ic_camera_24_regular), contentDescription = "thumbup")
//            Icon(modifier = modifier.clickable {  }, painter = painterResource(id = R.drawable.ic_sync_24_filled), contentDescription = "box")
//            Icon(modifier = modifier.clickable {  }, painter = painterResource(id = R.drawable.ic_clock_24_filled), contentDescription = "send")
//        }
//    }
//}
//@Composable
//fun threeIconsView(): @Composable (() -> Unit) {
//
//    return {
//        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painter = painterResource(id = drawable.ic_camera_24_regular), contentDescription = "thumbup")
//            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painter = painterResource(id = drawable.ic_sync_24_filled), contentDescription = "thumbup")
//            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painter = painterResource(id = drawable.ic_clock_24_filled), contentDescription = "thumbup")
//            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painter = painterResource(id = drawable.ic_camera_24_regular), contentDescription = "thumbup")
//            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painter = painterResource(id = drawable.ic_sync_24_filled), contentDescription = "thumbup")
//            }
//            IconButton(onClick = { /*TODO*/ }) {
//                Icon(painter = painterResource(id = drawable.ic_clock_24_filled), contentDescription = "thumbup")
//            }
//        }
//    }
//}
//
//@Composable
//fun overFlowMenuView(): @Composable (() -> Unit){
//    return{
//        FluentTheme{
//            var checked by remember { mutableStateOf(true) }
//            var enabled by remember { mutableStateOf(false) }
//            Row {
//                RadioButton(onClick = { /*TODO*/ })
//            }
//        }
//
//    }
//}
//@Composable
//fun singleButton(): @Composable () -> Unit{
//    return {
//        Button(
//            size = ButtonSize.Small,
//            style = ButtonStyle.OutlinedButton,
//            text = "Text",
//            onClick = { }
//        )
//    }
//}
//@Composable
//fun threeButtonView(): @Composable (() -> Unit)? {
//    val scaffoldState = rememberScaffoldState()
//    val coroutineScope = rememberCoroutineScope()
//    return {
//        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
//            Button(
//                size = ButtonSize.Small,
//                style = ButtonStyle.OutlinedButton,
//                text = "Text",
//                onClick = {
//                    coroutineScope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = "This is your message"
//                        )
//                    }
//                }
//            )
//            Button(
//                size = ButtonSize.Small,
//                style = ButtonStyle.OutlinedButton,
//                text = "Text",
//                onClick = {
//                    coroutineScope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = "This is your message"
//                        )
//                    }
//                }
//            )
//            Button(
//                size = ButtonSize.Small,
//                style = ButtonStyle.OutlinedButton,
//                text = "Text",
//                onClick = {
//                    coroutineScope.launch {
//                        scaffoldState.snackbarHostState.showSnackbar(
//                            message = "This is your message"
//                        )
//                    }
//                }
//            )
//        }
//
//    }
//}
//
//@Composable
//fun arrowView(): @Composable (() -> Unit){
//    return{
//
//    }
//}
//
//@Composable
//fun radioButtonView(): @Composable (() -> Unit){
//    return{
//        Row() {
//            RadioButton(onClick = { /*TODO*/ })
//        }
//
//    }
//}
//
