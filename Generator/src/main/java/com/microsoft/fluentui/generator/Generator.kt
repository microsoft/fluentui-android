package com.microsoft.fluentui.generator

import com.microsoft.fluentui.generator.model.FluentUIDocument
import com.microsoft.fluentui.generator.model.clearBindingMappingFile
import com.microsoft.fluentui.generator.model.resources.clearInterpolatorsFile
import org.yaml.snakeyaml.Yaml
import java.io.File
import java.io.FileInputStream

lateinit var global_documentType: DocumentType

var global_documentsCount = 0
var global_resourceCount = 0
var global_enumsCount = 0
var global_bindingMethodsCount = 0
var global_errorsCount = 0
var global_warningsCount = 0

enum class DocumentType { ROOT, REGULAR, DARKTHEME }

// Document identifier for generating light and dark resources.
// The forNightTheme argument is used to force mapping of resources to the night directory.
// This is necessary to support a single stylesheet that includes both light and dark values.
fun DocumentType.identifierString(forNightTheme: Boolean = false): String =
    if (global_documentType == DocumentType.DARKTHEME || forNightTheme) "-night" else ""


data class StylesheetDocument(val path: String, val type: DocumentType) {
    var flavorName: String

    init {
        flavorName =
            path.substringAfterLast("/").substringBefore(YAML_STYLESHEET_SUFFIX).toLowerCase()
        when {
            flavorName.equals("generic") -> flavorName = "main"
            flavorName.endsWith("dark") -> flavorName = flavorName.substringBefore("dark")
        }
    }
}


class Generator {
    private val documents = mutableListOf<StylesheetDocument>()

    fun add(doc: StylesheetDocument) = documents.add(doc)
    fun addAll(docs: List<StylesheetDocument>) = documents.addAll(docs)

    fun generate() {
        val timer = System.currentTimeMillis()

        // Initial cleanup
        clearBindingMappingFile()
        clearInterpolatorsFile()

        documents.forEach {
            try {
                println("\n[${it.path}]")
                val doc: FluentUIDocument? =
                    Yaml().loadAs(FileInputStream(it.path), FluentUIDocument::class.java)

                if (doc == null) {
                    printWarning("parsing failed, file empty?")
                } else {
                    configFlavor(it.flavorName)
                    global_documentType = it.type

                    println(
                        ">>> ${it.flavorName.capitalize()} (${global_documentType.toString()
                            .toLowerCase().capitalize()}) <<<"
                    )
                    doc.generate(File(it.path).parent)
                }
            } catch (e: Throwable) {
                printError("parsing failed: ${e.message} ")
            }
            global_documentsCount++
        }

        println(
            "\nCode generation completed in ${System.currentTimeMillis() - timer}ms!\n" +
                    " - $global_documentsCount stylesheets\n" +
                    " - $global_resourceCount resource files\n" +
                    " - $global_enumsCount enums\n" +
                    " - $global_bindingMethodsCount binding methods\n" +
                    " - $global_errorsCount errors\n" +
                    " - $global_warningsCount warnings\n"
        )
    }
}