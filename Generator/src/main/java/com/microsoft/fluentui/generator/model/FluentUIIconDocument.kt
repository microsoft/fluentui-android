package com.microsoft.fluentui.generator.model

import com.microsoft.fluentui.generator.*
import com.microsoft.fluentui.generator.toJavaEnumValueName
import java.io.File
import java.util.*
import kotlin.collections.HashMap
import kotlin.collections.HashSet

class FluentUIIconDocument: LinkedHashMap<String, LinkedHashMap<String, Any>>() {

    private val iconSizeMap = HashMap<String, Int>()
    private val iconDefaults = HashMap<String, String>()
    private val iconNameList = HashSet<String>()

    fun generate() {
        parse2()
        ingestIcons2()
    }

    fun getIconSizes(): ArrayList<LinkedHashMap<String, Int>> {
        val defaultIconSizes = ArrayList<LinkedHashMap<String, Int>>()
        iconSizeMap.forEach { (key, value) ->
            val map = LinkedHashMap<String, Int>()
            map[key] = value
            defaultIconSizes.add(map)
        }
        return defaultIconSizes
    }

    fun getDefaultIcon(): String {
        return iconDefaults["icon"]?: ""
    }

    fun getDefaultIconSize(): String {
        return iconDefaults["size"]?: ""
    }

    fun getDefaultIconStyle(): String {
        return iconDefaults["style"]?: ""
    }

    fun getDefaultIconColor(): String {
        return iconDefaults["color"]?: ""
    }

    private fun parse2() {
        keys.firstOrNull { it == "FluentIcons" }?.let { enumKey ->
            this[enumKey]?.forEach {
                val sizeList: MutableList<Int> = ArrayList()
                val styleList: MutableList<String> = ArrayList()
                (it.value as List<*>).forEach { value ->
                    (value as Map<*, *>).forEach { (iconKey, iconValue) ->
                        when (iconKey) {
                            "size" -> {
                                sizeList.addAll((iconValue as List<Int>))
                            }
                            "style" -> {
                                styleList.addAll((iconValue as List<String>))
                            }
                        }
                    }
                }

                var fileName = "ic_fluent_" + (it.key).trim().toJavaEnumValueName().toLowerCase(Locale.ENGLISH)

                // special case
                if (it.key == "shifts24h") {
                    fileName = "ic_fluent_shifts_24h"
                }

                sizeList.forEach{ iconSize ->
                    var fileNameSize = fileName + "_" + iconSize
                    styleList.forEach{ iconStyle ->
                        iconNameList.add(fileNameSize + "_" + iconStyle + ".xml")
                    }
                }
            }
        }
    }

    private fun ingestIcons2() {
        File("assets/icons").walk().forEach { iconFile ->
            if (iconFile.extension == "xml" && iconNameList.contains(iconFile.name)) {
                iconFile.copyTo(File(global_flavorPath + "res/drawable/${iconFile.name}"), overwrite = true)
                global_iconsCount++
                iconNameList.remove(iconFile.name)
            }
        }

        var hasError = false
        iconNameList.forEach { name ->
            printWarning("Unable to find file: ${name}")
            hasError = true
        }

        if (hasError) {
           printError("Unable to find icon asset")
        }
    }

}