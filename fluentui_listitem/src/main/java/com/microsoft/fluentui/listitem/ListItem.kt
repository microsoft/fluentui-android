package com.microsoft.fluentui.listitem

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.min
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.*
import com.microsoft.fluentui.theme.token.controlTokens.ListItemType.*

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
        fun OneLine(modifier: Modifier = Modifier,
                      text:String,
                      subText:String? = null,
                      secondarySubText:String? = null,
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
                    MultiLineTwo
                }else{
                    MultiLineThree
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
                    Box(Modifier.padding(start = horizontalPadding).width(IntrinsicSize.Max), contentAlignment = Alignment.Center){
                        Column() {
                            Text(text = text, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor)
                            if(subText != null){
                                Text(text = subText, fontSize = subLabelSize.fontSize.size, fontWeight = subLabelSize.weight, color = subLabelColor)
                            }
                            if(secondarySubText != null){
                                Text(text = secondarySubText, fontSize = subLabelSize.fontSize.size, fontWeight = subLabelSize.weight, color = subLabelColor)
                            }
                        }

                    }
                    Spacer(modifier = Modifier.weight(1f))
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
                          actionTitle:String? = null,
                          actionOnClick: (() -> Unit)? = null,
                          border:BorderType = BorderType.No_Border,
                          borderInset:BorderInset = BorderInset.None,
                          listItemTokens: ListItemTokens? = null,
                          style:SectionHeaderStyle = SectionHeaderStyle.Standard,
                          accessoryIcon:(@Composable () -> Unit)? = null,
                          rightAccessory:(@Composable () -> Unit)? = null,
                          onClick: () -> Unit,
                          interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }){

            val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens
            CompositionLocalProvider(LocalListItemTokens provides token){

                val backgroundColor = getColorByState(stateData = getListItemTokens().backgroundColor(), enabled = true, interactionSource = interactionSource)
                val cellHeight = getListItemTokens().cellHeight(listItemType = OneLine)
                val textSize = getListItemTokens().textSize(textType = ListTextType.Text)
                val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
                val horizontalPadding = getListItemTokens().padding()
                val borderSize = getListItemTokens().borderSize().value
                val borderInset = with (LocalDensity.current) {getListItemTokens().borderInset(inset = borderInset).toPx()}
                val borderColor = getColorByState(stateData = getListItemTokens().borderColor(), enabled = true, interactionSource = interactionSource)
                Box() {
                    Column() {
                        Row(
                            modifier
                                .fillMaxWidth()
                                .heightIn(min = cellHeight, max = cellHeight)
                                .background(backgroundColor)
                                .borderModifier(border, borderColor, borderSize, borderInset)
                                .clickAndSemanticsModifier(
                                    interactionSource,
                                    onClick = onClick), verticalAlignment = Alignment.CenterVertically){
                            if(accessoryIcon != null){
                                Box(Modifier.padding(start = horizontalPadding), contentAlignment = Alignment.Center){
                                    accessoryIcon()
                                }
                            }
                            Box(Modifier.padding(start = horizontalPadding), contentAlignment = Alignment.Center){
                                Text(text = title, fontSize = textSize.fontSize.size, fontWeight = textSize.weight, color = textColor.rest)
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            if(actionTitle != null){
                                Box(Modifier.padding(end = horizontalPadding), contentAlignment = Alignment.Center){
                                    Text(text = actionTitle, modifier.clickable(
                                        interactionSource = interactionSource,
                                        indication = LocalIndication.current,
                                        onClickLabel = null,
                                        role = Role.Button,
                                        onClick = actionOnClick ?:{}
                                    ))
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
        @Composable
        fun AvatarCarousel(modifier: Modifier = Modifier,
                           text:String,
                           subText:String? = null,
                           listItemTokens: ListItemTokens? = null,
                           avatar:(@Composable () -> Unit)){
//TODO
        }

        @Composable
        fun SectionDescription(modifier: Modifier = Modifier,
                               description:String,
                               listItemTokens: ListItemTokens? = null,
                               icon:(@Composable () -> Unit)? = null,
                               action:(@Composable () -> Unit)? = null){

        }

    }

}

