package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.databinding.V2ActivityComposeBinding
import com.microsoft.fluentuidemo.util.invokeToast

class V2PersonaActivity : DemoActivity() {
    override val contentNeedsScrollableContainer: Boolean
        get() = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val v2ActivityComposeBinding = V2ActivityComposeBinding.inflate(LayoutInflater.from(container.context), container,true)
        v2ActivityComposeBinding.composeHere.setContent {
            FluentTheme {
                CreatePersonaActivityUI(this)
            }
        }
    }

    @Composable
    private fun CreatePersonaActivityUI(context: Context) {
        val person1 = Person(
            "Allan", "Munger",
            image = R.drawable.avatar_allan_munger, isActive = true,
            status = AvatarStatus.Available, isOOO = false
        )
        val person2 = Person(
            "Charlotte", "Waltson",
            image = R.drawable.avatar_charlotte_waltson, isActive = false,
            status = AvatarStatus.Blocked, isOOO = false
        )
        val person3 = Person(
            "Carole", "Poland",
            image = R.drawable.avatar_carole_poland, isActive = false,
            status = AvatarStatus.Away, isOOO = true
        )
        Box {
            Column {
                LazyColumn {
                    item {
                        Column {
                            Text(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                text = "One line Persona view with small Avatar",
                                color = Color(0xFF2886DE)
                            )
                            Persona(
                                person = person1,
                                primaryText = person1.firstName + " " + person1.lastName,
                                onClick = {
                                    invokeToast(
                                        person1.firstName,
                                        context
                                    )
                                },
                                enableAvatarActivityRings = true
                            )
                        }
                    }
                    item {
                        Column {
                            Text(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                text = "Two line Persona view with large Avatar",
                                color = Color(0xFF2886DE)
                            )
                            Persona(
                                person = person2,
                                primaryText = person2.firstName + " " + person2.lastName,
                                secondaryText = "Microsoft",
                                onClick = {
                                    invokeToast(
                                        person2.firstName,
                                        context
                                    )
                                },
                            )
                        }
                    }
                    item {
                        Column {
                            Text(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                text = "Three line Persona View with Xlarge Avatar",
                                color = Color(0xFF2886DE)
                            )
                            Persona(
                                person = person3,
                                primaryText = person3.firstName + " " + person3.lastName,
                                secondaryText = "Microsoft",
                                tertiaryText = person3.status.toString(),
                                onClick = {
                                    invokeToast(
                                        person3.firstName,
                                        context
                                    )
                                },
                            )
                        }
                    }
                }
            }

        }

    }
}
