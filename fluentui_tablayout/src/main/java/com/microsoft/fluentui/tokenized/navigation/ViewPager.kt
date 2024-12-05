package com.microsoft.fluentui.tokenized.navigation

import android.graphics.Paint.Align
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.ViewPagerInfo
import com.microsoft.fluentui.theme.token.controlTokens.ViewPagerTokens

/**
 * API to create a ViewPager.
 *
 * @param pagerState PagerState to manage the state of ViewPager
 * @param pageContent Content to be displayed in ViewPager
 * @param modifier Optional modifier for ViewPager
 * @param pageSize Size of the page. Default: [PageSize.Fill]
 * @param userScrollEnabled Boolean for enabling/disabling user scroll. Default: [false]
 * @param verticalAlignment Alignment of content in ViewPager. Default: [Alignment.CenterVertically]
 * @param viewPagerTokens Tokens to customize appearance of ViewPager. Default: [null]
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ViewPager(
    pagerState: PagerState,
    pageContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    pageSize: PageSize = PageSize.Fill,
    userScrollEnabled: Boolean = false,
    verticalAlignment: Alignment.Vertical = Alignment.CenterVertically,
    viewPagerTokens: ViewPagerTokens? = null
) {
    val token =
        viewPagerTokens
            ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.ViewPagerControlType] as ViewPagerTokens

    val viewPagerInfo = ViewPagerInfo()
    // HorizontalPager is a horizontally scrolling pager using the provided pagerState
        HorizontalPager(
            state = pagerState,
            modifier = modifier,
            contentPadding = token.contentPadding(viewPagerInfo),
            pageSpacing = token.pageSpacing(viewPagerInfo),
            pageSize = pageSize,
            userScrollEnabled = userScrollEnabled,
            verticalAlignment = verticalAlignment
        ) {
            pageContent()
        }
}
