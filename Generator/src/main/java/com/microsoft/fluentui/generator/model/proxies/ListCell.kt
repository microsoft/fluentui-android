package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.global_flavorMidPath
import com.microsoft.fluentui.generator.model.AttributeType
import com.microsoft.fluentui.generator.model.resources.*


class ListCell : AppearanceProxy() {

    override val stylesheetName: String
        get() = "ListCellTokens"

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
            drawables.add(Resource("background_${apNameLowerCase}","R.drawable.${apNameLowerCase}_background", ResourceType.DRAWABLE))
        }
        return drawables
    }

    override fun setupCustomAttributes(): List<CustomAttribute> {
        val attrsList = mutableListOf<CustomAttribute>()
        attrsList.addAll(listOf(
                CustomAttribute("listLeadingViewSize", AttributeType.ENUM, propertyReference = "size"),
                CustomAttribute("listTitle", AttributeType.STRING),
                CustomAttribute("listSubtitle", AttributeType.STRING),
                CustomAttribute("listFooter", AttributeType.STRING),
                CustomAttribute("listTitleMaxLines", AttributeType.INTEGER),
                CustomAttribute("listSubtitleMaxLines", AttributeType.INTEGER),
                CustomAttribute("listFooterMaxLines", AttributeType.INTEGER)
        ))
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