package com.microsoft.fluentui.persona

import org.junit.Assert
import org.junit.Test

class InitialsDrawableTest {

    companion object {
        private const val NAME = "Lorem Ipsum"
        private const val ONE_WORD_NAME = "Lorem"
        private const val EMPTY_NAME = ""
        private const val NAME_WITH_DIGITS = "Lorem 2 Ipsum"
        private const val NAME_WITH_SPECIAL_CHAR = "_Lorem @ Ipsum"
        private const val EMAIL = "loremipsum@mymail.com"
        private const val EMPTY_EMAIL = ""
    }

    @Test
    fun testGetInitialsHappyPath(){
        val expectedInitials = "LI"
        val resultantInitials = InitialsDrawable.getInitials(NAME, EMAIL)
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsFirstName(){
        val expectedInitials = "L"
        val resultantInitials = InitialsDrawable.getInitials(ONE_WORD_NAME, EMAIL)
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsWithDigits(){
        val expectedInitials = "L2"
        val resultantInitials = InitialsDrawable.getInitials(NAME_WITH_DIGITS, EMAIL)
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsWithSpecialChars(){
        val expectedInitials = "I"
        val resultantInitials = InitialsDrawable.getInitials(NAME_WITH_SPECIAL_CHAR, EMAIL)
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsNoName(){
        val expectedInitials = "L"
        val resultantInitials = InitialsDrawable.getInitials(EMPTY_NAME, EMAIL)
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsNoNameNoEmail(){
        val expectedInitials = "#"
        val resultantInitials = InitialsDrawable.getInitials(EMPTY_NAME, EMPTY_EMAIL)
        Assert.assertEquals(expectedInitials, resultantInitials)
    }
}