package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.theme.token.AnonymousAccentAvatarTokens
import com.example.theme.token.AnonymousAvatarTokens
import com.example.theme.token.StandardInvertedAvatarTokens
import com.microsoft.fluentui.theme.FluentTheme.aliasTokens
import com.microsoft.fluentui.theme.token.FluentAliasTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarGroupStyle
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.persona.AvatarGroup
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

class V2AvatarGroupActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-3"
    override val controlTokensUrl =
        "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-3"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            Column(
                Modifier.background(Color.LightGray),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {
                var isActive by rememberSaveable { mutableStateOf(false) }
                var enablePresence by rememberSaveable { mutableStateOf(true) }
                var maxVisibleAvatar by rememberSaveable { mutableStateOf(1) }
                var enableActivityDot by rememberSaveable { mutableStateOf(false) }

                val group = Group(
                    listOf(
                        Person(
                            "Allan", "Munger",
                            image = R.drawable.avatar_allan_munger,
                            isActive = isActive
                        ),
                        Person(
                            "Amanda", "Brady",
                            isActive = !isActive, status = AvatarStatus.Offline
                        ),
                        Person(
                            "", "",
                            isActive = isActive, status = AvatarStatus.DND, isOOO = true
                        ),
                        Person(
                            "Carlos", "Slathery",
                            isActive = !isActive, status = AvatarStatus.Busy, isOOO = true
                        ),
                        Person(
                            "Celeste", "Burton",
                            image = R.drawable.avatar_celeste_burton,
                            isActive = isActive, status = AvatarStatus.Away
                        ),
                        Person(
                            "", "",
                            isActive = isActive, status = AvatarStatus.Unknown
                        ),
                        Person(
                            "Miguel", "Garcia",
                            image = R.drawable.avatar_miguel_garcia,
                            isActive = isActive, status = AvatarStatus.Blocked
                        )
                    ), "Fluent UI Android"
                )

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { if (maxVisibleAvatar > 0) maxVisibleAvatar-- },
                        enabled = (maxVisibleAvatar > 0),
                        text = "-",
                        contentDescription = "Max Visible Avatar $maxVisibleAvatar"
                    )
                    BasicText("$maxVisibleAvatar")
                    Button(
                        onClick = { maxVisibleAvatar++ },
                        enabled = (maxVisibleAvatar < group.members.size),
                        text = "+",
                        contentDescription = "Max Visible Avatar $maxVisibleAvatar"
                    )
                    Button(
                        onClick = { enableActivityDot = !enableActivityDot },
                        text = "Show Activity Dot",
                        contentDescription = "Activity Dot ${if (enableActivityDot) "Enabled" else "Disabled"}"
                    )
                }

                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(5.dp), horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { isActive = !isActive },
                        text = "Swap Active State",
                        contentDescription = "Active Status ${if (isActive) "Active" else "Inactive"}"
                    )
                    Button(
                        onClick = { enablePresence = !enablePresence },
                        text = "Toggle Presence",
                        contentDescription = "Presence Status ${if (enablePresence) "Enabled" else "Disabled"}"
                    )
                }

                Divider()

                LazyColumn(modifier = Modifier.fillMaxWidth()) {
                    item {
                        Row(horizontalArrangement = Arrangement.Center) {
                            BasicText(
                                "Stack Group Style",
                                style = aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]
                            )
                        }
                    }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            item {
                                BasicText("Size 16: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size16,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 20: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size20,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = AnonymousAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 24: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size24,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = AnonymousAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 32: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size32,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 40: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size40,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = AnonymousAccentAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 56: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size56,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 72: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size72,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = StandardInvertedAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.Center) {
                            BasicText(
                                "Pile Group Style",
                                style = aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]
                            )
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 16: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size16,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 20: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size20,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = AnonymousAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 24: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size24,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = AnonymousAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 32: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size32,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 40: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size40,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = AnonymousAccentAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 56: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size56,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item { BasicText("Size 72: ") }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size72,
                                    style = AvatarGroupStyle.Pile,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enablePresence = enablePresence,
                                    avatarToken = StandardInvertedAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }

                    item {
                        Row(horizontalArrangement = Arrangement.Center) {
                            BasicText(
                                "Pie Group Style",
                                style = aliasTokens.typography[FluentAliasTokens.TypographyTokens.Title2]
                            )
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 16: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size16,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 20: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size20,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    avatarToken = AnonymousAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 24: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size24,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    avatarToken = AnonymousAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 32: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size32,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 40: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size40,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    avatarToken = AnonymousAccentAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item {
                                BasicText("Size 56: ")
                            }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size56,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                    item {
                        LazyRow(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            item { BasicText("Size 72: ") }
                            item {
                                AvatarGroup(
                                    group,
                                    size = AvatarSize.Size72,
                                    style = AvatarGroupStyle.Pie,
                                    maxVisibleAvatar = maxVisibleAvatar,
                                    avatarToken = StandardInvertedAvatarTokens(),
                                    enableActivityDot = enableActivityDot
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

