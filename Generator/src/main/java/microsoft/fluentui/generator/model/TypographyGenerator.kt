package microsoft.fluentui.generator.model

import microsoft.fluentui.generator.model.resources.*
import java.util.*

private const val TEXT_STYLES_KEY = "textStyles"

// FIXME: Update font styles for fluent.
enum class TextStyles(val fontWeight: String, val fontSize: Int) {
    LARGE_TITLE("normal", 34),
    TITLE1("normal", 28),
    TITLE2("normal", 22),
    TITLE3("normal", 20),
    HEADLINE("bold", 20),
    SUBHEADLINE("semibold", 14),
    BODY("normal", 14),
    CALLOUT("normal", 14),
    FOOTNOTE("normal", 12),
    CAPTION1("normal", 12),
    CAPTION2("normal", 11);

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
                                            stylesKeys[StyleKeys.TEXT_STYLE] = convertTextStyle(
                                                TextStyles.getStyleByName(fontDefinition.key.toString())?.fontWeight
                                                    ?: "normal"
                                            )
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
                            stylesKeys[StyleKeys.TEXT_STYLE] = convertTextStyle(
                                TextStyles.getStyleByName(entry.key)?.fontWeight ?: "normal"
                            )
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
                generateStyles(name, stylesList)
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

    // FIXME: Fix font weights
    companion object {
        fun convertTextStyle(style: String) = when (style) {
            "bold" -> "bold"
            "semibold" -> "normal"
            "italic" -> "italic"
            else -> "normal"
        }
    }
}
