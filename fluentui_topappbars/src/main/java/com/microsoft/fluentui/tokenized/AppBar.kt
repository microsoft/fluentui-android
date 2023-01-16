package com.microsoft.fluentui.tokenized

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextGeometricTransform
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.core.R
import com.microsoft.fluentui.icons.ListItemIcons
import com.microsoft.fluentui.icons.SearchBarIcons
import com.microsoft.fluentui.icons.listitemicons.Chevron
import com.microsoft.fluentui.icons.searchbaricons.Arrowback
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.themeMode
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.FluentIcon
import com.microsoft.fluentui.theme.token.FluentStyle
import com.microsoft.fluentui.theme.token.controlTokens.AppBarInfo
import com.microsoft.fluentui.theme.token.controlTokens.AppBarStyle
import com.microsoft.fluentui.theme.token.controlTokens.AppBarTokens

private val LocalAppBarTokens = compositionLocalOf { AppBarTokens() }
private val LocalAppBarInfo = compositionLocalOf { AppBarInfo(FluentStyle.Neutral) }

@OptIn(ExperimentalTextApi::class)
@Composable
fun AppBar(
    title: String,
    modifier: Modifier = Modifier,
    appBarStyle: AppBarStyle = AppBarStyle.Medium,
    style: FluentStyle = FluentStyle.Neutral,
    subTitle: String? = null,
    logo: @Composable (() -> Unit)? = null,
    searchMode: Boolean = false,
    navigationIcon: FluentIcon = FluentIcon(SearchBarIcons.Arrowback),
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

    CompositionLocalProvider(
        LocalAppBarTokens provides token,
        LocalAppBarInfo provides AppBarInfo(style, appBarStyle)
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .background(getAppBarTokens().backgroundColor(getAppBarInfo()))
            ) {
                Row(
                    modifier
                        .requiredHeight(56.dp * appTitleDelta)
                        .animateContentSize()
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .scale(scaleX = 1.0F, scaleY = appTitleDelta)
                        .alpha(if (appTitleDelta != 1.0F) appTitleDelta / 3 else 1.0F),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (appBarStyle != AppBarStyle.Large && navigationIcon.isAvailable()) {
                        Box(modifier = Modifier
                            .then(
                                if (navigationIcon.onClick != null) {
                                    Modifier.clickable(
                                        role = Role.Button,
                                        onClick = navigationIcon.onClick!!
                                    )
                                } else
                                    Modifier
                            ), contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                navigationIcon.value(themeMode = themeMode),
                                navigationIcon.contentDescription,
                                modifier = Modifier
                                    .padding(getAppBarTokens().navigationIconPadding(getAppBarInfo()))
                                    .size(getAppBarTokens().leftIconSize(getAppBarInfo()).size),
                                tint = getAppBarTokens().navigationIconColor(getAppBarInfo())
                            )
                        }
                    }

                    if(appBarStyle != AppBarStyle.Medium)
                        logo?.invoke()

                    val titleFontInfo = getAppBarTokens().titleTypography(getAppBarInfo())
                    val subtitleFontInfo =
                        getAppBarTokens().subtitleTypography(getAppBarInfo())

                    if (appBarStyle != AppBarStyle.Large && !subTitle.isNullOrBlank()) {
                        Column(
                            modifier = Modifier
                                .weight(1F)
                                .padding(getAppBarTokens().logoPadding(getAppBarInfo()))
                        ) {
                            Row(modifier = Modifier
                                .then(
                                    if(postTitleIcon.onClick != null && appBarStyle == AppBarStyle.Small)
                                        Modifier.clickable(onClick = postTitleIcon.onClick!!)
                                    else
                                        Modifier
                                ), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = title,
                                    style = TextStyle(
                                        color = getAppBarTokens().titleTextColor(getAppBarInfo()),
                                        fontSize = titleFontInfo.fontSize.size,
                                        lineHeight = titleFontInfo.fontSize.lineHeight,
                                        fontWeight = titleFontInfo.weight,
                                        platformStyle = PlatformTextStyle(
                                            includeFontPadding = false
                                        )
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (postTitleIcon.isAvailable() && appBarStyle == AppBarStyle.Small)
                                    Icon(
                                        postTitleIcon.value(themeMode),
                                        postTitleIcon.contentDescription,
                                        modifier = Modifier
                                            .size(getAppBarTokens().titleIconSize(getAppBarInfo()).size),
                                        tint = getAppBarTokens().titleIconColor(getAppBarInfo())
                                    )
                            }
                            Row(modifier = Modifier
                                .then(
                                    if(postSubtitleIcon.onClick != null)
                                        Modifier.clickable(onClick = postSubtitleIcon.onClick!!)
                                    else
                                        Modifier
                                ), verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (preSubtitleIcon.isAvailable())
                                    Icon(
                                        preSubtitleIcon.value(themeMode),
                                        preSubtitleIcon.contentDescription,
                                        modifier = Modifier
                                            .size(
                                                getAppBarTokens().subtitleIconSize(
                                                    getAppBarInfo()
                                                ).size
                                            ),
                                        tint = getAppBarTokens().subtitleIconColor(getAppBarInfo())
                                    )
                                Text(
                                    subTitle,
                                    style = TextStyle(
                                        color = getAppBarTokens().subtitleTextColor(
                                            getAppBarInfo()
                                        ),
                                        fontSize = subtitleFontInfo.fontSize.size,
                                        lineHeight = subtitleFontInfo.fontSize.lineHeight,
                                        fontWeight = subtitleFontInfo.weight
                                    ),
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                if (postSubtitleIcon.isAvailable())
                                    Icon(
                                        postSubtitleIcon.value(themeMode),
                                        postSubtitleIcon.contentDescription,
                                        modifier = Modifier
                                            .size(
                                                getAppBarTokens().subtitleIconSize(
                                                    getAppBarInfo()
                                                ).size
                                            ),
                                        tint = getAppBarTokens().subtitleIconColor(getAppBarInfo())
                                    )
                            }
                        }
                    } else {
                        Text(
                            text = title,
                            modifier = Modifier.padding(getAppBarTokens().logoPadding(getAppBarInfo())),
                            style = TextStyle(
                                color = getAppBarTokens().titleTextColor(getAppBarInfo()),
                                fontSize = titleFontInfo.fontSize.size,
                                lineHeight = titleFontInfo.fontSize.lineHeight,
                                fontWeight = titleFontInfo.weight,
                                textAlign = TextAlign.Center
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
                            .height(56.dp * accessoryDelta)
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
                            .height(48.dp * accessoryDelta)
                            .padding(vertical = 8.dp)
                    ) {
                        bottomBar()
                    }
                }
            }
        }
    }
}

@Composable
private fun getAppBarTokens(): AppBarTokens {
    return LocalAppBarTokens.current
}

@Composable
private fun getAppBarInfo(): AppBarInfo {
    return LocalAppBarInfo.current
}