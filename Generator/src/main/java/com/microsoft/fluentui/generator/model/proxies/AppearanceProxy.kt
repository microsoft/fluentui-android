package com.microsoft.fluentui.generator.model.proxies

import com.microsoft.fluentui.generator.model.resources.Resource

abstract class AppearanceProxy {
    /**
     * Name of the appearance proxy as it's declared in the stylesheet
     */
    open val stylesheetName: String
        get() = this.javaClass.simpleName

    /**
     * Name of the corresponding view for which we generate the resources and attributes XML files
     */
    open val viewName: String
        get() = stylesheetName

    abstract val sourcePath: String

    internal var ignoreDataBindingMethods: Boolean = true

    open fun generate(
        apName: String,
        ap: LinkedHashMap<String, Any>,
        parentName: String?,
        parentParameters: LinkedHashMap<String, Any>?,
        parameters: LinkedHashMap<String, Any>
    ) {

        AppearanceProxyGenerator(viewName, ap, ignoreDataBindingMethods).apply {
            setupGeneratorParameters(ap)
            setupStyles(ap, parentName)
            setupCustomAttributes().forEach { addCustomAttribute(it) }
            setupCustomResources(ap).forEach { addCustomResource(it) }
            generateAll(sourcePath)
        }

        ResourceProxyGenerator(apName, viewName, parameters, parentName, parentParameters).apply {
            generateAll(sourcePath)
        }
    }

    protected open fun setupGeneratorParameters(parameters: LinkedHashMap<String, Any>) {
        // nothing to do usually
    }

    protected open fun setupStyles(parameters: LinkedHashMap<String, Any>, parentName: String?) {
        // nothing to do usually
    }

    protected open fun setupCustomAttributes(): List<CustomAttribute>{
        return emptyList()
    }

    protected open fun setupCustomResources(parameters: LinkedHashMap<String, Any>): List<Resource> {
        // nothing to do usually
        return emptyList()
    }
}