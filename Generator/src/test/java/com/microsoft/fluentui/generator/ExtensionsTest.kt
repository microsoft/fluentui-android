package com.microsoft.fluentui.generator

import com.microsoft.fluentui.generator.model.Attribute
import com.microsoft.fluentui.generator.model.AttributeType
import com.microsoft.fluentui.generator.model.resources.Resource
import com.microsoft.fluentui.generator.model.resources.ResourceType
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test


class ExtensionsTest {

    @Before
    fun setup() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Color reference`() {
        Assert.assertEquals("#33001122", "#00112233".autoResolveReference())
        Assert.assertEquals("#FFFFFF", "#FFFFFF".autoResolveReference())
        Assert.assertEquals("@color/color_red", "\$Color.red".autoResolveReference())
        Assert.assertEquals("@color/color_red_dark", "\$Color.red.dark".autoResolveReference())
        Assert.assertEquals("@color/emphasiscolor_red", "\$EmphasisColor.red".autoResolveReference())
        Assert.assertEquals("\$Emphasis.red", "\$Emphasis.red".autoResolveReference())
    }

    @Test
    fun `Prepend camelcase`() {
        Assert.assertEquals("test", "test".prependCamelCase(""))
        Assert.assertEquals("prefixTest", "test".prependCamelCase("prefix"))
    }

    @Test
    fun `Valid color`() {
        Assert.assertEquals(true, "#FFFFFF".isValidColor())
        Assert.assertEquals(true, "#FFFFFFFF".isValidColor())
        Assert.assertEquals(true, "#ffffff".isValidColor())
        Assert.assertEquals(true, "#ffffffff".isValidColor())
        Assert.assertEquals(true, "@color/red".isValidColor())
        Assert.assertEquals(true, "@color/red_dark".isValidColor())
        Assert.assertEquals(false, "@dimen/red_dark".isValidColor())
        Assert.assertEquals(false, "hello".isValidColor())
        Assert.assertEquals(false, "FFFFFFF".isValidColor())
        Assert.assertEquals(false, "\$Color.red".isValidColor())
    }

    @Test
    fun `Attributes list`() {
        testAttributeList(mutableListOf(), mutableListOf())
        testAttributeList(
            mutableListOf(
                Attribute("boolean", AttributeType.BOOLEAN),
                Attribute("color", AttributeType.COLOR),
                Attribute("dimension", AttributeType.DIMENSION),
                Attribute("integer", AttributeType.STRING),
                Attribute("typography", AttributeType.DIMENSION)
            ),
            mutableListOf(
                Resource("boolean", "true", ResourceType.BOOLEAN),
                Resource("color", "value", ResourceType.COLOR),
                Resource("dimension", "value", ResourceType.DIMENSION),
                Resource("integer", "value", ResourceType.INTEGER),
                Resource("typography", "value", ResourceType.TYPOGRAPHY)
            )
        )
    }

    private fun testAttributeList(expected: MutableList<Attribute>, input: MutableList<Resource>) {
        val result = input.toAttributesList()
        Assert.assertEquals(expected.isEmpty(), result.isEmpty())
        Assert.assertTrue(expected.equals(result))
    }
}
