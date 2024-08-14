package com.microsoft.fluentuidemo

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatDelegate
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import androidx.core.app.NavUtils.navigateUpTo
import androidx.core.view.WindowCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.theme.token.ExcelAliasTokens
import com.example.theme.token.M365AliasTokens
import com.example.theme.token.OneNoteAliasTokens
import com.example.theme.token.PowerPointAliasTokens
import com.example.theme.token.TeamsAliasTokens
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

object AppThemeViewModel : ViewModel() {
    lateinit var appThemeStyle: State<FluentStyle>
    private var themeStyle: MutableLiveData<FluentStyle> = MutableLiveData(FluentStyle.Brand)
    var selectedThemeIndex_: MutableLiveData<Int> = MutableLiveData(0)
    var selectedThemeMode_: MutableLiveData<ThemeMode> = MutableLiveData(ThemeMode.Auto)

    fun updateThemeStyle(overrideThemeStyle: FluentStyle) {
        themeStyle.value = overrideThemeStyle
    }

    @Composable
    fun observeThemeStyle(initial: FluentStyle): State<FluentStyle> {
        return this.themeStyle.observeAsState(initial)
    }
}

@Composable
fun SetStatusBarColor() {
    AppThemeViewModel.appThemeStyle =
        AppThemeViewModel.observeThemeStyle(initial = FluentStyle.Brand)
    val view = LocalView.current
    val window = (view.context as Activity).window
    val insets = WindowCompat.getInsetsController(window, view)
    window.statusBarColor = if (AppThemeViewModel.appThemeStyle.value == FluentStyle.Brand)
        FluentTheme.aliasTokens.brandBackgroundColor[FluentAliasTokens.BrandBackgroundColorTokens.BrandBackground1].value()
            .toArgb()
    else
        FluentTheme.aliasTokens.neutralBackgroundColor[FluentAliasTokens.NeutralBackgroundColorTokens.Background1].value()
            .toArgb()

    insets?.isAppearanceLightStatusBars =
        (!isSystemInDarkTheme() && AppThemeViewModel.appThemeStyle.value != FluentStyle.Brand && AppThemeViewModel.selectedThemeMode_.value != ThemeMode.Dark) || (isSystemInDarkTheme() && AppThemeViewModel.selectedThemeMode_.value == ThemeMode.Light && AppThemeViewModel.appThemeStyle.value != FluentStyle.Brand)
}

@Composable
fun AppBarMenu() {
    var expandedMenu by rememberSaveable { mutableStateOf(false) }
    Icon(
        painter = painterResource(id = R.drawable.ic_fluent_more_vertical_24_regular),
        contentDescription = stringResource(id = R.string.app_bar_more),
        modifier = Modifier
            .padding(FluentGlobalTokens.SizeTokens.Size120.value)
            .clickable { expandedMenu = true },
        tint = if (AppThemeViewModel.appThemeStyle.value == FluentStyle.Neutral) {
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
            var accentEnabled by remember { mutableStateOf(true) }
            ListItem.Item(
                text = stringResource(id = R.string.accent),
                onClick = {
                    accentEnabled = !accentEnabled
                    if (accentEnabled) {
                        AppThemeViewModel.updateThemeStyle(
                            FluentStyle.Brand
                        )
                    } else {
                        AppThemeViewModel.updateThemeStyle(
                            FluentStyle.Neutral
                        )
                    }
                },
                trailingAccessoryContent = {
                    ToggleSwitch(
                        onValueChange = {
                            accentEnabled = it
                            if (it) {
                                AppThemeViewModel.updateThemeStyle(
                                    FluentStyle.Brand
                                )
                            } else {
                                AppThemeViewModel.updateThemeStyle(
                                    FluentStyle.Neutral
                                )
                            }
                        },
                        checkedState = AppThemeViewModel.appThemeStyle.value == FluentStyle.Brand,
                    )
                },
                border = BorderType.Bottom,
                listItemTokens = CustomizedTokens.listItemTokens
            )

            var showAppearanceDialog by remember { mutableStateOf(false) }
            ListItem.Item(
                text = stringResource(id = R.string.appearance),
                leadingAccessoryContent = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_fluent_dark_theme_24_regular),
                        contentDescription = stringResource(id = R.string.appearance),
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
                    SetAppThemeMode { showAppearanceDialog = !showAppearanceDialog }
                }
            }

            ListItem.SectionHeader(
                title = stringResource(id = R.string.choose_brand_theme),
                enableChevron = false,
                style = SectionHeaderStyle.Subtle,
                listItemTokens = CustomizedTokens.listItemTokens
            )
            SetAppTheme()
        }
    }
}

@Composable
fun SetAppThemeMode(onThemeModeClick: () -> Unit) {
    Column {
        ListItem.SectionHeader(
            title = stringResource(id = R.string.choose_appearance),
            enableChevron = false,
            listItemTokens = CustomizedTokens.listItemTokens
        )

        val selectedThemeMode by AppThemeViewModel.selectedThemeMode_.observeAsState()
        ListItem.Item(
            text = stringResource(id = R.string.appearance_system_default),
            onClick = { appThemeToggle(ThemeMode.Auto, onThemeModeClick ) },
            leadingAccessoryContent = {
                RadioButton(
                    selected = selectedThemeMode == ThemeMode.Auto,
                    onClick = { appThemeToggle(ThemeMode.Auto, onThemeModeClick ) },
                )
            },
            listItemTokens = CustomizedTokens.listItemTokens
        )

        ListItem.Item(
            text = stringResource(id = R.string.appearance_light),
            onClick = { appThemeToggle(ThemeMode.Light, onThemeModeClick ) },
            leadingAccessoryContent = {
                RadioButton(
                    selected = selectedThemeMode == ThemeMode.Light,
                    onClick = { appThemeToggle(ThemeMode.Light, onThemeModeClick ) },
                )
            },
            listItemTokens = CustomizedTokens.listItemTokens
        )

        ListItem.Item(
            text = stringResource(id = R.string.appearance_dark),
            onClick = { appThemeToggle(ThemeMode.Dark, onThemeModeClick) },
            leadingAccessoryContent = {
                RadioButton(
                    selected = selectedThemeMode == ThemeMode.Dark,
                    onClick = { appThemeToggle(ThemeMode.Dark, onThemeModeClick) },
                )
            },
            listItemTokens = CustomizedTokens.listItemTokens
        )
    }
}

fun appThemeToggle(appThemeMode: ThemeMode, onThemeModeClick: () -> Unit) {
    AppThemeViewModel.selectedThemeMode_.value = appThemeMode
    FluentTheme.updateThemeMode(appThemeMode)
    onThemeModeClick()
    when(appThemeMode){
        ThemeMode.Auto -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        ThemeMode.Light -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        ThemeMode.Dark -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }
}
@Composable
fun SetAppTheme() {
    val selectedThemeIndex by AppThemeViewModel.selectedThemeIndex_.observeAsState()
    val themesList = arrayOf(
        Pair(AliasTokens(), stringResource(id = R.string.fluent_brand_theme)),
        Pair(OneNoteAliasTokens(), stringResource(id = R.string.one_note_theme)),
        Pair(WordAliasTokens(), stringResource(id = R.string.word_theme)),
        Pair(ExcelAliasTokens(), stringResource(id = R.string.excel_theme)),
        Pair(PowerPointAliasTokens(), stringResource(id = R.string.powerpoint_theme)),
        Pair(M365AliasTokens(), stringResource(id = R.string.m365_theme)),
        Pair(TeamsAliasTokens(), stringResource(id = R.string.teams_theme))
    )

    themesList.forEachIndexed { index, theme ->
        ListItem.Item(
            text = theme.second,
            onClick = {
                FluentTheme.updateAliasTokens(theme.first)
                AppThemeViewModel.selectedThemeIndex_.value = index
            },
            leadingAccessoryContent = {
                RadioButton(
                    selected = selectedThemeIndex == index,
                    onClick = {
                        FluentTheme.updateAliasTokens(theme.first)
                        AppThemeViewModel.selectedThemeIndex_.value = index
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