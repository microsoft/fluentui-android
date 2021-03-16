package com.microsoft.fluentui.generator

import com.microsoft.fluentui.generator.model.FluentUIDocument
import com.microsoft.fluentui.generator.model.TextStyles
import com.microsoft.fluentui.generator.model.TypographyGenerator
import com.microsoft.fluentui.generator.model.resources.*
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.yaml.snakeyaml.Yaml
import java.io.File


class ResourcesTest {
    @Before
    fun setup() {
        global_projectRoot = com.microsoft.fluentui.generator.testProjectPath
        global_documentType = DocumentType.ROOT
        configFlavor("coconut")
        File(global_flavorPath).deleteRecursively()
    }

    @After
    fun tearDown() {
        File(global_flavorPath).deleteRecursively()
    }

    @Test
    fun `Resources generation`() {
        testResources(listOf(Resource("CAPITAL", "1", ResourceType.DIMENSION)))
        testResources(listOf(Resource("CamelCase", "1", ResourceType.DIMENSION)))
        testResources(listOf(Resource("", "1", ResourceType.DIMENSION)))
        testResources(listOf(Resource("myDimen", "", ResourceType.DIMENSION)))
        testResources(listOf(Resource("", "", ResourceType.DIMENSION)))

        testResources(listOf(Resource("myBool", "true", ResourceType.BOOLEAN)))
        testResources(listOf(Resource("myBool", "false", ResourceType.BOOLEAN)))
        testResources(listOf(Resource("myColor", "#FFFFFF", ResourceType.COLOR)))
        testResources(listOf(Resource("myDimen", "1", ResourceType.DIMENSION)))
        testResources(listOf(Resource("myInt", "0", ResourceType.INTEGER)))
        testResources(listOf(Resource("myFloat", "3.5", ResourceType.FLOAT)))
        testResources(listOf(Resource("myFontSize", "10", ResourceType.TYPOGRAPHY)))
        testResources(
            listOf(
                Resource(
                    "myTextStyle",
                    "\$Typography.medium.normal".autoResolveReference(),
                    ResourceType.STYLE
                )
            )
        )

        testResources(emptyList())
        testResources(
            listOf(
                Resource("myColor", "#FFFFFF", ResourceType.COLOR),
                Resource("myDimen", "1", ResourceType.DIMENSION),
                Resource("myBool", "true", ResourceType.BOOLEAN),
                Resource("myInt", "0", ResourceType.INTEGER),
                Resource("myFontSize", "10", ResourceType.TYPOGRAPHY)
            )
        )
    }

    @Test
    fun `Colors generation`() {
        testSingleColors(listOf(Triple("black", "#000000", true)))
        testSingleColors(listOf(Triple("white", "#FFFFFF", true)))
        testSingleColors(listOf(Triple("white", "#ffffff", true)))
        testSingleColors(listOf(Triple("black", "#00000000", true)))
        testSingleColors(listOf(Triple("black", "000000", false)))
        testSingleColors(listOf(Triple("black", "#000", false)))
        testSingleColors(listOf(Triple("black", "#0000000000", false)))
        testSingleColors(listOf(Triple("black", "#zzzzzz", false)))
        testSingleColors(listOf(Triple("black", "black", false)))
        testSingleColors(
            listOf(
                Triple("black", "#000000", true),
                Triple("white", "#FFFFFF", true)
            )
        )
        testSingleColors(
            listOf(
                Triple("black", "#000000", false),
                Triple("black", "#FFFFFF", true)
            )
        )
        testSingleColors(
            listOf(
                Triple("black", "#000000", false),
                Triple("black", "#F", false)
            )
        )


        testColorList("black", listOf(Triple("normal", "#000000", true)))
        testColorList(
            "black", listOf(
                Triple("normal", "#000000", true),
                Triple("white", "#FFFFFF", true)
            )
        )
        testColorList(
            "black", listOf(
                Triple("normal", "#000000", true),
                Triple("normal", "#FFFFFF", false)
            )
        )
        testColorList(
            "black", listOf(
                Triple("normal", "#000000", true),
                Triple("normal", "#F", false)
            )
        )
    }

    @Test
    fun `Color selectors`() {
        testColorSelector("[rest: \"#000000\"]", true)

        testColorSelector(
            "[rest: \$Colors.Neutral.black, hover: \$Colors.Neutral.black, pressed: \$Colors.Neutral.black, selected: \$Colors.Neutral.black, disabled: \$Colors.Neutral.black]",
            true
        )

        testColorSelector(
            "[rest: \"#000000\", hover: \"#000000\", pressed: \"#000000\", selected: \"#000000\", disabled: \"#000000\"]",
            true
        )
    }

    @Test
    fun `Typography generation`() {
        // FIXME when we develop for Vnext Avatar
//        testTypographyList("medium", listOf(Triple("normal", "Font(System, 14)", 14)))
//        testTypographyList(
//                "small", listOf(
//                Triple("normal", "Font(System, 12)", 12),
//                Triple("bold", "Font(System-bold, 12)", 12)
//        )
//        )
//        testTypographyList(
//                "large", listOf(
//                Triple("light", "Font(System-ultraLight, 18)", 18),
//                Triple("semilight", "Font(System-light, 18)", 18),
//                Triple("normal", "Font(System, 18)", 18),
//                Triple("semibold", "Font(System-semibold, 18)", 18),
//                Triple("bold", "Font(System-bold, 18)", 18),
//                Triple("italic", "Font(SystemItalic, 18)", 18)
//        )
//        )
    }

    @Test
    fun `TextStyles generation`() {
        // Fixme when we develop for Avatar Vnext
//        configFlavor("main")
//        var document = "Typography:\n"
//        document += " textStyles: ["
//        TextStyles.values()
//            .forEach { document += "${it.name.toLowerCase()}: \"Font(PreferredSystem, ${it.name.toLowerCase()})\"," }
//        document += "]"
//
//        Yaml().loadAs(document, FluentUIDocument::class.java).generate("it.path")
//
//        val outputfile = File(
//            "$global_flavorPath/res/values/${ResourceType.TYPOGRAPHY.toString()
//                .toLowerCase()}_res.autogenerated.xml"
//        )
//        Assert.assertTrue(outputfile.exists())
//
//        outputfile.readText().let { content ->
//            TextStyles.values().forEach {
//                Assert.assertTrue(content.contains("<dimen name=\"font_size_${it.name.toLowerCase()}\">${it.fontSize}sp</dimen>"))
//            }
//        }
//
//        val stylesFile = File(
//            "$global_flavorPath/res/values/${ResourceType.TYPOGRAPHY.toString()
//                .toLowerCase()}_styles.autogenerated.xml"
//        )
//        Assert.assertTrue(stylesFile.exists())
//
//        stylesFile.readText().let { content ->
//            TextStyles.values().forEach {
//                var style = "\t<style name=\"typography_textStyles_${it.name.toLowerCase()}\">\n"
//                style += "\t\t<item name=\"android:${StyleKeys.TEXT_SIZE.androidKey}\">@dimen/font_size_${it.name.toLowerCase()}</item>\n"
//                style =
//                    "\t\t<item name=\"android:${StyleKeys.TEXT_STYLE.androidKey}\">${TypographyGenerator.convertTextStyle(
//                        it.fontFamily
//                    )}</item>\n"
//                style += "\t</style>"
//
//                Assert.assertTrue(content.contains(style))
//            }
//        }
    }

    @Test
    fun `TextStyle conversion`() {
        Assert.assertEquals("bold", TypographyGenerator.convertTextStyle("bold"))
        Assert.assertEquals("normal", TypographyGenerator.convertTextStyle("semibold"))
        Assert.assertEquals("italic", TypographyGenerator.convertTextStyle("italic"))
        Assert.assertEquals("normal", TypographyGenerator.convertTextStyle("normal"))
    }

    @Test
    fun `Global metrics generation and resolution`() {
        configFlavor("main")
        val document = "Metric:\n floatMetric: 5f\n dimenMetric: 5dp\n intMetric: 5\n" +
                "AnotherNode:\n dimensRef: \$Metric.dimenMetric\n floatRef: \$Metric.floatMetric\n intRef: \$Metric.intMetric"

        Yaml().loadAs(document, FluentUIDocument::class.java).generate("it.path")

        val allResFile = File("$global_flavorPath/res/values/all_res.autogenerated.xml")
        Assert.assertTrue(allResFile.exists())

        allResFile.readText().let { content ->
            Assert.assertTrue(content.contains("<item name=\"metric_floatMetric\" format=\"float\" type=\"dimen\">5</item>"))
            Assert.assertTrue(content.contains("<integer name=\"metric_intMetric\">5</integer>"))
            Assert.assertTrue(content.contains("<item name=\"anothernode_floatRef\" format=\"float\" type=\"dimen\">@dimen/metric_floatMetric</item>"))
        }

        val dimensFile = File("$global_flavorPath/res/values/dimens_res.autogenerated.xml")
        Assert.assertTrue(dimensFile.exists())

        dimensFile.readText().let { content ->
            Assert.assertTrue(content.contains("<dimen name=\"metric_dimenMetric\">5dp</dimen>"))
            Assert.assertTrue(content.contains("<dimen name=\"anothernode_dimensRef\">@dimen/metric_dimenMetric</dimen>"))
        }


    }

    @Test
    fun `Global enums code generation`() {
        configFlavor("main")
        val document = "Enums:\n EnumName: EnumDef(value1, value2, value3)"

        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)
        doc.generate("it.path")

        val outputfile = File("$global_flavorPath/java/com/microsoft/fluentui/generator/Enums.kt")
        Assert.assertTrue(outputfile.exists())

        val content = outputfile.readText()
        Assert.assertTrue(content.contains("enum class EnumName (val value: Int) {"))
        Assert.assertTrue(content.contains("VALUE_1(0),"))
        Assert.assertTrue(content.contains("VALUE_2(1),"))
        Assert.assertTrue(content.contains("VALUE_3(2),"))
    }

    @Test
    fun `Global masks code generation`() {
        configFlavor("main")
        val document = "Options:\n OptionName: OptionDef(value1, value2, value3, value4, value5)"

        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)
        doc.generate("it.path")

        val outputfile = File("$global_flavorPath/java/com/microsoft/fluentui/generator/Enums.kt")
        Assert.assertTrue(outputfile.exists())

        val content = outputfile.readText()
        Assert.assertTrue(content.contains("enum class OptionName (val value: Int) {"))
        Assert.assertTrue(content.contains("VALUE_1(1),"))
        Assert.assertTrue(content.contains("VALUE_2(2),"))
        Assert.assertTrue(content.contains("VALUE_3(4),"))
        Assert.assertTrue(content.contains("VALUE_4(8),"))
        Assert.assertTrue(content.contains("VALUE_5(16),"))
    }

    @Test
    fun `Enums and Masks generation`() {
        testEnumsAndMasks("name", emptyList())
        testEnumsAndMasks("name", listOf("value1"))
        testEnumsAndMasks("name", listOf("value1", "value2"))
        testEnumsAndMasks("name", listOf("value1", "value2", "value3"))
        testEnumsAndMasks("name", listOf("value1", "value2", "value3", "value4"))
    }

    @Test
    fun `Animators generation`() {
        val document = "AN_Animator:\n" +
                "  rotateTest:\n" +
                "    keyFrames: [{relativeStartTime: 0f, animationValues: [{type: rotate, from: 0f, to: 360.0f}]}]\n" +
                "    repeatCount: -1\n" +
                "    duration: 100\n" +
                "    delay: 0\n" +
                "  colorTest:\n" +
                "    keyFrames: [{relativeStartTime: 0f, animationValues: [{type: color, from: \$Color.white.normal, to: \$Color.black.normal}]}]\n" +
                "    repeatCount: -1\n" +
                "    duration: 100\n" +
                "    delay: 0\n" +
                "  scaleTest:\n" +
                "    keyFrames: [{relativeStartTime: 0f, animationValues: [{type: scale, from: 0f, to: 1f}]}]\n" +
                "    repeatCount: -1\n" +
                "    duration: 100\n" +
                "    delay: 0"


        Yaml().loadAs(document, FluentUIDocument::class.java).generate("it.path")

        val outputfile = File("$global_flavorPath/res/animator/rotatetest.xml")
        Assert.assertTrue(outputfile.exists())
        outputfile.readText().let { content ->
            Assert.assertTrue(
                content.contains(
                    "<objectAnimator xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                            "\tandroid:propertyName=\"rotation\"\n" +
                            "\tandroid:valueFrom=\"0f\"\n" +
                            "\tandroid:valueTo=\"360.0f\"\n" +
                            "\tandroid:repeatCount=\"-1\"\n" +
                            "\tandroid:duration=\"100\"\n" +
                            "\tandroid:startOffset=\"0\"\n" +
                            "/>"
                )
            )
        }
        val outputfile2 = File("$global_flavorPath/res/animator/colortest.xml")
        Assert.assertTrue(outputfile2.exists())
        outputfile2.readText().let { content ->
            Assert.assertTrue(
                content.contains(
                    "<objectAnimator xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                            "\tandroid:propertyName=\"color\"\n" +
                            "\tandroid:valueFrom=\"@color/color_white_normal\"\n" +
                            "\tandroid:valueTo=\"@color/color_black_normal\"\n" +
                            "\tandroid:repeatCount=\"-1\"\n" +
                            "\tandroid:duration=\"100\"\n" +
                            "\tandroid:startOffset=\"0\"\n" +
                            "/>"
                )
            )
        }
        val outputfile3 = File("$global_flavorPath/res/animator/scaletest.xml")
        Assert.assertTrue(outputfile3.exists())
        outputfile3.readText().let { content ->
            Assert.assertTrue(
                content.contains(
                    "<objectAnimator xmlns:android=\"http://schemas.android.com/apk/res/android\"\n" +
                            "\tandroid:propertyName=\"scaleX\"\n" +
                            "\tandroid:valueFrom=\"0f\"\n" +
                            "\tandroid:valueTo=\"1f\"\n" +
                            "\tandroid:repeatCount=\"-1\"\n" +
                            "\tandroid:duration=\"100\"\n" +
                            "\tandroid:startOffset=\"0\"\n" +
                            "/>"
                )
            )
        }
        val animatorsEnumFile =
            File("$global_projectSourcePath/main/java/com/microsoft/fluentui/generator/Animators.kt")
        Assert.assertTrue(animatorsEnumFile.exists())
        animatorsEnumFile.readText().let { content ->
            Assert.assertTrue(content.contains("ROTATETEST"))
            Assert.assertTrue(content.contains("SCALETEST"))
            Assert.assertTrue(content.contains("COLORTEST"))
        }
    }

    @Test
    fun `Interpolators generation`() {
        testInterpolators("test", listOf("1.0", "0.0", "0.78", "1.00"))
        testInterpolators("test2", listOf("0.0", "0.0", "0.0", "0.0"))
        testInterpolators("test3", listOf("0.0", "0.0", "0.0", "0.0", "1.0"))
        testInterpolators("test4", listOf("1.0", "0.0", "0.78"))
    }

    @Test
    fun `Compile-time expression recognition`() {
        Assert.assertFalse(isResourceExpressionModifier("whatever"))
        Assert.assertFalse(isResourceExpressionModifier("random-key"))
        Assert.assertFalse(isResourceExpressionModifier("rs=>sadf"))
        Assert.assertFalse(isResourceExpressionModifier("s"))
        Assert.assertFalse(isResourceExpressionModifier("="))
        Assert.assertFalse(isResourceExpressionModifier(""))
        Assert.assertFalse(isResourceExpressionModifier("default"))

        Assert.assertTrue(isResourceExpressionModifier("platform=ios"))
        Assert.assertTrue(isResourceExpressionModifier("platform = ios"))
        Assert.assertTrue(isResourceExpressionModifier("platform = iOS"))
        Assert.assertTrue(isResourceExpressionModifier("platform=android"))
        Assert.assertTrue(isResourceExpressionModifier("platform = android"))
        Assert.assertTrue(isResourceExpressionModifier("platform = Android"))
        Assert.assertTrue(isResourceExpressionModifier("  platform = android "))
        Assert.assertTrue(isResourceExpressionModifier("  platform =android "))
        Assert.assertTrue(isResourceExpressionModifier("random=key"))
        Assert.assertTrue(isResourceExpressionModifier("random = key"))
        Assert.assertTrue(isResourceExpressionModifier(" random >= key"))
        Assert.assertTrue(isResourceExpressionModifier(" random <key"))
    }

    @Test
    fun `Compile-time expression resolving`() {
        // test recognized expressions
        Assert.assertFalse(resolveResourceExpression("platform=ios"))
        Assert.assertFalse(resolveResourceExpression("platform = ios"))
        Assert.assertFalse(resolveResourceExpression("platform = iOS"))
        Assert.assertFalse(resolveResourceExpression("platform = windows"))
        Assert.assertFalse(resolveResourceExpression("platform != android"))
        Assert.assertTrue(resolveResourceExpression("platform=android"))
        Assert.assertTrue(resolveResourceExpression("platform = android"))
        Assert.assertTrue(resolveResourceExpression("platform = Android"))
        Assert.assertTrue(resolveResourceExpression("  platform = android "))
        Assert.assertTrue(resolveResourceExpression("  platform =android "))
        Assert.assertTrue(resolveResourceExpression("platform != ios "))

        // test unrecognized expressions - those should fail
        Assert.assertFalse(resolveResourceExpression("random=key"))
        Assert.assertFalse(resolveResourceExpression("random = key"))
        Assert.assertFalse(resolveResourceExpression(" random >= key"))
        Assert.assertFalse(resolveResourceExpression(" random <key"))

        // test default (expression fallback)
        Assert.assertTrue(resolveResourceExpression("default"))
    }


    private fun testInterpolators(key: String, values: List<String>) {
        generateInterpolator(
            key,
            "TimingFunction(" + values.joinToString(separator = "f,", postfix = "f)")
        )

        val outputFile = File("$global_flavorPath/res/anim-v21/${key.toLowerCase()}.xml")
        Assert.assertEquals(values.size == 4, outputFile.exists())
        if (outputFile.exists()) {
            outputFile.readText().let { content ->
                Assert.assertTrue(content.contains("android:controlX1=\"${values[0]}\""))
                Assert.assertTrue(content.contains("android:controlY1=\"${values[1]}\""))
                Assert.assertTrue(content.contains("android:controlX2=\"${values[2]}\""))
                Assert.assertTrue(content.contains("android:controlY2=\"${values[3]}\""))
            }
        }
        outputFile.delete()
        val sourceFile =
            File(global_projectSourcePath.plus("main/java/com/microsoft/fleuntui/generator/Interpolators.kt"))
        if (values.size == 4 && sourceFile.exists()) {
            sourceFile.readText().let { content ->
                val interpolatorName =
                    "fluentUIInterpolator${key.substringAfterLast("_").capitalize()}"
                Assert.assertTrue(
                    content.contains(
                        "val $interpolatorName = PathInterpolatorCompat.create" +
                                values.joinToString(
                                    separator = "f,",
                                    prefix = "(",
                                    postfix = "f)\n"
                                )
                    )
                )
            }
        }
    }

    private fun testEnumsAndMasks(key: String, values: List<String>) {
        testEnumsMasks(true, key, values)
        testEnumsMasks(false, key, values)
    }


    private fun testEnumsMasks(isEnum: Boolean, key: String, values: List<String>) {
        configFlavor("main")
        val document =
            "root:\n $key: ${if (isEnum) "EnumDef" else "OptionDef"}${values.joinToString(
                separator = ",",
                prefix = "(",
                postfix = ")"
            )}"


        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)
        doc.generate()

        val outputfile = File("$global_flavorPath/res/values/enums_res.autogenerated.xml")
        Assert.assertEquals(values.isNotEmpty(), outputfile.exists())

        if (values.isNotEmpty()) {
            val content = outputfile.readText()
            values.forEachIndexed { index, s ->
                Assert.assertTrue(
                    content.contains(
                        "<integer name=\"${key}_$s\">" +
                                "${if (isEnum) index else 1 shl index}" +
                                "</integer>", ignoreCase = true
                    )
                )
            }
        }
    }


    private fun testResources(resources: List<Resource>) {
        generateResources(global_flavorPath, "myresource", resources)

        val outputFile =
            File(global_flavorPath.plus(("res/values/myresource_res.autogenerated.xml")))
        Assert.assertEquals(resources.isNotEmpty(), outputFile.exists())

        if (resources.isNotEmpty()) {
            val content = outputFile.readText()
            resources.forEach {
                if (it.name.isNotBlank() && it.value.isNotBlank()) {
                    when (it.type) {
                        ResourceType.BOOLEAN -> Assert.assertTrue(content.contains("<bool name=\"${it.name}\">${it.value}</bool>"))
                        ResourceType.COLOR -> Assert.assertTrue(content.contains("<color name=\"${it.name}\">${it.value}</color>"))
                        ResourceType.DIMENSION -> Assert.assertTrue(content.contains("<dimen name=\"${it.name}\">${it.value}dp</dimen>"))
                        ResourceType.FLOAT -> Assert.assertTrue(content.contains("<item name=\"${it.name}\" format=\"float\" type=\"dimen\">${it.value}</item>"))
                        ResourceType.INTEGER -> Assert.assertTrue(content.contains("<integer name=\"${it.name}\">${it.value}</integer>"))
                        ResourceType.TYPOGRAPHY -> Assert.assertTrue(content.contains("<dimen name=\"${it.name}\">${it.value}sp</dimen>"))
                        ResourceType.STYLE -> Assert.assertTrue(content.contains("<style parent=\"${it.value}\" name=\"${it.name}\"/>"))
                        else -> Unit
                    }
                }
            }
        }
        outputFile.delete()
    }


    private fun testSingleColors(colorList: List<Triple<String, String, Boolean>>) {
        configFlavor("main")
        var document = "Color:\n"
        colorList.forEach { document += " ${it.first}: \"${it.second}\"\n" }

        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)
        doc.generate()

        val outputfile = File("$global_flavorPath/res/values/colors_res.autogenerated.xml")
        Assert.assertTrue(outputfile.exists())

        val content = outputfile.readText()
        colorList.forEach {
            Assert.assertEquals(
                it.third,
                content.contains(
                    "<color name=\"Color_${it.first}\">${it.second}</color>",
                    ignoreCase = true
                )
            )
        }
    }

    private fun testColorList(colorName: String, colorList: List<Triple<String, String, Boolean>>) {
        configFlavor("main")
        var document = "Color:\n"
        document += " $colorName: ["
        colorList.forEach { document += "${it.first}: \"${it.second}\"," }
        document += "]"

        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)
        doc.generate()

        val outputfile = File("$global_flavorPath/res/values/colors_res.autogenerated.xml")
        Assert.assertTrue(outputfile.exists())

        val content = outputfile.readText()
        colorList.forEach {
            Assert.assertEquals(
                it.third,
                content.contains(
                    "<color name=\"Color_${colorName}_${it.first}\">${it.second}</color>",
                    ignoreCase = true
                )
            )
        }
    }

    private fun testColorSelector(content: String, expected: Boolean) {
        val document = "${AP_PREFIX}ButtonTokens:\n test: $content"
        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)

        val value = (doc["${AP_PREFIX}ButtonTokens"] as LinkedHashMap<String, Any>)["test"] as ArrayList<LinkedHashMap<*, *>>
        Assert.assertEquals(expected, isColorSelector(value))

        if (expected) {
            configFlavor("main")
            doc.generate()
            val outputfile = File(global_flavorPath.plus("res/color/buttontokensview_test.xml"))
            Assert.assertTrue(outputfile.exists())

            val output = outputfile.readText()

            value.forEach {
                val colorValue = it.values.single().toString().autoResolveReference()
                when (it.keys.single().toString()) {
                    "rest" -> Assert.assertTrue(output.contains("<item android:color="))
                    "selected" -> Assert.assertTrue(output.contains("android:state_activated=\"true\""))
                    "pressed" -> Assert.assertTrue(output.contains("android:state_pressed=\"true\""))
                    "disabled" -> Assert.assertTrue(output.contains("android:state_enabled=\"false\""))
                }
            }




        }
    }

    private fun testTypographyList(name: String, list: List<Triple<String, String, Int>>) {
        configFlavor("main")
        var document = "Typography:\n"
        document += " $name: ["
        list.forEach { document += "${it.first}: \"${it.second}\"," }
        document += "]"

        val doc = Yaml().loadAs(document, FluentUIDocument::class.java)
        doc.generate()

        val outputfile = File(
            "$global_flavorPath/res/values/${ResourceType.TYPOGRAPHY.toString()
                .toLowerCase()}_res.autogenerated.xml"
        )
        Assert.assertTrue(outputfile.exists())

        val content = outputfile.readText()
        list.forEach {
            Assert.assertEquals(
                true,
                content.contains(
                    "<dimen name=\"font_size_${name}\">${it.third}sp</dimen>",
                    ignoreCase = true
                )
            )
        }
    }


}
