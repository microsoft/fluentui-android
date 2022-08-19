package com.microsoft.fluentui.tokenized.persona

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.ImageBitmap
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus

/**
 * Data Class for Person
 *
 * @property firstName First Name of person
 * @property lastName Last Name of person
 * @property email Email Id for person
 * @property image Drawable Image for a person
 * @property imageBitmap Bitmap Image for a person
 * @property isActive Enable/Disable Active status of a person
 * @property status Current Status of the person
 * @property isOOO Enable/Disable Out-Of-Office flag for person
 */
class Person(
        val firstName: String = "",
        val lastName: String = "",
        val email: String? = null,
        @DrawableRes val image: Int? = null,
        val imageBitmap: ImageBitmap? = null,
        val isActive: Boolean = false,
        val status: AvatarStatus = AvatarStatus.Available,
        val isOOO: Boolean = false
) {
    fun getName(): String {
        val name = "$firstName $lastName"
        if (name.trim().isBlank())
            return "Anonymous"
        return name
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

        if (initial.isBlank() && !email.isNullOrBlank()) {
            initial += email[0]
        }

        return initial.uppercase()
    }
}

/**
 * Data Class for Group
 *
 * @property members List of [Person] in group
 * @property groupName Name of the group
 * @property email E-mail ID for the group
 * @property image Drawable Image for the group
 * @property imageBitmap Bitmap Image for the group
 */
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

    fun getName(): String {
        if (groupName.trim().isBlank())
            return "Anonymous"
        return groupName
    }

    fun getInitials(): String {
        var initial = ""
        if (groupName.isNotBlank()) {
            for (word in groupName.split(" ")) {
                if (word.isNotBlank())
                    initial += word[0]

                if (initial.length == 2)
                    break
            }
        }
        if (initial.isBlank() && !email.isNullOrBlank()) {
            initial += email[0]
        }
        return initial.uppercase()
    }
}