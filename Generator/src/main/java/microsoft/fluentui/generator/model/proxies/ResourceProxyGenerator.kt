package microsoft.fluentui.generator.model.proxies

import microsoft.fluentui.generator.global_projectSourcePath
import microsoft.fluentui.generator.model.resources.Resource
import microsoft.fluentui.generator.model.resources.generateComponentProxyFile
import microsoft.fluentui.generator.model.resources.parseResourceCodeWrapper


class ResourceProxyGenerator(
    private val stylesheetName: String,
    private val viewName: String,
    private val parameters: LinkedHashMap<String, Any> = LinkedHashMap(),
    private val parentName: String?,
    private val parentParameters: LinkedHashMap<String, Any>?
) {
    private val baseResourceProxyPath = global_projectSourcePath.plus("main/java/microsoft/fluentui/generator/resourceProxies/")
    private val customResources = mutableListOf<Resource>()

    fun generateAll() {
        val resourceList = mutableListOf<Resource>()
        resourceList.addAll(customResources)

        // Parse all resources
        parameters.forEach { parseResourceCodeWrapper(resourceList, it.key, it.value) }
        val resourceProxyPath = "$baseResourceProxyPath$stylesheetName" +"Proxy.kt"
        generateComponentProxyFile(resourceProxyPath, stylesheetName, viewName, resourceList, parentName, parentParameters)
    }
}