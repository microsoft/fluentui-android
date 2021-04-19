package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.global_flavorMidPath
import com.microsoft.fluentui.generator.model.resources.Resource
import com.microsoft.fluentui.generator.model.resources.generateComponentProxyFile
import com.microsoft.fluentui.generator.model.resources.generateTokenSystemFile
import com.microsoft.fluentui.generator.model.resources.parseResourceCodeWrapper


class ResourceProxyGenerator(
    private val stylesheetName: String,
    private val viewName: String,
    private val parameters: LinkedHashMap<String, Any> = LinkedHashMap(),
    private val parentName: String?,
    private val parentParameters: LinkedHashMap<String, Any>?
) {
    private val baseResourceProxyPath = "java/com/microsoft/fluentui/generator/resourceProxies/"
    private val customResources = mutableListOf<Resource>()

    fun generateAll(sourcePath: String, importPath: String, drawables: List<Resource>?) {
        val resourceList = mutableListOf<Resource>()
        resourceList.addAll(customResources)

        // Parse all resources
        parameters.forEach { parseResourceCodeWrapper(resourceList, it.key, it.value) }
        val resourceProxyPath = "$sourcePath$global_flavorMidPath$baseResourceProxyPath$stylesheetName" +"Proxy.kt"
        if (drawables != null) {
            resourceList.addAll(drawables)
        }
        generateComponentProxyFile(resourceProxyPath, importPath, stylesheetName, viewName, resourceList, parentName, parentParameters)
        if(parentName == null){
            val resourceTokenSystemPath = "$sourcePath$global_flavorMidPath$baseResourceProxyPath$stylesheetName" +"System.kt"
            generateTokenSystemFile(resourceTokenSystemPath, importPath, stylesheetName, viewName, parameters, drawables)
        }
    }
}