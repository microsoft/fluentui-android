package com.msft.stardust.model

import com.msft.stardust.global_flavorPath
import com.msft.stardust.model.resources.Resource
import com.msft.stardust.model.resources.ResourceType
import com.msft.stardust.model.resources.generateResources
import com.msft.stardust.model.resources.parseResource
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