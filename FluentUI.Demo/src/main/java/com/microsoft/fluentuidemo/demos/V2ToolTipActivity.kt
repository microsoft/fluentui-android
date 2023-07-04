package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.runtime.rememberCoroutineScope
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.notification.ToolTipBox
import com.microsoft.fluentui.tokenized.notification.rememberTooltipState
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import kotlinx.coroutines.launch

class V2ToolTipActivity : DemoActivity() {
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
            val tooltipState = rememberTooltipState()
            val scope = rememberCoroutineScope()
            ToolTipBox(
                tooltipContent = {
                    Label(
                        text = "ToolTip!!",
                        textStyle = FluentAliasTokens.TypographyTokens.Caption1,
                        colorStyle = ColorStyle.White
                    )
                },
                tooltipState = tooltipState,
                onDismissRequest = { /*TODO*/ }) {
                Button(onClick = { scope.launch { tooltipState.show() } }, text = "Click Me")
            }
        }
    }
}