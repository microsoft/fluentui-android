package microsoft.fluentui.generator.model

import microsoft.fluentui.generator.AP_PREFIX
import microsoft.fluentui.generator.EXTENSION_TAG
import microsoft.fluentui.generator.isValidAPKey
import microsoft.fluentui.generator.isValidAnimatorKey
import microsoft.fluentui.generator.model.resources.generateAnimators
import java.util.*
import kotlin.collections.LinkedHashMap

private val TYPOGRAPHY_KEYS = listOf("Typography")

class FluentUIDocument: LinkedHashMap<String, LinkedHashMap<String, Any>>() {
    private val typographyGenerator = TypographyGenerator()
    private val enumGenerator = EnumGenerator()
    private val genericGenerator = GenericResourceGenerator()
    private val generatorsList = mutableListOf(typographyGenerator, enumGenerator, genericGenerator)
    private var currentDirectory = ""

    fun generate(path: String = "") {
        currentDirectory = path

        // First pass: generate all resources
        println("> RESOURCES")
        keys.forEach { rootKey ->
            val rootValues = this[rootKey]

            enumGenerator.parse(rootKey, rootValues) // look for enums in all nodes
            when {
                TYPOGRAPHY_KEYS.contains(rootKey) -> typographyGenerator.parse(rootKey, rootValues)
                rootKey.isValidAPKey() -> {
                    // AP's may define inline typography styles.
                    val apRawName = rootKey.substringAfter(AP_PREFIX)
                    val apName: String = if (apRawName.contains(EXTENSION_TAG)) {
                        apRawName.substringBefore(EXTENSION_TAG)
                    } else {
                        apRawName
                    }
                    TypographyGenerator(
                        apName.plus("_typography").toLowerCase(Locale.ENGLISH)
                    ).also {
                        generatorsList.add(it)
                        it.parse(rootKey, rootValues)
                    }
                }
                rootKey.isValidAnimatorKey() -> Unit // ignore APs and animators they will processed in the second pass
                else -> genericGenerator.parse(rootKey, rootValues)
            }
        }
        generatorsList.forEach { it.generate() }


        // Second pass: generate proxies and animators
        generateAnimators(this)
        generateAppearanceProxies(this)
    }
}