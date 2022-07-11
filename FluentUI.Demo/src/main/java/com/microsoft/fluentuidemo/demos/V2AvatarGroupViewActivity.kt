package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.controls.Button
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.AliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarGroupStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarImageNA
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenised.persona.AvatarGroup
import com.microsoft.fluentui.tokenised.persona.Group
import com.microsoft.fluentui.tokenised.persona.Person
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2AvatarGroupViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)
        val context = this

        compose_here.setContent {
            Column(Modifier.background(Color.LightGray), verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)) {
                var isActive by rememberSaveable { mutableStateOf(false) }
                var maxAvatar by rememberSaveable { mutableStateOf(1) }

                val group = Group(
                        listOf(
                                Person("Allan", "Munger",
                                        image = R.drawable.avatar_allan_munger,
                                        isActive = isActive),
                                Person("Amanda", "Brady",
                                        isActive = !isActive, status = AvatarStatus.Offline),
                                Person("Ashley", "McCarthy",
                                        image = R.drawable.avatar_ashley_mccarthy,
                                        isActive = isActive, status = AvatarStatus.DND, isOOO = true),
                                Person("Carlos", "Slathery",
                                        isActive = !isActive, status = AvatarStatus.Busy, isOOO = true),
                                Person("Celeste", "Burton",
                                        image = R.drawable.avatar_celeste_burton,
                                        isActive = isActive, status = AvatarStatus.Away),
                                Person("Tim", "Deboer",
                                        image = R.drawable.avatar_tim_deboer,
                                        isActive = isActive, status = AvatarStatus.Unknown),
                                Person("Miguel", "Garcia",
                                        image = R.drawable.avatar_miguel_garcia,
                                        isActive = isActive, status = AvatarStatus.Blocked)
                        ), "FluentUI Android"
                )

                Row(Modifier
                        .fillMaxWidth()
                        .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically) {
                    Button(onClick = { if (maxAvatar > 0) maxAvatar-- }, text = "-")
                    Text("${maxAvatar}")
                    Button(onClick = { maxAvatar++ }, text = "+")

                    Button(onClick = { isActive = !isActive }, text = "Swap Active State")
                }

                Divider()

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text("Stack Group Style",
                                    fontSize = aliasTokens.typography[AliasTokens.TypographyTokens.Title2].fontSize.size)
                        }
                    }
                    item {
                        LazyRow(horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically) {
                            item {
                                Text("XSmall: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.XSmall, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("Small: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.Small, AvatarImageNA.StandardInverted, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("Medium: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.Medium, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("Large: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.Large, AvatarImageNA.Anonymous, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("XLarge: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.XLarge, AvatarImageNA.Initials, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("XXLarge: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.XXLarge, AvatarImageNA.AnonymousAccent, maxAvatar = maxAvatar)
                            }
                        }
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text("Pile Group Style",
                                    fontSize = aliasTokens.typography[AliasTokens.TypographyTokens.Title2].fontSize.size)
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("XSmall: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.XSmall, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("Small: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.Small, AvatarImageNA.StandardInverted, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("Medium: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.Medium, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("Large: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.Large, AvatarImageNA.Anonymous, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item {
                                Text("XLarge: ")
                            }
                            item {
                                AvatarGroup(group, AvatarSize.XLarge, AvatarImageNA.Initials, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar)
                            }
                        }
                    }
                    item {
                        LazyRow(verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.fillMaxSize()) {
                            item { Text("XXLarge: ") }
                            item { AvatarGroup(group, AvatarSize.XXLarge, AvatarImageNA.AnonymousAccent, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar) }
                        }
                    }
                }
//                AvatarGroup(group, AvatarSize.XXLarge, AvatarImageNA.AnonymousAccent, style = AvatarGroupStyle.Pile, maxAvatar = maxAvatar)
//                AvatarGroup(group, AvatarSize.XXLarge, AvatarImageNA.AnonymousAccent, style = AvatarGroupStyle.Stack, maxAvatar = maxAvatar)
            }
        }
    }
}
