package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.TextType
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.navigation.TabBar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding

class V2LabelActivity : DemoActivity() {
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
                CreateLabelUI()
            }
        }
    }

    @Composable
    private fun CreateLabelUI() {
        var colorStyle by rememberSaveable { mutableStateOf(ColorStyle.Primary) }

        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .horizontalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(style = ButtonStyle.OutlinedButton, onClick = { colorStyle = ColorStyle.Primary }, text = "Primary")
                Button(style = ButtonStyle.OutlinedButton, onClick = { colorStyle = ColorStyle.Secondary }, text = "Secondary")
                Button(style = ButtonStyle.OutlinedButton, onClick = { colorStyle = ColorStyle.White }, text = "White")
                Button(style = ButtonStyle.OutlinedButton, onClick = { colorStyle = ColorStyle.Brand }, text = "Brand")
                Button(style = ButtonStyle.OutlinedButton, onClick = { colorStyle = ColorStyle.Error }, text = "Error")
            }
            Label(text = "Display is Regular 60sp", textStyle = TextType.Display, colorStyle = colorStyle)
            Label(text = "LargeTitle is Regular 34sp", textStyle = TextType.LargeTitle, colorStyle = colorStyle)
            Label(text = "Title1 is Bold 24sp", textStyle = TextType.Title1, colorStyle = colorStyle)
            Label(text = "Title2 is Medium 20sp", textStyle = TextType.Title2, colorStyle = colorStyle)
            Label(text = "Title3 is Medium is 18sp", textStyle = TextType.Title3, colorStyle = colorStyle)
            Label(text = "Body1Strong is SemiBold 16sp", textStyle = TextType.Body1Strong, colorStyle = colorStyle)
            Label(text = "Body1 is Regular 16sp", textStyle = TextType.Body1, colorStyle = colorStyle)
            Label(text = "Body2Strong is Medium 14sp", textStyle = TextType.Body2Strong, colorStyle = colorStyle)
            Label(text = "Body2 is Regular 14sp", textStyle = TextType.Body2, colorStyle = colorStyle)
            Label(text = "Caption1Strong is Medium 13sp", textStyle = TextType.Caption1Strong, colorStyle = colorStyle)
            Label(text = "Caption1 is Regular 13sp", textStyle = TextType.Caption1, colorStyle = colorStyle)
            Label(text = "Caption2 is Regular 12sp", textStyle = TextType.Caption2, colorStyle = colorStyle)
        }
    }
}