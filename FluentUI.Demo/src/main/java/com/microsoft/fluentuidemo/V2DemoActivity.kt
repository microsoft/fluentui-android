package com.microsoft.fluentuidemo

import android.content.res.Resources.Theme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import com.example.theme.token.ExcelAliasTokens
import com.example.theme.token.M365AliasTokens
import com.example.theme.token.OneNoteAliasTokens
import com.example.theme.token.PowerPointAliasTokens
import com.example.theme.token.WordAliasTokens
import com.microsoft.fluentui.compose.Scaffold
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.ListItemInfo
import com.microsoft.fluentui.theme.token.controlTokens.ListItemTokens
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Menu

open class V2DemoActivity : ComponentActivity() {
    companion object {
        const val DEMO_TITLE = "demo_title"
    }

    private var content : @Composable () -> Unit = {}
    fun setActivityContent (content : @Composable () -> Unit) {
        this@V2DemoActivity.content = content
    }

    private var bottomBar : @Composable (RowScope.() -> Unit)? = null
    fun setButtonBar (bottomBar : @Composable (RowScope.() -> Unit)) {
        this@V2DemoActivity.bottomBar = bottomBar
    }

    open val appBarSize = AppBarSize.Medium
    lateinit var appThemeStyle: State<FluentStyle>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val demoTitle = intent.getSerializableExtra(DEMO_TITLE) as String

        setContent {
            FluentTheme {
                appThemeStyle = AppTheme.observeThemeStyle(initial = FluentStyle.Neutral)
                Scaffold(
                    topBar = {
                            AppBar(
                                title = demoTitle,
                                navigationIcon = FluentIcon(
                                    SearchBarIcons.Arrowback,
                                    contentDescription = "Navigate Back",
                                    onClick = { Navigation.backNavigation(this) }
                                ),
                                style = appThemeStyle.value,
                                appBarSize = appBarSize,
                                bottomBar = bottomBar,
                                rightAccessoryView = {
                                    var expandedMenu by remember { mutableStateOf(false) }
                                    val listItemToken = object : ListItemTokens() {
                                        @Composable
                                        override fun backgroundBrush(listItemInfo: ListItemInfo): StateBrush {
                                            return StateBrush(
                                                rest = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2].value(themeMode = FluentTheme.themeMode)),
                                                pressed = SolidColor(FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background2Pressed].value(themeMode = FluentTheme.themeMode))
                                            )
                                        }
                                    }
                                    Box {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_fluent_more_vertical_24_regular),
                                            contentDescription = "More",
                                            modifier = Modifier
                                                .padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
                                                .clickable { expandedMenu = true },
                                            tint = if(appThemeStyle.value == FluentStyle.Neutral) { FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value( FluentTheme.themeMode) }
                                            else { FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value( FluentTheme.themeMode) }
                                        )
                                        Menu (
                                            opened = expandedMenu,
                                            onDismissRequest = { expandedMenu = false},
                                        ) {
                                            val scrollState = rememberScrollState()
                                            Column (
                                                modifier = Modifier.verticalScroll(scrollState)
                                            ) {
                                                ListItem.Item(
                                                    text = "Brand",
                                                    trailingAccessoryContent = {
                                                        ToggleSwitch(
                                                            onValueChange = {
                                                                if (it) {AppTheme.updateThemeStyle(FluentStyle.Brand)}
                                                                else {AppTheme.updateThemeStyle(FluentStyle.Neutral)}
                                                            },
                                                            checkedState = appThemeStyle.value == FluentStyle.Brand,
                                                        )
                                                    },
                                                    border = BorderType.Bottom,
                                                    listItemTokens = listItemToken
                                                )

                                                ListItem.SectionHeader(
                                                    title = "Choose your brand theme:",
                                                    enableChevron = false,
                                                    style = SectionHeaderStyle.Subtle,
                                                    listItemTokens = listItemToken
                                                )

                                                Column (
                                                    modifier = Modifier
                                                        .padding(horizontal = FluentGlobalTokens.size(
                                                            FluentGlobalTokens.SizeTokens.Size160)),
                                                    verticalArrangement = Arrangement.spacedBy( FluentGlobalTokens.size(
                                                        FluentGlobalTokens.SizeTokens.Size100) )
                                                ) {
                                                    AppTheme.SetAppTheme()
                                                }
                                            }
                                        }
                                    }
                                }
                            )
                    }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    ) {
                        content()
                    }
                }
            }
        }
    }
}