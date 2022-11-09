package com.microsoft.fluentui.tokenized.persona

import androidx.annotation.DrawableRes
import androidx.compose.foundation.interaction.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import com.microsoft.fluentui.theme.token.StateColor
import com.microsoft.fluentui.theme.token.controlTokens.AvatarSize
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
    val enabled: Boolean = true,
    val person: Person = Person(),
    val enableActivityRing: Boolean = false,
    val onItemClick: (() -> Unit)? = null
)
@Composable
fun getColorByState(
    stateData: StateColor,
    enabled: Boolean,
    interactionSource: InteractionSource
): Color {
    if (enabled) {
        val isPressed by interactionSource.collectIsPressedAsState()
        if (isPressed)
            return stateData.pressed

        val isFocused by interactionSource.collectIsFocusedAsState()
        if (isFocused)
            return stateData.pressed

        val isHovered by interactionSource.collectIsHoveredAsState()
        if (isHovered)
            return stateData.pressed

        return stateData.rest
    } else
        return stateData.disabled
}
fun getAvatarSize(secondaryText: String?, tertiaryText: String?): AvatarSize {
    if(secondaryText == null && tertiaryText == null){
        return AvatarSize.Size24
    }
    if(secondaryText != null && tertiaryText == null){
        return AvatarSize.Size40
    }
    return AvatarSize.Size56
}