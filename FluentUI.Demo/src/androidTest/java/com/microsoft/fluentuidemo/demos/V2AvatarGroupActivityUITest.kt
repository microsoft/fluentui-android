package com.microsoft.fluentuidemo.demos

import androidx.compose.ui.test.*
import com.microsoft.fluentuidemo.BaseTest
import org.junit.Before
import org.junit.Test

class V2AvatarGroupActivityUITest : BaseTest() {

    @Before
    fun initialize() {
        launchActivity(V2AvatarGroupActivity::class.java)
    }

    @Test
    fun testIncreaseDecreaseMaxAvatar() {
        val plusButton = composeTestRule.onNodeWithText("+")
        val minusButton = composeTestRule.onNodeWithText("-")
        val layout = composeTestRule.onAllNodesWithContentDescription(
            "Group Name",
            substring = true,
            useUnmergedTree = true
        )[0]

        layout.onChildren().assertCountEquals(2)
        plusButton.performClick()
        layout.onChildren().assertCountEquals(3)
        plusButton.performClick()
        layout.onChildren().assertCountEquals(4)
        plusButton.performClick()
        layout.onChildren().assertCountEquals(5)
        minusButton.performClick()
        layout.onChildren().assertCountEquals(4)
        minusButton.performClick()
        layout.onChildren().assertCountEquals(3)
        minusButton.performClick()
        layout.onChildren().assertCountEquals(2)
    }

    @Test
    fun testOverflowAvatar() {
        val totalPeople = 7
        var maxAvatar = 1

        val plusButton = composeTestRule.onNodeWithText("+")
        val minusButton = composeTestRule.onNodeWithText("-")
        val layout = composeTestRule.onAllNodesWithContentDescription(
            "Group Name",
            substring = true,
            useUnmergedTree = true
        )[0]

        layout.onChildren().onLast().printToLog("Test")

        if (maxAvatar < totalPeople)
            layout.onChildren().onLast().assertContentDescriptionContains(
                (totalPeople - maxAvatar).toString(),
                substring = true
            )

        for (i in 1..(2..(totalPeople - 2)).random()) {
            plusButton.performClick()
            maxAvatar++
        }

        if (maxAvatar < totalPeople)
            layout.onChildren().onLast().assertContentDescriptionContains(
                (totalPeople - maxAvatar).toString(),
                substring = true
            )

        for (i in 1..(2..maxAvatar).random()) {
            minusButton.performClick()
            maxAvatar--
        }

        if (maxAvatar < totalPeople)
            layout.onChildren().onLast().assertContentDescriptionContains(
                (totalPeople - maxAvatar).toString(),
                substring = true
            )

    }
    
}