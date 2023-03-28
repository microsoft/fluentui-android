package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.junit4.createComposeRule
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import com.microsoft.fluentui.tokenized.persona.Person
import com.microsoft.fluentui.tokenized.persona.PersonaChip
import com.microsoft.fluentuidemo.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class V2PersonaChipUITest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Before
    fun initialize(){
        composeTestRule.setContent {
            val person1 = Person(
                "Allan",
                "Munger",
                image = R.drawable.avatar_allan_munger
            )
            PersonaChip(person = person1)
        }
    }

    @Test
    fun testPersonaChipBounds(){

    }
    @Test
    fun testAnonymousPersonaChip(){

    }
    @Test
    fun testDisabledPersonaChip(){

    }
    @Test
    fun testCloseIcon(){

    }
}