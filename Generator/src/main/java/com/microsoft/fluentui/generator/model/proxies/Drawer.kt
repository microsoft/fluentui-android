package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.model.AttributeType

class Drawer : AppearanceProxy() {

    override val stylesheetName: String
        get() = "DrawerTokens"

    override val viewName: String
        get() = super.viewName + "View"
    override val sourcePath: String
        get() = "../fluentui_drawer"
    override val importPath: String
        get() = "drawer."

    override fun setupCustomAttributes(): List<CustomAttribute> {
        val attrsList = mutableListOf<CustomAttribute>()
        attrsList.addAll(listOf(
                CustomAttribute("drawerType", AttributeType.ENUM, propertyReference = "style"),
                CustomAttribute("drawerBehavior", AttributeType.ENUM, propertyReference = "style")
        ))

        return attrsList
    }
}