package com.microsoft.fluentui.listitem

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.IntrinsicSize.Max
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.ListAccessoryType.Checkbox
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*
import com.microsoft.fluentui.theme.token.controlTokens.Placement.Top
import kotlin.math.max

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }

@Composable
fun getListItemTokens(): ListItemTokens {
    return LocalListItemTokens.current
}

class ListItem{
    companion object{
        private fun Modifier.clickAndSemanticsModifier(interactionSource: MutableInteractionSource, onClick: () -> Unit):Modifier = composed{
            Modifier.clickable(
                interactionSource = interactionSource,
                indication = LocalIndication.current,
                onClickLabel = null,
                role = Role.Tab,
                onClick = onClick
            )

        }
        private fun Modifier.borderModifier(border: BorderType, borderColor:Color, borderSize:Float, borderInset:Float): Modifier = composed{
            Modifier.drawBehind {
                when(border){
                    BorderType.Top -> drawLine(borderColor,
                        Offset(0f, 0f),
                        Offset(size.width, 0f),
                        borderSize * density)
                    BorderType.Bottom -> drawLine(
                        borderColor,
                        Offset(borderInset, size.height),
                        Offset(size.width, size.height),
                        borderSize * density
                    )
                    BorderType.Top_Bottom -> {
                        drawLine(
                            borderColor,
                            Offset(0f, 0f),
                            Offset(size.width, 0f),
                            borderSize * density
                        )
                        drawLine(
                            borderColor,
                            Offset(borderInset, size.height),
                            Offset(size.width, size.height),
                            borderSize * density
                        )
                    }
                }
            }

        }
        @Composable
        private fun descriptionText(enabled:Boolean, description: String, text:String, onClick: () -> Unit, descriptionTextColor: Color, actionTextColor: Color, descriptionTextSize:FontInfo, actionTextSize:FontInfo) {
            val annotatedText = buildAnnotatedString {
                val grayStyle = SpanStyle(color = descriptionTextColor, fontSize = descriptionTextSize.fontSize.size, fontWeight = descriptionTextSize.weight)
                pushStyle(grayStyle)
                append("$description ")
                pop()

                pushStringAnnotation(
                    tag = "text",
                    annotation = "text"
                )
                val style = SpanStyle(color = actionTextColor, fontSize = actionTextSize.fontSize.size, fontWeight = actionTextSize.weight)

                pushStyle(style)
                append(text)

                pop()
            }

            return ClickableText(text = annotatedText, onClick = {
                annotatedText.getStringAnnotations(
                    tag = "text",
                    start = it,
                    end = it
                ).firstOrNull().let { onClick() }
            })

        }

        @Composable
        fun OneLine(modifier: Modifier = Modifier,
                        text:String,
                        subText:String? = null,
                        secondarySubText:String? = null,
                        textMaxLines:Int = 1,
                        subTextMaxLines:Int = 1,
                        secondarySubTextMaxLines:Int = 1,
                        onClick: () -> Unit,
                        border:BorderType = BorderType.No_Border,
                        borderInset:BorderInset = BorderInset.None,
                        listItemTokens: ListItemTokens? = null,
                        leftAccessoryView:(@Composable () -> Unit)? = null,
                        rightAccessoryView:(@Composable () -> Unit)? = null,
                        interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
            CompositionLocalProvider(LocalListItemTokens provides token){

                var listItemType = if(subText == null && secondarySubText == null){
                    OneLine
                }else if((secondarySubText == null && subText != null) || (secondarySubText != null && subText == null)) {
                    TwoLine
                }else{
                    ThreeLine
                }
                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
                val cellHeight = getListItemTokens().cellHeight(listItemType = listItemType)
                val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
                val subLabelSize = getListItemTokens().textSize(textType = ListTextType.SubLabelText)
                val textColor = getColorByState(stateData = getListItemTokens().textColor(textType = ListTextType.Text), enabled = true, interactionSource = interactionSource)
                val subLabelColor = getColorByState(stateData = getListItemTokens().textColor(textType = ListTextType.SubLabelText), enabled = true, interactionSource = interactionSource)
                val horizontalPadding = getListItemTokens().padding()
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
                Row(
                    modifier
                        .background(backgroundColor)
                        .fillMaxWidth()
                        .height(cellHeight)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(interactionSource, onClick), verticalAlignment = Alignment.CenterVertically){
                    if(leftAccessoryView != null){
                        Box(Modifier.padding(start = horizontalPadding), contentAlignment = Alignment.Center){
                            leftAccessoryView()
                        }
                    }
                    Box(
                        Modifier
                            .padding(start = horizontalPadding, end = horizontalPadding)
                            .weight(1f), contentAlignment = Alignment.CenterStart){
                        Column() {
                            Text(text = text, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor, maxLines = textMaxLines)
                            if(subText != null){
                                Text(text = subText, fontSize = subLabelSize.fontSize.size, fontWeight = subLabelSize.weight, color = subLabelColor, maxLines = subTextMaxLines)
                            }
                            if(secondarySubText != null){
                                Text(text = secondarySubText, fontSize = subLabelSize.fontSize.size, fontWeight = subLabelSize.weight, color = subLabelColor, maxLines = secondarySubTextMaxLines)
                            }
                        }

                    }
                    if(rightAccessoryView != null){
                        Box(Modifier.padding(end = horizontalPadding), contentAlignment = Alignment.Center){
                            rightAccessoryView()
                        }

                    }
                }
            }

        }
        @Composable
        fun SectionHeader(modifier: Modifier = Modifier,
                            title:String,
                            titleMaxLines:Int = 1,
                            actionTitle:String? = null,
                            actionOnClick: (() -> Unit)? = null,
                            border:BorderType = BorderType.No_Border,
                            borderInset:BorderInset = BorderInset.None,
                            listItemTokens: ListItemTokens? = null,
                            style:SectionHeaderStyle = SectionHeaderStyle.Standard,
                            accessoryIcon:(@Composable () -> Unit)? = null,
                            rightAccessory:(@Composable () -> Unit)? = null,
                            onClick: (() -> Unit)? = null,
                            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
            CompositionLocalProvider(LocalListItemTokens provides token){

                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
                val cellHeight = getListItemTokens().cellHeight(listItemType = OneLine)
                val textSize = getListItemTokens().textSize(textType = ListTextType.Text, style = style)
                val actionTextSize = getListItemTokens().textSize(textType = ListTextType.ActionText, style = style)
                val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
                val actionTextColor = getListItemTokens().textColor(textType = ListTextType.ActionText)
                val horizontalPadding = getListItemTokens().padding()
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
                Row(
                    modifier
                        .fillMaxWidth()
                        .heightIn(min = cellHeight, max = cellHeight)
                        .background(backgroundColor)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(
                            interactionSource,
                            onClick = onClick?:{}
                        ), verticalAlignment = Alignment.CenterVertically){
                    if(accessoryIcon != null){
                        Box(Modifier.padding(start = horizontalPadding), contentAlignment = Alignment.Center){
                            accessoryIcon()
                        }
                    }
                    Box(
                        Modifier
                            .padding(start = horizontalPadding)
                            .weight(1f), contentAlignment = Alignment.CenterStart){
                        Text(text = title, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor.rest, maxLines = titleMaxLines)
                    }
                    if(actionTitle != null){
                        Box(Modifier.padding(end = horizontalPadding), contentAlignment = Alignment.Center){
                            Text(text = actionTitle, modifier.clickable(
                                interactionSource = interactionSource,
                                indication = LocalIndication.current,
                                onClickLabel = null,
                                role = Role.Button,
                                onClick = actionOnClick ?:{}
                            ), color = actionTextColor.rest, fontSize = actionTextSize.fontSize.size, fontWeight = actionTextSize.weight)
                        }
                    }
                    if(rightAccessory != null){
                        Box(Modifier.padding(end = horizontalPadding), contentAlignment = Alignment.Center){
                            rightAccessory()
                        }

                    }
                }
            }
        }
        @Composable
        fun AvatarCarousel(modifier: Modifier = Modifier,
                           firstName:String,
                           lastName:String? = null,
                           listItemTokens: ListItemTokens? = null,
                           avatar:(@Composable () -> Unit),
                           border:BorderType = BorderType.No_Border,
                           borderInset:BorderInset = BorderInset.None,
                           size:AvatarCarouselSize = AvatarCarouselSize.Medium,
                           onClick: (() -> Unit)? = null,
                           interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){
            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
            CompositionLocalProvider(LocalListItemTokens provides token){

                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
                val cellHeight = getListItemTokens().cellHeight(listItemType = AvatarCarousel)
                val firstNameTextSize = getListItemTokens().textSize(textType = ListTextType.Text)
                val lastNameTextSize = getListItemTokens().textSize(textType = ListTextType.SubLabelText)
                val firstNameTextColor = getListItemTokens().textColor(textType = ListTextType.Text)
                val lastNameTextColor = getListItemTokens().textColor(textType = ListTextType.DescriptionText)
                val horizontalPadding = getListItemTokens().padding()
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
                Column(
                    modifier
                        .widthIn(max = 88.dp)
                        .heightIn(min = cellHeight)
                        .background(backgroundColor)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(
                            interactionSource,
                            onClick = onClick?:{}), horizontalAlignment = Alignment.CenterHorizontally){
                    Box(Modifier.padding(top = 8.dp, bottom = 8.dp), contentAlignment = Alignment.Center){
                        avatar()
                    }
                    Box(
                        Modifier
                            .padding(start = 8.dp)){
                        Text(text = firstName, color = firstNameTextColor.rest, fontSize = firstNameTextSize.fontSize.size, fontWeight = firstNameTextSize.weight)
                    }
                    if(size == AvatarCarouselSize.Large && lastName != null){
                        Box(
                            Modifier
                                .padding(start = 8.dp)){
                            Text(text = lastName, color = lastNameTextColor.rest, fontSize = lastNameTextSize.fontSize.size, fontWeight = lastNameTextSize.weight)
                        }
                    }
                }
            }
        }

        @Composable
        fun SectionDescription(modifier: Modifier = Modifier,
                               description:String,
                               listItemTokens: ListItemTokens? = null,
                               iconAccessory:(@Composable () -> Unit)? = null,
            rightAccessory:(@Composable () -> Unit)? = null,
                               action:Boolean = false,
                                placement:Placement = Top,
            border:BorderType = BorderType.No_Border,
            borderInset:BorderInset = BorderInset.None,
            onClick: (() -> Unit)? = null,
            onActionClick: (() -> Unit)? = null,
                    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){
            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
            CompositionLocalProvider(LocalListItemTokens provides token){

                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
                val cellHeight = getListItemTokens().cellHeight(listItemType = SectionDescription)
                val textSize = getListItemTokens().textSize(textType = ListTextType.DescriptionText)
                val actionTextSize = getListItemTokens().textSize(textType = ListTextType.ActionText)
                val textColor = getListItemTokens().textColor(textType = ListTextType.DescriptionText)
                val actionTextColor = getListItemTokens().textColor(textType = ListTextType.ActionText)
                val horizontalPadding = getListItemTokens().padding()
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
                val descriptionPlacement = getListItemTokens().descriptionPlacement(placement = placement)
                Row(
                    modifier
                        .fillMaxWidth()
                        .heightIn(min = cellHeight)
                        .background(backgroundColor)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(
                            interactionSource,
                            onClick = onClick ?: {}), verticalAlignment = Alignment.CenterVertically){
                    if(iconAccessory != null){
                        Box(Modifier.padding(start = horizontalPadding), contentAlignment = Alignment.Center){
                            iconAccessory()
                        }
                    }
                    Box(
                        Modifier
                            .padding(start = horizontalPadding)
                            .weight(1f), contentAlignment = descriptionPlacement){
                        if(action){
                            descriptionText(enabled = action, description = description, text = "Action", onClick = onActionClick?:{}, descriptionTextColor = textColor.rest, actionTextColor = actionTextColor.rest, descriptionTextSize = textSize, actionTextSize = actionTextSize)
                        }else{
                            Text(text = description, color = textColor.rest, fontSize = textSize.fontSize.size, fontWeight = textSize.weight)
                        }
                    }
                    if(rightAccessory != null){
                        Box(Modifier.padding(end = horizontalPadding), contentAlignment = Alignment.Center){
                            rightAccessory()
                        }

                    }
                }
            }

        }

    }

}

