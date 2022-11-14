package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
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
import com.microsoft.fluentui.theme.token.AliasTokens
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
    val textColor = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
        themeMode = FluentTheme.themeMode
    )
    val brandTextColor = FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
        themeMode = FluentTheme.themeMode
    )
    Column(
        Modifier
            .width(360.dp)
            .padding(start = 12.dp, top = 12.dp)){
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "ProgressBars",
                color = brandTextColor,
                fontSize = 20.sp
            )
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "XXXSmall - 2dp",
                    color = textColor)
                LinearProgressBar(0.75f, modifier = Modifier.width(240.dp))
            }
        }
        Column() {
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "XSmall - 12dp", modifier = Modifier.width(100.dp),
                    color = textColor)
                CircularProgressBar(0.8f)
            }
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "Small - 16dp", modifier = Modifier.width(100.dp),
                    color = textColor)
                CircularProgressBar(0.8f, size = CircularProgressBarIndicatorSize.XSmall)
            }
            Row(Modifier.height(42.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "Medium - 24dp", modifier = Modifier.width(100.dp),
                    color = textColor)
                CircularProgressBar(0.8f, size = CircularProgressBarIndicatorSize.Medium)
            }
            Row(Modifier.height(48.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "Large - 32dp", modifier = Modifier.width(100.dp),
                    color = textColor)
                CircularProgressBar(0.8f, size = CircularProgressBarIndicatorSize.Large)
            }
            Row(Modifier.height(64.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
                Text(text = "XLarge - 36dp", modifier = Modifier.width(100.dp),
                    color = textColor)
                CircularProgressBar(0.8f, CircularProgressBarIndicatorSize.XLarge)
            }
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "Determinate ProgressBar",
            color = brandTextColor,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = "Linear Progressbar",
            color = textColor
        )
        Row(Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
            LinearProgressBar(linearProgress, modifier = Modifier.width(240.dp))
            Text(text = ""+"%.0f".format(linearProgress*100)+"%")
        }
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            text = "Circular ProgressBar",
            color = textColor
        )
        Row(Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
            CircularProgressBar(circularProgress, size = CircularProgressBarIndicatorSize.XLarge)
            Text(text = ""+"%.0f".format(circularProgress*100)+"%")
        }
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "InDeterminate ProgressBar",
            color = brandTextColor,
            fontSize = 18.sp
        )
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
            text = "Linear Progressbar",
            color = textColor
        )
        Row(Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
            LinearProgressBar(modifier = Modifier.width(360.dp), totalAnimationDuration = 5000)
        }
        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
            text = "Circular ProgressBar",
            color = textColor
        )
        Row(Modifier.height(24.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(32.dp)){
            CircularProgressBar(size = CircularProgressBarIndicatorSize.XLarge)
        }
    }
    LaunchedEffect(key1 = linearProgress){
        if(linearProgress>=1.0){
            linearProgress = 1f
            delay(1000)
            linearProgress = 0f
        }else{
            delay(500)
            linearProgress+= Random.nextFloat()/5
        }
    }
    LaunchedEffect(key1 = circularProgress){
        if(circularProgress>=1.0){
            circularProgress = 1f
            delay(1000)
            circularProgress = 0f
        }else{
            delay(500)
            circularProgress+= Random.nextFloat()/5
        }
    }
}