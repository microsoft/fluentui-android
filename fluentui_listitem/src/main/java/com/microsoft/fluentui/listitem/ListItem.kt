package com.microsoft.fluentui.listitem

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection.Ltr
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens.ControlType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListAccessoryType.Icon
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.ListTextType

val LocalListItemTokens = compositionLocalOf { ListItemTokens() }

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title:String? = null,
    subTitle:String? = null,
    listItemTokens: ListItemTokens? = null,
    leftAccessoryView:(@Composable () -> Unit)? = null,
    rightAccessoryView:(@Composable () -> Unit)? = null
) {
    val token = listItemTokens ?: FluentTheme.controlTokens.tokens[ControlType.ListItem] as ListItemTokens

    CompositionLocalProvider(LocalListItemTokens provides token){
        val backgroundColor = getListItemTokens().backgroundColor()
        val contentPadding = getListItemTokens().padding()
        val iconSpacing = getListItemTokens().spacing(accessoryType = Icon)
        val borders = getListItemTokens().borderSize()
        val borderColor = getListItemTokens().borderColor()
        val textColor = getListItemTokens().textColor(textType = ListTextType.Text)
        val textFont = getListItemTokens().textSize(textType = ListTextType.Text)
        Box(
            modifier
                .fillMaxWidth()
                .background(backgroundColor.rest)
        ) {
            Row(Modifier.fillMaxWidth()) {
                if (leftAccessoryView != null) {
                    Box(
                        Modifier
                            .align(Alignment.CenterVertically)
                            .padding(contentPadding)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(iconSpacing)) {
                            leftAccessoryView()
                        }
                    }
                }
                if(title!=null){
                    Box(
                        Modifier
                            .align(Alignment.CenterVertically)
                            .padding(contentPadding)
                    ){
                        Column() {
                            Text(text=title,color=textColor.rest, fontSize = textFont.fontSize.size, fontWeight = textFont.weight)
                            if(subTitle!=null && subTitle.isNotEmpty()){
                                Text(text = subTitle,color=textColor.rest)
                            }
                        }
                    }
                }

                if (rightAccessoryView != null) {
                    Box(
                        Modifier
                            .align(Alignment.CenterVertically)
                            .padding(contentPadding)
                            .fillMaxWidth(),
                        Alignment.CenterEnd
                    ) {
                        Row( horizontalArrangement = Arrangement.End) {
                            rightAccessoryView()
                        }
                    }
                }
            }

        }
    }

}
@Composable
fun getListItemTokens(): ListItemTokens {
    return LocalListItemTokens.current
}