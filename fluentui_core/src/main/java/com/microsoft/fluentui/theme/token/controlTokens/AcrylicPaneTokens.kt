package com.microsoft.fluentui.theme.token.controlTokens


import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

enum class AcrylicPaneOrientation {
    TOP,
    BOTTOM,
    CENTER
}

open class AcrylicPaneInfo(
    val style: FluentStyle = FluentStyle.Neutral,
    val orientation: AcrylicPaneOrientation = AcrylicPaneOrientation.BOTTOM
) : ControlInfo

@Parcelize
open class AcrylicPaneTokens : IControlToken, Parcelable {

    @Composable
    open fun acrylicPaneGradient(acrylicPaneInfo: AcrylicPaneInfo): Brush {
        val startColor: Color = FluentColor(
            light = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                ThemeMode.Light
            ),
            dark = FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background5].value(
                ThemeMode.Dark
            )
        ).value(FluentTheme.themeMode)
        when (acrylicPaneInfo.orientation) {
            AcrylicPaneOrientation.CENTER -> return Brush.verticalGradient(
                colors = listOf(
                    startColor,
                    startColor,
                    startColor.copy(alpha = 0.5f),
                    startColor.copy(alpha = 0.0f),
                ),
                tileMode = TileMode.Decal
            )
            AcrylicPaneOrientation.TOP -> return Brush.verticalGradient(
                colors = listOf(
                    startColor.copy(alpha = 0.0f),
                    startColor.copy(alpha = 0.5f),
                    startColor,
                    startColor,
                    startColor.copy(alpha = 0.5f),
                    startColor.copy(alpha = 0.0f),
                ),
                tileMode = TileMode.Decal
            )
            AcrylicPaneOrientation.BOTTOM -> return Brush.verticalGradient(
                colors = listOf(
                    startColor.copy(alpha = 0.0f),
                    startColor.copy(alpha = 0.5f),
                    startColor
                ),
                tileMode = TileMode.Decal
            )
        }
    }
}