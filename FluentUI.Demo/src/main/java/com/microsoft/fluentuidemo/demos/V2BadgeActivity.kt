package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Text
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.ThemeMode
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.BadgeType
import com.microsoft.fluentui.tokenized.notification.Badge
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R


class V2BadgeActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val composeHere = findViewById<ComposeView>(R.id.compose_here)

        composeHere.setContent {
            FluentTheme {
                val title1Font =
                    FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Title1]
                val title2Font =
                    FluentTheme.aliasTokens.typography[AliasTokens.TypographyTokens.Title2]

                Column(Modifier.background(Color.Gray)) {
                    Text(
                        text = resources.getString(R.string.badge_notification_badge),
                        style = title1Font,
                        color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(),
                        modifier = Modifier.padding(8.dp)

                    )
                    Row(Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
                        Text(
                            text = resources.getString(R.string.badge_notification_dot),
                            style = title2Font,
                            color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        Badge()
                    }
                    Row(Modifier.padding(16.dp), verticalAlignment = CenterVertically) {
                        Text(
                            text = resources.getString(R.string.badge_notification_character),
                            style = title2Font,
                            color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value()
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        LazyRow {
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("1", badgeType = BadgeType.Character)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("2", badgeType = BadgeType.Character)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("8", badgeType = BadgeType.Character)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("12", badgeType = BadgeType.Character)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("123", badgeType = BadgeType.Character)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("12345678910", badgeType = BadgeType.Character)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("Badge", badgeType = BadgeType.Character)
                            }
                        }
                    }
                    Row(Modifier.padding(16.dp)) {
                        Text(
                            text = "List",
                            style = title2Font,
                            color = FluentTheme.aliasTokens.neutralForegroundColor[AliasTokens.NeutralForegroundColorTokens.Foreground1].value(
                                themeMode = ThemeMode.Auto
                            )
                        )
                        Spacer(modifier = Modifier.width(16.dp))
                        LazyRow {
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("1", badgeType = BadgeType.List)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("2", badgeType = BadgeType.List)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("8", badgeType = BadgeType.List)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("12", badgeType = BadgeType.List)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("123", badgeType = BadgeType.List)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("12345678910", badgeType = BadgeType.List)
                            }
                            item {
                                Spacer(modifier = Modifier.width(8.dp))
                                Badge("Badge", badgeType = BadgeType.List)
                            }
                        }
                    }
                }
            }
        }
    }
}