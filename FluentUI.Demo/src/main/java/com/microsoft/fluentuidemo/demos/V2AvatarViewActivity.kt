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
import com.microsoft.fluentui.controls.Button
import com.microsoft.fluentui.persona.AvatarStyle
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.FluentTheme.controlTokens
import com.microsoft.fluentui.theme.token.ControlTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarImageNA
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.AvatarTokens
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2AvatarViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)
        val context = this

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

                    val avatarTokens = AvatarTokens()
                    controlTokens.updateTokens(ControlTokens.ControlType.Avatar, avatarTokens)

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Allan", "Munger",
                                image = R.drawable.avatar_allan_munger, isActive = isActive,
                                status = AvatarStatus.Available, isOOO = isOOO)

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

                        avatarTokens.avatarStyle = AvatarImageNA.AnonymousAccent
                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = false, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = false, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = false, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Large, enableActivityRings = false, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.XLarge, enableActivityRings = false, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.XXLarge, enableActivityRings = false, avatarToken = avatarTokens)
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Robin", "Counts",
                                isActive = isActive, status = AvatarStatus.DND, isOOO = isOOO)

                        avatarTokens.avatarStyle = AvatarImageNA.Initials
                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = true, avatarToken = avatarTokens)
                        avatarTokens.avatarStyle = AvatarImageNA.Standard
                        Avatar(person, size = AvatarSize.Large, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.XLarge, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.XXLarge, enableActivityRings = true, avatarToken = avatarTokens)
                    }

                    Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
                            verticalAlignment = Alignment.CenterVertically) {
                        val person: Person = Person("Wanda", "Howard",
                                isActive = isActive, status = AvatarStatus.Offline, isOOO = isOOO)

                        avatarTokens.avatarStyle = AvatarImageNA.StandardInverted
                        Avatar(person, size = AvatarSize.XSmall, enableActivityRings = false, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Small, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.Medium, enableActivityRings = true, avatarToken = avatarTokens)

                        avatarTokens.avatarStyle = AvatarImageNA.Anonymous

                        Avatar(person, size = AvatarSize.Large, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.XLarge, enableActivityRings = true, avatarToken = avatarTokens)
                        Avatar(person, size = AvatarSize.XXLarge, enableActivityRings = true, avatarToken = avatarTokens)
                    }

                    avatarTokens.avatarStyle = AvatarImageNA.Initials
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
                        Avatar(group, size = AvatarSize.XSmall, avatarToken = avatarTokens)
                        Avatar(group, size = AvatarSize.Small, avatarToken = avatarTokens)
                        Avatar(group, size = AvatarSize.Medium, avatarToken = avatarTokens)
                        Avatar(group, size = AvatarSize.Large, avatarToken = avatarTokens)
                        Avatar(group, size = AvatarSize.XLarge, avatarToken = avatarTokens)
                        Avatar(group, size = AvatarSize.XXLarge, avatarToken = avatarTokens)
                    }
                }
            }
        }
    }
}
