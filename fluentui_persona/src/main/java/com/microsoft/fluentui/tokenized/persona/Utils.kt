package com.microsoft.fluentui.tokenized.persona

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.toUpperCase
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus


class Person(
        val firstName: String = "Anonymous",
        val lastName: String = "",
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
            if (lastName.isBlank())
                initial += firstName.subSequence(0, 2)
            else {
                initial += firstName[0]
                initial += lastName[0]
            }
        } else if (firstName.isNotBlank()) {
            initial += firstName
        } else {
            if (lastName.isNotBlank())
                initial += lastName[0]
        }

        if (initial.isBlank())
            initial = "AU"

        return initial
    }
}

class Group(
        val members: List<Person> = listOf(),
        val groupName: String = "",
        val email: String? = null,
        @DrawableRes val image: Int? = null,
        val imageBitmap: ImageBitmap? = null,
) {
    fun getInitials(): String {
        var initials: String = ""
        if (groupName.isNotBlank()) {
            for (word in groupName.split(" ")) {
                if (word.isNotBlank())
                    initials += word[0]

                if (initials.length == 2)
                    break
            }
        }
        return if (initials.isNotBlank()) initials.uppercase() else "AG"
    }
}