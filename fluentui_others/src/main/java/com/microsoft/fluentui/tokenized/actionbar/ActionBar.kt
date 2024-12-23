package com.microsoft.fluentui.tokenized.actionbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ActionBarIcons
import com.microsoft.fluentui.icons.actionbaricons.Arrowright
import com.microsoft.fluentui.icons.actionbaricons.Chevron
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.ACTIONBARTYPE
import com.microsoft.fluentui.theme.token.controlTokens.ActionBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.ActionBarTokens
import com.microsoft.fluentui.theme.token.controlTokens.ButtonStyle
import com.microsoft.fluentui.tokenized.controls.Button
import kotlinx.coroutines.launch

/**
 * ActionBar is a composable that provides a way to navigate between pages.
 *
 * @param pagerState: PagerState
 * @param modifier: Modifier
 * @param type: Int
 * @param startCallback: () -> Unit
 * @param actionBarTokens: ActionBarTokens?
 */
@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ActionBar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    type: Int = ACTIONBARTYPE.BASIC.ordinal,
    startCallback: () -> Unit,
    actionBarTokens: ActionBarTokens? = null
) {
    val token =
        actionBarTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ActionBarControlType] as ActionBarTokens
    val noOfPages = pagerState.pageCount
    val actionBarInfo = ActionBarInfo()
    val height = token.actionBarHeight(actionBarInfo)
    Box(
        modifier = modifier.fillMaxWidth().height(height).background(
            token.actionBarColor(actionBarInfo)
        )
    ) {
        val scope = rememberCoroutineScope()
        var selectedPage by rememberSaveable { mutableStateOf(0) }

        // carousel indicator
        if (type == ACTIONBARTYPE.CAROUSEL.ordinal) {
            Row(
                Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
                    .align(Alignment.Center),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pagerState.pageCount) { iteration ->
                    val color =
                        if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
                    Box(
                        modifier = Modifier
                            .padding(2.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(8.dp)
                    )
                }
            }
        }

        // left action
        if (selectedPage < noOfPages - 1) {
            Button(
                style = ButtonStyle.TextButton,
                onClick = {
                    scope.launch {
                        selectedPage = noOfPages - 1
                        pagerState.animateScrollToPage(noOfPages - 1)
                    }
                },
                modifier = Modifier.align(Alignment.CenterStart),
                text = "Skip"
            )
        }

        // right action
        val rightActionText =
            if (type == ACTIONBARTYPE.CAROUSEL.ordinal) "" else if (selectedPage == noOfPages - 1) "Start" else "Next"
        val trailingIcon =
            if (type == ACTIONBARTYPE.ICON.ordinal) {
                ActionBarIcons.Chevron
            } else if (type == ACTIONBARTYPE.CAROUSEL.ordinal) {
                ActionBarIcons.Arrowright
            } else {
                null
            }

        Button(
            style = ButtonStyle.TextButton,
            trailingIcon = trailingIcon,
            onClick = {
                if (selectedPage < noOfPages - 1) {
                    selectedPage += 1
                    scope.launch {
                        pagerState.animateScrollToPage(selectedPage)
                    }
                } else {
                    startCallback()
                }
            },
            modifier = Modifier.align(Alignment.CenterEnd),
            text = rightActionText
        )

    }
}
