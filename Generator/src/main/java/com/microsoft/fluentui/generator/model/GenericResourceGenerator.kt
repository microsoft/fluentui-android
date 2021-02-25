package com.microsoft.fluentui.generator.model

import com.microsoft.fluentui.generator.global_flavorPath
import com.microsoft.fluentui.generator.model.resources.Resource
import com.microsoft.fluentui.generator.model.resources.ResourceType
import com.microsoft.fluentui.generator.model.resources.generateResources
import com.microsoft.fluentui.generator.model.resources.parseResource
import java.util.*

class GenericResourceGenerator : ResourceGenerator("generic") {

    override fun parse(key: String, values: LinkedHashMap<String, Any>?) {
        val list = mutableListOf<Resource>()

        values?.apply {
            forEach { entry ->
                parseResource(list, "${key.toLowerCase()}_${entry.key}", entry.value)
            }
        }

        list.forEach { addResource(it) }
    }

    override fun generate() {

        // Generate all resource types and remove them from the main collection
        resourceList.filter { it.type == ResourceType.COLOR }.let {
            generateResources(global_flavorPath, "colors", it)
            resourceList.removeAll(it)
        }

        resourceList.filter { it.type == ResourceType.DIMENSION }.let {
            generateResources(global_flavorPath, "dimens", it)
            resourceList.removeAll(it)
        }

        resourceList.filter { it.type == ResourceType.STRING }.let {
            generateResources(global_flavorPath, "strings", it)
            resourceList.removeAll(it)
        }

        // Add whatever is left to a common res file
        generateResources(global_flavorPath, "all", resourceList)
    }
}