package com.microsoft.fluentui.listitem

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.ListAccessoryType.Button
import com.microsoft.fluentui.theme.token.controlTokens.ListAccessoryType.Icon
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.OneLine
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }

object OneLine{
    @Composable
    fun ListItem(
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        text:String,
        listItemTokens: ListItemTokens? = null,
        leftIconAccessoryView:(@Composable () -> Unit)? = null,
        leftButtonAccessoryView:(@Composable () -> Unit)? = null,
        rightAccessoryView:(@Composable () -> Unit)? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

        val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(LocalListItemTokens provides token){

            val clickAndSemanticsModifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClickLabel = null,
                role = Role.Tab,
                onClick = onClick
            )

            val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
            val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
            val borderSize = getListItemTokens().borderSize()
            val cellHeight = getListItemTokens().height(listItemType = OneLine)
            val contentPadding = getListItemTokens().padding(Icon)
            val buttonContentPadding = getListItemTokens().padding(Button)
            val textSize = getListItemTokens().textSize(textType = ListTextType.Text)

            Row(
                modifier
                    .background(backgroundColor)
                    .border(width = borderSize, color = borderColor)
                    .fillMaxWidth()
                    .height(cellHeight)
                    .then(clickAndSemanticsModifier),
                verticalAlignment = Alignment.CenterVertically
            ){
                if(leftIconAccessoryView != null){
                    Box(Modifier.padding(contentPadding)){
                        leftIconAccessoryView()
                    }
                }
                if(leftButtonAccessoryView != null){
                    Box(Modifier.padding(buttonContentPadding),
                    ){
                        leftButtonAccessoryView()
                    }
                }

                Box(Modifier.padding(contentPadding)){
                    Row(){
                        Text(text = text, fontSize = textSize.fontSize.size, fontWeight = textSize.weight)
                    }
                }
                Spacer(Modifier.weight(1f))
                if(rightAccessoryView != null){
                    Box(Modifier.padding(contentPadding)){
                        rightAccessoryView()
                    }
                }

            }
        }

    }
}

public object MultiLine{
    @Composable
    fun ListItem(modifier: Modifier = Modifier,
        text:String,
        subText:String? = null,
        secondarySubText:String? = null,
        listItemTokens: ListItemTokens? = null,
        leftAccessoryView:(@Composable () -> Unit)? = null,
        rightAccessoryView:(@Composable () -> Unit)? = null){

    }
}

object SectionHeader{
    @Composable
    fun ListItem(modifier: Modifier = Modifier,
        title:String,
        listItemTokens: ListItemTokens? = null,
        list:(@Composable () -> Unit),
        accessoryIcon:(@Composable () -> Unit)? = null,
        action:(@Composable () -> Unit)? = null,
        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

        val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
        CompositionLocalProvider(LocalListItemTokens provides token){

            var show by remember { mutableStateOf(false) }

            val clickAndSemanticsModifier = Modifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClickLabel = null,
                role = Role.Tab,
                onClick = {
                    show = !show
                }
            )

            val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
            val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
            val borderSize = getListItemTokens().borderSize()
            val cellHeight = getListItemTokens().height(listItemType = OneLine)
            val contentPadding = getListItemTokens().padding(Icon)
            val textSize = getListItemTokens().textSize(textType = ListTextType.Text)

            Box() {
                Column() {
                    Row(
                        modifier
                            .background(backgroundColor)
                            .border(width = borderSize, color = borderColor)
                            .fillMaxWidth()
                            .height(cellHeight)
                            .then(clickAndSemanticsModifier),
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        if(accessoryIcon != null){
                            Box(Modifier.padding(contentPadding)){
                                accessoryIcon()
                            }
                        }
                        Box(Modifier.padding(contentPadding)){
                            Row(){
                                Text(text = title, fontSize = textSize.fontSize.size, fontWeight = textSize.weight)
                            }
                        }
                        Spacer(Modifier.weight(1f))
                        if(action != null){
                            Box(Modifier.padding(contentPadding)){
                                action()
                            }
                        }

                    }
                    if(show){
                        Row(){
                            list()
                        }
                    }

                }


            }
        }
    }
}

object SectionDescription{
    @Composable
    fun ListItem(modifier: Modifier = Modifier,
        description:String,
        listItemTokens: ListItemTokens? = null,
        icon:(@Composable () -> Unit)? = null,
        action:(@Composable () -> Unit)? = null){

    }
}

object AvatarCarousel{
    @Composable
    fun ListItem(modifier: Modifier = Modifier,
        text:String,
        subText:String? = null,
        listItemTokens: ListItemTokens? = null,
        avatar:(@Composable () -> Unit)){

    }
}
@Composable
fun getListItemTokens(): ListItemTokens {
    return LocalListItemTokens.current
}