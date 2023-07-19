package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.divider.Divider
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.notification.ToolTipBox
import com.microsoft.fluentui.tokenized.notification.rememberTooltipState
import com.microsoft.fluentui.util.isAccessibilityEnabled
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.invokeToast
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
            CreateToolTipActivityUI(this)
        }
    }
}

@Composable
fun CreateToolTipActivityUI(context: Context) {
    val xOffsetState = rememberSaveable { mutableStateOf("0") }
    val yOffsetState = rememberSaveable { mutableStateOf("0") }
    val toolTipTitle =
        rememberSaveable { mutableStateOf(context.getString(R.string.tooltip_title)) }
    val toolTipText =
        rememberSaveable { mutableStateOf(context.getString(R.string.tooltip_text)) }
    Column {
        val xOffset =
            if (xOffsetState.value.toFloatOrNull() == null) 0.dp else xOffsetState.value.toFloat().dp
        val yOffset =
            if (yOffsetState.value.toFloatOrNull() == null) 0.dp else yOffsetState.value.toFloat().dp

        val action = stringResource(id = R.string.tooltip_dismiss_message)
        val scope = rememberCoroutineScope()
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            //Top
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //Top Left
                val topLeftTooltipState = rememberTooltipState()
                val topStartMessage = stringResource(id = R.string.tooltip_top_start)
                ToolTipBox(
                    title = toolTipTitle.value,
                    text = toolTipText.value,
                    tooltipState = topLeftTooltipState,
                    offset = DpOffset(x = xOffset, y = yOffset),
                    focusable = context.isAccessibilityEnabled,
                    onDismissRequest = { invokeToast(topStartMessage, context, action) }) {
                    Button(
                        onClick = { scope.launch { topLeftTooltipState.show() } },
                        text = stringResource(id = R.string.tooltip_button)
                    )
                }

                //Top Right
                val topRightTooltipState = rememberTooltipState()
                val topEndMessage = stringResource(id = R.string.tooltip_top_end)
                ToolTipBox(
                    title = toolTipTitle.value,
                    text = toolTipText.value,
                    tooltipState = topRightTooltipState,
                    offset = DpOffset(x = xOffset, y = yOffset),
                    focusable = context.isAccessibilityEnabled,
                    onDismissRequest = { invokeToast(topEndMessage, context, action) }) {
                    Button(
                        onClick = { scope.launch { topRightTooltipState.show() } },
                        text = stringResource(id = R.string.tooltip_button)
                    )
                }
            }
            //Config

            Column {
                ListItem.Header(title = context.getString(R.string.menu_xOffset),
                    trailingAccessoryContent = {
                        BasicTextField(value = xOffsetState.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = { xOffsetState.value = it.trim() })
                    }
                )
                ListItem.Header(title = context.getString(R.string.menu_yOffset),
                    trailingAccessoryContent = {
                        BasicTextField(value = yOffsetState.value,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            onValueChange = { yOffsetState.value = it.trim() })
                    })
                ListItem.Header(title = context.getString(R.string.tooltip_title),
                    trailingAccessoryContent = {
                        BasicTextField(
                            value = toolTipTitle.value,
                            onValueChange = { toolTipTitle.value = it })
                    })
                ListItem.Header(title = context.getString(R.string.tooltip_text),
                    trailingAccessoryContent = {
                        BasicTextField(
                            value = toolTipText.value,
                            onValueChange = { toolTipText.value = it })
                    })
                Divider()
            }
            //Center with Customized Content
            val centerCustomizedTooltipState = rememberTooltipState()
            val customCenterMessage = stringResource(id = R.string.tooltip_custom_center)
            ToolTipBox(
                tooltipContent = {
                    getAndroidViewAsContent(ContentType.WRAPPED_SIZE_CONTENT)() {}
                },
                tooltipState = centerCustomizedTooltipState,
                offset = DpOffset(x = xOffset, y = yOffset),
                focusable = context.isAccessibilityEnabled,
                onDismissRequest = {
                    invokeToast(
                        customCenterMessage,
                        context,
                        action
                    )
                }) {
                Button(
                    onClick = { scope.launch { centerCustomizedTooltipState.show() } },
                    text = stringResource(id = R.string.tooltip_custom_content)
                )
            }

            val centerTooltipState = rememberTooltipState()
            val centerMessage = stringResource(id = R.string.tooltip_center)
            ToolTipBox(
                title = toolTipTitle.value,
                text = toolTipText.value,
                tooltipState = centerTooltipState,
                offset = DpOffset(x = xOffset, y = yOffset),
                focusable = context.isAccessibilityEnabled,
                onDismissRequest = { invokeToast(centerMessage, context, action) }) {
                Button(
                    onClick = { scope.launch { centerTooltipState.show() } },
                    text = stringResource(id = R.string.tooltip_button)
                )
            }

            //Bottom
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                //Bottom Left
                val bottomLeftTooltipState = rememberTooltipState()
                val bottomStartMessage = stringResource(id = R.string.tooltip_bottom_start)
                ToolTipBox(
                    title = toolTipTitle.value,
                    text = toolTipText.value,
                    tooltipState = bottomLeftTooltipState,
                    offset = DpOffset(x = xOffset, y = yOffset),
                    focusable = context.isAccessibilityEnabled,
                    onDismissRequest = { invokeToast(bottomStartMessage, context, action) }) {
                    Button(
                        onClick = { scope.launch { bottomLeftTooltipState.show() } },
                        text = stringResource(id = R.string.tooltip_button)
                    )
                }

                //Bottom Right
                val bottomRightTooltipState = rememberTooltipState()
                val bottomEndMessage = stringResource(id = R.string.tooltip_bottom_end)
                ToolTipBox(
                    title = toolTipTitle.value,
                    text = toolTipText.value,
                    tooltipState = bottomRightTooltipState,
                    offset = DpOffset(x = xOffset, y = yOffset),
                    focusable = context.isAccessibilityEnabled,
                    onDismissRequest = { invokeToast(bottomEndMessage, context, action) }) {
                    Button(
                        onClick = { scope.launch { bottomRightTooltipState.show() } },
                        text = stringResource(id = R.string.tooltip_button)
                    )
                }
            }
        }
    }
}
