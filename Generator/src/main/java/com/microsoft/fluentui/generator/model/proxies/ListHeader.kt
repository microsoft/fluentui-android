package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.global_flavorMidPath
import com.microsoft.fluentui.generator.model.AttributeType
import com.microsoft.fluentui.generator.model.resources.*


class ListHeader(val type: Type = Type.Default()) : AppearanceProxy() {

    sealed class Type(val apName: String) {
        class Default : Type("ListHeaderTokens")
        class Accent : Type("AccentListHeaderTokens")
    }

    override val stylesheetName: String
        get() = type.apName

    override val viewName: String
        get() = super.viewName + "View"
    override val sourcePath: String
        get() = "../fluentui_listitem"
    override val importPath: String
        get() = "listitem."

    override fun setupGeneratorParameters(parameters: LinkedHashMap<String, Any>): List<Resource> {
        val apNameLowerCase = viewName.toLowerCase()

        val drawables: ArrayList<Resource> = ArrayList()
        // Generate all color selectors
        parameters.forEach {
            if (isColorSelector(it.value)) {
                generateColorSelector(sourcePath.plus(global_flavorMidPath),apNameLowerCase + "_" + it.key, it.value as ArrayList<*>)
            }
        }
        mutableMapOf<ShapeKeys, String>().let { attributes ->
            parameters["backgroundColor"]?.let {
                attributes[ShapeKeys.BACKGROUND_COLOR] = "${apNameLowerCase}_backgroundColor"
            }
            attributes[ShapeKeys.SHAPE] = "@integer/${apNameLowerCase}_backgroundShape"
            generateShape(sourcePath.plus(global_flavorMidPath),"${apNameLowerCase}_background", attributes)
            drawables.add(Resource("background","R.drawable.${apNameLowerCase}_background", ResourceType.DRAWABLE))
        }
        return drawables
    }

    override fun setupCustomAttributes(): List<CustomAttribute> {
        val attrsList = mutableListOf<CustomAttribute>()
        if (type is Type.Default) {
            attrsList.addAll(listOf(
                    CustomAttribute("listHeaderStyle", AttributeType.ENUM, propertyReference = "style"),
                    CustomAttribute("listHeaderTitle", AttributeType.STRING)
            ))
        }
        return attrsList
    }

    override fun setupCustomResources(parameters: LinkedHashMap<String, Any>): List<Resource> {
        // On android background shapes can be represented as an integer.
        // Rectangle = 0 and Oval = 1.
        return listOf(
                Resource(
                        "backgroundShape", when (parameters["hasEqualDimensions"] == true && parameters["rounded"] == true) {
                    true -> "1"
                    else -> "0"
                }, ResourceType.INTEGER
                )
        )
    }
}