package com.microsoft.fluentui.tokenized.bottomsheet

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Text
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.rememberModalBottomSheetState

import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.expand
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.BottomSheetInfo
import com.microsoft.fluentui.theme.token.controlTokens.BottomSheetTokens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetV2(
    sheetContent: @Composable ColumnScope.() -> Unit,
    modifier: Modifier = Modifier,
    sheetState: BottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
    expandable: Boolean = true,
    peekHeight: Dp = 110.dp,
    scrimVisible: Boolean = true,
    showHandle: Boolean = true,
    slideOver: Boolean = true,
    enableSwipeDismiss: Boolean = false,
    preventDismissalOnScrimClick: Boolean = false,  // if true, the sheet will not be dismissed when the scrim is clicked
    stickyThresholdUpward: Float = 56f,
    stickyThresholdDownward: Float = 56f,
    bottomSheetTokens: BottomSheetTokens? = null,
    onDismiss: () -> Unit = {}, // callback to be invoked after the sheet is closed,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()
    val tokens = bottomSheetTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.BottomSheetControlType] as BottomSheetTokens
    val bottomSheetInfo = BottomSheetInfo()
    val sheetShape: Shape = RoundedCornerShape(
        topStart = tokens.cornerRadius(bottomSheetInfo),
        topEnd = tokens.cornerRadius(bottomSheetInfo)
    )
    val sheetElevation: Dp = tokens.elevation(bottomSheetInfo)
    val sheetBackgroundColor: Color = tokens.backgroundColor(bottomSheetInfo)
    val sheetHandleColor: Color = tokens.handleColor(bottomSheetInfo)
    if (scrimVisible) {
        BoxWithConstraints(modifier) {
            val fullHeight = constraints.maxHeight.toFloat()
            val sheetHeightState =
                remember(sheetContent.hashCode()) { mutableStateOf<Float?>(null) }

            Box(
                Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
                    .semantics {
                        if (!sheetState.bottomSheetState.isVisible) {
                            expand {
//                                if (sheetState.bottomSheetState.(BottomSheetValue.Shown)) {
                                scope.launch { sheetState.bottomSheetState.show() }
//                                }
                                true
                            }
                        }
                    }) {
                content()
                if (sheetState.bottomSheetState.isVisible) {

                    ModalBottomSheet(
                        sheetState = sheetState.bottomSheetState,
                        containerColor = sheetBackgroundColor,
                        tonalElevation = sheetElevation,
                        modifier = modifier,
                        onDismissRequest = onDismiss,
                        shape = sheetShape,
                        dragHandle = {
                            if (showHandle) {
                                BottomSheetDefaults.DragHandle(
                                    color = sheetHandleColor,
                                    shape = MaterialTheme.shapes.medium
                                )
                            } else {
                            }
                        },
                    ) {
                        sheetContent()
                    }

                }
            }
        }
    } else {
        BottomSheetScaffold(
            sheetContent = sheetContent,
            modifier = modifier,
            scaffoldState = sheetState,
            sheetPeekHeight = peekHeight,
            sheetSwipeEnabled = enableSwipeDismiss,
            sheetContentColor = sheetBackgroundColor,
            sheetTonalElevation = sheetElevation,
            sheetShape = sheetShape,
            sheetDragHandle = {
                if (showHandle) {
                    BottomSheetDefaults.DragHandle(
                        color = sheetHandleColor,
                        shape = MaterialTheme.shapes.medium
                    )
                } else {
                }
            },
        ) {
            content()
        }
    }
}