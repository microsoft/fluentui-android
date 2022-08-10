package com.microsoft.fluentui.tokenized.persona

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
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
    fun getName(): String {
        return firstName + " " + lastName
    }

    fun isImageAvailable(): Boolean {
        return image != null || imageBitmap != null
    }

    fun getInitials(): String {
        var initial: String = ""

        for (char in firstName) {
            if (!char.isLetter())
                continue
            initial += char
            break
        }

        for (char in lastName) {
            if (!char.isLetter())
                continue
            initial += char
            break
        }

        if (initial.isBlank()) {
            if (!email.isNullOrBlank())
                initial += email[0]
        }

        return initial.uppercase()
    }
}

class Group(
        val members: List<Person> = listOf(),
        val groupName: String = "",
        val email: String? = null,
        @DrawableRes val image: Int? = null,
        val imageBitmap: ImageBitmap? = null,
) {
    fun isImageAvailable(): Boolean {
        return image != null || imageBitmap != null
    }

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
        if (!initials.isNotBlank()) {
            if (!email.isNullOrBlank())
                initials += email[0]
        }
        return initials.uppercase()
    }
}