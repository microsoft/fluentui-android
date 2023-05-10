package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.tokenized.controls.Label
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
        Column(
            modifier = Modifier
                .padding(all = 16.dp)
                .horizontalScroll(
                    rememberScrollState()
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Label.Display(text = "Display is Regular 60sp")
            Label.LargeTitle(text = "LargeTitle is Regular 34sp")
            Label.Title1(text = "Title1 is Bold 24sp")
            Label.Title2(text = "Title2 is Medium 20sp")
            Label.Title3(text = "Title3 is Medium is 18sp")
            Label.Body1Strong(text = "Body1Strong is SemiBold 16sp")
            Label.Body1(text = "Body1 is Regular 16sp")
            Label.Body2Strong(text = "Body2Strong is Medium 14sp")
            Label.Body2(text = "Body2 is Regular 14sp")
            Label.Caption1Strong(text = "Caption1Strong is Medium 13sp")
            Label.Caption1(text = "Caption1 is Regular 13sp")
            Label.Caption2(text = "Caption2 is Regular 12sp")

        }
    }
}