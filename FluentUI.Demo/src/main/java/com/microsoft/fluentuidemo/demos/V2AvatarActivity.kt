package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.example.theme.token.AnonymousAccentAvatarTokens
import com.example.theme.token.AnonymousAvatarTokens
import com.example.theme.token.StandardInvertedAvatarTokens
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2AvatarActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                Column(Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)) {
                    var isActive by rememberSaveable { mutableStateOf(true) }
                    var isOOO by rememberSaveable { mutableStateOf(false) }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)) {
                        Button(onClick = { isActive = !isActive }, text = "Toggle Activity")
                        Button(onClick = { isOOO = !isOOO }, text = "Toggle OOO")
                    }

                    Divider()

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Allan", "Munger",
                                image = R.drawable.avatar_allan_munger, isActive = isActive,
                                status = AvatarStatus.Available, isOOO = isOOO)
                        val personNoImage: Person = Person("Allan", "Munger",
                                isActive = isActive,
                                status = AvatarStatus.Available, isOOO = isOOO)

                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = true)

                        Avatar(personNoImage, size = AvatarSize.Large, enableActivityRings = true, avatarToken = AnonymousAvatarTokens())
                        Avatar(person, size = AvatarSize.XLarge, enableActivityRings = true, avatarToken = AnonymousAvatarTokens())
                        Avatar(personNoImage, size = AvatarSize.XXLarge, enableActivityRings = true, avatarToken = AnonymousAvatarTokens())
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Amanda", "Brady",
                                image = R.drawable.avatar_amanda_brady, isActive = isActive,
                                status = AvatarStatus.Away, isOOO = isOOO)

                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Large, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.XLarge, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.XXLarge, enableActivityRings = true)
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Kat", "Larson", isActive = isActive,
                                status = AvatarStatus.Busy, isOOO = isOOO)

                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = false, avatarToken = AnonymousAccentAvatarTokens())
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = false, avatarToken = AnonymousAccentAvatarTokens())
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = false, avatarToken = AnonymousAccentAvatarTokens())
                        Avatar(person, size = AvatarSize.Large, enableActivityRings = false, avatarToken = AnonymousAccentAvatarTokens())
                        Avatar(person, size = AvatarSize.XLarge, enableActivityRings = false, avatarToken = AnonymousAccentAvatarTokens())
                        Avatar(person, size = AvatarSize.XXLarge, enableActivityRings = false, avatarToken = AnonymousAccentAvatarTokens())
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Robin", "Counts",
                                isActive = isActive, status = AvatarStatus.DND, isOOO = isOOO)

                        val personNoInitial: Person = Person("123", "456",
                                isActive = isActive, status = AvatarStatus.DND, isOOO = isOOO)


                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = true)
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = true)

                        Avatar(personNoInitial, size = AvatarSize.Large, enableActivityRings = true, avatarToken = StandardInvertedAvatarTokens())
                        Avatar(personNoInitial, size = AvatarSize.XLarge, enableActivityRings = true, avatarToken = StandardInvertedAvatarTokens())
                        Avatar(personNoInitial, size = AvatarSize.XXLarge, enableActivityRings = true, avatarToken = StandardInvertedAvatarTokens())
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Wanda", "Howard",
                                isActive = isActive, status = AvatarStatus.Offline, isOOO = isOOO)
                        val personNoName: Person = Person("", "",
                                isActive = isActive, status = AvatarStatus.Offline, isOOO = isOOO)

                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = false, avatarToken = StandardInvertedAvatarTokens())
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = true, avatarToken = StandardInvertedAvatarTokens())
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = true, avatarToken = StandardInvertedAvatarTokens())

                        Avatar(personNoName, size = AvatarSize.Large, enableActivityRings = false, avatarToken = AnonymousAvatarTokens())
                        Avatar(personNoName, size = AvatarSize.XLarge, enableActivityRings = true, avatarToken = AnonymousAvatarTokens())
                        Avatar(personNoName, size = AvatarSize.XXLarge, enableActivityRings = true, avatarToken = AnonymousAvatarTokens())
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val group: Group = Group(listOf(
                                Person("Allan", "Munger",
                                        image = R.drawable.avatar_allan_munger, isActive = isActive,
                                        status = AvatarStatus.Available, isOOO = isOOO),
                                Person("Wanda", "Howard",
                                        image = R.drawable.avatar_wanda_howard,
                                        status = AvatarStatus.Busy, isOOO = isOOO),
                                Person("Kat", "Larson",
                                        status = AvatarStatus.Busy, isOOO = isOOO),
                                Person("Amanda", "Brady",
                                        image = R.drawable.avatar_amanda_brady, isActive = isActive,
                                        status = AvatarStatus.Away, isOOO = isOOO)
                        ), "Gang Of 4")

                        val groupNoName: Group = Group(listOf(
                                Person("Allan", "Munger",
                                        image = R.drawable.avatar_allan_munger, isActive = isActive,
                                        status = AvatarStatus.Available, isOOO = isOOO),
                                Person("Wanda", "Howard",
                                        image = R.drawable.avatar_wanda_howard,
                                        status = AvatarStatus.Busy, isOOO = isOOO),
                                Person("Kat", "Larson",
                                        status = AvatarStatus.Busy, isOOO = isOOO),
                                Person("Amanda", "Brady",
                                        image = R.drawable.avatar_amanda_brady, isActive = isActive,
                                        status = AvatarStatus.Away, isOOO = isOOO)
                        ), "")

                        Avatar(group, size = AvatarSize.XSmall)
                        Avatar(group, size = AvatarSize.Small, avatarToken = StandardInvertedAvatarTokens())
                        Avatar(group, size = AvatarSize.Medium, avatarToken = AnonymousAvatarTokens())

                        Avatar(groupNoName, size = AvatarSize.Large, avatarToken = AnonymousAccentAvatarTokens())
                        Avatar(group, size = AvatarSize.XLarge)
                        Avatar(groupNoName, size = AvatarSize.XXLarge, avatarToken = StandardInvertedAvatarTokens())
                    }
                }
            }
        }
    }
}
