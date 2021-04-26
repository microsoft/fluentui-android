package com.microsoft.fluentui.generator.model

import com.microsoft.fluentui.generator.global_flavorPath
import com.microsoft.fluentui.generator.model.resources.*
import java.util.*

private const val TEXT_STYLES_KEY = "textStyles"

// FIXME: Update font styles for fluent.
enum class TextStyles(val fontWeight: Int, val fontSize: Int, val fontFamily: String) {
    DISPLAY(400,60,"sans-serif"),
    LARGE_TITLE(400, 34, "sans-serif"),
    TITLE1(400, 24,"sans-serif"),
    TITLE2(500, 20,"sans-serif-medium"),
    TITLE3(500, 18,"sans-serif-medium"),
    BODY1(400, 16,"sans-serif"),
    BODY1_STRONG(500, 16,"sans-serif-medium"),
    BODY2(400, 14,"sans-serif"),
    BODY2_STRONG(500, 14,"sans-serif-medium"),
    CAPTION1(400, 13, "sans-serif"),
    CAPTION1_STRONG(500, 13, "sans-serif-medium"),
    CAPTION2(400, 12,"sans-serif");

    companion object {
        fun getStyleByName(name: String): TextStyles? = valueOf(name.toUpperCase())
    }
}

class TypographyGenerator(name: String? = null) : ResourceGenerator(name ?: "typography") {
    override fun parse(key: String, values: LinkedHashMap<String, Any>?) {
        values?.apply {
            // Generate font size resources before mapping to text appearance styles.
            forEach { entry ->
                when (val value = entry.value) {
                    is ArrayList<*> -> when (entry.key) {
                        TEXT_STYLES_KEY -> generateTextStyleSizeResources(value)
                        else -> generateSizeResources(entry.key, value)
                    }
                    is String ->
                        if (Regex(FONT_PATTERN).matches(value.toString())) {
                            // FIXME: Fonts declared outside of a map will cause a parsing failure.
                            val textStyle = TextStyles.getStyleByName(entry.key)
                            textStyle?.let { style ->
                                addResource(
                                    "font_size_${entry.key}",
                                    style.fontSize.toString(),
                                    ResourceType.TYPOGRAPHY
                                )
                            }
                        }
                }
            }

            // Generate text appearance styles.
            val stylesList = mutableListOf<Style>()
            forEach { entry ->
                when (val value = entry.value) {
                    is ArrayList<*> -> {
                        value.forEach { fontList ->
                            when (fontList) {
                                is LinkedHashMap<*, *> -> {
                                    if (entry.key == TEXT_STYLES_KEY) {
                                        fontList.forEach { fontDefinition ->
                                            val stylesKeys = mutableMapOf<StyleKeys, String>()
                                            stylesKeys[StyleKeys.TEXT_SIZE] =
                                                "@dimen/font_size_${fontDefinition.key}"
                                            stylesKeys[StyleKeys.FONT_FAMILY] = "\"${TextStyles.getStyleByName(fontDefinition.key.toString())?.fontFamily}\""
                                            stylesKeys[StyleKeys.FONT_WEIGHT] = "${TextStyles.getStyleByName(fontDefinition.key.toString())?.fontWeight}"
                                            stylesList.add(
                                                Style(
                                                    "${name}_${entry.key}_${fontDefinition.key}".decapitalize(),
                                                    null,
                                                    stylesKeys,
                                                    false
                                                )
                                            )
                                        }
                                    } else {
                                        fontList.forEach { fontDefinition ->
                                            if (Regex(FONT_PATTERN).matches(fontDefinition.value.toString())) {
                                                val stylesKeys = mutableMapOf<StyleKeys, String>()
                                                stylesKeys[StyleKeys.TEXT_SIZE] = "@dimen/font_size_${entry.key}_${fontDefinition.key}"
                                                stylesKeys[StyleKeys.TEXT_STYLE] = convertTextStyle(fontDefinition.key.toString())
                                                stylesList.add(
                                                    Style(
                                                        "${name}_${entry.key}_${fontDefinition.key}".decapitalize(),
                                                        null,
                                                        stylesKeys,
                                                        false
                                                    )
                                                )
                                            }
                                        }
                                    }
                                }
                                else -> Unit
                            }
                        }
                    }
                    is String ->
                        if (Regex(FONT_PATTERN).matches(value.toString())) {
                            val stylesKeys = mutableMapOf<StyleKeys, String>()
                            stylesKeys[StyleKeys.TEXT_SIZE] = "@dimen/font_size_${entry.key}"
                            stylesKeys[StyleKeys.FONT_FAMILY] =
                                "\"${TextStyles.getStyleByName(entry.key)?.fontFamily}\""
                            stylesKeys[StyleKeys.FONT_WEIGHT] = "${TextStyles.getStyleByName(entry.key)?.fontWeight}"
                            stylesList.add(
                                Style(
                                    "${name}_${entry.key}".decapitalize(),
                                    null,
                                    stylesKeys,
                                    false
                                )
                            )
                        }
                }
            }
            if (stylesList.isNotEmpty()) {
                generateStyles(global_flavorPath, name, stylesList)
                generateStyles(global_flavorPath, name,stylesList,true)
            }
        }
    }

    private fun generateSizeResources(key: String, value: ArrayList<*>) {
        value.forEach {
            when (it) {
                is LinkedHashMap<*, *> -> {
                    val entry = it.values.single().toString()
                    if (Regex(FONT_PATTERN).matches(entry)) {
                        val resKey = it.keys.single().toString()
                        "\\d{1,2}".toRegex().find(entry)?.let { fontDefinition ->
                            addResource("font_size_${key}_$resKey", fontDefinition.value, ResourceType.TYPOGRAPHY)
                        }
                    }
                }
            }
        }
    }

    private fun generateTextStyleSizeResources(value: ArrayList<*>) {
        value.forEach { fontDefinition ->
            if (fontDefinition is LinkedHashMap<*, *>) {
                fontDefinition.forEach {
                    val key = it.key
                    val textStyle = TextStyles.getStyleByName(it.key.toString())
                    textStyle?.let { style ->
                        addResource(
                            "font_size_$key",
                            style.fontSize.toString(),
                            ResourceType.TYPOGRAPHY
                        )
                    }
                }
            }
        }
    }

    // FIXME: Fix font weights when used with generic style, will be fixed with avatar
    companion object {
        fun convertTextStyle(style: String) = when (style) {
            "bold" -> "bold"
            "semibold" -> "normal"
            "italic" -> "italic"
            else -> "normal"
        }
    }
}
