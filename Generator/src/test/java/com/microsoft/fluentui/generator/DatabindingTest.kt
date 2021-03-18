package com.microsoft.fluentui.generator

import com.microsoft.fluentui.generator.model.bindingMethodsFilePath
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File

class DatabindingTest {

    @Before
    fun setup() {
        global_documentType = DocumentType.ROOT
        global_projectRoot = testProjectPath
        configFlavor("main")
    }

    @After
    fun tearDown() {
        File(testProjectPath).deleteRecursively()
    }


    @Test
    fun `Binding methods properties`() {
        Generator().apply {
            add(StylesheetDocument("$YAML_PATH/GenericStyle.yml", DocumentType.ROOT))
            generate()
        }

        File(bindingMethodsFilePath).let {
            Assert.assertTrue(it.exists())

            val content = it.readText()
            val propertiesMap = mutableMapOf<String, MutableList<String>>()

            Regex("type = (.*)::class, attribute = \"(.*)\", method = \"(.*)\"").findAll(content).forEach {
                val className = it.groupValues[1] + ".kt"
                val fluentUIAttributeName = it.groupValues[2]
                var propertyName = it.groupValues[3].substringAfter("set")

                // Booleans are handled by databinding's reflection logic as "setValue" / "isValue"
                if (fluentUIAttributeName.substringBefore(propertyName).toLowerCase().endsWith("is"))
                    propertyName = "is${propertyName}"

                propertiesMap[className] = (propertiesMap[className] ?: mutableListOf()).apply {
                    add(propertyName.decapitalize())
                }
            }
        }

    }
}