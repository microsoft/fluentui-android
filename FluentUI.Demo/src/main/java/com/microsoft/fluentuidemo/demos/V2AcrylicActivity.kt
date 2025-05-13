package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import com.microsoft.fluentui.tokenized.acrylicpane.AcrylicPane
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Office
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens.NeutralForegroundColorTokens.Foreground2
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.drawer.DrawerValue
import com.microsoft.fluentui.tokenized.drawer.rememberBottomDrawerState
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.CustomizedSearchBarTokens
import com.microsoft.fluentuidemo.util.DemoAppStrings
import com.microsoft.fluentuidemo.util.PrimarySurfaceContent
import com.microsoft.fluentuidemo.util.getAndroidViewAsContent
import com.microsoft.fluentuidemo.util.getDemoAppString
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.content.res.Configuration
import android.os.Build
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource

import androidx.compose.ui.text.font.FontWeight


class V2AcrylicPaneActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-18" //TODO: Update this URL
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-18"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setActivityContent {
            FrostedGlassEffectDemoNoMaterialTheme()
        }
    }
}

val sampleImages = listOf(
    R.drawable.avatar_miguel_garcia, // Replace with your actual images
    android.R.drawable.star_big_on,
    android.R.drawable.btn_star_big_off
)
// Define some basic colors to avoid MaterialTheme
object AppColors {
    val controlPanelBackground = Color(0xFF2C2C2E) // Dark gray
    val controlPanelText = Color.White
    val buttonBackground = Color(0xFF3A3A3C)
    val buttonContent = Color.White
    val accentColor = Color(0xFF0A84FF) // A blue accent
    val disabledText = Color.Gray
}

@Composable
fun FrostedGlassEffectDemoNoMaterialTheme() {
    val context = LocalContext.current
    var currentImageIndex by remember { mutableStateOf(0) }

    // Controls for the frosted glass effect
    var glassOpacity by remember { mutableStateOf(0.25f) }
    var blurRadiusX by remember { mutableStateOf(20f) }
    var blurRadiusY by remember { mutableStateOf(20f) }
    var tintRed by remember { mutableStateOf(1f) }
    var tintGreen by remember { mutableStateOf(1f) }
    var tintBlue by remember { mutableStateOf(1f) }

    val glassTintColor = Color(tintRed, tintGreen, tintBlue)

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            currentImageIndex = (currentImageIndex + 1) % sampleImages.size
        }
    }

    Column(modifier = Modifier.fillMaxSize().background(Color.Black)) { // Main background
        Box(modifier = Modifier.weight(1f)) {
            val backgroundModifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Modifier
                    .fillMaxSize()
                    .blur(radiusX = blurRadiusX.dp, radiusY = blurRadiusY.dp)
            } else {
                Modifier.fillMaxSize()
            }

            Image(
                painter = painterResource(id = sampleImages[currentImageIndex]),
                contentDescription = "Dynamic Background",
                modifier = backgroundModifier,
                contentScale = ContentScale.Crop
            )

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .width(300.dp)
                    .height(200.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(glassTintColor.copy(alpha = glassOpacity))
                    .border(
                        1.dp,
                        Color.White.copy(alpha = (glassOpacity + 0.2f).coerceAtMost(1f)),
                        RoundedCornerShape(20.dp)
                    )
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        "Frosted Glass",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = getContrastingTextColor(glassTintColor.copy(alpha = glassOpacity))
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "No Material Theme",
                        fontSize = 16.sp,
                        color = getContrastingTextColor(glassTintColor.copy(alpha = glassOpacity)).copy(alpha = 0.9f)
                    )
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "(Blur effect requires Android 12+)",
                            fontSize = 12.sp,
                            color = Color.Yellow.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        // Controls Area
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(AppColors.controlPanelBackground)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Text(
                "Controls",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AppColors.controlPanelText,
                modifier = Modifier.padding(bottom = 12.dp)
            )

            ValueAdjuster(
                label = "Glass Opacity",
                valueString = String.format("%.2f", glassOpacity),
                onIncrement = { glassOpacity = (glassOpacity + 0.05f).coerceIn(0f, 1f) },
                onDecrement = { glassOpacity = (glassOpacity - 0.05f).coerceIn(0f, 1f) }
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                Text(
                    "Blur Radius (Android 12+)",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = AppColors.controlPanelText,
                    modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
                )
                ValueAdjuster(
                    label = "Blur Radius X",
                    valueString = "${blurRadiusX.toInt()} dp",
                    onIncrement = { blurRadiusX = (blurRadiusX + 1f).coerceIn(0f, 50f) },
                    onDecrement = { blurRadiusX = (blurRadiusX - 1f).coerceIn(0f, 50f) }
                )
                ValueAdjuster(
                    label = "Blur Radius Y",
                    valueString = "${blurRadiusY.toInt()} dp",
                    onIncrement = { blurRadiusY = (blurRadiusY + 1f).coerceIn(0f, 50f) },
                    onDecrement = { blurRadiusY = (blurRadiusY - 1f).coerceIn(0f, 50f) }
                )
            } else {
                Text(
                    "Blur controls disabled (Requires Android 12+)",
                    fontSize = 12.sp,
                    color = AppColors.disabledText,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            Text(
                "Glass Tint Color",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = AppColors.controlPanelText,
                modifier = Modifier.padding(top = 12.dp, bottom = 6.dp)
            )
            Box(
                Modifier
                    .size(50.dp)
                    .background(glassTintColor, RoundedCornerShape(4.dp))
                    .border(1.dp, AppColors.controlPanelText.copy(alpha = 0.5f), RoundedCornerShape(4.dp))
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            )
            ValueAdjusterColor(
                label = "Red",
                value = tintRed,
                onValueChange = { tintRed = it },
                color = Color.Red
            )
            ValueAdjusterColor(
                label = "Green",
                value = tintGreen,
                onValueChange = { tintGreen = it },
                color = Color.Green
            )
            ValueAdjusterColor(
                label = "Blue",
                value = tintBlue,
                onValueChange = { tintBlue = it },
                color = Color.Blue
            )
        }
    }
}

@Composable
fun ValueAdjuster(
    label: String,
    valueString: String,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$label: $valueString", fontSize = 14.sp, color = AppColors.controlPanelText)
        Row {
            AdjusterButton(text = "-", onClick = onDecrement, modifier = Modifier.size(40.dp))
            Spacer(Modifier.width(8.dp))
            AdjusterButton(text = "+", onClick = onIncrement, modifier = Modifier.size(40.dp))
        }
    }
}

@Composable
fun ValueAdjusterColor(
    label: String,
    value: Float,
    onValueChange: (Float) -> Unit,
    color: Color
) {
    Column(modifier = Modifier.padding(vertical = 2.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$label: ${String.format("%.2f", value)}",
                fontSize = 14.sp,
                color = AppColors.controlPanelText
            )
            Row {
                AdjusterButton(
                    text = "-",
                    onClick = { onValueChange((value - 0.1f).coerceIn(0f, 1f)) },
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(8.dp))
                AdjusterButton(
                    text = "+",
                    onClick = { onValueChange((value + 0.1f).coerceIn(0f, 1f)) },
                    modifier = Modifier.size(40.dp)
                )
            }
        }
        // Basic visual representation of a slider
        Box(modifier = Modifier.fillMaxWidth().height(20.dp).padding(vertical = 6.dp)) {
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth().background(color.copy(alpha=0.3f), RoundedCornerShape(4.dp)))
            Box(modifier = Modifier.fillMaxHeight().fillMaxWidth(value).background(color, RoundedCornerShape(4.dp)))
        }
    }
}


@Composable
fun AdjusterButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button( // Using material3.Button
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppColors.buttonBackground,
            contentColor = AppColors.buttonContent
        ),
        contentPadding = PaddingValues(0.dp) // Allow text to fill small button
    ) {
        Text(text, fontSize = 16.sp)
    }
}

// Helper function to determine contrasting text color (remains the same)
fun getContrastingTextColor(backgroundColor: Color): Color {
    val r = backgroundColor.red
    val g = backgroundColor.green
    val b = backgroundColor.blue
    val alpha = backgroundColor.alpha
    if (alpha < 0.3f) return Color.White
    val luminance = (0.299 * r + 0.587 * g + 0.114 * b) * alpha + (1 - alpha) * 0.5
    return if (luminance > 0.5) Color.Black else Color.White
}
