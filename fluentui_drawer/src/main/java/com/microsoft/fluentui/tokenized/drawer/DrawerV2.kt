package com.microsoft.fluentui.tokenized.drawer
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BehaviorType
import com.microsoft.fluentui.theme.token.controlTokens.DrawerInfo
import com.microsoft.fluentui.theme.token.controlTokens.DrawerTokens

@Composable
fun DrawerV2(
    modifier: Modifier = Modifier,
    behaviorType: BehaviorType = BehaviorType.BOTTOM,
    drawerState: DrawerState = rememberDrawerState(DrawerValue.Closed),
    scrimVisible: Boolean = true,
    offset: IntOffset? = null,
    drawerTokens: DrawerTokens? = null,
    drawerContent: @Composable () -> Unit,
    preventDismissalOnScrimClick: Boolean = false,
    onScrimClick: () -> Unit = {}
) {
    val tokens = drawerTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.DrawerControlType] as DrawerTokens
    val scope = rememberCoroutineScope()
    val drawerInfo = DrawerInfo(type = behaviorType)
    val drawerShape: Shape =
        when (behaviorType) {
            BehaviorType.BOTTOM, BehaviorType.BOTTOM_SLIDE_OVER -> RoundedCornerShape(
                topStart = tokens.borderRadius(drawerInfo),
                topEnd = tokens.borderRadius(drawerInfo)
            )

            BehaviorType.TOP -> RoundedCornerShape(
                bottomStart = tokens.borderRadius(drawerInfo),
                bottomEnd = tokens.borderRadius(drawerInfo)
            )

            else -> RoundedCornerShape(tokens.borderRadius(drawerInfo))
        }
    val drawerElevation: Dp = tokens.elevation(drawerInfo)
    val drawerBackgroundColor: Color =
        tokens.backgroundColor(drawerInfo)
    val drawerHandleColor: Color = tokens.handleColor(drawerInfo)
    val scrimOpacity: Float = tokens.scrimOpacity(drawerInfo)
    val scrimColor: Color =
        tokens.scrimColor(drawerInfo).copy(alpha = scrimOpacity)
    when(behaviorType) {
        BehaviorType.BOTTOM, BehaviorType.BOTTOM_SLIDE_OVER -> {
            ModalDrawerSheet(
                drawerState = drawerState,
                modifier = modifier,
                drawerShape = drawerShape,
                drawerContainerColor=drawerBackgroundColor,
                drawerTonalElevation = drawerElevation,
            ) {
                drawerContent()
            }
        }
        BehaviorType.TOP -> {

        }
        BehaviorType.LEFT_SLIDE_OVER -> {
            ModalNavigationDrawer(
                drawerContent = drawerContent,
                drawerState = drawerState,
                modifier = modifier,
                scrimColor = scrimColor
            ) {

            }
        }
        BehaviorType.RIGHT_SLIDE_OVER -> {

        }


    }
}