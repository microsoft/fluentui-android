package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.global_projectSourcePath
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
    private val baseResourceProxyPath = global_projectSourcePath.plus("main/java/com/microsoft/fluentui/generator/resourceProxies/")
    private val customResources = mutableListOf<Resource>()

    fun generateAll() {
        val resourceList = mutableListOf<Resource>()
        resourceList.addAll(customResources)

        // Parse all resources
        parameters.forEach { parseResourceCodeWrapper(resourceList, it.key, it.value) }
        val resourceProxyPath = "$baseResourceProxyPath$stylesheetName" +"Proxy.kt"
        generateComponentProxyFile(resourceProxyPath, stylesheetName, viewName, resourceList, parentName, parentParameters)
        if(parentName == null){
            val resourceTokenSystemPath = "$baseResourceProxyPath$stylesheetName" +"System.kt"
            generateTokenSystemFile(resourceTokenSystemPath, stylesheetName, viewName, parameters)
        }
    }
}