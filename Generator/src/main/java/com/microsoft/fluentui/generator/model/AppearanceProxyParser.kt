package com.microsoft.fluentui.generator.model

import com.microsoft.fluentui.generator.*
import com.microsoft.fluentui.generator.model.proxies.*
import java.io.File

val appearanceProxies = listOf(
    Button(Button.Type.Default()),
    Button(Button.Type.Primary()),
    Button(Button.Type.Secondary()),
    Button(Button.Type.Ghost()),
    Avatar(),
    Avatar(Avatar.Type.Accent()),
    Avatar(Avatar.Type.Group()),
    Avatar(Avatar.Type.Outlined()),
    Avatar(Avatar.Type.OutlinedPrimary()),
    Avatar(Avatar.Type.Overflow()),
    ListCell(),
    ListHeader(ListHeader.Type.Default()),
    ListHeader(ListHeader.Type.Accent())
)

val resourceOnlyAppearanceProxies = listOf<String>()

fun generateAppearanceProxies(document: FluentUIDocument) {
    document.forEach { rootNode ->
        if (rootNode.key.isValidAPKey()) {
            val apRawName = rootNode.key.substringAfter(AP_PREFIX)
            var apParentName: String? = null
            val apName: String = if (apRawName.contains(EXTENSION_TAG)) {
                apParentName = apRawName.substringAfter(EXTENSION_TAG)
                apRawName.substringBefore(EXTENSION_TAG)
            } else {
                apRawName
            }

            val parentParameters =
                if (apParentName != null) getParentParameters(document, apRawName, LinkedHashMap()) else null

            val baseResourceProxyPath = global_projectSourcePath.plus("main/java/com/microsoft/fluentui/generator/resourceProxies/")
            val genericProxyPath = baseResourceProxyPath + "GenericProxy.kt"
            File(genericProxyPath).apply {
                parentFile.mkdirs()
                writeText("package com.microsoft.fluentui.generator.resourceProxies\n" +
                        "import android.content.Context\n" +
                        "import android.content.res.Resources\n" +
                        "open class GenericProxy(context: Context, resources: Resources)")
            }

            parseAppearanceProxy(
                apName,
                rootNode.value,
                apParentName,
                parentParameters
            )
        }
    }
}

fun getParentParameters(document: FluentUIDocument, apRawName: String, list: LinkedHashMap<String, Any>): LinkedHashMap<String, Any>? {
    val apParentName = if (apRawName.contains(EXTENSION_TAG)) apRawName.substringAfter(EXTENSION_TAG) else apRawName

    // TODO: we could probably optimize this
    document[apParentName.prependCamelCase(AP_PREFIX)]?.let {
        list.putAll(it)
        return list
    } ?: document.forEach { rootNode ->
        if (rootNode.key.isValidAPKey() && rootNode.key.startsWith(apParentName)) {
            list.putAll(rootNode.value)
            getParentParameters(document, rootNode.key, list)
        }
    }

    return list
}

private fun parseAppearanceProxy(
    name: String,
    parameters: LinkedHashMap<String, Any>,
    parentName: String?,
    parentParameters: LinkedHashMap<String, Any>?
) {
    println("> APPEARANCE PROXY \"$name\" ${parentName?.let { "extends \"$it\", has parentParameters: ${parentParameters != null}" } ?: ""}")

    // If defined, merge the current appearance proxy with its parent
    val mergedParameters = LinkedHashMap<String, Any>()
    parentParameters?.let { mergedParameters.putAll(it) }
    mergedParameters.putAll(parameters)
    mergedParameters["ApName"] = name
    generateAppearanceProxy(name, parameters, mergedParameters, parentName, parentParameters)
}

/**
 * Generate resources and/or View parameters for recognized components
 */
private fun generateAppearanceProxy(
    apName: String,
    parameters: LinkedHashMap<String, Any>,
    mergedParameters: LinkedHashMap<String, Any>,
    parentName: String?,
    parentParameters: LinkedHashMap<String, Any>?
) {
    // try recognized components
    appearanceProxies.find { it.stylesheetName == apName }?.generate(apName, mergedParameters, parentName, parentParameters, parameters)

    // try components we use resources from
    if (resourceOnlyAppearanceProxies.contains(apName)){
        AppearanceProxyResourceOnly(apName).generate(
            apName,
            mergedParameters,
            parentName,
            parentParameters,
            parameters
        )
    }
}