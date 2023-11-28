package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.theme.token.controlTokens.ProgressTextInfo
import com.microsoft.fluentui.theme.token.controlTokens.ProgressTextTokens
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.tokenized.progress.LinearProgressIndicator
import com.microsoft.fluentui.tokenized.progress.ProgressText
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.delay
import kotlin.random.Random

class V2ProgressActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-26"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-25"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreateProgressActivityUI(this)
        }
    }
}

@Composable
private fun CreateProgressActivityUI(context: Context) {
    var linearProgress by remember { mutableStateOf(0f) }
    var circularProgress by remember { mutableStateOf(0f) }
    var progressString by remember {
        mutableStateOf("Starting...")
    }
    var texts = ArrayList<String>()
    texts.add("Starting...")
    texts.add("Generating...")
    texts.add("Publishing...")
    texts.add("Completed...")
    val textColor =
        FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    val brandTextColor =
        FluentTheme.aliasTokens.brandForegroundColor[FluentAliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
            themeMode = FluentTheme.themeMode
        )
    Column(
        Modifier
            .padding(all = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LinearProgressIndicatorDemo(brandTextColor = brandTextColor, textColor = textColor)
        CircularProgressIndicatorDemo(textColor = textColor)
        DeterminateProgressIndicatorDemo(
            linearProgress,
            circularProgress
        )
        IndeterminateProgressIndicatorDemo()
        ProgressTextDemo(linearProgress, context, progressString)
    }
    LaunchedEffect(key1 = linearProgress) {
        if (linearProgress >= 1.0) {
            linearProgress = 1f
            delay(1000)
            linearProgress = 0f
        } else {
            delay(500)
            linearProgress += Random.nextFloat() / 5
            if (linearProgress < 0.2f) {
                progressString = texts[0]
            } else if (linearProgress > 0.20f && linearProgress < 0.5f) {
                progressString = texts[1]
            } else if (linearProgress > 0.5f && linearProgress < 0.9f) {
                progressString = texts[2]
            } else if (linearProgress > 0.9f) {
                progressString = texts[3]
            }
        }
    }
    LaunchedEffect(key1 = circularProgress) {
        if (circularProgress >= 1.0) {
            circularProgress = 1f
            delay(1000)
            circularProgress = 0f
        } else {
            delay(500)
            circularProgress += Random.nextFloat() / 5
        }
    }
}

@Composable
private fun LinearProgressIndicatorDemo(brandTextColor: Color, textColor: Color) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Label(
            text = "ProgressIndicators",
            textStyle = FluentAliasTokens.TypographyTokens.Title2,
            colorStyle = ColorStyle.Brand
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Label(
                text = "XXXSmall - 2dp",
                textStyle = FluentAliasTokens.TypographyTokens.Caption1
            )
            LinearProgressIndicator(modifier = Modifier.width(240.dp))
        }
    }
}

@Composable
private fun CircularProgressIndicatorDemo(textColor: Color) {
    Column {
        Row(
            Modifier.height(42.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(36.dp)
        ) {
            Label(
                modifier = Modifier.width(100.dp),
                text = "XSmall - 12dp",
                textStyle = FluentAliasTokens.TypographyTokens.Caption1
            )
            CircularProgressIndicator(style = FluentStyle.Brand)
            CircularProgressIndicator()
        }
        Row(
            Modifier.height(42.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Label(
                modifier = Modifier.width(100.dp),
                text = "Small - 16dp",
                textStyle = FluentAliasTokens.TypographyTokens.Caption1
            )
            CircularProgressIndicator(
                size = CircularProgressIndicatorSize.XSmall,
                style = FluentStyle.Brand
            )
            CircularProgressIndicator(
                CircularProgressIndicatorSize.XSmall
            )
        }
        Row(
            Modifier.height(42.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Label(
                modifier = Modifier.width(100.dp),
                text = "Medium - 24dp",
                textStyle = FluentAliasTokens.TypographyTokens.Caption1
            )
            CircularProgressIndicator(
                size = CircularProgressIndicatorSize.Medium,
                style = FluentStyle.Brand
            )
            CircularProgressIndicator(
                CircularProgressIndicatorSize.Medium
            )
        }
        Row(
            Modifier.height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Label(
                modifier = Modifier.width(100.dp),
                text = "Large - 32dp",
                textStyle = FluentAliasTokens.TypographyTokens.Caption1
            )
            CircularProgressIndicator(
                size = CircularProgressIndicatorSize.Large,
                style = FluentStyle.Brand
            )
            CircularProgressIndicator(
                CircularProgressIndicatorSize.Large
            )
        }
        Row(
            Modifier.height(64.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Label(
                modifier = Modifier.width(100.dp),
                text = "XLarge - 36dp",
                textStyle = FluentAliasTokens.TypographyTokens.Caption1
            )
            CircularProgressIndicator(
                CircularProgressIndicatorSize.XLarge,
                style = FluentStyle.Brand
            )
            CircularProgressIndicator(
                CircularProgressIndicatorSize.XLarge
            )
        }
    }
}

@Composable
private fun DeterminateProgressIndicatorDemo(
    linearProgress: Float,
    circularProgress: Float
) {
    Label(
        modifier = Modifier.padding(top = 16.dp),
        text = "Determinate ProgressIndicator",
        textStyle = FluentAliasTokens.TypographyTokens.Title2,
        colorStyle = ColorStyle.Brand
    )
    Label(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        text = "Linear ProgressIndicator",
        textStyle = FluentAliasTokens.TypographyTokens.Caption1
    )
    Row(
        Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        LinearProgressIndicator(linearProgress, modifier = Modifier.width(240.dp))
        Label(
            text = "" + "%.0f".format(linearProgress * 100) + "%",
            textStyle = FluentAliasTokens.TypographyTokens.Caption1
        )
    }
    Label(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        text = "Circular ProgressIndicator",
        textStyle = FluentAliasTokens.TypographyTokens.Caption1
    )
    Row(
        Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        CircularProgressIndicator(
            circularProgress,
            size = CircularProgressIndicatorSize.XLarge,
            style = FluentStyle.Brand
        )
        Label(
            text = "" + "%.0f".format(circularProgress * 100) + "%",
            textStyle = FluentAliasTokens.TypographyTokens.Caption1
        )
    }
}

@Composable
private fun IndeterminateProgressIndicatorDemo() {
    Label(
        modifier = Modifier.padding(top = 16.dp),
        text = "InDeterminate ProgressIndicator",
        textStyle = FluentAliasTokens.TypographyTokens.Title2,
        colorStyle = ColorStyle.Brand
    )
    Label(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        text = "Linear ProgressIndicator",
        textStyle = FluentAliasTokens.TypographyTokens.Caption1
    )
    Row(
        Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        LinearProgressIndicator(modifier = Modifier.width(240.dp))
    }
    Label(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        text = "Circular ProgressIndicator",
        textStyle = FluentAliasTokens.TypographyTokens.Caption1
    )
    Row(
        Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        CircularProgressIndicator(
            size = CircularProgressIndicatorSize.XLarge,
            style = FluentStyle.Brand
        )
    }
}

@Composable
private fun ProgressTextDemo(linearProgress: Float, context: Context, progressString: String) {
    Label(
        modifier = Modifier.padding(top = 16.dp),
        text = "Progress Text",
        textStyle = FluentAliasTokens.TypographyTokens.Title2,
        colorStyle = ColorStyle.Brand
    )
    Spacer(modifier = Modifier.height(12.dp))
    ProgressText(
        text = progressString,
        progress = linearProgress,
        modifier = Modifier.width(300.dp),
    )
    Spacer(modifier = Modifier.height(12.dp))
    ProgressText(
        text = "Ok... I'll summarize what you missed this morning",
        progress = linearProgress,
        leadingIconAccessory = FluentIcon(
            Icons.Outlined.Clear,
            onClick = { Toast.makeText(context, "Canceled", Toast.LENGTH_SHORT).show() }),
        modifier = Modifier.width(325.dp),
        progressTextTokens = GradientProgressTextToken()
    )
}

class GradientProgressTextToken : ProgressTextTokens() {
    @Composable
    override fun progressbarBrush(progressTextInfo: ProgressTextInfo): Brush {
        return gradient
    }
}

private var gradient = Brush.horizontalGradient(
    0.0f to Color(0xFF464FEB),
    0.7f to Color(0xFF47CFFA),
    0.92f to Color(0xFFB47CF8)
)