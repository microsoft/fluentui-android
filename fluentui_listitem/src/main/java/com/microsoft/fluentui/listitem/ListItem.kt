package com.microsoft.fluentui.listitem

import android.R
import android.R.drawable
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
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
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.OneLine
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }

//object OneLine{
//    @Composable
//    fun ListItem(
//        modifier: Modifier = Modifier,
//        onClick: () -> Unit,
//        text:String,
//        listItemTokens: ListItemTokens? = null,
//        leftAccessoryView: (@Composable () -> Unit)? = null,
//        leftIconAccessoryView:(@Composable () -> Unit)? = null,
//        leftButtonAccessoryView:(@Composable () -> Unit)? = null,
//        rightAccessoryView:(@Composable () -> Unit)? = null,
//        rightIconAccessoryView:(@Composable () -> Unit)? = null,
//        rightButtonAccessoryView:(@Composable () -> Unit)? = null,
//        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){
//
//        val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
//        CompositionLocalProvider(LocalListItemTokens provides token){
//
//            val clickAndSemanticsModifier = Modifier.clickable(
//                interactionSource = interactionSource,
//                indication = LocalIndication.current,
//                onClickLabel = null,
//                role = Role.Tab,
//                onClick = onClick
//            )
//
//            val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
//            val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
//            val borderSize = getListItemTokens().borderSize()
//            val cellHeight = getListItemTokens().height(listItemType = OneLine)
//            val contentSpacing = getListItemTokens().spacing(Icon)
//            val buttonContentSpacing = getListItemTokens().spacing(Button)
//            val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
//            val horizontalPadding = getListItemTokens().padding()
//            val textColor = getListItemTokens().textColor(textType = ListTextType.Text).rest
//            Row(
//                modifier
//                    .background(backgroundColor)
//                    .border(width = borderSize, color = borderColor)
//                    .fillMaxWidth()
//                    .height(cellHeight)
//                    .padding(horizontal = horizontalPadding)
//                    .then(clickAndSemanticsModifier),
//                verticalAlignment = Alignment.CenterVertically
//            ){
//                if(leftAccessoryView != null){
//                    Box(){
//                        Row(Modifier.absolutePadding(right = contentSpacing), horizontalArrangement = Arrangement.spacedBy(contentSpacing)) {
//                            leftAccessoryView()
//                        }
//                    }
//                }
//                if(leftIconAccessoryView != null){
//                    Box(){
//                        Row(Modifier.absolutePadding(right = contentSpacing), horizontalArrangement = Arrangement.spacedBy(contentSpacing)) {
//                            leftIconAccessoryView()
//                        }
//                    }
//                }
//                if(leftButtonAccessoryView != null){
//                    Box(){
//                        Row(Modifier.absolutePadding(right = horizontalPadding), horizontalArrangement = Arrangement.spacedBy(buttonContentSpacing), verticalAlignment = Alignment.CenterVertically) {
//                            leftButtonAccessoryView()
//                        }
//
//                    }
//                }
//
//                Box(){
//                    Row(Modifier.absolutePadding(right = horizontalPadding)){
//                        Text(text = text, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor)
//                    }
//                }
//                Spacer(Modifier.weight(1f))
//                if(rightAccessoryView != null){
//                    Box(){
//                        Row(Modifier.absolutePadding(right = contentSpacing), horizontalArrangement = Arrangement.spacedBy(contentSpacing)) {
//                            rightAccessoryView()
//                        }
//                    }
//                }
//                if(rightIconAccessoryView != null){
//                    Box(){
//                        Row(Modifier.absolutePadding(right = contentSpacing), horizontalArrangement = Arrangement.spacedBy(contentSpacing)) {
//                            rightIconAccessoryView()
//                        }
//                    }
//                }
//                if(rightButtonAccessoryView != null){
//                    Box(){
//                        Row(Modifier.absolutePadding(right = horizontalPadding), horizontalArrangement = Arrangement.spacedBy(buttonContentSpacing), verticalAlignment = Alignment.CenterVertically) {
//                            rightButtonAccessoryView()
//                        }
//
//                    }
//                }
//
//            }
//        }
//
//    }
//}

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
            val horizontalPadding = getListItemTokens().padding()
            val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
            val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
            Box() {
                Column() {
                    Row(
                        modifier
                            .background(backgroundColor)
                            .border(width = borderSize, color = borderColor)
                            .fillMaxWidth()
                            .height(cellHeight)
                            .then(clickAndSemanticsModifier), verticalAlignment = Alignment.CenterVertically){
                        if(accessoryIcon != null){
                            Box(Modifier.padding(start = horizontalPadding)){
                                accessoryIcon()
                            }
                        }
                        Box(Modifier.padding(start = horizontalPadding)){
                            Text(text = title, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor.rest)
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        if(action != null){
                            Box(Modifier.padding(end = horizontalPadding)){
                                action()
                            }

                        }
                    }
                    AnimatedVisibility(visible = show){
                        Box(
                            Modifier
                                .fillMaxSize()){
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

object OneLine{
    @Composable
    fun ListItem(leftAccessoryView: (@Composable () -> Unit)? = null,
        text:String,
        rightAccessoryView: (@Composable () -> Unit)? = null,
        modifier:Modifier = Modifier,
        listItemTokens: ListItemTokens? = null,
        onClick: () -> Unit,
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
            val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
            val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
            val horizontalPadding = getListItemTokens().padding()
            Row(
                modifier
                    .background(backgroundColor)
                    .border(width = borderSize, color = borderColor)
                    .fillMaxWidth()
                    .height(cellHeight)
                    .then(clickAndSemanticsModifier), verticalAlignment = Alignment.CenterVertically){
                if(leftAccessoryView != null){
                    Box(Modifier.padding(start = horizontalPadding)){
                        leftAccessoryView()
                    }
                }
                Box(Modifier.padding(start = horizontalPadding)){
                    Text(text = text, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor.rest)
                }
                Spacer(modifier = Modifier.weight(1f))
                if(rightAccessoryView != null){
                    Box(Modifier.padding(end = horizontalPadding)){
                        rightAccessoryView()
                    }

                }
            }
        }
    }
}

