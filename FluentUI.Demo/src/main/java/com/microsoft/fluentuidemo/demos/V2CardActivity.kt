package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.AnnouncementCard
import com.microsoft.fluentui.tokenized.controls.BasicCard
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.FileCard
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

class V2CardActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-14"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-14"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateCardUI(this)
        }
    }


    @Composable
    private fun CreateCardUI(context: Context) {
        var index by remember { mutableStateOf(1) }
        Box(Modifier.verticalScroll(rememberScrollState())) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                ListItem.Header(
                    modifier = Modifier.wrapContentWidth(),
                    title = context.getString(R.string.basic_card)
                )
                Column(
                    modifier = Modifier.padding(start = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Button(
                        text = context.getString(R.string.card_randomize),
                        size = ButtonSize.Small,
                        style = ButtonStyle.OutlinedButton,
                        onClick = { index = (1..5).random() })
                    BasicCard(
                        content = { GetContent(index = index, context = context) })
                }
                BasicCard {
                    BasicCardUI(context)
                }
                ListItem.Header(title = context.getString(R.string.file_card))
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        item {
                            FileCard(
                                actionOverflowIcon = FluentIcon(
                                    Icons.Outlined.MoreVert,
                                    onClick = {
                                        Toast.makeText(
                                            context,
                                            "Clicked",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    },
                                    contentDescription = context.getString(R.string.card_options)
                                ),
                                onClick = {},
                                text = context.getString(R.string.persona_name_carlos_slattery),
                                subText = context.getString(R.string.persona_subtitle_designer),
                                textIcon = Icons.Outlined.Call,
                                previewImageDrawable = R.drawable.avatar_carlos_slattery
                            )
                        }
                        item {
                            FileCard(
                                actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                onClick = {},
                                text = context.getString(R.string.persona_name_allan_munger),
                                subText = context.getString(R.string.persona_subtitle_manager),
                                textIcon = Icons.Outlined.Call,
                                previewImageDrawable = R.drawable.avatar_allan_munger
                            )
                        }
                        item {
                            FileCard(
                                actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                onClick = {},
                                text = context.getString(R.string.persona_name_elvia_atkins),
                                subText = context.getString(R.string.persona_subtitle_engineer),
                                textIcon = Icons.Outlined.Call,
                                previewImageDrawable = R.drawable.avatar_elvia_atkins
                            )
                        }
                        item {
                            FileCard(
                                actionOverflowIcon = FluentIcon(Icons.Outlined.MoreVert),
                                text = context.getString(R.string.persona_name_kat_larsson),
                                subText = context.getString(R.string.persona_subtitle_engineer),
                                textIcon = Icons.Outlined.Call,
                                previewImageDrawable = R.drawable.avatar_kat_larsson
                            )
                        }
                    }
                }
                ListItem.Header(title = context.getString(R.string.announcement_card))
                AnnouncementCard(
                    title = context.getString(R.string.card_title),
                    description = context.getString(R.string.card_description),
                    buttonText = context.getString(R.string.card_button),
                    buttonOnClick = {},
                    previewImageDrawable = R.drawable.card_cover
                )
                Spacer(Modifier.height(32.dp))
            }
        }

    }

    @Composable
    private fun GetContent(index: Int, context: Context) {
        return when (index) {
            1 -> CardContent1(context)
            2 -> CardContent2(context)
            3 -> CardContent3(context)
            4 -> CardContent4()
            5 -> CardContent5(context)
            else -> {}
        }
    }

    @Composable
    private fun CardContent1(context: Context) {
        val textColor =
            aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(
            modifier = Modifier
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    enabled = true,
                    onClick = { },
                    role = Role.Button
                )
                .padding(all = 8.dp)
        ) {
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
                    BasicText(
                        text = context.getString(R.string.card_text),
                        style = TextStyle(color = textColor)
                    )
                    BasicText(
                        text = context.getString(R.string.card_subtext),
                        style = TextStyle(color = textColor)
                    )
                }
            }
        }
    }

    @Composable
    private fun CardContent2(context: Context) {
        val textColor =
            aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(modifier = Modifier.padding(all = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column {
                    BasicText(
                        text = context.getString(R.string.card_text),
                        style = TextStyle(color = textColor)
                    )
                }
            }
        }
    }

    @Composable
    private fun CardContent3(context: Context) {
        val textColor =
            aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
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
                        BasicText(
                            text = context.getString(R.string.card_text),
                            style = TextStyle(color = textColor)
                        )
                        BasicText(
                            text = context.getString(R.string.card_subtext),
                            style = TextStyle(color = textColor)
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun CardContent4() {
        Box {
            Image(
                painterResource(id = R.drawable.image_un), contentDescription = ""
            )
        }
    }

    @Composable
    private fun CardContent5(context: Context) {
        val textColor =
            aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Box(modifier = Modifier.padding(end = 8.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painterResource(id = R.drawable.avatar_carlos_slattery), contentDescription = ""
                )
                Column {
                    BasicText(
                        text = context.getString(R.string.card_text),
                        style = TextStyle(color = textColor)
                    )
                    BasicText(
                        text = context.getString(R.string.card_subtext),
                        style = TextStyle(color = textColor)
                    )
                }
            }
        }
    }

    @Composable
    private fun BasicCardUI(context: Context) {
        val iconTint =
            aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()
        Column {
            ListItem.Header(
                border = BorderType.Bottom,
                title = context.getString(R.string.basic_card),
                trailingAccessoryContent = { Icon(Icons.Outlined.Refresh, "", tint = iconTint) })
            ListItem.Item(
                border = BorderType.Bottom,
                text = context.getString(R.string.card_text),
                subText = context.getString(R.string.card_subtext),
                leadingAccessoryContent = { Icon(Icons.Outlined.Home, "", tint = iconTint) },
                trailingAccessoryContent = { Icon(Icons.Outlined.PlayArrow, "", tint = iconTint) })
            ListItem.Item(
                border = BorderType.Bottom,
                text = context.getString(R.string.card_text),
                subText = context.getString(R.string.card_subtext),
                leadingAccessoryContent = { Icon(Icons.Outlined.AccountBox, "", tint = iconTint) },
                trailingAccessoryContent = { Icon(Icons.Outlined.PlayArrow, "", tint = iconTint) })
            ListItem.Item(
                border = BorderType.Bottom,
                text = context.getString(R.string.card_text),
                subText = context.getString(R.string.card_subtext),
                leadingAccessoryContent = { Icon(Icons.Outlined.Call, "", tint = iconTint) },
                trailingAccessoryContent = { Icon(Icons.Outlined.PlayArrow, "", tint = iconTint) })
            ListItem.Item(
                text = context.getString(R.string.card_text),
                subText = context.getString(R.string.card_subtext),
                leadingAccessoryContent = { Icon(Icons.Outlined.Add, "", tint = iconTint) },
                trailingAccessoryContent = { Icon(Icons.Outlined.PlayArrow, "", tint = iconTint) })
        }
    }
}