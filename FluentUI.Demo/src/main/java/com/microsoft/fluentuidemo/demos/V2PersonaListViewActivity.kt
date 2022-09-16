package com.microsoft.fluentuidemo.demos

import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import com.microsoft.fluentui.theme.FluentTheme
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus.Available
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaListView
import com.microsoft.fluentui.tokenized.persona.PersonaView
import com.microsoft.fluentuidemo.DemoActivity
import com.microsoft.fluentuidemo.R
import com.microsoft.fluentuidemo.R.drawable

class V2PersonaListViewActivity : DemoActivity() {
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
    private fun createPersonsList(): List<Person>{
        return arrayListOf(
            Person("Allan", "Munger",
            image = drawable.avatar_allan_munger, email = "allan.munger@microsoft.com", isActive = true,
            status = Available, isOOO = false),
            Person("Amanda", "Brady",
                image = drawable.avatar_amanda_brady, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Ashley", "McCarthy",
                image = drawable.avatar_ashley_mccarthy, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Wanda", "Howard",
                image = drawable.avatar_wanda_howard, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Celeste", "Burton",
                image = drawable.avatar_celeste_burton, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Cecil", "Folk",
                image = drawable.avatar_cecil_folk, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Carlos", "Slattery",
                image = drawable.avatar_carlos_slattery, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Carole", "Poland",
                image = drawable.avatar_carole_poland, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Charlotte", "Waltson",
                image = drawable.avatar_charlotte_waltson, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Colin", "Badllinger",
                image = drawable.avatar_colin_ballinger, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Daisy", "Phillips",
                image = drawable.avatar_daisy_phillips, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("elliot", "Woodward",
                image = drawable.avatar_elliot_woodward, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false),
            Person("Elvia", "Atkins",
                image = drawable.avatar_elvia_atkins, email = "allan.munger@microsoft.com", isActive = true,
                status = Available, isOOO = false))
    }
    @Composable
    fun createActivityUI(){
        Box(){
            PersonaListView(persons = createPersonsList())
        }
    }
}