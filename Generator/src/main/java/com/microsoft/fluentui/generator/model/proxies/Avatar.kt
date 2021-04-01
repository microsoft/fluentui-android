package com.microsoft.fluentui.generator.model.proxies



class Avatar() : AppearanceProxy() {

    override val stylesheetName = "AvatarTokens"
    override val viewName = "AvatarTokensView"
    override val sourcePath: String
        get() = "../fluentui_others"

    override fun setupCustomAttributes(): List<CustomAttribute> {
        return mutableListOf()
    }
}