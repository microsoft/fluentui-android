package microsoft.fluentui.generator

import microsoft.fluentui.generator.model.resources.Resource
import microsoft.fluentui.generator.model.resources.ResourceType
import microsoft.fluentui.generator.model.resources.parseBorderWidth
import microsoft.fluentui.generator.model.resources.parseResource
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ParsersTest {

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Parse border width`() {
        Assert.assertEquals(null, parseBorderWidth(null))
        Assert.assertEquals(null, parseBorderWidth("Hello?"))
        Assert.assertEquals(null, parseBorderWidth(arrayListOf("Hello?")))
        Assert.assertEquals(null, parseBorderWidth(ArrayList<String>()))
        Assert.assertEquals("1", parseBorderWidth(arrayListOf(LinkedHashMap<String, String>().apply { put("width", "1") })))
        Assert.assertEquals("", parseBorderWidth(arrayListOf(LinkedHashMap<String, String>().apply { put("width", "") })))
        Assert.assertEquals("1", parseBorderWidth(arrayListOf(LinkedHashMap<String, String>().apply { put("normal", "1") },
            LinkedHashMap<String, String>().apply { put("active", "2") })))
    }

    @Test
    fun `Parse typed dimensions`() {
        testParseTypedPrimitive(emptyList(), mutableListOf(), "name", null)
        testParseTypedPrimitive(emptyList(), mutableListOf(), "name", "1.0 f")
        testParseTypedPrimitive(emptyList(), mutableListOf(), "name", "10 dp")

        testParseTypedPrimitive(listOf(Resource("name", "1", ResourceType.INTEGER)), mutableListOf(), "name", 1)
        testParseTypedPrimitive(listOf(Resource("name", "0", ResourceType.INTEGER)), mutableListOf(), "name", 0)
        testParseTypedPrimitive(listOf(Resource("name", "1", ResourceType.INTEGER)), mutableListOf(), "name", "1".toInt())

        testParseTypedPrimitive(listOf(Resource("name", "1.0", ResourceType.FLOAT)), mutableListOf(), "name", 1.0)
        testParseTypedPrimitive(listOf(Resource("name", "1.0", ResourceType.FLOAT)), mutableListOf(), "name", "1.0")
        testParseTypedPrimitive(listOf(Resource("name", "1.0", ResourceType.FLOAT)), mutableListOf(), "name", (1.0).toDouble())
        testParseTypedPrimitive(listOf(Resource("name", "1.0", ResourceType.FLOAT)), mutableListOf(), "name", "1.0".toDouble())
        testParseTypedPrimitive(listOf(Resource("name", "0.5", ResourceType.FLOAT)), mutableListOf(), "name", "0.5f")
        testParseTypedPrimitive(listOf(Resource("name", ".5", ResourceType.FLOAT)), mutableListOf(), "name", ".5f")
        testParseTypedPrimitive(listOf(Resource("name", "1", ResourceType.FLOAT)), mutableListOf(), "name", "1f")
        testParseTypedPrimitive(listOf(Resource("name", "10", ResourceType.FLOAT)), mutableListOf(), "name", "10f")
        testParseTypedPrimitive(listOf(Resource("name", "12345.54321", ResourceType.FLOAT)), mutableListOf(), "name", "12345.54321f")

        testParseTypedPrimitive(listOf(Resource("name", "5", ResourceType.DIMENSION)), mutableListOf(), "name", "5dp")
        testParseTypedPrimitive(listOf(Resource("name", "0.5", ResourceType.DIMENSION)), mutableListOf(), "name", "0.5dp")
        testParseTypedPrimitive(listOf(Resource("name", ".5", ResourceType.DIMENSION)), mutableListOf(), "name", ".5dp")

        testParseTypedPrimitive(listOf(Resource("name", "#FFFFFF", ResourceType.COLOR)), mutableListOf(), "name", "#FFFFFF")
        testParseTypedPrimitive(listOf(Resource("name", "#33001122", ResourceType.COLOR)), mutableListOf(), "name", "#00112233")
        testParseTypedPrimitive(listOf(Resource("name", "@color/color_red", ResourceType.COLOR)), mutableListOf(), "name", "\$Color.red")
        testParseTypedPrimitive(listOf(Resource("name", "@color/color_red_dark", ResourceType.COLOR)), mutableListOf(), "name", "\$Color.red.dark")

        testParseTypedPrimitive(
            listOf(
                Resource("name_width", "1", ResourceType.DIMENSION),
                Resource("name_height", "2", ResourceType.DIMENSION)
            ), mutableListOf(), "name", "Size(1dp, 2dp)"
        )
        testParseTypedPrimitive(
            listOf(
                Resource("name_top", "1", ResourceType.DIMENSION),
                Resource("name_start", "2", ResourceType.DIMENSION),
                Resource("name_bottom", "3", ResourceType.DIMENSION),
                Resource("name_end", "4", ResourceType.DIMENSION)
            ), mutableListOf(), "name", "Insets(1dp, 2dp, 3dp, 4dp)"
        )

        testParseTypedPrimitive(
            listOf(
                Resource("name_x", "1", ResourceType.DIMENSION),
                Resource("name_y", "2", ResourceType.DIMENSION),
                Resource("name_width", "3", ResourceType.DIMENSION),
                Resource("name_height", "4", ResourceType.DIMENSION)
            ), mutableListOf(), "name", "Rect(1dp, 2dp, 3dp, 4dp)"
        )

        testParseTypedPrimitive(listOf(Resource("name1_name2", "1", ResourceType.DIMENSION)), mutableListOf(), "name1",
            LinkedHashMap<String, String>().apply { put("name2", "1dp") })
        testParseTypedPrimitive(
            listOf(
                Resource("name1_name2", "1", ResourceType.DIMENSION),
                Resource("name1_name3", "2", ResourceType.DIMENSION)
            ), mutableListOf(), "name1",
            LinkedHashMap<String, String>().apply {
                put("name2", "1dp")
                put("name3", "2dp")
            })

        testParseTypedPrimitive(
            listOf(
                Resource("name", "1", ResourceType.INTEGER),
                    Resource("name", "2", ResourceType.INTEGER)
            ), mutableListOf(), "name", ArrayList<Int>().apply {
                add(1)
                add(2)
            }
        )

        testParseTypedPrimitive(
            listOf(
                Resource(
                    "name_iconName",
                    "iconName",
                    ResourceType.STRING
                )
            ), mutableListOf(), "name", "Icon(iconName)"
        )

        testParseTypedPrimitive(
            listOf(
                Resource(
                    "name",
                    "@integer/enumName_EnumValue",
                    ResourceType.INTEGER
                )
            ), mutableListOf(), "name", "Enum(EnumName.EnumValue)"
        )

        testParseTypedPrimitive(
            listOf(
                Resource(
                    "name",
                    "@integer/optionName_OptionValue",
                    ResourceType.INTEGER
                )
            ), mutableListOf(), "name", "Option(OptionName.OptionValue)"
        )
    }


    @Test
    fun `Generate initials`() {
        Assert.assertEquals(null, generateInitials(""))
        Assert.assertEquals("J", generateInitials("john"))
        Assert.assertEquals("JD", generateInitials("john doe"))
        Assert.assertEquals("JD", generateInitials("John Doe"))
        Assert.assertEquals("JD", generateInitials("john A B doe"))
        Assert.assertEquals("JD", generateInitials("John Doe (Software Engineer)"))
        Assert.assertEquals("JD", generateInitials("John Doe {Software Engineer}"))
        Assert.assertEquals("JD", generateInitials("John Doe [Software Engineer]"))
    }


    private fun testParseTypedPrimitive(expected: List<Resource>, resourceList: MutableList<Resource>, resKey: String, values: Any?) {
        parseResource(resourceList, resKey, values)

        if (expected.isNotEmpty()) Assert.assertTrue(resourceList.containsAll(expected))
        else Assert.assertTrue(resourceList.isEmpty())
    }

    // This is annoying, the actual function is in the AvatarView template so there's no way to access it
    // from the tests... we'll figure it out
    private fun generateInitials(name: String?): String? = if (name != null && name.isNotBlank()) {
        val words = name.replace("[(,{,\\[].*[),},\\]]".toRegex(), "").trim().split(" ")

        when (words.size) {
            0 -> null
            1 -> words.first()[0]
            else -> words.first()[0].plus(words.last()[0].toString())
        }?.toString()!!.toUpperCase()
    } else null
}
