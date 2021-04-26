package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.global_flavorPath

class AppearanceProxyResourceOnly(override val viewName: String) : AppearanceProxy() {

    override val stylesheetName: String
        get() = viewName
    override val sourcePath: String
        get() = global_flavorPath

    override fun generate(
        apName: String,
        ap: LinkedHashMap<String, Any>,
        parentName: String?,
        parentParameters: LinkedHashMap<String, Any>?,
        parameters: LinkedHashMap<String, Any>
    ) {
        AppearanceProxyGenerator(viewName, ap).apply {
            // For these components, we don't need the attributes generation phase,
            // so we just generate the resources
            generateResourcesOnly(sourcePath)
        }

        ResourceProxyGenerator(apName, viewName, parameters, parentName, parentParameters).apply {
            generateAll(sourcePath)
        }
    }
}