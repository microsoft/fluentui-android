package com.microsoft.fluentui.listitem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Icon
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
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.FontInfo
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.Medium
import com.microsoft.fluentui.theme.token.GlobalTokens.SpacingTokens.XSmall
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*
import com.microsoft.fluentui.theme.token.controlTokens.TextPlacement.Top
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import kotlin.math.exp

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
                indication = rememberRipple(),
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
        private fun descriptionText(description: String, text:String, onClick: () -> Unit, descriptionTextColor: Color, actionTextColor: Color, descriptionTextSize:FontInfo, actionTextSize:FontInfo) {
            val annotatedText = buildAnnotatedString {
                val grayStyle = SpanStyle(color = descriptionTextColor, fontSize = descriptionTextSize.fontSize.size, fontWeight = descriptionTextSize.weight)
                pushStyle(grayStyle)
                append("$description ")
                pop()

                pushStringAnnotation(
                    tag = "text",
                    annotation = text
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
                val horizontalPadding = getListItemTokens().padding(Medium)
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
                            enableChevron:Boolean = true,
                            rightAccessory:(@Composable () -> Unit)? = null,
                            content:(@Composable () -> Unit)? = null,
                            interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
            CompositionLocalProvider(LocalListItemTokens provides token){

                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
                val cellHeight = getListItemTokens().cellHeight(listItemType = OneLine)
                val textSize = getListItemTokens().textSize(textType = ListTextType.Text, style = style)
                val actionTextSize = getListItemTokens().textSize(textType = ListTextType.ActionText, style = style)
                val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
                val actionTextColor = getListItemTokens().textColor(textType = ListTextType.ActionText)
                val horizontalPadding = getListItemTokens().padding(Medium)
                val verticalPadding = getListItemTokens().padding(size = XSmall)
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)

                var expandedState by remember { mutableStateOf(false)}
                val rotationState by animateFloatAsState(
                    targetValue = if(expandedState) 90f else 0f)
                Row(
                    modifier
                        .fillMaxWidth()
                        .heightIn(min = cellHeight, max = cellHeight)
                        .background(backgroundColor)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(
                            interactionSource,
                            onClick = { expandedState = !expandedState }
                        ), verticalAlignment = Alignment.Bottom){
                    Box(
                        Modifier
                            .padding(start = horizontalPadding, bottom = verticalPadding)
                            .weight(1f)){
                        Row(verticalAlignment = Alignment.CenterVertically){
                            if(enableChevron){
                                Icon(painter = painterResource(id = R.drawable.ic_chevron_right_12), "chevron",
                                    modifier
                                        .clickable { expandedState = !expandedState }
                                        .rotate(rotationState))
                            }
                            Text(text = title, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor.rest, maxLines = titleMaxLines)
                        }

                    }
                    if(actionTitle != null){
                        Box(Modifier.padding(end = horizontalPadding, bottom = verticalPadding), contentAlignment = Alignment.Center){
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
                        Box(Modifier.padding(end = horizontalPadding, bottom = verticalPadding), contentAlignment = Alignment.Center){
                            rightAccessory()
                        }

                    }
                }
                if(expandedState){
                    AnimatedVisibility(visible = true) {
                        if (content != null) {
                            content()
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
                                descriptionPlacement:TextPlacement = Top,
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
                val horizontalPadding = getListItemTokens().padding(Medium)
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
                val descriptionAlignment = getListItemTokens().descriptionPlacement(placement = descriptionPlacement)
                val verticalPadding = if(descriptionPlacement == Top) {
                    PaddingValues(top = getListItemTokens().padding(size = XSmall))
                }else{
                    PaddingValues(bottom = getListItemTokens().padding(size = XSmall))
                }
                Row(
                    modifier
                        .fillMaxWidth()
                        .heightIn(min = cellHeight)
                        .background(backgroundColor)
                        .borderModifier(border, borderColor, borderSize, borderInset)
                        .clickAndSemanticsModifier(
                            interactionSource,
                            onClick = onClick ?: {}), verticalAlignment = descriptionAlignment){
                    if(iconAccessory != null){
                        Box(
                            Modifier
                                .padding(start = horizontalPadding)
                                .padding(verticalPadding), contentAlignment = Alignment.Center){
                            iconAccessory()
                        }
                    }
                    Box(
                        Modifier
                            .padding(start = horizontalPadding)
                            .padding(verticalPadding)
                            .weight(1f)){
                        if(action){
                            descriptionText(description = description, text = "Action", onClick = onActionClick?:{}, descriptionTextColor = textColor.rest, actionTextColor = actionTextColor.rest, descriptionTextSize = textSize, actionTextSize = actionTextSize)
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
//        @Composable
//        fun AvatarCarousel(modifier: Modifier = Modifier,
//                            person: Person,
//                           listItemTokens: ListItemTokens? = null,
//                           border:BorderType = BorderType.No_Border,
//                           borderInset:BorderInset = BorderInset.None,
//                           size:AvatarCarouselSize = AvatarCarouselSize.Medium,
//                           onClick: (() -> Unit)? = null,
//                           interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){
//            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
//            CompositionLocalProvider(LocalListItemTokens provides token){
//
//                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
//                val cellWidth = getListItemTokens().cellHeight(listItemType = AvatarCarousel)
//                val firstNameTextSize = getListItemTokens().textSize(textType = ListTextType.FirstName)
//                val lastNameTextSize = getListItemTokens().textSize(textType = ListTextType.LastName)
//                val firstNameTextColor = getListItemTokens().textColor(textType = ListTextType.FirstName)
//                val lastNameTextColor = getListItemTokens().textColor(textType = ListTextType.LastName)
//                val horizontalPadding = getListItemTokens().avatarTextHorizontalPadding(carouselSize = size)
//                val verticalPadding = getListItemTokens().avatarTextVerticalPadding(carouselSize = size)
//                val borderSize = getListItemTokens().borderSize().value
//                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
//                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
//                val avatarSize = if(size == AvatarCarouselSize.Medium) {
//                    AvatarSize.XLarge
//                }else{
//                    AvatarSize.XXLarge
//                }
//                Column(
//                    modifier
//                        .widthIn(max = cellWidth)
//                        .background(backgroundColor)
//                        .borderModifier(border, borderColor, borderSize, borderInset)
//                        .clickAndSemanticsModifier(
//                            interactionSource,
//                            onClick = onClick ?: {}), horizontalAlignment = Alignment.CenterHorizontally){
//                    Box(Modifier.padding(top = 8.dp, bottom = verticalPadding), contentAlignment = Alignment.Center){
//                        Avatar(person = person, size = avatarSize)
//                    }
//                    Box(
//                        Modifier
//                            .padding(start = horizontalPadding, end = horizontalPadding)){
//                        Text(text = person.firstName, color = firstNameTextColor.rest, fontSize = firstNameTextSize.fontSize.size, fontWeight = firstNameTextSize.weight)
//                    }
//                    if(size == AvatarCarouselSize.Large && person.lastName.isNotEmpty()){
//                        Box(
//                            Modifier
//                                .padding(start = horizontalPadding, end = horizontalPadding)){
//                            Text(text = person.lastName, color = lastNameTextColor.rest, fontSize = lastNameTextSize.fontSize.size, fontWeight = lastNameTextSize.weight)
//                        }
//                    }
//                }
//            }
//        }

}

