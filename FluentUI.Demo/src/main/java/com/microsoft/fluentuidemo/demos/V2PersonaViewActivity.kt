package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import android.widget.Toast
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
        val person1 = Person("Allan", "Munger",
            image = R.drawable.avatar_allan_munger, isActive = true,
            status = AvatarStatus.Available, isOOO = false)
        val person2 = Person("Charlotte", "Waltson",
            image = R.drawable.avatar_charlotte_waltson, isActive = false,
            status = AvatarStatus.Blocked, isOOO = false)
        val person3 = Person("Carole", "Poland",
            image = R.drawable.avatar_carole_poland, isActive = false,
            status = AvatarStatus.Away, isOOO = true)
        Box(){
            LazyColumn(){
                item {
                    Column() {
                        Text(modifier = Modifier.padding(start = 8.dp, top = 16.dp), text = "One line Persona view with small Avatar", color = Color(0xFF2886DE))
                        PersonaView(person = person1, primaryText = person1.firstName+" "+person1.lastName, onClick = { onClick(person1.firstName) }, enableAvatarActivityRings = true)
                    }
                }
                item {
                    Column() {
                        Text(modifier = Modifier.padding(start = 8.dp, top = 16.dp), text = "Two line Persona view with large Avatar", color = Color(0xFF2886DE))
                        PersonaView(person = person2, primaryText = person2.firstName+" "+person2.lastName, secondaryText = "Microsoft", onClick = { onClick(person2.firstName) })
                    }
                }
                item {
                    Column() {
                        Text(modifier = Modifier.padding(start = 8.dp, top = 16.dp), text = "Two line Persona View with large Avatar", color = Color(0xFF2886DE))
                        PersonaView(person = person3, primaryText = person3.firstName+" "+person3.lastName, secondaryText = "Microsoft", tertiaryText = person3.status.toString(), onClick = { onClick(person3.firstName) })
                    }
                }
            }
        }
    }
    fun onClick(text: String){
        Toast.makeText(this, "Clicked on $text", Toast.LENGTH_SHORT).show()
    }
}
