package com.microsoft.fluentuidemo.demos.actionbar

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.actionbar.ActionBar
import com.microsoft.fluentui.tokenized.drawer.BottomDrawer
import com.microsoft.fluentui.tokenized.drawer.rememberDrawerState
import com.microsoft.fluentuidemo.AppBarMenu
import com.microsoft.fluentuidemo.AppThemeViewModel
import com.microsoft.fluentuidemo.Navigation
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.SetStatusBarColor
import com.microsoft.fluentuidemo.V2DemoActivity
import kotlinx.coroutines.launch

class V2ActionBarDemoActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = this

        setContent {
            FluentTheme {
                SetStatusBarColor()
                val pagerState = rememberPagerState(pageCount = { 6 })
                val scope = rememberCoroutineScope()

                Scaffold(
                    contentWindowInsets = WindowInsets.statusBars,
                    topBar = {
                        ActionBar(
                            pagerState = pagerState,
                            leftActionText = "Left",
                            rightActionText = "Right",
                            pageContent = {}
                        )
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value())
                            .padding(it)
                    ) {
               
                    }
                }
            }
        }
    }
}