package com.microsoft.fluentuidemo.demos.actionbar

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.tokenized.actionbar.ActionBar
import com.microsoft.fluentui.tokenized.navigation.ViewPager
import com.microsoft.fluentuidemo.SetStatusBarColor
import com.microsoft.fluentuidemo.V2DemoActivity

class V2ActionBarDemoActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this
        val selectedActionBarType = intent.getIntExtra("ACTION_BAR_TYPE", 0)
        val selectedActionBarPosition = intent.getIntExtra("ACTION_BAR_POSITION", 0)
        setContent {
            FluentTheme {
                SetStatusBarColor()
                val noOfPages = 5
                val pagerState = rememberPagerState(pageCount = { noOfPages })

                val actionBar = @androidx.compose.runtime.Composable {
                    ActionBar(
                        pagerState = pagerState,
                        startCallback = {
                            this.finish()
                        },
                        type = selectedActionBarType
                    )
                }
                Scaffold(
                    contentWindowInsets = WindowInsets.statusBars,
                    topBar = if (selectedActionBarPosition == 0)
                        actionBar
                    else {
                        {}
                    },
                    bottomBar = if (selectedActionBarPosition == 1) {
                        actionBar
                    } else {
                        {}
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
                            .padding(it)
                    ) {
                        ViewPager(
                            pagerState = pagerState,
                            modifier = Modifier.fillMaxSize(),
                            pageContent = {
                                Box(
                                    Modifier
                                        .fillMaxSize()
                                        .background(
                                            color = if (pagerState.currentPage % 2 == 0) Color.Cyan else Color.LightGray
                                        )
                                ) {
                                    BasicText(
                                        text = "Page ${pagerState.currentPage}",
                                        modifier = Modifier.align(Alignment.Center)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}