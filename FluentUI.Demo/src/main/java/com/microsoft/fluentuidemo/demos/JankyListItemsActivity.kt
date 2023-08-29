package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.theme.token.controlTokens.BorderInset
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ButtonSize
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.BasicChip
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.V2DemoActivity

class JankyListItemsActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateJankyListActivityUI(this)
        }
    }
}
@Composable
private fun CreateJankyListActivityUI(context: Context) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(Modifier.verticalScroll(rememberScrollState())) {
            for(i in 1..300){
                ListItem.Item(
                    text = "$i $sampleText button",
                    subText = sampleText,
                    onClick = {},
                    textMaxLines = 4,
                    subTextMaxLines = 4,
                    trailingAccessoryContent = {
                         //  Text("hi")
                        //BasicChip(label = "hi")
                        Button(
                            style = ButtonStyle.OutlinedButton,
                            size = ButtonSize.Medium,
                            onClick = {},
                            text = "trailling button"
                        )
                    },
                    border = BorderType.Bottom,
                    borderInset = BorderInset.XXLarge
                )
            }
        }
    }
}