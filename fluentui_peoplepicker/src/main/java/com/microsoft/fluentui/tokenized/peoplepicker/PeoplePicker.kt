package com.microsoft.fluentui.tokenized.peoplepicker

import android.view.KeyEvent
import android.widget.TextView
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.unit.dp

@Composable
fun PeoplePicker(){
    TextField(
        value = "hint",
        onValueChange = {  },
        label = { Text("Type your text here") },
        modifier = Modifier
            .padding(start = 8.dp)
    )
}