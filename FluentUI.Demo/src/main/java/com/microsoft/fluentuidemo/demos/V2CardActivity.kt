package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.AnnouncementCard
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FileCard
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import kotlin.random.Random

class V2CardActivity: DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(LayoutInflater.from(container.context), container,true)
        v2ActivityComposeBinding.composeHere.setContent {
            FluentTheme {
                CreateCardUI()
            }
        }
    }

    @Composable
    private fun CreateCardUI(){
        var index by remember { mutableStateOf(1) }
        Box(){
            LazyColumn(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                item {
                    ListItem.Header(modifier = Modifier.wrapContentWidth(), title = "Basic Card")
                }
                item {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(24.dp)) {
                        BasicCard(onClick = {}, content = { getContent(index = index) })
                        Button(text = "Randomize UI", size = ButtonSize.Small, style = ButtonStyle.OutlinedButton, onClick = { index = (1..5).random() })
                    }
                }
                item {
                    ListItem.Header(title = "File Card")
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            item{
                                FileCard(actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert), onClick = {}, text = "Carlos", subText = "Slattery", textIcon = FluentIcon(Icons.Outlined.Call), previewImageDrawable = R.drawable.avatar_carlos_slattery)
                            }
                            item{
                                FileCard(actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert), onClick = {}, text = "Allan", subText = "Munger", textIcon = FluentIcon(Icons.Outlined.Call), previewImageDrawable = R.drawable.avatar_allan_munger)
                            }
                            item{
                                FileCard(actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert), onClick = {}, text = "Ashley", subText = "McCarthy", textIcon = FluentIcon(Icons.Outlined.Call), previewImageDrawable = R.drawable.avatar_ashley_mccarthy)
                            }
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)){
                            item{
                                FileCard(actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert), onClick = {}, text = "Amanda", subText = "Brady", textIcon = FluentIcon(Icons.Outlined.Call), previewImageDrawable = R.drawable.avatar_amanda_brady)
                            }
                            item{
                                FileCard(actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert), onClick = {}, text = "Kat", subText = "Larsson", textIcon = FluentIcon(Icons.Outlined.Call), previewImageDrawable = R.drawable.avatar_kat_larsson)
                            }
                            item{
                                FileCard(actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert), onClick = {}, text = "Cecil", subText = "Folk", textIcon = FluentIcon(Icons.Outlined.Call), previewImageDrawable = R.drawable.avatar_cecil_folk)
                            }
                        }
                    }
                }
                item {
                    ListItem.Header(title = "Announcement Card")
                }
                item{
                    AnnouncementCard(
                        title = "Title",
                        text = "Secondary copy for this banner can wrap to two lines if needed.",
                        buttonText = "Button",
                        buttonOnClick = {},
                        previewImageDrawable = R.drawable.card_cover
                    )
                }
                item { 
                    Text(text = "")
                }
            }
        }

    }
    
    @Composable
    private fun getContent(index: Int): Unit{
        return when(index){
            1 -> CardContent1()
            2 -> CardContent2()
            3 -> CardContent3()
            4 -> CardContent4()
            5 -> CardContent5()
            else -> {}
        }
    }

    @Composable
    private fun CardContent1(){
        Box(modifier = Modifier.padding(all = 8.dp)){
          Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
              Icon(
                  painter = painterResource(id = R.drawable.ic_fluent_flag_24_regular),
                  contentDescription = "Flag"
              )
              Column{
                  Text(text = "Text")
                  Text(text = "SubText")
              }
          }
        }
    }
    @Composable
    private fun CardContent2(){
        Box(modifier = Modifier.padding(all = 8.dp)){
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Column{
                    Text(text = "Text")
                }
            }
        }
    }
    @Composable
    private fun CardContent3(){
        Box(modifier = Modifier.padding(all = 8.dp)){
            Column() {
                Image(
                    painterResource(id = R.drawable.cover), contentDescription = ""
                )
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_flag_24_regular),
                        contentDescription = "Flag"
                    )
                    Column{
                        Text(text = "Text")
                        Text(text = "SubText")
                    }
                }
            }
        }
    }
    @Composable
    private fun CardContent4(){
        Box(){
            Image(
                painterResource(id = R.drawable.image_un), contentDescription = ""
            )
        }
    }
    @Composable
    private fun CardContent5(){
        Box(modifier = Modifier.padding(end = 8.dp)){
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Image(
                    painterResource(id = R.drawable.avatar_carlos_slattery), contentDescription = ""
                )
                Column{
                    Text(text = "Text")
                    Text(text = "SubText")
                }
            }
        }
    }
}