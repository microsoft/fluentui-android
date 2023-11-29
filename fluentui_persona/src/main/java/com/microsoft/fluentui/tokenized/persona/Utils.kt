package com.microsoft.fluentui.tokenized.persona

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
import com.microsoft.fluentui.theme.token.controlTokens.AvatarStatus
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

/**
 * Data Class for Person
 *
 * @property firstName First Name of person
 * @property lastName Last Name of person
 * @property email Email Id for person
 * @property image Drawable Image for a person
 * @property bitmap Bitmap Image for a person
 * @property isActive Enable/Disable Active status of a person
 * @property status Current Status of the person
 * @property isOOO Enable/Disable Out-Of-Office flag for person
 */

const val ANONYMOUS = "Anonymous"

@Parcelize
class Person(
    val firstName: String = "",
    val lastName: String = "",
    val email: String? = null,
    @DrawableRes val image: Int? = null,
    val bitmap: @RawValue Bitmap? = null,
    val isActive: Boolean = false,
    val status: AvatarStatus = AvatarStatus.Available,
    val isOOO: Boolean = false
) : Parcelable {

    fun getName(): String {
        val name = "$firstName $lastName"
        if (name.trim().isBlank())
            return ANONYMOUS
        return name
    }

    fun getLabel(): String {
        val label = "$firstName $lastName"
        if (label.trim().isNotBlank())
            return label
        if (!email.isNullOrBlank())
            return email
        return ANONYMOUS
    }

    fun isImageAvailable(): Boolean {
        return image != null || bitmap != null
    }

    fun getInitials(): String {
        var initial = ""

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
 * @property bitmap Bitmap Image for the group
 */
@Parcelize
class Group(
    val members: List<Person> = listOf(),
    val groupName: String = "",
    val email: String? = null,
    @DrawableRes val image: Int? = null,
    val bitmap: @RawValue Bitmap? = null,
) : Parcelable {
    fun isImageAvailable(): Boolean {
        return image != null || bitmap != null
    }

    fun getName(): String {
        if (groupName.trim().isBlank())
            return ANONYMOUS
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

class Persona(
    val person: Person,
    val title: String,
    val subTitle: String? = null,
    val footer: String? = null,
    val enabled: Boolean = true,
    val trailingIcon: (@Composable () -> Unit)? = null,
    val onClick: (() -> Unit)? = null
)

class AvatarCarouselItem(
    val person: Person,
    val enabled: Boolean = true,
    val enableActivityRing: Boolean = false,
    val onItemClick: (() -> Unit)? = null
)

fun getAvatarSize(secondaryText: String?, tertiaryText: String?): AvatarSize {
    if (secondaryText == null && tertiaryText == null) {
        return AvatarSize.Size24
    }
    if (secondaryText != null && tertiaryText == null) {
        return AvatarSize.Size40
    }
    return AvatarSize.Size56
}