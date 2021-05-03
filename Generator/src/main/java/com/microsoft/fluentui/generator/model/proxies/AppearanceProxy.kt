package com.microsoft.fluentui.generator.model.proxies


import com.microsoft.fluentui.generator.model.resources.Resource
import kotlin.collections.List

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
    open val importPath: String = ""

    internal var ignoreDataBindingMethods: Boolean = true

    open fun generate(
        apName: String,
        ap: LinkedHashMap<String, Any>,
        parentName: String?,
        parentParameters: LinkedHashMap<String, Any>?,
        parameters: LinkedHashMap<String, Any>
    ) {

        var drawables: List<Resource>? = null
        AppearanceProxyGenerator(viewName, ap, ignoreDataBindingMethods).apply {
            drawables = setupGeneratorParameters(ap)
            setupStyles(ap, parentName)
            setupCustomAttributes().forEach { addCustomAttribute(it) }
            setupCustomResources(ap).forEach { addCustomResource(it) }
            generateAll(sourcePath, importPath)
        }

        ResourceProxyGenerator(apName, viewName, parameters, parentName, parentParameters).apply {
            generateAll(sourcePath, importPath, drawables)
        }
    }

    protected open fun setupGeneratorParameters(parameters: LinkedHashMap<String, Any>): List<Resource>? {
        // nothing to do usually
        return null
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