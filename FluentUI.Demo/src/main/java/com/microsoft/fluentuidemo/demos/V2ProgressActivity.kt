package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.CircularProgressIndicatorSize
import com.microsoft.fluentui.theme.token.controlTokens.ShimmerShape
import com.microsoft.fluentui.tokenized.progress.CircularProgressIndicator
import com.microsoft.fluentui.tokenized.progress.LinearProgressIndicator
import com.microsoft.fluentui.tokenized.shimmer.Shimmer
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import kotlinx.coroutines.delay
import kotlin.random.Random

class V2ProgressActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(LayoutInflater.from(container.context), container,true)
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
        FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
            themeMode = FluentTheme.themeMode
        )
    val brandTextColor =
        FluentTheme.aliasTokens.brandForegroundColor[AliasTokens.BrandForegroundColorTokens.BrandForeground1].value(
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
            brandTextColor = brandTextColor,
            textColor = textColor,
            linearProgress,
            circularProgress
        )
        IndeterminateProgressIndicatorExamples(
            brandTextColor = brandTextColor,
            textColor = textColor
        )
        shimmerExamples(brandTextColor = brandTextColor, textColor = textColor)
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
        Text(
            modifier = Modifier.padding(top = 16.dp),
            text = "ProgressIndicators",
            color = brandTextColor,
            fontSize = 20.sp
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "XXXSmall - 2dp",
                color = textColor
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
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "XSmall - 12dp", modifier = Modifier.width(100.dp),
                color = textColor
            )
            CircularProgressIndicator(style = FluentStyle.Brand)
            CircularProgressIndicator()
        }
        Row(
            Modifier.height(42.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Small - 16dp", modifier = Modifier.width(100.dp),
                color = textColor
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
            Text(
                text = "Medium - 24dp", modifier = Modifier.width(100.dp),
                color = textColor
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
            Text(
                text = "Large - 32dp", modifier = Modifier.width(100.dp),
                color = textColor
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
            Text(
                text = "XLarge - 36dp", modifier = Modifier.width(100.dp),
                color = textColor
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
    brandTextColor: Color,
    textColor: Color,
    linearProgress: Float,
    circularProgress: Float
) {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Determinate ProgressIndicator",
        color = brandTextColor,
        fontSize = 18.sp
    )
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        text = "Linear ProgressIndicator",
        color = textColor
    )
    Row(
        Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        LinearProgressIndicator(linearProgress, modifier = Modifier.width(240.dp))
        Text(text = "" + "%.0f".format(linearProgress * 100) + "%", color = textColor)
    }
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        text = "Circular ProgressIndicator",
        color = textColor
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
        Text(text = "" + "%.0f".format(circularProgress * 100) + "%", color = textColor)
    }
}

@Composable
private fun IndeterminateProgressIndicatorExamples(brandTextColor: Color, textColor: Color) {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "InDeterminate ProgressIndicator",
        color = brandTextColor,
        fontSize = 18.sp
    )
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        text = "Linear ProgressIndicator",
        color = textColor
    )
    Row(
        Modifier.height(24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(32.dp)
    ) {
        LinearProgressIndicator(modifier = Modifier.width(240.dp))
    }
    Text(
        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
        text = "Circular ProgressIndicator",
        color = textColor
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
private fun shimmerExamples(brandTextColor: Color, textColor: Color) {
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Shimmer",
        color = brandTextColor,
        fontSize = 18.sp
    )
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Box shimmer",
        color = textColor
    )
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Shimmer(modifier = Modifier.size(120.dp, 80.dp))
        Column(
            Modifier
                .height(80.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Shimmer(modifier = Modifier.size(140.dp, 12.dp))
            Shimmer(modifier = Modifier.size(180.dp, 12.dp))
            Shimmer(modifier = Modifier.size(200.dp, 12.dp))
        }
    }
    Text(
        modifier = Modifier.padding(top = 16.dp),
        text = "Circle shimmer",
        color = textColor
    )
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Shimmer(modifier = Modifier.size(60.dp, 60.dp), shape = ShimmerShape.Circle)
        Column(
            Modifier
                .height(80.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Shimmer(modifier = Modifier.size(140.dp, 12.dp))
            Shimmer(modifier = Modifier.size(180.dp, 12.dp))
        }
    }
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Shimmer(modifier = Modifier.size(60.dp, 60.dp), shape = ShimmerShape.Circle)
        Column(
            Modifier
                .height(80.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Shimmer(modifier = Modifier.size(140.dp, 12.dp))
            Shimmer(modifier = Modifier.size(180.dp, 12.dp))
        }
    }
    Row(
        modifier = Modifier
            .padding(top = 8.dp, bottom = 16.dp)
            .height(60.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Shimmer(modifier = Modifier.size(60.dp), shape = ShimmerShape.Circle)
        Column(
            Modifier
                .height(80.dp)
                .padding(top = 10.dp, bottom = 10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Shimmer(modifier = Modifier.size(140.dp, 12.dp))
            Shimmer(modifier = Modifier.size(180.dp, 12.dp))
        }
    }
}