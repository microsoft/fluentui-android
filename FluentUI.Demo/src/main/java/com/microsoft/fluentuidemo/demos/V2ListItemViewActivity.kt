package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.listitemicons.Folder
import com.microsoft.fluentui.tokenized.listitem.ChevronTransition
import com.microsoft.fluentui.tokenized.listitem.TextIcons
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Large
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Small
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.XLarge
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.XXLarge
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Subtle
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Bottom
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R.drawable
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.persona.AvatarGroup
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.controls.RadioButton

class V2ListItemViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeHere = findViewById<ComposeView>(R.id.compose_here)

        composeHere.setContent {
            FluentTheme {
                CreateListActivityUI()
            }
        }
    }
}
const val sampleText = "Fluent UI for Android is a native library that provides the Office UI experience for the Android platform. It contains information about colors and typography, as well as custom controls and customizations for platform controls, all from the official Fluent design language used in Microsoft 365 products."
const val primaryText = "Title, primary text"
const val secondaryText = "Subtitle, secondary text"
const val tertiaryText = "Footer, tertiary text"

@Composable
fun CreateListActivityUI(){
    Box(
        Modifier
            .fillMaxSize()){
        LazyColumn{
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "One-Line list", color = Color(0xFF2886DE) )
                OneLineListAccessoryViewContent()
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Two-Line list", color = Color(0xFF2886DE))
                TwoLineListAccessoryViewContent()
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Three-Line list", color = Color(0xFF2886DE))
                ThreeLineListAccessoryViewContent()
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Text Only", color = Color(0xFF2886DE))
                ListItem.Item(text = primaryText, border = BorderType.Bottom, borderInset = XXLarge, primaryTrailingTextIcons = oneTextIcon20())
                ListItem.Item(text = primaryText, subText = secondaryText, border = BorderType.Bottom, borderInset = XXLarge, secondaryLeadingTextIcons = oneTextIcon16())
                ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, border = BorderType.Bottom, secondaryTailingTextIcons = twoTextIcons16())
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Wrapped text list", color = Color(0xFF2886DE))
                ListItem.Item(text = sampleText, textMaxLines = 4, leadingAccessoryView = { LeftViewFolderIcon40() }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = sampleText, subText = sampleText, textMaxLines = 4, subTextMaxLines = 4, leadingAccessoryView = { LeftViewFolderIcon40() }, trailingAccessoryView = { RightViewButton(ButtonSize.Small) }, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.Item(text = sampleText, subText = sampleText, secondarySubText = sampleText, textMaxLines = 4, subTextMaxLines = 4, secondarySubTextMaxLines = 4, leadingAccessoryView = { LeftViewAvatar(XLarge) }, trailingAccessoryView = { RightViewText(text = "Value")}, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 16.dp), text = "Section description", color = Color(0xFF2886DE))
                ListItem.SectionDescription(description = "Sample description with the description placed close to the Top with no Action text and no icon", border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.SectionDescription(description = "Sample description with the description placed close to the Bottom with Icon Accessory and no Action text", leadingAccessoryView = { LeftViewRadioButton()}, descriptionPlacement = Bottom, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.SectionDescription(description = "Sample description with the description placed close to the Top, with Action text", actionText = true, onActionClick = {}, border = BorderType.Bottom, borderInset = XXLarge)
                ListItem.SectionDescription(description = "Sample description with the description placed close to the Bottom with Icon accessory and Action text", actionText = true, onActionClick = {}, leadingAccessoryView = { LeftViewRadioButton()}, descriptionPlacement = Bottom, border = BorderType.Bottom)
            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp), text = "Section Headers with chevron", color = Color(0xFF2886DE))
                Column(Modifier.padding(top = 2.dp, bottom = 1.dp)) {
                    ListItem.SectionHeader(title = "One-Line list", enableChevron = true, chevronOrientation = ChevronTransition(90f, 0f), accessoryTextTitle = "Action", accessoryTextOnClick = {}, trailingAccessoryView = { RightViewThreeButton() }, content = { OneLineSimpleList() }, border = BorderType.Bottom, borderInset = XXLarge)
                    ListItem.SectionHeader(title = "Two-Line list", style = Subtle, enableChevron = true, chevronOrientation = ChevronTransition(90f, 0f), content = { TwoLineSimpleList() }, border = BorderType.Bottom, borderInset = XXLarge)
                }

            }
            item{
                Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp), text = "Section Headers No chevron", color = Color(0xFF2886DE))
                Column(Modifier.padding(top = 2.dp, bottom = 1.dp)) {
                    ListItem.SectionHeader(title = "Two-Line list", enableChevron = false, trailingAccessoryView = { RightViewToggle() }, content = { TwoLineSimpleList() }, border = BorderType.Bottom, borderInset = XXLarge)
                    ListItem.SectionHeader(title = "One-Line list", style = Subtle, enableChevron = false, accessoryTextTitle = "Action", accessoryTextOnClick = {}, content = { OneLineSimpleList() }, border = BorderType.Bottom, borderInset = XXLarge)
                }

            }
        }
    }
}
@Composable
fun OneLineSimpleList(){
    return Column {
        ListItem.Item(text = primaryText, leadingAccessoryView = { GetAvatar(size = Small, drawable.avatar_amanda_brady) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, leadingAccessoryView = { GetAvatar(size = Small, drawable.avatar_allan_munger) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, leadingAccessoryView = { GetAvatar(size = Small, drawable.avatar_ashley_mccarthy) }, border = BorderType.Bottom, borderInset = XXLarge)
    }
}
@Composable
fun TwoLineSimpleList(){
    return Column {
        ListItem.Item(text = primaryText, subText = "Subtitle", leadingAccessoryView = { GetAvatar(size = Large, drawable.avatar_daisy_phillips) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = "Subtitle", leadingAccessoryView = { GetAvatar(size = Large, drawable.avatar_elliot_woodward) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = "Subtitle", leadingAccessoryView = { GetAvatar(size = Large, drawable.avatar_charlotte_waltson) }, border = BorderType.Bottom, borderInset = XXLarge)
    }
}

@Composable
fun OneLineListAccessoryViewContent(){
    return Column {
        ListItem.Item(text = primaryText, leadingAccessoryView = { RightViewButton(size = ButtonSize.Small) }, primaryLeadingTextIcons = twoTextIcons20(), primaryTrailingTextIcons = oneTextIcon20(), border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(leadingAccessoryView = { LeftViewThreeButton() }, text = "", border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, leadingAccessoryView = { LeftViewAvatar(size = Small) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = "", leadingAccessoryView = { LeftViewThreeIcon()}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, leadingAccessoryView = { LeftViewRadioButton() }, trailingAccessoryView = { RightViewAvatarStack(Small)}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, leadingAccessoryView = { LeftViewRadioButton() }, trailingAccessoryView = { RightViewCheckbox()}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, leadingAccessoryView = { LeftViewThreeButton() }, trailingAccessoryView = { RightViewToggle() }, border = BorderType.Bottom, borderInset = XXLarge)
    }
}
@Composable
fun TwoLineListAccessoryViewContent(){
    return Column {
        ListItem.Item(text = primaryText, subText = secondaryText, leadingAccessoryView = { LeftViewFolderIcon40() }, primaryLeadingTextIcons = twoTextIcons20(), primaryTrailingTextIcons = oneTextIcon20(), secondaryTailingTextIcons = twoTextIcons16(), border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = secondaryText, leadingAccessoryView = { LeftViewAvatar(size = Large) }, border = BorderType.Bottom, borderInset = XXLarge, primaryTrailingTextIcons = oneTextIcon20(), secondaryTailingTextIcons = oneTextIcon16())
        ListItem.Item(text = primaryText, subText = secondaryText, leadingAccessoryView = { LeftViewFolderIcon40()}, trailingAccessoryView = { RightViewAvatarStack(Large)}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = secondaryText, leadingAccessoryView = { LeftViewFolderIcon40() }, secondaryLeadingTextIcons = twoTextIcons16(), secondaryTailingTextIcons = twoTextIcons16(), trailingAccessoryView = { RightViewButton(ButtonSize.Small)}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = secondaryText, leadingAccessoryView = { LeftViewFolderIcon40() }, secondaryTailingTextIcons = twoTextIcons16(), trailingAccessoryView = { RightViewToggle()}, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = secondaryText, leadingAccessoryView = { LeftViewThreeButton() }, trailingAccessoryView = { RightViewText("Value") }, border = BorderType.Bottom, borderInset = XXLarge)
    }
}
@Composable
fun ThreeLineListAccessoryViewContent(){
    return Column {
        ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, primaryLeadingTextIcons = twoTextIcons20(), primaryTrailingTextIcons = oneTextIcon20(), secondaryLeadingTextIcons = twoTextIcons16(), secondaryTailingTextIcons = twoTextIcons16(), leadingAccessoryView = { LeftViewFolderIcon40() }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, leadingAccessoryView = { LeftViewFolderIcon40() }, trailingAccessoryView = { RightViewButton(ButtonSize.Small) }, border = BorderType.Bottom, borderInset = XXLarge)
        ListItem.Item(text = primaryText, subText = secondaryText, secondarySubText = tertiaryText, primaryLeadingTextIcons = oneTextIcon20(), secondaryTailingTextIcons = twoTextIcons16(), leadingAccessoryView = { LeftViewAvatar(XLarge) }, trailingAccessoryView = { RightViewText(text = "Value")}, border = BorderType.Bottom)
    }
}

fun getPersonsList(): List<Person> {
    val person1 = Person(firstName = "Allan", image = drawable.avatar_allan_munger)
    val person2 = Person(firstName = "Amanda", image = drawable.avatar_amanda_brady)
    val person3 = Person(firstName = "Ashley", image = drawable.avatar_ashley_mccarthy)
    return listOf(person1, person2, person3)
}
fun groupAvatar(): Group {
    return Group(getPersonsList())
}
@Composable
fun LeftViewRadioButton(){
    var checked by remember { mutableStateOf(false) }
    return RadioButton(enabled = true,
        selected = checked,
        onClick = {
            checked = !checked
        }
    )
}
@Composable
fun RightViewAvatarStack(size:AvatarSize){
    return AvatarGroup(group = groupAvatar(), size = size)
}
@Composable
fun LeftViewThreeIcon(){
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(painter = painterResource(id = drawable.ic_fluent_flag_24_regular), contentDescription = "Flag", tint = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            themeMode))
        Icon(painter = painterResource(id = drawable.ic_fluent_reply_24_regular), contentDescription = "Reply", tint = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            themeMode))
        Icon(painter = painterResource(id = drawable.ic_fluent_forward_24_regular), contentDescription = "Forward", tint = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            themeMode))
    }
}
@Composable
fun RightViewToggle(){
    var enabled by remember { mutableStateOf(false) }
    return ToggleSwitch(onValueChange = {
        enabled = it
    }, enabledSwitch = true, checkedState = enabled)
}
@Composable
fun RightViewThreeButton(){
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
    }
}
@Composable
fun RightViewCheckbox(){
    var checked by remember { mutableStateOf(true) }
    return CheckBox(enabled = true, checked = !checked, onCheckedChanged = {
        checked = !it
    })
}
@Composable
fun LeftViewThreeButton(){
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)){
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
        Button(onClick = { /*TODO*/ }, size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, text = "Text")
    }
}

@Composable
fun oneTextIcon16(): TextIcons {
    return TextIcons({ Icon16() })
}

@Composable
fun twoTextIcons16(): TextIcons {
    return TextIcons({ Icon16() }, { Icon16() })
}
@Composable
fun oneTextIcon20(): TextIcons {
    return TextIcons({ Icon20() })
}

@Composable
fun twoTextIcons20(): TextIcons {
    return TextIcons({ Icon20() }, { Icon20() })
}
@Composable
fun Icon16(){
    return Icon(painter = painterResource(id = drawable.ic_check_circle_outline_16), contentDescription = "Flag", tint = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
        themeMode))
}
@Composable
fun Icon20(){
    return Icon(painter = painterResource(id = drawable.ic_check_circle_outline_20), contentDescription = "Flag", tint = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground3].value(
        themeMode))
}
@Composable
fun LeftViewFolderIcon40() {
    return Image(ListItemIcons.Folder, "Folder")
}
@Composable
fun RightViewButton(size:ButtonSize) {
    return Button(text = "Text", onClick = { /*TODO*/ }, size = size, style = ButtonStyle.OutlinedButton)
}
@Composable
fun LeftViewAvatar(size: AvatarSize) {
    val person = Person(firstName = "", lastName = "",  image = drawable.avatar_amanda_brady)
    return Avatar(person = person, size = size, enablePresence = false)
}

@Composable
fun GetAvatar(size: AvatarSize, image: Int) {
    val person = Person(firstName = "", lastName = "",  image = image)
    return Avatar(person = person, size = size, enablePresence = false)
}
@Composable
fun RightViewText(text:String) {
    return Text(text = text)
}