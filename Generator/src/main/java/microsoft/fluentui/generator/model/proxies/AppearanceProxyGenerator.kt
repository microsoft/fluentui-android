package com.msft.stardust.model.proxies

import com.msft.stardust.ATTRIBUTE_PREFIX
import com.msft.stardust.DocumentType
import com.msft.stardust.global_documentType
import com.msft.stardust.global_flavorPath
import com.msft.stardust.model.*
import com.msft.stardust.model.resources.Resource
import com.msft.stardust.model.resources.generateResources
import com.msft.stardust.model.resources.parseResource

data class CustomAttribute(val name: String,
                           val type: AttributeType,
                           val globalEnumReference: String? = null,
                           val propertyReference: String? = null)

class AppearanceProxyGenerator(
    private val viewName: String,
    private val parameters: LinkedHashMap<String, Any> = LinkedHashMap(),
    private val ignoreDataBindingMethods: Boolean = false
) {
    private val customAttributes = mutableListOf<Attribute>()
    private val bindingMapping = mutableMapOf<String, String>()
    private val customResources = mutableListOf<Resource>()

    fun addCustomAttribute(customAttribute: CustomAttribute){
        val customValuesMap: Map<String, Int>? =
            when (customAttribute.type) {
                AttributeType.ENUM, AttributeType.FLAG -> getEnum(customAttribute.globalEnumReference ?: customAttribute.name)
                else -> null
            }

        addAttribute(Attribute(customAttribute.name, customAttribute.type, customValuesMap))

        val bindingMethodName = "set" +
                (customAttribute.propertyReference ?: customAttribute.name).let { propertyName ->
                    val index = propertyName.toLowerCase().indexOf("is")
                    if (index != -1 && index + 2 != propertyName.length) {
                        propertyName.substring(index+2, propertyName.length)
                    } else {
                        propertyName
                    }
                }.capitalize()

        bindingMapping["${ATTRIBUTE_PREFIX}${customAttribute.name}"] = bindingMethodName
    }

    fun addAttribute(attribute: Attribute) {
        customAttributes.add(attribute)
    }

    fun addCustomResource(resource: Resource) {
        customResources.add(resource)
    }

    fun generateAll() {
        generateResourcesOnly()
        // Generate source files and parameters only for the root document
        if (global_documentType == DocumentType.ROOT) {
            generateAttributes(global_flavorPath, viewName, customAttributes)
            if (!ignoreDataBindingMethods) addBindingMethods(viewName, bindingMapping)
        }
    }

    fun generateResourcesOnly() {
        val resourceList = mutableListOf<Resource>()
        resourceList.addAll(customResources)

        // Parse all resources
        parameters.forEach { parseResource(resourceList, it.key, it.value) }
        generateResources(global_flavorPath, viewName.toLowerCase(), resourceList, prependNameToResourceName = true)
    }
}