package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.AppBarSize
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

const val BADGE_DOT = "Dot Badge"
const val BADGE_CHARACTER = "Character Badge"
const val BADGE_LIST = "List Badge"

class V2BadgeActivity : V2DemoActivity() {
    override val appBarSize = AppBarSize.Medium

    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-4"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            val title1Font =
                FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title1]
            val title2Font =
                FluentTheme.aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]

            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Gray)
            ) {
                BasicText(
                    text = resources.getString(R.string.badge_notification_badge),
                    style = title1Font.merge(TextStyle(color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value())),
                    modifier = Modifier.padding(8.dp)
                )
                Row(Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
                    BasicText(
                        text = resources.getString(R.string.badge_notification_dot),
                        style = title2Font.merge(TextStyle(color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Badge(modifier = Modifier.testTag(BADGE_DOT))
                }
                Row(Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
                    BasicText(
                        text = resources.getString(R.string.badge_notification_character),
                        style = title2Font.merge(TextStyle(color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value()))
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    LazyRow {
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(
                                text = "1",
                                badgeType = BadgeType.Character,
                                modifier = Modifier.testTag(BADGE_CHARACTER)
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "2", badgeType = BadgeType.Character)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "8", badgeType = BadgeType.Character)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "12", badgeType = BadgeType.Character)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "123", badgeType = BadgeType.Character)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "12345678910", badgeType = BadgeType.Character)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "Badge", badgeType = BadgeType.Character)
                        }
                    }
                }
                Row(Modifier.padding(16.dp)) {
                    BasicText(
                        text = "List",
                        style = title2Font.merge(
                            TextStyle(
                                color = FluentTheme.aliasTokens.neutralForegroundColor[FluentAliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                    themeMode = FluentTheme.themeMode
                                )
                            )
                        )
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    LazyRow {
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(
                                text = "1",
                                badgeType = BadgeType.List,
                                modifier = Modifier.testTag(BADGE_LIST)
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "2", badgeType = BadgeType.List)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "8", badgeType = BadgeType.List)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "12", badgeType = BadgeType.List)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "123", badgeType = BadgeType.List)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "12345678910", badgeType = BadgeType.List)
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                            Badge(text = "Badge", badgeType = BadgeType.List)
                        }
                    }
                }
            }
        }
    }
}