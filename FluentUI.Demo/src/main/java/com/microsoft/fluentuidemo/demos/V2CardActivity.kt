package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.AliasTokens
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

class V2CardActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(
            LayoutInflater.from(container.context),
            container,
            true
        )
        v2ActivityComposeBinding.composeHere.setContent {
            FluentTheme {
                CreateCardUI(this)
            }
        }
    }


    @Composable
    private fun CreateCardUI(context: Context) {
        val textColor = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        var index by remember { mutableStateOf(1) }
        Box {
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    ListItem.Header(modifier = Modifier.wrapContentWidth(), title = context.getString(R.string.basic_card))
                }
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        BasicCard {
                            Box(Modifier.padding(all = 8.dp)){
                                BasicCard() {
                                    Box(modifier = Modifier.padding(end = 8.dp)) {
                                        Row(
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                                        ) {
                                            Image(
                                                painterResource(id = R.drawable.avatar_carlos_slattery), contentDescription = ""
                                            )
                                            Column {
                                                Text(text = context.getString(R.string.card_text), color = textColor)
                                                Text(text = context.getString(R.string.card_subtext), color = textColor)
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        BasicCard(onClick = {}, content = { getContent(index = index, context = context) })
                        Button(
                            text = context.getString(R.string.card_randomize),
                            size = ButtonSize.Small,
                            style = ButtonStyle.OutlinedButton,
                            onClick = { index = (1..5).random() })
                    }
                }
                item {
                    ListItem.Header(title = context.getString(R.string.file_card))
                }
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            item {
                                FileCard(
                                    actionOverflowIcon = FluentIcon(
                                        Icons.Outlined.MoreVert,
                                        contentDescription = context.getString(R.string.card_options)
                                    ),
                                    onClick = {},
                                    text = "Carlos",
                                    subText = context.getString(R.string.persona_name_carlos_slattery),
                                    textIcon = FluentIcon(Icons.Outlined.Call),
                                    previewImageDrawable = R.drawable.avatar_carlos_slattery
                                )
                            }
                            item {
                                FileCard(
                                    actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                    onClick = {},
                                    text = context.getString(R.string.persona_name_allan_munger),
                                    subText = context.getString(R.string.persona_email_allan_munger),
                                    textIcon = FluentIcon(Icons.Outlined.Call),
                                    previewImageDrawable = R.drawable.avatar_allan_munger
                                )
                            }
                            item {
                                FileCard(
                                    actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                    onClick = {},
                                    text = context.getString(R.string.persona_name_elvia_atkins),
                                    subText = context.getString(R.string.persona_email_elvia_atkins),
                                    textIcon = FluentIcon(Icons.Outlined.Call),
                                    previewImageDrawable = R.drawable.avatar_elvia_atkins
                                )
                            }
                        }
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            item {
                                FileCard(
                                    actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                    onClick = {},
                                    text = context.getString(R.string.persona_name_amanda_brady),
                                    subText = context.getString(R.string.persona_email_amanda_brady),
                                    textIcon = FluentIcon(Icons.Outlined.Call),
                                    previewImageDrawable = R.drawable.avatar_amanda_brady
                                )
                            }
                            item {
                                FileCard(
                                    actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                    onClick = {},
                                    text = context.getString(R.string.persona_name_kat_larsson),
                                    subText = context.getString(R.string.persona_email_kat_larsson),
                                    textIcon = FluentIcon(Icons.Outlined.Call),
                                    previewImageDrawable = R.drawable.avatar_kat_larsson
                                )
                            }
                            item {
                                FileCard(
                                    actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                    onClick = {},
                                    text = context.getString(R.string.persona_name_cecil_folk),
                                    subText = context.getString(R.string.persona_email_cecil_folk),
                                    textIcon = FluentIcon(Icons.Outlined.Call),
                                    previewImageDrawable = R.drawable.avatar_cecil_folk
                                )
                            }
                        }
                    }
                }
                item {
                    ListItem.Header(title = context.getString(R.string.announcement_card))
                }
                item {
                    AnnouncementCard(
                        title = context.getString(R.string.card_title),
                        description = "779932818@7799328718",
                        buttonText = context.getString(R.string.card_button),
                        buttonOnClick = {},
                        previewImageDrawable = R.drawable.avatar_colin_ballinger
                    )
                }
                item {
                    Text(text = "")
                }
            }
        }

    }

    @Composable
    private fun getContent(index: Int, context: Context): Unit {
        return when (index) {
            1 -> CardContent1(context)
            2 -> CardContent2(context)
            3 -> CardContent3(context)
            4 -> CardContent4(context)
            5 -> CardContent5(context)
            else -> {}
        }
    }

    @Composable
    private fun CardContent1(context: Context) {
        val textColor = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(modifier = Modifier.padding(all = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_fluent_flag_24_regular),
                    contentDescription = "",
                    tint = textColor
                )
                Column {
                    Text(text = context.getString(R.string.card_text), color = textColor)
                    Text(text = context.getString(R.string.card_subtext), color = textColor)
                }
            }
        }
    }

    @Composable
    private fun CardContent2(context: Context) {
        val textColor = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(modifier = Modifier.padding(all = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column {
                    Text(text = context.getString(R.string.card_text), color = textColor)
                }
            }
        }
    }

    @Composable
    private fun CardContent3(context: Context) {
        val textColor = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(modifier = Modifier.padding(all = 8.dp)) {
            Column {
                Image(
                    painterResource(id = R.drawable.cover), contentDescription = ""
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_flag_24_regular),
                        contentDescription = "",
                        tint = textColor
                    )
                    Column {
                        Text(text = context.getString(R.string.card_text), color = textColor)
                        Text(text = context.getString(R.string.card_subtext), color = textColor)
                    }
                }
            }
        }
    }

    @Composable
    private fun CardContent4(context: Context) {
        Box {
            Image(
                painterResource(id = R.drawable.image_un), contentDescription = ""
            )
        }
    }

    @Composable
    private fun CardContent5(context: Context) {
        val textColor = aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(modifier = Modifier.padding(end = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.avatar_carlos_slattery), contentDescription = ""
                )
                Column {
                    Text(text = context.getString(R.string.card_text), color = textColor)
                    Text(text = context.getString(R.string.card_subtext), color = textColor)
                }
            }
        }
    }
}