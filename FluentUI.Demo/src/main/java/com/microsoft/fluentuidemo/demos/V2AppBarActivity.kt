package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.FluentColor
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.AppBar
import com.microsoft.fluentui.tokenized.SearchBar
import com.microsoft.fluentui.tokenized.controls.ToggleSwitch
import com.microsoft.fluentui.tokenized.listitem.ChevronOrientation
import com.microsoft.fluentui.tokenized.listitem.ListItem
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillBar
import com.microsoft.fluentui.tokenized.segmentedcontrols.PillMetaData
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import kotlin.math.max

class V2AppBarLayoutActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeHere = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        composeHere.setContent {
            FluentTheme {
                var style: FluentStyle by rememberSaveable { mutableStateOf(FluentStyle.Neutral) }
                var appBarSize: AppBarSize by rememberSaveable { mutableStateOf(AppBarSize.Small) }
                var searchMode: Boolean by rememberSaveable { mutableStateOf(false) }
                var subtitle: String? by rememberSaveable { mutableStateOf("Subtitle") }
                var enableSearchBar: Boolean by rememberSaveable { mutableStateOf(false) }
                var enableButtonBar: Boolean by rememberSaveable { mutableStateOf(false) }
                var yAxisDelta: Float by rememberSaveable { mutableStateOf(1.0F) }

                Column(modifier = Modifier.pointerInput(Unit) {
                    detectDragGestures { _, distance ->
                        if (searchMode)
                            yAxisDelta = 0F
                        else
                            yAxisDelta = max(0F, distance.y + 10F) / 20F
                    }
                }) {
                    ListItem.SectionHeader(
                        title = LocalContext.current.resources.getString(R.string.app_modifiable_parameters),
                        enableChevron = true,
                        enableContentOpenCloseTransition = true,
                        chevronOrientation = ChevronOrientation(90f, 0f),
                    ) {
                        Column {
                            PillBar(
                                mutableListOf(
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_large),
                                        onClick = { appBarSize = AppBarSize.Large },
                                        selected = appBarSize == AppBarSize.Large
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_medium),
                                        onClick = { appBarSize = AppBarSize.Medium },
                                        selected = appBarSize == AppBarSize.Medium
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_small),
                                        onClick = { appBarSize = AppBarSize.Small },
                                        selected = appBarSize == AppBarSize.Small
                                    ),
                                    PillMetaData(
                                        text = LocalContext.current.resources.getString(R.string.fluentui_search),
                                        onClick = { searchMode = !searchMode },
                                        selected = searchMode
                                    )
                                ), style = style,
                                showBackground = true
                            )

                            var subtitleText =
                                LocalContext.current.resources.getString(R.string.app_bar_subtitle)
                            ListItem.Item(
                                text = subtitleText,
                                subText = if (subtitle.isNullOrBlank())
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            subtitle =
                                                if (subtitle.isNullOrBlank())
                                                    subtitleText
                                                else
                                                    null
                                        },
                                        checkedState = !subtitle.isNullOrBlank()
                                    )
                                }
                            )

                            ListItem.Item(
                                text = LocalContext.current.resources.getString(R.string.app_bar_style),
                                subText = if (style == FluentStyle.Neutral)
                                    LocalContext.current.resources.getString(R.string.fluentui_neutral)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_brand),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            style =
                                                if (style == FluentStyle.Neutral)
                                                    FluentStyle.Brand
                                                else
                                                    FluentStyle.Neutral
                                        },
                                        checkedState = style == FluentStyle.Brand
                                    )
                                }
                            )

                            ListItem.Item(
                                text = LocalContext.current.resources.getString(R.string.buttonbar),
                                subText = if (enableButtonBar)
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                trailingAccessoryView = {
                                    ToggleSwitch(
                                        onValueChange = {
                                            enableButtonBar = !enableButtonBar
                                        },
                                        checkedState = enableButtonBar
                                    )
                                }
                            )

                            ListItem.Item(
                                text = LocalContext.current.resources.getString(R.string.searchbar),
                                subText = if (enableSearchBar)
                                    LocalContext.current.resources.getString(R.string.fluentui_enabled)
                                else
                                    LocalContext.current.resources.getString(R.string.fluentui_disabled),
                                trailingAccessoryView = {
                                    enableSearchBar = enableSearchBar || searchMode
                                    ToggleSwitch(
                                        onValueChange = {
                                            enableSearchBar = !enableSearchBar
                                        },
                                        checkedState = enableSearchBar,
                                        enabledSwitch = !searchMode
                                    )
                                }
                            )
                        }
                    }

                    val buttonBarList = mutableListOf<PillMetaData>()
                    for (idx in 1..6) {
                        buttonBarList.add(
                            PillMetaData(
                                "Button $idx",
                                {
                                    Toast.makeText(
                                        context,
                                        "Button $idx pressed",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            )
                        )
                    }

                    val appTitleDelta: Float by animateFloatAsState(
                        if (searchMode) 0F else 1F,
                        animationSpec = tween(durationMillis = 150, easing = LinearEasing)
                    )

                    val yAxisDeltaCoerced = yAxisDelta.coerceIn(0F, 1F)

                    val accessoryDelta: Float by animateFloatAsState(yAxisDeltaCoerced)
                    val rightIconColor: Color = if (style == FluentStyle.Neutral)
                        FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                            FluentTheme.themeMode
                        )
                    else
                        FluentColor(
                            light = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.ForegroundOnColor].value(
                                ThemeMode.Light
                            ),
                            dark = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground2].value(
                                ThemeMode.Dark
                            )
                        ).value(FluentTheme.themeMode)

                    AppBar(
                        title = "Fluent UI Demo",
                        navigationIcon = FluentIcon(
                            SearchBarIcons.Arrowback,
                            contentDescription = "Navigate Back",
                            onClick = {
                                Toast.makeText(
                                    context,
                                    "Navigation Icon pressed",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            flipOnRtl = true
                        ),
                        subTitle = subtitle,
                        logo = {
                            Avatar(
                                Person(
                                    "Allan",
                                    "Munger",
                                    status = AvatarStatus.DND,
                                    isActive = true
                                ),
                                enablePresence = true,
                                size = AvatarSize.Size32
                            )
                        },
                        postTitleIcon = FluentIcon(
                            ListItemIcons.Chevron,
                            contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron),
                            onClick = {
                                Toast.makeText(context, "Title Icon pressed", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            flipOnRtl = true
                        ),
                        postSubtitleIcon = FluentIcon(
                            ListItemIcons.Chevron,
                            contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron),
                            onClick = {
                                Toast.makeText(context, "Subtitle Icon pressed", Toast.LENGTH_SHORT)
                                    .show()
                            },
                            flipOnRtl = true
                        ),
                        appBarSize = appBarSize,
                        style = style,
                        searchMode = searchMode,
                        searchBar = if (enableSearchBar) {
                            {
                                SearchBar(
                                    onValueChange = { _, _ -> },
                                    modifier = Modifier.onFocusChanged { focusState ->
                                        when {
                                            focusState.isFocused -> {
                                                searchMode = true
                                            }
                                        }
                                    },
                                    style = style,
                                    navigationIconCallback = { searchMode = false }
                                )
                            }
                        } else null,
                        bottomBar = if (enableButtonBar) {
                            { PillBar(metadataList = buttonBarList, style = style) }
                        } else null,
                        appTitleDelta = appTitleDelta,
                        accessoryDelta = accessoryDelta,
                        rightAccessoryView = {
                            Box(
                                Modifier
                                    .size(44.dp)
                                    .clickable(
                                        onClick = {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Navigation Icon 1 Pressed",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Add,
                                    "Add",
                                    tint = rightIconColor
                                )
                            }

                            Box(
                                Modifier
                                    .size(44.dp)
                                    .clickable(
                                        onClick = {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Navigation Icon 2 Pressed",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    Icons.Filled.Email,
                                    "E-mail",
                                    tint = rightIconColor
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}