package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressBarIndicatorSize
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.progress.CircularProgressBar
import com.microsoft.fluentui.tokenized.progress.LinearProgressBar
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class V2ProgressActivity: DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                createActivityUI()
            }
        }
    }
}

@Composable
fun createActivityUI(){
    var linearProgress by remember { mutableStateOf(0f) }
    var circularProgress by remember { mutableStateOf(0f) }
    Column(
        Modifier
            .width(360.dp)
            .padding(start = 12.dp, top = 12.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "ProgressBars",
                color = Color(0xFF2886DE),
                fontSize = 18.sp
            )
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "XXXSmall - 2dp")
                LinearProgressBar(0.75f, modifier = Modifier.width(240.dp))
            }
        }
        Column() {
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "XSmall - 12dp", modifier = Modifier.width(100.dp))
                CircularProgressBar(0.8f)
            }
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "Small - 16dp", modifier = Modifier.width(100.dp))
                CircularProgressBar(0.8f, size = CircularProgressBarIndicatorSize.Small)
            }
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "Medium - 24dp", modifier = Modifier.width(100.dp))
                CircularProgressBar(0.8f, size = CircularProgressBarIndicatorSize.Medium)
            }
            Row(Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "Large - 32dp", modifier = Modifier.width(100.dp))
                CircularProgressBar(0.8f, size = CircularProgressBarIndicatorSize.Large)
            }
            Row(Modifier.height(64.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "XLarge - 36dp", modifier = Modifier.width(100.dp))
                CircularProgressBar(0.8f, CircularProgressBarIndicatorSize.XLarge)
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Determinate ProgressBar",
            color = Color(0xFF2886DE),
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = "Linear Progressbar",
            color = Color(0xFF2886DE)
        )
        Row(Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
            LinearProgressBar(linearProgress, modifier = Modifier.width(240.dp))
        }
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            text = "Circular ProgressBar",
            color = Color(0xFF2886DE)
        )
        Row(Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
            CircularProgressBar(circularProgress, size = CircularProgressBarIndicatorSize.Large)
        }
    }
    LaunchedEffect(key1 = linearProgress){
        if(linearProgress>=1.0){
            delay(3000)
            linearProgress = 0f
        }else{
            delay(1000)
            linearProgress+= Random.nextFloat()/2
        }
    }
    LaunchedEffect(key1 = circularProgress){
        if(circularProgress>=1.0){
            delay(3000)
            circularProgress = 0f
        }else{
            delay(1000)
            circularProgress+= Random.nextFloat()/2
        }
    }
}