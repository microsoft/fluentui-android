package com.microsoft.fluentui.tokenized

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.core.R
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.*
import com.microsoft.fluentui.theme.token.controlTokens.AppBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.AppBarTokens

/**
 * An app bar appears at the top of an app screen, below the status bar,
 * and enables navigation through a series of hierarchical screens.
 * When a new screen is displayed, a back button, often labeled with the title of
 * the previous screen, appears on the left side of the bar. Sometimes, the right side
 * of a navigation bar contains a control, like an Edit or a Done button,
 * for managing the content within the active view. In a split view,
 * a navigation bar may appear in a single pane of the split view.
 * Navigation bars are translucent, may have a background tint, and can be configured
 * to hide when the keyboard is onscreen, a gesture occurs, or a view resizes.
 *
 * @param title Title Of the current page
 * @param modifier Optional Modifier for updating appbar
 * @param appBarSize Enum to define App Bar Size. Default: [AppBarSize.Medium]
 * @param style Fluent Style of AppBar. Default: [FluentStyle.Neutral]
 * @param subTitle Subtitle to be displayed. Default: [null]
 * @param logo Composable to be placed at left of Title. Guideline is to not increase a size of 32x32. Default: [null]
 * @param searchMode Boolean to enable/disable searchMode. Default: [false]
 * @param navigationIcon Navigate Back Icon to be placed at extreme left. Default: [SearchBarIcons.Arrowback]
 * @param postTitleIcon Icon to be placed after title making the title clickable. Default: Empty [FluentIcon]
 * @param preSubtitleIcon Icon to be placed before subtitle. Default: Empty [FluentIcon]
 * @param postSubtitleIcon Icon to be placed after subtitle. Default: [ListItemIcons.Chevron]
 * @param rightAccessoryView Row Placeholder to be placed at right on AppTitle. Default: [null]
 * @param searchBar Composable to be placed as searchbar below appTitle. Default: [null]
 * @param bottomBar Composable to Be placed below appTitle. Displayed if searchbar is not provided or when in searchmode. Default: [null]
 * @param appTitleDelta Ratio of opening of appTitle. Used for Shychrome and other animations. Default: [1.0F]
 * @param accessoryDelta Ratio of opening of accessory View. Used for Shychrome and other animations. Default: [1.0F]
 * @param appBarTokens Optional Tokens for App Bar to customize it. Default: [null]
 */
@OptIn(ExperimentalTextApi::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    appBarSize: AppBarSize = AppBarSize.Medium,
    style: FluentStyle = FluentStyle.Neutral,
    subTitle: String? = null,
    logo: @Composable (() -> Unit)? = null,
    searchMode: Boolean = false,
    navigationIcon: FluentIcon = FluentIcon(SearchBarIcons.Arrowback, flipOnRtl = true),
    postTitleIcon: FluentIcon = FluentIcon(),
    preSubtitleIcon: FluentIcon = FluentIcon(),
    postSubtitleIcon: FluentIcon = FluentIcon(
        ListItemIcons.Chevron,
        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron)
    ),
    rightAccessoryView: @Composable (RowScope.() -> Unit)? = null,
    searchBar: @Composable (RowScope.() -> Unit)? = null,
    bottomBar: @Composable (RowScope.() -> Unit)? = null,
    appTitleDelta: Float = 1.0F,
    accessoryDelta: Float = 1.0F,
    appBarTokens: AppBarTokens? = null
) {
    val token = appBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AppBar] as AppBarTokens

    val appBarInfo = AppBarInfo(style, appBarSize)
    Surface(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(token.backgroundColor(appBarInfo))
        ) {
            Row(
                Modifier
                    .requiredHeight(56.dp * appTitleDelta)
                    .animateContentSize()
                    .fillMaxWidth()
                    .scale(scaleX = 1.0F, scaleY = appTitleDelta)
                    .alpha(if (appTitleDelta != 1.0F) appTitleDelta / 3 else 1.0F),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (appBarSize != AppBarSize.Large && navigationIcon.isIconAvailable()) {
                    Icon(
                        navigationIcon,
                        modifier = Modifier
                            .padding(token.navigationIconPadding(appBarInfo))
                            .size(token.leftIconSize(appBarInfo)),
                        tint = token.navigationIconColor(appBarInfo)
                    )
                }

                if (appBarSize != AppBarSize.Medium) {
                    Box(
                        modifier = Modifier
                            .then(
                                if (appBarSize == AppBarSize.Large)
                                    Modifier.padding(start = 16.dp)
                                else
                                    Modifier
                            )
                    ) {
                        logo?.invoke()
                    }
                }

                val titleTextStyle = token.titleTypography(appBarInfo)
                val subtitleTextStyle = token.subtitleTypography(appBarInfo)

                if (appBarSize != AppBarSize.Large && !subTitle.isNullOrBlank()) {
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .padding(token.textPadding(appBarInfo))
                    ) {
                        Row(
                            modifier = Modifier
                                .then(
                                    if (postTitleIcon.onClick != null && appBarSize == AppBarSize.Small)
                                        Modifier.clickable(onClick = postTitleIcon.onClick!!)
                                    else
                                        Modifier
                                ), verticalAlignment = Alignment.CenterVertically
                        ) {
                            BasicText(
                                text = title,
                                style = titleTextStyle.merge(
                                    TextStyle(
                                        color = token.titleTextColor(appBarInfo),
                                        platformStyle = PlatformTextStyle(includeFontPadding = true)
                                    )
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (postTitleIcon.isIconAvailable() && appBarSize == AppBarSize.Small)
                                Icon(
                                    postTitleIcon.value(),
                                    postTitleIcon.contentDescription,
                                    modifier = Modifier
                                        .size(token.titleIconSize(appBarInfo)),
                                    tint = token.titleIconColor(appBarInfo),
                                )
                        }
                        Row(
                            modifier = Modifier
                                .then(
                                    if (postSubtitleIcon.onClick != null)
                                        Modifier.clickable(onClick = postSubtitleIcon.onClick!!)
                                    else
                                        Modifier
                                ), verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (preSubtitleIcon.isIconAvailable())
                                Icon(
                                    preSubtitleIcon,
                                    modifier = Modifier
                                        .size(
                                            token.subtitleIconSize(
                                                appBarInfo
                                            )
                                        ),
                                    tint = token.subtitleIconColor(appBarInfo)
                                )
                            BasicText(
                                subTitle,
                                style = subtitleTextStyle.merge(
                                    TextStyle(
                                        color = token.subtitleTextColor(
                                            appBarInfo
                                        ),
                                        platformStyle = PlatformTextStyle(includeFontPadding = true)
                                    )
                                ),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            if (postSubtitleIcon.isIconAvailable())
                                Icon(
                                    postSubtitleIcon.value(),
                                    contentDescription = postSubtitleIcon.contentDescription,
                                    modifier = Modifier
                                        .size(
                                            token.subtitleIconSize(
                                                appBarInfo
                                            )
                                        ),
                                    tint = token.subtitleIconColor(appBarInfo)
                                )
                        }
                    }
                } else {
                    BasicText(
                        text = title,
                        modifier = Modifier
                            .padding(token.textPadding(appBarInfo))
                            .weight(1F),
                        style = titleTextStyle.merge(
                            TextStyle(
                                color = token.titleTextColor(appBarInfo),
                                platformStyle = PlatformTextStyle(
                                    includeFontPadding = true
                                )
                            )
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                if (rightAccessoryView != null) {
                    rightAccessoryView()
                }
            }

            if (searchBar != null) {
                Row(
                    modifier
                        .animateContentSize()
                        .fillMaxWidth()
                        .then(if (!searchMode) Modifier.height(56.dp * accessoryDelta) else Modifier)
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    searchBar()
                }
            }
            if (bottomBar != null && (searchMode || searchBar == null)) {
                Row(
                    Modifier
                        .animateContentSize()
                        .fillMaxWidth()
                        .then(if (!searchMode) Modifier.height(48.dp * accessoryDelta) else Modifier)
                        .padding(vertical = 8.dp)
                ) {
                    bottomBar()
                }
            }
        }
    }
}