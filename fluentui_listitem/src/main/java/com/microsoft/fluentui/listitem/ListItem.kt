package com.microsoft.fluentui.listitem

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    title:String = "title",
    subTitle:String = "subTitle",
    footer:String = "footer",
    leftAccessoryView:(@Composable () -> Unit)? = null,
    rightAccessoryView:(@Composable () -> Unit)? = null
) {
    Box(
        modifier
            .border(width = 1.dp, color = Black)
            .fillMaxWidth()
    ) {
        Row() {
            if (leftAccessoryView != null) {
                leftAccessoryViewContainer(leftAccessoryView)
            }
            textViewContainer(title, subTitle, footer)
            if (rightAccessoryView != null) {
                rightAccessoryViewContainer(rightAccessoryView)
            }
        }

    }
}

@Composable
fun leftAccessoryViewContainer(leftAccessoryView: @Composable () -> Unit){
    LazyRow(
        Modifier
            .padding(12.dp)
            .fillMaxHeight()
    ){
        leftAccessoryView
    }
}

@Composable
fun rightAccessoryViewContainer(rightAccessoryView: @Composable (() -> Unit)){
    LazyRow(
        Modifier
            .padding(12.dp)
            .fillMaxHeight()
    ){
        rightAccessoryView
    }
}

@Composable
fun textViewContainer(title: String, subTitle: String, footer: String){
    LazyColumn(
        Modifier
            .padding(12.dp)
            .fillMaxHeight()
    ){
        item {
            Text(title)
        }
        item {
            Text(subTitle)
        }
        item {
            Text(footer)
        }
    }
}

@Preview
@Composable
fun previewList(){
    ListItem()
}