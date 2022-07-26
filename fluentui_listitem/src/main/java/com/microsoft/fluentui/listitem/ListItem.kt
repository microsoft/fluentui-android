package com.microsoft.fluentui.listitem

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title:String = "title",
    subTitle:String = "subTitle",
    leftAccessoryView:(@Composable () -> Unit)? = null,
    rightAccessoryView:(@Composable () -> Unit)? = null
) {
    Box(
        modifier
            .border(width = 1.dp, color = Black)
            .wrapContentSize()
    ) {
        Row(Modifier.fillMaxWidth()) {
            if (leftAccessoryView != null) {
                leftAccessoryViewContainer(leftAccessoryView)
            }
            Divider(color = Color.Green, modifier = Modifier.width(1.dp))
            textViewContainer(title, subTitle)
            Divider(color = Color.Green, modifier = Modifier.width(1.dp))
            if (rightAccessoryView != null) {
                rightAccessoryViewContainer(rightAccessoryView)
            }
        }

    }
}

@Composable
fun leftAccessoryViewContainer(leftAccessoryView: @Composable () -> Unit){
    LazyRow(
    ){
        leftAccessoryView
    }
}

@Composable
fun rightAccessoryViewContainer(rightAccessoryView: @Composable (() -> Unit)){
    LazyRow(
    ){
        rightAccessoryView
    }
}

@Composable
fun textViewContainer(title: String, subTitle: String){
    LazyColumn(
    ){
        item {
            Text(title)
        }
        item {
            Text(subTitle)
        }
    }
}

@Preview
@Composable
fun previewList(){
    ListItem()
}