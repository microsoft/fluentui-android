package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.theme.token.AnonymousAccentAvatarTokens
import com.example.theme.token.AnonymousAvatarTokens
import com.example.theme.token.StandardInvertedAvatarTokens
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.CutoutStyle
import com.microsoft.fluentui.tokenized.controls.Button
import com.microsoft.fluentui.tokenized.persona.Avatar
import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity

class V2AvatarActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-1"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-1"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            Column(
                Modifier
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(14.dp, Alignment.CenterVertically)
            ) {
                var isActive by rememberSaveable { mutableStateOf(true) }
                var isOOO by rememberSaveable { mutableStateOf(false) }
                var isActivityDotPresent by rememberSaveable { mutableStateOf(false) }

                BasicText(
                    modifier = Modifier.padding(start = 16.dp),
                    text = "Avatar Cutout",
                    style = TextStyle(color = Color(0xFF2886DE))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val person: Person = Person(
                        "Kat", "Larsson",
                        isActive = isActive,
                        image = R.drawable.avatar_kat_larsson
                    )
                    val personNoImage: Person = Person(
                        "Kat", "Larsson",
                        isActive = isActive,
                    )
                    Avatar(
                        person,
                        cutoutContentDescription = "heart",
                        size = AvatarSize.Size40,
                        enableActivityRings = true,
                        cutoutIconDrawable = R.drawable.cutout_heart16x16
                    )
                    Avatar(
                        personNoImage,
                        size = AvatarSize.Size40,
                        enableActivityRings = true,
                        cutoutIconDrawable = R.drawable.cutout_laughing24x24
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size40,
                        enableActivityRings = true,
                        cutoutIconDrawable = R.drawable.cutout_excel32x32,
                        cutoutStyle = CutoutStyle.Square
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size56,
                        enableActivityRings = true,
                        cutoutIconDrawable = R.drawable.cutout_people32x32
                    )
                    Avatar(
                        personNoImage,
                        size = AvatarSize.Size56,
                        enableActivityRings = true,
                        cutoutIconDrawable = R.drawable.cutout_onenote32x32,
                        cutoutStyle = CutoutStyle.Square
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size56,
                        enableActivityRings = true,
                        cutoutIconDrawable = R.drawable.cutout_pp48x48,
                        cutoutStyle = CutoutStyle.Square
                    )
                }
                Divider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Button(
                        onClick = { isActive = !isActive },
                        text = "Toggle Activity Ring",
                        contentDescription = "Activity Ring ${if (isActive) "enabled" else "disabled"}"
                    )
                    Button(
                        onClick = { isOOO = !isOOO },
                        text = "Toggle OOO",
                        contentDescription = "OOO status ${if (isOOO) "enabled" else "disabled"}"
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        10.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    Button(
                        onClick = { isActivityDotPresent = !isActivityDotPresent },
                        text = "Toggle Activity Dot",
                        contentDescription = "Activity Dot ${if (isActivityDotPresent) "enabled" else "disabled"}"
                    )
                }

                Divider()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val person: Person = Person(
                        "Allan", "Munger",
                        image = R.drawable.avatar_allan_munger, isActive = isActive,
                        status = AvatarStatus.Available, isOOO = isOOO
                    )
                    val personNoImage: Person = Person(
                        "Allan", "Munger",
                        isActive = isActive,
                        status = AvatarStatus.Available, isOOO = isOOO
                    )

                    Avatar(person, size = AvatarSize.Size16, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size20, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size24, enableActivityRings = true, enableActivityDot = isActivityDotPresent)

                    Avatar(
                        personNoImage,
                        size = AvatarSize.Size32,
                        enableActivityRings = true,
                        avatarToken = AnonymousAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size40,
                        enableActivityRings = true,
                        avatarToken = AnonymousAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        personNoImage,
                        size = AvatarSize.Size56,
                        enableActivityRings = true,
                        avatarToken = AnonymousAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(personNoImage, size = AvatarSize.Size72, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val person: Person = Person(
                        "Amanda", "Brady",
                        image = R.drawable.avatar_amanda_brady, isActive = isActive,
                        status = AvatarStatus.Away, isOOO = isOOO
                    )

                    Avatar(person, size = AvatarSize.Size16, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size20, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size24, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size32, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size40, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size56, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size72, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val person: Person = Person(
                        "Kat", "Larson", isActive = isActive,
                        status = AvatarStatus.Busy, isOOO = isOOO
                    )

                    Avatar(
                        person,
                        size = AvatarSize.Size16,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size20,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size24,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size32,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size40,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size56,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size72,
                        enableActivityRings = false,
                        avatarToken = AnonymousAccentAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val person: Person = Person(
                        "Robin", "Counts",
                        isActive = isActive, status = AvatarStatus.DND, isOOO = isOOO
                    )

                    val personNoInitial: Person = Person(
                        "123", "456",
                        isActive = isActive, status = AvatarStatus.DND, isOOO = isOOO
                    )


                    Avatar(person, size = AvatarSize.Size16, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size20, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size24, enableActivityRings = true, enableActivityDot = isActivityDotPresent)
                    Avatar(person, size = AvatarSize.Size32, enableActivityRings = true, enableActivityDot = isActivityDotPresent)

                    Avatar(
                        personNoInitial,
                        size = AvatarSize.Size40,
                        enableActivityRings = true,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        personNoInitial,
                        size = AvatarSize.Size56,
                        enableActivityRings = true,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        personNoInitial,
                        size = AvatarSize.Size72,
                        enableActivityRings = true,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val person: Person = Person(
                        "Wanda", "Howard",
                        isActive = isActive, status = AvatarStatus.Offline, isOOO = isOOO
                    )
                    val personNoName: Person = Person(
                        "", "",
                        isActive = isActive, status = AvatarStatus.Offline, isOOO = isOOO
                    )

                    Avatar(
                        person,
                        size = AvatarSize.Size16,
                        enableActivityRings = false,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size20,
                        enableActivityRings = true,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size24,
                        enableActivityRings = true,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        person,
                        size = AvatarSize.Size32,
                        enableActivityRings = true,
                        avatarToken = StandardInvertedAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )

                    Avatar(
                        personNoName,
                        size = AvatarSize.Size40,
                        enableActivityRings = false,
                        avatarToken = AnonymousAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        personNoName,
                        size = AvatarSize.Size56,
                        enableActivityRings = true,
                        avatarToken = AnonymousAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                    Avatar(
                        personNoName,
                        size = AvatarSize.Size72,
                        enableActivityRings = true,
                        avatarToken = AnonymousAvatarTokens(),
                        enableActivityDot = isActivityDotPresent
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp,
                        Alignment.CenterHorizontally
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val group: Group = Group(
                        listOf(
                            Person(
                                "Allan", "Munger",
                                image = R.drawable.avatar_allan_munger, isActive = isActive,
                                status = AvatarStatus.Available, isOOO = isOOO
                            ),
                            Person(
                                "Wanda", "Howard",
                                image = R.drawable.avatar_wanda_howard,
                                status = AvatarStatus.Busy, isOOO = isOOO
                            ),
                            Person(
                                "Kat", "Larson",
                                status = AvatarStatus.Busy, isOOO = isOOO
                            ),
                            Person(
                                "Amanda", "Brady",
                                image = R.drawable.avatar_amanda_brady, isActive = isActive,
                                status = AvatarStatus.Away, isOOO = isOOO
                            )
                        ), "Gang Of 4"
                    )

                    val groupNoName: Group = Group(
                        listOf(
                            Person(
                                "Allan", "Munger",
                                image = R.drawable.avatar_allan_munger, isActive = isActive,
                                status = AvatarStatus.Available, isOOO = isOOO
                            ),
                            Person(
                                "Wanda", "Howard",
                                image = R.drawable.avatar_wanda_howard,
                                status = AvatarStatus.Busy, isOOO = isOOO
                            ),
                            Person(
                                "Kat", "Larson",
                                status = AvatarStatus.Busy, isOOO = isOOO
                            ),
                            Person(
                                "Amanda", "Brady",
                                image = R.drawable.avatar_amanda_brady, isActive = isActive,
                                status = AvatarStatus.Away, isOOO = isOOO
                            )
                        ), ""
                    )

                    Avatar(group, size = AvatarSize.Size16)
                    Avatar(
                        group,
                        size = AvatarSize.Size20,
                        avatarToken = StandardInvertedAvatarTokens()
                    )
                    Avatar(
                        group,
                        size = AvatarSize.Size24,
                        avatarToken = StandardInvertedAvatarTokens()
                    )
                    Avatar(
                        group,
                        size = AvatarSize.Size32,
                        avatarToken = AnonymousAvatarTokens()
                    )

                    Avatar(
                        groupNoName,
                        size = AvatarSize.Size40,
                        avatarToken = AnonymousAccentAvatarTokens()
                    )
                    Avatar(group, size = AvatarSize.Size56)
                    Avatar(
                        groupNoName,
                        size = AvatarSize.Size72,
                        avatarToken = StandardInvertedAvatarTokens()
                    )
                }
            }
        }
    }
}
