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
import com.microsoft.fluentui.theme.token.controlTokens.AvatarImageNA
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenised.persona.Avatar
import com.microsoft.fluentui.tokenised.persona.Group
import com.microsoft.fluentui.tokenised.persona.Person
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
            Column(Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)) {
                var isActive by rememberSaveable { mutableStateOf(true) }
                var isOOO by rememberSaveable { mutableStateOf(false) }

                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally)) {
                    Button(onClick = { isActive = !isActive }, text = "Toggle Activity")
                    Button(onClick = {isOOO = !isOOO}, text = "Toggle OOO")
                }

                Divider()

                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically) {
                    val person: Person = Person("Allan", "Munger",
                            image = R.drawable.avatar_allan_munger, isActive = isActive,
                            status = AvatarStatus.Available, isOOO = isOOO)
                    Avatar(person, AvatarSize.XSmall, enableActivityRings = true)
                    Avatar(person, AvatarSize.Small, enableActivityRings = true)
                    Avatar(person, AvatarSize.Medium, enableActivityRings = true)
                    Avatar(person, AvatarSize.Large, enableActivityRings = true)
                    Avatar(person, AvatarSize.XLarge, enableActivityRings = true)
                    Avatar(person, AvatarSize.XXLarge, enableActivityRings = true)
                }

                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically) {
                    val person: Person = Person("Amanda", "Brady",
                            image = R.drawable.avatar_amanda_brady, isActive = isActive,
                            status = AvatarStatus.Away, isOOO = isOOO)
                    Avatar(person, AvatarSize.XSmall, enableActivityRings = true)
                    Avatar(person, AvatarSize.Small, enableActivityRings = true)
                    Avatar(person, AvatarSize.Medium, enableActivityRings = true)
                    Avatar(person, AvatarSize.Large, enableActivityRings = true)
                    Avatar(person, AvatarSize.XLarge, enableActivityRings = true)
                    Avatar(person, AvatarSize.XXLarge, enableActivityRings = true)
                }

                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically) {
                    val person: Person = Person("Kat", "Larson", isActive = isActive,
                            status = AvatarStatus.Busy, isOOO = isOOO)
                    Avatar(person, AvatarSize.XSmall, enableActivityRings = false)
                    Avatar(person, AvatarSize.Small, imageNAStyle = AvatarImageNA.AnonymousAccent, enableActivityRings = false)
                    Avatar(person, AvatarSize.Medium, imageNAStyle = AvatarImageNA.Standard, enableActivityRings = false)
                    Avatar(person, AvatarSize.Large, imageNAStyle = AvatarImageNA.StandardInverted, enableActivityRings = false)
                    Avatar(person, AvatarSize.XLarge, imageNAStyle = AvatarImageNA.Initials, enableActivityRings = false)
                    Avatar(person, AvatarSize.XXLarge, imageNAStyle = AvatarImageNA.Anonymous, enableActivityRings = false)
                }

                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically) {
                    val person: Person = Person("Robin", "Counts",
                            image = R.drawable.avatar_robin_counts, isActive = isActive,
                            status = AvatarStatus.DND, isOOO = isOOO)
                    Avatar(person, AvatarSize.XSmall, enableActivityRings = true)
                    Avatar(person, AvatarSize.Small, enableActivityRings = true)
                    Avatar(person, AvatarSize.Medium, enableActivityRings = true)
                    Avatar(person, AvatarSize.Large, enableActivityRings = true)
                    Avatar(person, AvatarSize.XLarge, enableActivityRings = true)
                    Avatar(person, AvatarSize.XXLarge, enableActivityRings = true)
                }

                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
                        verticalAlignment = Alignment.CenterVertically) {
                    val person: Person = Person("Wanda", "Howard",
                            image = R.drawable.avatar_wanda_howard, isActive = isActive,
                            status = AvatarStatus.Offline, isOOO = isOOO)
                    Avatar(person, AvatarSize.XSmall, enableActivityRings = false)
                    Avatar(person, AvatarSize.Small, imageNAStyle = AvatarImageNA.AnonymousAccent, enableActivityRings = true)
                    Avatar(person, AvatarSize.Medium, imageNAStyle = AvatarImageNA.Standard ,enableActivityRings = true)
                    Avatar(person, AvatarSize.Large, imageNAStyle = AvatarImageNA.StandardInverted ,enableActivityRings = true)
                    Avatar(person, AvatarSize.XLarge, imageNAStyle = AvatarImageNA.Initials ,enableActivityRings = true)
                    Avatar(person, AvatarSize.XXLarge, imageNAStyle = AvatarImageNA.Anonymous, enableActivityRings = true)
                }

                Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterHorizontally),
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
                    Avatar(group, AvatarSize.XSmall)
                    Avatar(group, AvatarSize.Small, imageNAStyle = AvatarImageNA.AnonymousAccent)
                    Avatar(group, AvatarSize.Medium, imageNAStyle = AvatarImageNA.Standard)
                    Avatar(group, AvatarSize.Large, imageNAStyle = AvatarImageNA.StandardInverted)
                    Avatar(group, AvatarSize.XLarge, imageNAStyle = AvatarImageNA.Initials)
                    Avatar(group, AvatarSize.XXLarge, imageNAStyle = AvatarImageNA.Anonymous)
                }
            }
        }
    }
}
