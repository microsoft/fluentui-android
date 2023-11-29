package com.microsoft.fluentuidemo.demos

import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.Persona
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.V2DemoActivity
import com.microsoft.fluentuidemo.util.invokeToast

class V2PersonaActivity : V2DemoActivity() {
    init {
        setupActivity(this)
    }

    override val paramsUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#params-23"
    override val controlTokensUrl = "https://github.com/microsoft/fluentui-android/wiki/Controls#control-tokens-22"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setActivityContent {
            CreatePersonaActivityUI(this)
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
                            BasicText(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                text = "One line Persona view with small Avatar",
                                style = TextStyle(color = Color(0xFF2886DE))
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
                            BasicText(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                text = "Two line Persona view with large Avatar",
                                style = TextStyle(color = Color(0xFF2886DE))
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
                            BasicText(
                                modifier = Modifier.padding(start = 8.dp, top = 16.dp),
                                text = "Three line Persona View with Xlarge Avatar",
                                style = TextStyle(color = Color(0xFF2886DE))
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
