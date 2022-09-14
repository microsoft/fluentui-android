package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Busy
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R

class V2PersonaViewActivity : DemoActivity() {
    override val contentLayoutId: Int
        get() = R.layout.v2_activity_compose
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val compose_here = findViewById<ComposeView>(R.id.compose_here)

        compose_here.setContent {
            FluentTheme {
                createActivityUI()
            }
        }
    }
    @Composable
    fun createActivityUI(){
        val person = Person("Allan", "Munger",
            image = R.drawable.avatar_allan_munger, isActive = true,
            status = AvatarStatus.Available, isOOO = false)
        Box(){
            LazyColumn(){
                item {
                    Column() {
                        Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp), text = "One line Persona view", color = Color(0xFF2886DE))
                        PersonaView(person = person, primaryText = person.firstName+" "+person.lastName)
                    }
                }
                item {
                    Column() {
                        Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp), text = "Two line Persona view", color = Color(0xFF2886DE))
                        PersonaView(person = person, primaryText = person.firstName, secondaryText = person.lastName)
                    }
                }
                item {
                    Column() {
                        Text(modifier = Modifier.padding(start = 16.dp, top = 24.dp, bottom = 8.dp), text = "Two line Persona View", color = Color(0xFF2886DE))
                        PersonaView(person = person, primaryText = person.firstName, secondaryText = "Microsoft")
                    }
                }
            }
        }
    }
}
