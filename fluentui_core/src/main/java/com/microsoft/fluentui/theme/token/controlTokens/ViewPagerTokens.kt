package com.microsoft.fluentui.theme.token.controlTokens

import android.os.Parcelable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlInfo
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.IControlToken
import kotlinx.parcelize.Parcelize


open class ViewPagerInfo: ControlInfo
@Parcelize
open class ViewPagerTokens : IControlToken, Parcelable {

    @Composable
    open fun contentPadding(viewPagerInfo: ViewPagerInfo): PaddingValues {
        return PaddingValues(0.dp)
    }

    @Composable
    open fun pageSpacing(viewPagerInfo: ViewPagerInfo): Dp {
        return 0.dp
    }
}
