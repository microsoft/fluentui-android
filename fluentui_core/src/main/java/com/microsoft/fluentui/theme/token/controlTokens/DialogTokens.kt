package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize

open class DialogInfo : ControlInfo

@Parcelize
open class DialogTokens : IControlToken, Parcelable {
    @Composable
    open fun backgroundBrush(dialogInfo: DialogInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun cornerRadius(dialogInfo: DialogInfo): Dp {
        return FluentGlobalTokens.cornerRadius(FluentGlobalTokens.CornerRadiusTokens.CornerRadius120)
    }

    @Composable
    open fun elevation(dialogInfo: DialogInfo): Dp {
        return FluentGlobalTokens.elevation(FluentGlobalTokens.ShadowTokens.Shadow04)
    }

    @Composable
    open fun borderBrush(dialogInfo: DialogInfo): Brush {
        return SolidColor(
            FluentTheme.aliasTokens.neutralStrokeColor[FluentAliasTokens.NeutralStrokeColorTokens.Stroke1].value(
                themeMode = FluentTheme.themeMode
            )
        )
    }

    @Composable
    open fun borderStrokeWidth(dialogInfo: DialogInfo): Dp {
        return FluentGlobalTokens.strokeWidth(FluentGlobalTokens.StrokeWidthTokens.StrokeWidth05)
    }
}