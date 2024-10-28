package com.microsoft.fluentui.tokenized

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.core.R
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
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
 * @param postTitleIcon Icon to be placed after title making the title clickable. Default: Empty [FluentIcon]
 * @param preSubtitleIcon Icon to be placed before subtitle. Default: Empty [FluentIcon]
 * @param postSubtitleIcon Icon to be placed after subtitle. Default: [ListItemIcons.Chevron]
 * @param rightAccessoryView Row Placeholder to be placed at right on AppTitle. Default: [null]
 * @param searchBar Composable to be placed as searchbar below appTitle. Default: [null]
 * @param bottomBar Composable to Be placed below appTitle. Displayed if searchbar is not provided or when in searchmode. Default: [null]
 * @param bottomBorder Boolean to place a bottom border on AppBar. Applies only when searchBar and bottomBar are empty. Default: [true]
 * @param appTitleDelta Ratio of opening of appTitle. Used for Shychrome and other animations. Default: [1.0F]
 * @param accessoryDelta Ratio of opening of accessory View. Used for Shychrome and other animations. Default: [1.0F]
 * @param centerAlignAppBar boolean indicating if the app bar should be center aligned. Default: [false]
 * @param navigationIcon Navigate Back Icon to be placed at extreme left. Default: [null]
 * @param appBarTokens Optional Tokens for App Bar to customize it. Default: [null]
 */

// TAGS FOR TESTING
const val APP_BAR = "Fluent App bar"
const val APP_BAR_SUBTITLE = "Fluent App bar Subtitle"
const val APP_BAR_BOTTOM_BAR = "Fluent App bar Bottom bar"
const val APP_BAR_SEARCH_BAR = "Fluent App bar Search bar"

@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    appBarSize: AppBarSize = AppBarSize.Medium,
    style: FluentStyle = FluentStyle.Neutral,
    subTitle: String? = null,
    logo: @Composable (() -> Unit)? = null,
    searchMode: Boolean = false,
    postTitleIcon: FluentIcon = FluentIcon(),
    preSubtitleIcon: FluentIcon = FluentIcon(),
    postSubtitleIcon: FluentIcon = FluentIcon(
        ListItemIcons.Chevron,
        contentDescription = LocalContext.current.resources.getString(R.string.fluentui_chevron)
    ),
    rightAccessoryView: @Composable (RowScope.() -> Unit)? = null,
    searchBar: @Composable (RowScope.() -> Unit)? = null,
    bottomBar: @Composable (RowScope.() -> Unit)? = null,
    bottomBorder: Boolean = true,
    appTitleDelta: Float = 1.0F,
    accessoryDelta: Float = 1.0F,
    centerAlignAppBar: Boolean = false,
    navigationIcon: FluentIcon? = null,
    appBarTokens: AppBarTokens? = null,
) {
    val themeID =
        FluentTheme.themeID    //Adding This only for recomposition in case of Token Updates. Unused otherwise.

    val token = appBarTokens
        ?: FluentTheme.controlTokens.tokens[ControlTokens.ControlType.AppBarControlType] as AppBarTokens


    val appBarInfo = AppBarInfo(style, appBarSize)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .testTag(APP_BAR)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(token.backgroundBrush(appBarInfo))
                .then(
                    if (bottomBorder && searchBar == null && bottomBar == null) {
                        val strokeWidth =
                            with(LocalDensity.current) { token.borderStroke(appBarInfo).width.toPx() }
                        val strokeColor = token.borderStroke(appBarInfo).brush
                        Modifier.drawBehind {
                            val y = size.height - strokeWidth / 2
                            drawLine(
                                strokeColor,
                                Offset(0f, y),
                                Offset(size.width, y),
                                strokeWidth
                            )
                        }
                    } else {
                        Modifier
                    }
                )
        ) {
            Row(
                Modifier
                    .requiredHeight(56.dp * appTitleDelta)
                    .animateContentSize()
                    .fillMaxWidth()
                    .scale(scaleX = 1.0F, scaleY = appTitleDelta)
                    .alpha(if (appTitleDelta != 1.0F) appTitleDelta / 3 else 1.0F),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (navigationIcon !== null && navigationIcon.isIconAvailable()) {
                    Icon(
                        navigationIcon,
                        modifier =
                        Modifier
                            .then(
                                if (navigationIcon.onClick != null)
                                    Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = rememberRipple(color = token.navigationIconRippleColor()),
                                        enabled = true,
                                        onClick = navigationIcon.onClick ?: {}
                                    )
                                else Modifier
                            )
                            .padding(token.navigationIconPadding(appBarInfo))
                            .size(token.leftIconSize(appBarInfo)),
                        tint = token.navigationIconColor(appBarInfo)
                    )
                }

                logo?.invoke()

                val titleTextStyle = token.titleTypography(appBarInfo)
                val subtitleTextStyle = token.subtitleTypography(appBarInfo)
                val titleAlignment: Alignment.Horizontal =
                    if (centerAlignAppBar) Alignment.CenterHorizontally else Alignment.Start

                if (appBarSize != AppBarSize.Large && !subTitle.isNullOrBlank()) {
                    Column(
                        modifier = Modifier
                            .weight(1F)
                            .padding(token.textPadding(appBarInfo))
                            .testTag(APP_BAR_SUBTITLE),
                        horizontalAlignment = titleAlignment
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
                                        color = token.titleTextColor(appBarInfo)
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
                                        )
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
                    Column(
                        modifier = Modifier
                            .padding(token.textPadding(appBarInfo))
                            .weight(1F)
                            .semantics { heading() },
                        horizontalAlignment = titleAlignment
                    ) {

                        BasicText(
                            text = title,
                            style = titleTextStyle.merge(
                                TextStyle(
                                    color = token.titleTextColor(appBarInfo)
                                )
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                    }
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
                        .padding(vertical = 8.dp)
                        .testTag(APP_BAR_SEARCH_BAR),
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
                        .testTag(APP_BAR_BOTTOM_BAR),
                ) {
                    bottomBar()
                }
            }
        }
    }
}
