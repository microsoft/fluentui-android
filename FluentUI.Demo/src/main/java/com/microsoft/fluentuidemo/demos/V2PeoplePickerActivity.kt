package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.peoplepicker.PeoplePicker
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2PeoplePickerActivity: DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val context: Context = this
        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                CreatePeoplePickerActivity()
            }
        }
    }

    @Composable
    private fun CreatePeoplePickerActivity(){
        val people = listOf(
            Person(
                "Allan", "Munger",
                image = R.drawable.avatar_allan_munger,
                email = "allan.munger@xyz.com",
                isActive = true
            ),
            Person(
                "Amanda", "Brady",
                email = "amanda.brady@xyz.com",
                isActive = false, status = AvatarStatus.Offline
            ),
            Person(
                "Abhay", "Singh",
                email = "abhay.singh@xyz.com",
                isActive = true, status = AvatarStatus.DND, isOOO = true
            ),
            Person(
                "Carlos", "Slathery",
                email = "carlos.slathery@xyz.com",
                isActive = false, status = AvatarStatus.Busy, isOOO = true
            ),
            Person(
                "Celeste", "Burton",
                email = "celeste.burton@xyz.com",
                image = R.drawable.avatar_celeste_burton,
                isActive = true, status = AvatarStatus.Away
            ),
            Person(
                "Ankit", "Gupta",
                email = "ankit.gupta@xyz.com",
                isActive = true, status = AvatarStatus.Unknown
            ),
            Person(
                "Miguel", "Garcia",
                email = "miguel.garcia@xyz.com",
                image = R.drawable.avatar_miguel_garcia,
                isActive = true, status = AvatarStatus.Blocked
            )
        )
        var listOfPeople by rememberSaveable { mutableStateOf(people.toMutableList()) }
        val listOfPersonas = mutableListOf<Persona>()
        listOfPeople.forEach {
            listOfPersonas.add(
                Persona(
                    it,
                    "${it.firstName} ${it.lastName}",
                    subTitle = it.email
                )
            )
        }

        Row(modifier = Modifier.padding(36.dp).border(2.dp, color = Color.Green)) {
            PeoplePicker(personas = listOfPersonas)
        }
    }
}