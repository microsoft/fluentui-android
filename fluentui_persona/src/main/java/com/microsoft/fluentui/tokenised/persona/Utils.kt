package com.microsoft.fluentui.tokenised.persona

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus


class Person(
        val firstName: String = "Anonymous",
        val lastName: String?,
        val email: String? = null,
        @DrawableRes val image: Int? = null,
        val imageBitmap: ImageBitmap? = null,
        val isActive: Boolean = false,
        val status: AvatarStatus = AvatarStatus.Available,
        val isOOO: Boolean = false
) {
    fun getInitials(): String {
        var initial: String = ""
        if (firstName.length >= 2) {
            if (lastName == null || lastName.isBlank())
                initial += firstName.subSequence(0, 2)
            else {
                initial += firstName[0]
                initial += lastName[0]
            }
        } else if (firstName.isNotBlank()) {
            initial += firstName
        } else {
            if (lastName != null && lastName.isNotBlank())
                initial += lastName[0]
        }

        if (initial.isBlank())
            initial = "AU"

        return initial
    }

    fun isPersonActive() = this.isActive
}

class Group(
        val members: List<Person> = listOf(),
        val groupName: String?,
        val email: String? = null,
        @DrawableRes val image: Int? = null,
        val imageBitmap: ImageBitmap? = null,
        val backgroundColor: Color = Color.Red
) {
    fun getInitials(): String {
        var initials: String = ""
        if (groupName != null && groupName.isNotBlank()) {
            for (word in groupName.split(" ")) {
                initials += word[0]
            }
        }
        return initials
    }
}