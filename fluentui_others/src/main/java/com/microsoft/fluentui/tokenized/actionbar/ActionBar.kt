package com.microsoft.fluentui.tokenized.actionbar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.tokenized.controls.Button


@Composable
@OptIn(ExperimentalFoundationApi::class)
fun ActionBar(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    leftActionText: String? = null,
    rightActionText: String? = null,
    carouselMode: Boolean = false,
    pageContent: @Composable () -> Unit
) {
    // create a top bar with left and right actions
    Box(
       modifier = modifier.fillMaxWidth()
    ) {
        // left action
        if (leftActionText != null) {
            // create a button with the left action text
             Button(
                 onClick = { /* handle left action click */ },
                 modifier = Modifier.align(Alignment.CenterStart),
                 text = leftActionText
             )
        }

        // right action
        if (rightActionText != null) {
           //  create a button with the right action text
             Button(
                 onClick = { /* handle right action click */ },
                 modifier = Modifier.align(Alignment.CenterEnd),
                 text = rightActionText
             )
        }
    }
}