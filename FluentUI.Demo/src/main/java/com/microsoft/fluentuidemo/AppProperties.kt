package com.microsoft.fluentuidemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.NavUtils.navigateUpTo
import androidx.core.view.WindowCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theme.token.ExcelAliasTokens
import com.example.theme.token.M365AliasTokens
import com.example.theme.token.OneNoteAliasTokens
import com.example.theme.token.PowerPointAliasTokens
import com.example.theme.token.WordAliasTokens
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.FluentGlobalTokens
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.Icon
import com.microsoft.fluentui.theme.token.StateBrush
import com.microsoft.fluentui.theme.token.controlTokens.BorderType
import com.microsoft.fluentui.theme.token.controlTokens.RadioButtonInfo
import com.microsoft.fluentui.theme.token.controlTokens.RadioButtonTokens
import com.microsoft.fluentui.theme.token.controlTokens.SectionHeaderStyle
import com.microsoft.fluentui.tokenized.controls.RadioButton
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.menu.Dialog
import com.microsoft.fluentui.tokenized.menu.Menu

object AppTheme : ViewModel() {
    lateinit var appThemeStyle: State<FluentStyle>
    private var themeStyle: MutableLiveData<FluentStyle> = MutableLiveData(FluentStyle.Neutral)
    private var selectedThemeIndex_: MutableLiveData<Int> = MutableLiveData(0)
    private var selectedThemeMode_: MutableLiveData<ThemeMode> = MutableLiveData(ThemeMode.Auto)

    private fun updateThemeStyle(overrideThemeStyle: FluentStyle) {
        themeStyle.value = overrideThemeStyle
    }

    @Composable
    fun observeThemeStyle(initial: FluentStyle): State<FluentStyle> {
        return this.themeStyle.observeAsState(initial)
    }

    @Composable
    fun AppBarMenu() {
        var expandedMenu by remember { mutableStateOf(false) }
        Icon(
            painter = painterResource(id = R.drawable.ic_fluent_more_vertical_24_regular),
            contentDescription = "More",
            modifier = Modifier
                .padding(FluentGlobalTokens.size(FluentGlobalTokens.SizeTokens.Size120))
                .clickable { expandedMenu = true },
            tint = if (appThemeStyle.value == FluentStyle.Neutral) {
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                    FluentTheme.themeMode
                )
            } else {
                FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.ForegroundLightStatic].value(
                    FluentTheme.themeMode
                )
            }
        )
        Menu(
            opened = expandedMenu,
            onDismissRequest = { expandedMenu = false },
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState())
            ) {
                var accentEnabled by remember { mutableStateOf(false) }
                ListItem.Item(
                    text = "Accent",
                    onClick = {
                        accentEnabled = !accentEnabled
                        if (accentEnabled) {
                            updateThemeStyle(
                                FluentStyle.Brand
                            )
                        } else {
                            updateThemeStyle(
                                FluentStyle.Neutral
                            )
                        }
                    },
                    trailingAccessoryContent = {
                        ToggleSwitch(
                            onValueChange = {
                                accentEnabled = it
                                if (it) {
                                    updateThemeStyle(
                                        FluentStyle.Brand
                                    )
                                } else {
                                    updateThemeStyle(
                                        FluentStyle.Neutral
                                    )
                                }
                            },
                            checkedState = appThemeStyle.value == FluentStyle.Brand,
                        )
                    },
                    border = BorderType.Bottom,
                    listItemTokens = CustomizedTokens.listItemTokens
                )

                var showAppearanceDialog by remember { mutableStateOf(false) }
                ListItem.Item(
                    text = "Appearance",
                    leadingAccessoryContent = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_fluent_dark_theme_24_regular),
                            contentDescription = "Appearance Icon",
                            tint = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground2].value(),
                        )
                    },
                    onClick = { showAppearanceDialog = !showAppearanceDialog },
                    listItemTokens = CustomizedTokens.listItemTokens
                )

                if (showAppearanceDialog) {
                    Dialog(
                        dialogProperties = DialogProperties(
                            dismissOnClickOutside = true,
                            dismissOnBackPress = true
                        ),
                        onDismiss = { showAppearanceDialog = !showAppearanceDialog },
                    ) {
                        SetAppThemeMode()
                    }
                }

                ListItem.SectionHeader(
                    title = "Choose your brand theme:",
                    enableChevron = false,
                    style = SectionHeaderStyle.Subtle,
                    listItemTokens = CustomizedTokens.listItemTokens
                )

                SetAppTheme()
            }
        }
    }

    @Composable
    fun SetAppTheme() {
        val selectedThemeIndex by selectedThemeIndex_.observeAsState()
        val themesList = arrayOf(
            Pair(AliasTokens(), "Fluent Brand"),
            Pair(OneNoteAliasTokens(), "One Note"),
            Pair(WordAliasTokens(), "Word"),
            Pair(ExcelAliasTokens(), "Excel"),
            Pair(PowerPointAliasTokens(), "PowerPoint"),
            Pair(M365AliasTokens(), "M365")
        )

        themesList.forEachIndexed { index, theme ->
            ListItem.Item(
                onClick = {
                    FluentTheme.updateAliasTokens(theme.first)
                    selectedThemeIndex_.value = index
                },
                text = theme.second,
                leadingAccessoryContent = {
                    RadioButton(
                        selected = selectedThemeIndex == index,
                        onClick = {
                            FluentTheme.updateAliasTokens(theme.first)
                            selectedThemeIndex_.value = index
                        },
                        radioButtonToken = object : RadioButtonTokens() {
                            @Composable
                            override fun backgroundBrush(radioButtonInfo: RadioButtonInfo): StateBrush {
                                return StateBrush(
                                    rest = SolidColor(theme.first.brandColor[FluentAliasTokens.BrandColorTokens.Color80]),
                                    selected = SolidColor(theme.first.brandColor[FluentAliasTokens.BrandColorTokens.Color80])
                                )
                            }
                        }
                    )
                },
                listItemTokens = CustomizedTokens.listItemTokens
            )
        }
    }

    @Composable
    fun SetAppThemeMode() {
        Column {
            ListItem.SectionHeader(
                title = "Choose Appearance",
                enableChevron = false,
                listItemTokens = CustomizedTokens.listItemTokens
            )
            val selectedThemeMode by selectedThemeMode_.observeAsState()
            ListItem.Item(
                text = "System Default",
                onClick = {
                    selectedThemeMode_.value = ThemeMode.Auto
                    FluentTheme.updateThemeMode(ThemeMode.Auto)
                },
                leadingAccessoryContent = {
                    RadioButton(
                        selected = selectedThemeMode == ThemeMode.Auto,
                        onClick = {
                            selectedThemeMode_.value = ThemeMode.Auto
                            FluentTheme.updateThemeMode(ThemeMode.Auto)
                        },
                    )
                },
                listItemTokens = CustomizedTokens.listItemTokens
            )

            ListItem.Item(
                text = "Light",
                onClick = {
                    selectedThemeMode_.value = ThemeMode.Light
                    FluentTheme.updateThemeMode(ThemeMode.Light)
                },
                leadingAccessoryContent = {
                    RadioButton(
                        selected = selectedThemeMode == ThemeMode.Light,
                        onClick = {
                            selectedThemeMode_.value = ThemeMode.Light
                            FluentTheme.updateThemeMode(ThemeMode.Light)
                        },
                    )
                },
                listItemTokens = CustomizedTokens.listItemTokens
            )

            ListItem.Item(
                text = "Dark",
                onClick = {
                    selectedThemeMode_.value = ThemeMode.Dark
                    FluentTheme.updateThemeMode(ThemeMode.Dark)
                },
                leadingAccessoryContent = {
                    RadioButton(
                        selected = selectedThemeMode == ThemeMode.Dark,
                        onClick = {
                            selectedThemeMode_.value = ThemeMode.Dark
                            FluentTheme.updateThemeMode(ThemeMode.Dark)
                        },
                    )
                },
                listItemTokens = CustomizedTokens.listItemTokens
            )
        }

    }

    @Composable
    fun SetStatusBarColor() {
        appThemeStyle = observeThemeStyle(initial = FluentStyle.Neutral)
        val view = LocalView.current
        val window = (view.context as Activity).window
        val insets = WindowCompat.getInsetsController(window, view)
        window.statusBarColor = if (appThemeStyle.value == FluentStyle.Brand)
            FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value()
                .toArgb()
        else
            FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value()
                .toArgb()

        insets?.isAppearanceLightStatusBars =
            (!isSystemInDarkTheme() && appThemeStyle.value != FluentStyle.Brand && selectedThemeMode_.value != ThemeMode.Dark) || (isSystemInDarkTheme() && selectedThemeMode_.value == ThemeMode.Light && appThemeStyle.value != FluentStyle.Brand)
    }
}


object Navigation {
    private var navigationStack = ArrayDeque<Class<ComponentActivity>>()
    fun forwardNavigation(
        currentActivity: ComponentActivity,
        targetActivity: Class<out androidx.core.app.ComponentActivity>,
        vararg extraData: Pair<String, java.io.Serializable>
    ) {
        val intent = Intent(currentActivity, targetActivity)
        extraData.forEach {
            intent.putExtra(it.first, it.second)
        }
        navigationStack.add(currentActivity.javaClass)
        currentActivity.startActivity(intent)
    }

    fun backNavigation(context: Context) {
        navigateUpTo(context as Activity, Intent(context, navigationStack.removeLast()))
    }
}