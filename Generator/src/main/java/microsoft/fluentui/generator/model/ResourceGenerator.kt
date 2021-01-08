package com.msft.stardust.model

import com.msft.stardust.global_flavorPath
import com.msft.stardust.model.resources.Resource
import com.msft.stardust.model.resources.ResourceType
import com.msft.stardust.model.resources.generateResources
import com.msft.stardust.printWarning
import java.util.*

abstract class ResourceGenerator(protected val name: String) {
    protected val resourceList = mutableListOf<Resource>()

    open fun generate() {
        generateResources(global_flavorPath, name, resourceList)
    }

    fun addResource(name: String, value: String, type: ResourceType) {
        addResource(Resource(name.decapitalize(), value, type))
    }

    fun addResource(resource: Resource) {
        if (resourceList.find { it.name.equals(resource.name, ignoreCase = true) && it.forNightTheme == resource.forNightTheme } != null) {
            printWarning("duplicate resource key: ${resource.name}")
        } else {
            resourceList.add(resource)
            resourceMap[resource.name] = resource
        }
    }

    abstract fun parse(key: String, values: LinkedHashMap<String, Any>?)
}


private val resourceMap = mutableMapOf<String, Resource>()
fun global_getResourceType(name: String): ResourceType? = resourceMap[name]?.type