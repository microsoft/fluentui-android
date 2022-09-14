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
    @Composable
    fun createActivityUI(){
        val person1 = Person("Allan", "Munger",
            image = drawable.avatar_allan_munger, isActive = true,
            status = Available, isOOO = false)
        val person2 = Person("Amanda", "Brady",
            image = drawable.avatar_amanda_brady, isActive = true,
            status = Available, isOOO = false)
        val person3 = Person("Ashley", "McCarthy",
            image = drawable.avatar_ashley_mccarthy, isActive = true,
            status = Available, isOOO = false)
        val person4 = Person("Wanda", "Howard",
            image = drawable.avatar_wanda_howard, isActive = true,
            status = Available, isOOO = false)
        val person5 = Person("Celeste", "Burton",
            image = drawable.avatar_celeste_burton, isActive = true,
            status = Available, isOOO = false)
        val person6 = Person("Cecil", "Folk",
            image = drawable.avatar_cecil_folk, isActive = true,
            status = Available, isOOO = false)
        val person7 = Person("Carlos", "Slattery",
            image = drawable.avatar_carlos_slattery, isActive = true,
            status = Available, isOOO = false)
        val person8 = Person("Carole", "Poland",
            image = drawable.avatar_carole_poland, isActive = true,
            status = Available, isOOO = false)
        val person9 = Person("Charlotte", "Waltson",
            image = drawable.avatar_charlotte_waltson, isActive = true,
            status = Available, isOOO = false)
        val person10 = Person("Colin", "Badllinger",
            image = drawable.avatar_colin_ballinger, isActive = true,
            status = Available, isOOO = false)
        val person11 = Person("Daisy", "Phillips",
            image = drawable.avatar_daisy_phillips, isActive = true,
            status = Available, isOOO = false)
        val person12 = Person("elliot", "Woodward",
            image = drawable.avatar_elliot_woodward, isActive = true,
            status = Available, isOOO = false)
        val person13 = Person("Elvia", "Atkins",
            image = drawable.avatar_elvia_atkins, isActive = true,
            status = Available, isOOO = false)
        val persons = listOf(person1, person2, person3, person4, person5, person6, person7, person8, person9, person10, person11, person12, person13)
        Box(){
            PersonaListView(persons = persons)
        }
    }
}