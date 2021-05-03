package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.model.AttributeType


class Avatar(val type: Avatar.Type = Avatar.Type.Default()) : AppearanceProxy() {

    sealed class Type(val apName: String) {
        class Default : Type("AvatarTokens")
        class Accent : Type("AccentAvatarTokens")
        class Group : Type("GroupAvatarTokens")
        class Outlined : Type("OutlinedAvatarTokens")
        class OutlinedPrimary : Type("OutlinedPrimaryAvatarTokens")
        class Overflow : Type("OverflowAvatarTokens")
    }

    override val stylesheetName: String
        get() = type.apName

    override val viewName: String
        get() = super.viewName + "View"
    override val sourcePath: String
        get() = "../fluentui_others"

    override fun setupCustomAttributes(): List<CustomAttribute> {
        val attrsList = mutableListOf<CustomAttribute>()
        if (type is Avatar.Type.Default) {
            attrsList.addAll(listOf(
                    CustomAttribute("avatarSize", AttributeType.ENUM, propertyReference = "size"),
                    CustomAttribute("avatarStyle", AttributeType.ENUM, propertyReference = "style"),
                    CustomAttribute("avatarName", AttributeType.STRING),
                    CustomAttribute("avatarEmail", AttributeType.STRING),
                    CustomAttribute("avatarBackGroundColor", AttributeType.REFERENCE),
                    CustomAttribute("avatarImageDrawable", AttributeType.REFERENCE)
            ))
        }

        return attrsList
    }
}