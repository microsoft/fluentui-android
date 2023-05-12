package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.ColorStyle
import com.microsoft.fluentui.tokenized.controls.Label
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.tokenized.progress.LinearProgressIndicator
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import kotlinx.coroutines.delay
import kotlin.random.Random

class V2ProgressActivity : DemoActivity() {
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
                CreateProgressActivityUI()
            }
        }
    }
}

@Composable
private fun CreateProgressActivityUI() {
    var linearProgress by remember { mutableStateOf(0f) }
    var circularProgress by remember { mutableStateOf(0f) }
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
            .padding(start = 12.dp, top = 12.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LinearProgressIndicatorExample(brandTextColor = brandTextColor, textColor = textColor)
        CircularProgressIndicatorExamples(textColor = textColor)
        DeterminateProgressIndicatorExamples(
            linearProgress,
            circularProgress
        )
        IndeterminateProgressIndicatorExamples()
    }
    LaunchedEffect(key1 = linearProgress) {
        if (linearProgress >= 1.0) {
            linearProgress = 1f
            delay(1000)
            linearProgress = 0f
        } else {
            delay(500)
            linearProgress += Random.nextFloat() / 5
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
private fun LinearProgressIndicatorExample(brandTextColor: Color, textColor: Color) {
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
private fun CircularProgressIndicatorExamples(textColor: Color) {
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
private fun DeterminateProgressIndicatorExamples(
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
private fun IndeterminateProgressIndicatorExamples() {
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