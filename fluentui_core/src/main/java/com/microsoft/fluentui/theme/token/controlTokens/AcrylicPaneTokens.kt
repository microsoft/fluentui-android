package com.microsoft.fluentui.theme.token.controlTokens


import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class AcrylicPaneInfo(
    val style: FluentStyle = FluentStyle.Neutral
) : ControlInfo

@Parcelize
open class AcrylicPaneTokens : IControlToken, Parcelable {

    @Composable
    open fun acrylicPaneGradient(acrylicPaneInfo: AcrylicPaneInfo): Brush {
        if(acrylicPaneInfo.style == FluentStyle.Neutral) {
            val startColor: Color = Color(red = 0xF7, green = 0xF8 , blue = 0xFB, alpha = 0xFF)
            return Brush.verticalGradient(
                colors = listOf(
                    startColor,
                    startColor,
                    startColor,
                    startColor.copy(alpha = 0.5f),
                    startColor.copy(alpha = 0.0f),
                ),
               tileMode = TileMode.Decal
            )
        }
        else{
            val startColor: Color = Color(0xFE106cbc)
            return Brush.verticalGradient(
                colors = listOf(
                    startColor,
                    startColor,
                    startColor.copy(alpha = 0.8f),
                    startColor.copy(alpha = 0.5f),
                    startColor.copy(alpha = 0.0f),
                ),
                tileMode = TileMode.Decal
            )
        }
    }
}