package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Size24
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Size40
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize.Size56
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardInfo
import com.microsoft.fluentui.theme.token.controlTokens.BasicCardTokens
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset.XXLarge
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTextAlignment
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle.Subtle
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Bottom
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.CheckBox
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentui.tokenized.notification.ToolTipBox
import com.microsoft.fluentui.tokenized.notification.rememberTooltipState
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.AvatarGroup
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.progress.LinearProgressIndicator
import com.microsoft.fluentuidemo.R.drawable
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.icons.ListItemIcons
import com.microsoft.fluentuidemo.icons.listitemicons.Folder40
import com.microsoft.fluentuidemo.util.invokeToast
import kotlinx.coroutines.launch

class V2ListItemActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateListActivityUI(this)
        }
    }
}

const val sampleText =
    "Fluent UI for Android is a native library that provides the Office UI experience for the Android platform. It contains information about colors and typography, as well as custom controls and customizations for platform controls, all from the official Fluent design language used in Microsoft 365 products."
const val primaryText = "Title, primary text"
const val secondaryText = "Subtitle, secondary text"
const val tertiaryText = "Footer, tertiary text"
const val unclickableText = " (Unclickable) "


@Composable
private fun CreateListActivityUI(context: Context) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            ListItem.Header(title = "One-Line list")
            OneLineListAccessoryContentContent(context)
            ListItem.Header(title = "Two-Line list")
            TwoLineListAccessoryContentContent(context)
            ListItem.Header(title = "Three-Line list")
            ThreeLineListAccessoryContentContent(context)
            ListItem.Header(title = "Text Only")
            ListItem.Item(
                text = primaryText,
                onClick = {},
                border = BorderType.Bottom,
                borderInset = XXLarge,
                primaryTextTrailingContent = { Icon20() }
            )
            ListItem.Item(
                text = primaryText,
                onClick = {},
                subText = secondaryText,
                border = BorderType.Bottom,
                borderInset = XXLarge,
                secondarySubTextLeadingContent = { Icon16() }
            )
            ListItem.Item(
                text = primaryText,
                onClick = {},
                subText = secondaryText,
                secondarySubText = tertiaryText,
                border = BorderType.Bottom,
                secondarySubTextTrailingContent = {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Icon16()
                        Icon16()
                    }
                }
            )
            ListItem.Header(title = "Wrapped Text list")
            ListItem.Item(
                text = sampleText,
                onClick = {},
                textMaxLines = 4,
                leadingAccessoryContent = { LeftContentFolderIcon40() },
                border = BorderType.Bottom,
                borderInset = XXLarge
            )
            ListItem.Item(
                text = sampleText,
                subText = sampleText,
                onClick = {},
                textMaxLines = 4,
                subTextMaxLines = 4,
                leadingAccessoryContent = { LeftContentFolderIcon40() },
                trailingAccessoryContent = {
                    RightContentButton(
                        ButtonSize.Small,
                        context
                    )
                },
                border = BorderType.Bottom,
                borderInset = XXLarge
            )
            ListItem.Item(
                text = sampleText,
                subText = sampleText,
                secondarySubText = sampleText,
                onClick = {},
                textMaxLines = 4,
                subTextMaxLines = 4,
                secondarySubTextMaxLines = 4,
                leadingAccessoryContent = { LeftContentFolderIcon40() },
                trailingAccessoryContent = { RightContentText(text = "Value") },
                border = BorderType.Bottom
            )
            ListItem.Header(title = "Section description")
            ListItem.SectionDescription(
                description = "Sample description with the description placed at the Top with no Action text and no icon",
                border = BorderType.Bottom,
                borderInset = XXLarge
            )
            ListItem.SectionDescription(
                description = "Sample description with the description placed at the Bottom and no Action text",
                descriptionPlacement = Bottom,
                border = BorderType.Bottom,
                borderInset = XXLarge
            )
            ListItem.SectionDescription(
                description = "Sample description with the description placed at the Top, with Icon accessory and Action text",
                actionText = "Action",
                onActionClick = {},
                leadingAccessoryContent = { Icon16() },
                border = BorderType.Bottom,
                borderInset = XXLarge
            )
            ListItem.SectionDescription(
                description = "Sample description with the description placed at the Bottom and Action text",
                actionText = "More",
                onActionClick = {},
                descriptionPlacement = Bottom,
                border = BorderType.Bottom
            )
            ListItem.Header(title = "Section Headers with/without chevron")
            val toolTipState = rememberTooltipState()
            val scope = rememberCoroutineScope()
            Column {
                ListItem.SectionHeader(
                    title = "One-Line list",
                    enableChevron = true,
                    titleTrailingContent = {
                        ToolTipBox(
                            title = "",
                            text = "This is a tooltip",
                            tooltipState = toolTipState
                        ) {
                            Icon(
                                painter = painterResource(id = drawable.ic_icon__16x16_checkmark),
                                contentDescription = "Flag",
                                tint = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                                    themeMode
                                ),
                                onClick = { scope.launch { toolTipState.show() } }
                            )
                        }
                    },
                    enableContentOpenCloseTransition = true,
                    chevronOrientation = ChevronOrientation(90f, 0f),
                    accessoryTextTitle = "Action",
                    accessoryTextOnClick = {},
                    trailingAccessoryContent = { RightContentThreeButton() },
                    content = { OneLineSimpleList() },
                    border = BorderType.Bottom
                )
                ListItem.SectionHeader(
                    title = "Two-Line list",
                    style = Subtle,
                    enableChevron = true,
                    enableContentOpenCloseTransition = true,
                    chevronOrientation = ChevronOrientation(90f, 0f),
                    content = { TwoLineSimpleList() },
                    border = BorderType.Bottom
                )
                ListItem.SectionHeader(
                    title = "Three-Line list",
                    enableChevron = false,
                    enableContentOpenCloseTransition = true,
                    trailingAccessoryContent = { RightContentToggle() },
                    content = { ThreeLineSimpleList() },
                    border = BorderType.Bottom
                )
            }
            ListItem.Header(title = "Headers")
            ListItem.Header(
                title = "Standard heading",
                accessoryTextTitle = "Action",
                accessoryTextOnClick = {},
                trailingAccessoryContent = { RightContentThreeButton() },
                border = BorderType.Bottom
            )
            ListItem.Header(
                title = "Subtle heading",
                accessoryTextTitle = "Action",
                accessoryTextOnClick = {},
                style = Subtle
            )
            ListItem.Header(title = "Centered Action Text")
            ListItem.Item(
                text = "Action",
                onClick = {},
                textAlignment = ListItemTextAlignment.Centered,
                border = BorderType.Bottom
            )
            ListItem.Item(
                text = "Disabled",
                onClick = {},
                enabled = false,
                textAlignment = ListItemTextAlignment.Centered,
                border = BorderType.Bottom
            )
            ListItem.SectionDescription(description = "Centered action text only supports primary text and ignores any given trailing or leading accessory Contents")
            GroupedList()
        }
    }
}

@Composable
private fun GroupedList() {
    ListItem.Header("Grouped List")
    ListItem.SectionDescription(description = "Grouped List", modifier = Modifier.height(25.dp))
    Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 10.dp).clip(
        RoundedCornerShape(10.dp))) {
        for(i in 0..3) {
            ListItem.Item(
                text = "Text",
                onClick = {},
                textAlignment = ListItemTextAlignment.Regular,
                border = BorderType.Bottom,
                trailingAccessoryContent = { Icon(icon = FluentIcon(Icons.Outlined.KeyboardArrowRight))},
            )
        }
    }
    ListItem.SectionDescription(description = "Grouped list containing multiple similar elements", modifier = Modifier.wrapContentHeight().padding(0.dp))
}

@Composable
private fun OneLineSimpleList() {
    return Column {
        ListItem.Item(
            text = primaryText,
            leadingAccessoryContent = { GetAvatar(size = Size24, drawable.avatar_amanda_brady) },
            onClick={},
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            leadingAccessoryContent = { GetAvatar(size = Size24, drawable.avatar_allan_munger) },
            onClick={},
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            leadingAccessoryContent = { GetAvatar(size = Size24, drawable.avatar_ashley_mccarthy) },
            onClick={},
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
    }
}

@Composable
private fun TwoLineSimpleList() {
    return Column {
        ListItem.Item(
            text = primaryText,
            subText = "Subtitle",
            onClick={},
            leadingAccessoryContent = { GetAvatar(size = Size40, drawable.avatar_daisy_phillips) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = "Subtitle",
            onClick={},
            leadingAccessoryContent = { GetAvatar(size = Size40, drawable.avatar_elliot_woodward) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = "Subtitle",
            onClick={},
            leadingAccessoryContent = {
                GetAvatar(
                    size = Size40,
                    drawable.avatar_charlotte_waltson
                )
            },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
    }
}

@Composable
private fun ThreeLineSimpleList() {
    return Column {
        ListItem.Item(
            text = primaryText,
            subText = "Subtitle",
            secondarySubText = tertiaryText,
            onClick={},
            leadingAccessoryContent = { GetAvatar(size = Size56, drawable.avatar_daisy_phillips) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = "Subtitle",
            secondarySubText = tertiaryText,
            onClick={},
            leadingAccessoryContent = { GetAvatar(size = Size56, drawable.avatar_elliot_woodward) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = "Subtitle",
            secondarySubText = tertiaryText,
            onClick={},
            leadingAccessoryContent = {
                GetAvatar(
                    size = Size56,
                    drawable.avatar_charlotte_waltson
                )
            },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
    }
}

@Composable
private fun OneLineListAccessoryContentContent(context: Context) {
    var checked by remember { mutableStateOf(true) }
    return Column {
        ListItem.Item(
            text = primaryText,
            leadingAccessoryContent = { LeftContentRadioButton() },
            trailingAccessoryContent = {
                CheckBox(enabled = true, checked = !checked, onCheckedChanged = {
                    checked = !it
                })
            },
            border = BorderType.Bottom,
            borderInset = XXLarge,
            onClick = { checked = !checked }
        )
        ListItem.Item(
            text = primaryText,
            onClick = {},
            leadingAccessoryContent = {
                RightContentButton(
                    size = ButtonSize.Small,
                    context
                )
            },
            primaryTextLeadingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon20()
                    Icon20()
                }
            },
            primaryTextTrailingContent = { Icon20() },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentAvatar(size = Size24) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText + unclickableText,
            leadingAccessoryContent = { LeftContentAvatar(size = Size24) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = "",
            onClick = {},
            leadingAccessoryContent = { LeftContentThreeIcon() },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentRadioButton() },
            trailingAccessoryContent = { RightContentAvatarStack(Size24) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentThreeButton() },
            trailingAccessoryContent = { RightContentToggle() },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
    }
}

@Composable
private fun TwoLineListAccessoryContentContent(context: Context) {
    var unreadDot1 by remember { mutableStateOf(true) }
    var unreadDot2 by remember { mutableStateOf(true) }
    return Column {
        ListItem.Item(
            text = primaryText,
            secondarySubText = tertiaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentAvatar(size = Size40) },
            trailingAccessoryContent = { LeftContentAvatar(size = Size40) },
            border = BorderType.Bottom,
            borderInset = XXLarge,
            primaryTextTrailingContent = { Icon20() },
            secondarySubTextTrailingContent = { Icon16() }
        )
        ListItem.Item(
            text = primaryText,
            secondarySubText = tertiaryText,
            leadingAccessoryContent = { LeftContentAvatar(size = Size40) },
            border = BorderType.Bottom,
            borderInset = XXLarge,
            unreadDot = unreadDot1,
            onClick = { unreadDot1 = !unreadDot1 },
            primaryTextTrailingContent = { Icon20() },
            secondarySubTextTrailingContent = { Icon16() }
        )
        ListItem.Item(
            text = primaryText,
            secondarySubText = tertiaryText,
            leadingAccessoryContent = { LeftContentAvatarCutout(size = Size40) },
            border = BorderType.Bottom,
            borderInset = XXLarge,
            unreadDot = unreadDot2,
            onClick = { unreadDot2 = !unreadDot2 },
            primaryTextTrailingContent = { Icon20() },
            secondarySubTextTrailingContent = { Icon16() }
        )
        ListItem.Item(
            text = primaryText,
            secondarySubText = tertiaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentFolderIcon40() },
            primaryTextLeadingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon20()
                    Icon20()
                }
            },
            primaryTextTrailingContent = { Icon20() },
            secondarySubTextTrailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon16()
                    Icon16()
                }
            },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = secondaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentFolderIcon40() },
            trailingAccessoryContent = { RightContentAvatarStack(Size40) },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            secondarySubText = tertiaryText,
            leadingAccessoryContent = { LeftContentFolderIcon40() },
            onClick = {},
            secondarySubTextLeadingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon16()
                    Icon16()
                }
            },
            secondarySubTextTrailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon16()
                    Icon16()
                }
            },
            trailingAccessoryContent = {
                RightContentButton(
                    ButtonSize.Small,
                    context
                )
            },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            secondarySubText = tertiaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentFolderIcon40() },
            secondarySubTextTrailingContent = { Icon16() },
            trailingAccessoryContent = { RightContentToggle() },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = secondaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentThreeButton() },
            trailingAccessoryContent = { RightContentText("Value") },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
    }
}

@Composable
private fun ThreeLineListAccessoryContentContent(
    context: Context
) {
    val separator = " • "
    val footer = buildAnnotatedString {
        withStyle(SpanStyle(color = Color.Blue)) {
            append("3 min ago")
        }
        append(separator)
        append("FluentGuide V1.pptx")
    }
    return Column {
        ListItem.Item(
            text = "Amanda Brady replied to your comment",
            subText = "Wanda can you please update the file with comments",
            secondarySubTextAnnotated = footer,
            onClick = {},
            leadingAccessoryContent = { LeftContentAvatar(size = Size56) },
            trailingAccessoryContent = { rightContentIconButton() },
            primaryTextTrailingContent = { Badge(text = "2") },
            textMaxLines = 2,
            leadingAccessoryContentAlignment = Alignment.Top,
            trailingAccessoryContentAlignment = Alignment.Top,
            unreadDot = true,
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = secondaryText,
            secondarySubText = tertiaryText,
            onClick = {},
            leadingAccessoryContent = { LeftContentFolderIcon40() },
            primaryTextTrailingContent = { Badge(text = "Suggested") },
            trailingAccessoryContent = {
                RightContentButton(
                    ButtonSize.Small,
                    context
                )
            },
            border = BorderType.Bottom,
            borderInset = XXLarge
        )
        ListItem.Item(
            text = primaryText,
            subText = secondaryText,
            bottomContent = { LinearProgressIndicator() },
            onClick = {},
            primaryTextLeadingContent = { Icon20() },
            secondarySubTextTrailingContent = {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon16()
                    Icon16()
                }
            },
            leadingAccessoryContent = { LeftContentAvatar(Size56) },
            trailingAccessoryContent = { RightContentText(text = "Value") },
            border = BorderType.Bottom
        )
    }
}

private fun getPersonsList(): List<Person> {
    val person1 = Person(firstName = "Allan", image = drawable.avatar_allan_munger)
    val person2 = Person(firstName = "Amanda", image = drawable.avatar_amanda_brady)
    val person3 = Person(firstName = "Ashley", image = drawable.avatar_ashley_mccarthy)
    return listOf(person1, person2, person3)
}

private fun groupAvatar(): Group {
    return Group(getPersonsList())
}

@Composable
private fun LeftContentRadioButton() {
    var checked by remember { mutableStateOf(false) }
    return RadioButton(enabled = true,
        selected = checked,
        onClick = {
            checked = !checked
        }
    )
}

@Composable
private fun RightContentAvatarStack(size: AvatarSize) {
    return AvatarGroup(group = groupAvatar(), size = size)
}

@Composable
private fun LeftContentThreeIcon() {
    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Icon(
            painter = painterResource(id = drawable.ic_fluent_flag_24_regular),
            contentDescription = "Flag",
            tint = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                themeMode
            )
        )
        Icon(
            painter = painterResource(id = drawable.ic_fluent_reply_24_regular),
            contentDescription = "Reply",
            tint = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                themeMode
            )
        )
        Icon(
            painter = painterResource(id = drawable.ic_fluent_forward_24_regular),
            contentDescription = "Forward",
            tint = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
                themeMode
            )
        )
    }
}

@Composable
private fun RightContentToggle() {
    var enabled by remember { mutableStateOf(false) }
    return ToggleSwitch(onValueChange = {
        enabled = it
    }, enabledSwitch = true, checkedState = enabled)
}

@Composable
private fun RightContentThreeButton() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { /*TODO*/ },
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text"
        )
        Button(
            onClick = { /*TODO*/ },
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text"
        )
        Button(
            onClick = { /*TODO*/ },
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text"
        )
    }
}

@Composable
private fun LeftContentThreeButton() {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Button(
            onClick = { /*TODO*/ },
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text"
        )
        Button(
            onClick = { /*TODO*/ },
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text"
        )
        Button(
            onClick = { /*TODO*/ },
            size = ButtonSize.Small,
            style = ButtonStyle.OutlinedButton,
            text = "Text"
        )
    }
}

@Composable
private fun Icon16() {
    return Icon(
        painter = painterResource(id = drawable.ic_icon__16x16_checkmark),
        contentDescription = "Flag",
        tint = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            themeMode
        )
    )
}

@Composable
private fun Icon20() {
    return Icon(
        painter = painterResource(id = drawable.ic_icon__20x20_checkmark),
        contentDescription = "Flag",
        tint = aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground3].value(
            themeMode
        )
    )
}

@Composable
private fun LeftContentFolderIcon40() {
    return Image(ListItemIcons.Folder40, "Folder")
}

@Composable
private fun rightContentIconButton() {
    class Tokens : BasicCardTokens() {
        @Composable
        override fun cornerRadius(basicCardInfo: BasicCardInfo): Dp {
            return FluentGlobalTokens.CornerRadiusTokens.CornerRadius40.value
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        BasicCard(Modifier.padding(all = 4.dp), basicCardTokens = Tokens()) {
            Icon(Icons.Outlined.MoreVert, contentDescription = "")
        }
    }
}

@Composable
private fun RightContentButton(
    size: ButtonSize,
    context: Context
) {
    return Button(
        text = "Text",
        onClick = { invokeToast("Button", context) },
        size = size,
        style = ButtonStyle.OutlinedButton
    )
}

@Composable
private fun LeftContentAvatar(size: AvatarSize) {
    val person = Person(firstName = "", lastName = "", image = drawable.avatar_amanda_brady)
    return Avatar(person = person, size = size, enablePresence = false)
}

@Composable
private fun LeftContentAvatarCutout(size: AvatarSize) {
    val person =
        Person(firstName = "", lastName = "", image = drawable.avatar_amanda_brady, isActive = true)
    return Avatar(
        person = person,
        size = size,
        enablePresence = false,
        enableActivityRings = true,
        cutoutIconDrawable = drawable.cutout_heart16x16
    )
}

@Composable
private fun GetAvatar(size: AvatarSize, image: Int) {
    val person = Person(firstName = "", lastName = "", image = image)
    return Avatar(person = person, size = size, enablePresence = false)
}

@Composable
private fun RightContentText(text: String) {
    return BasicText(text = text)
}