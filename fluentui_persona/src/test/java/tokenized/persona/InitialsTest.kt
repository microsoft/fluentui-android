package tokenized.persona

import com.microsoft.fluentui.tokenized.persona.Group
import com.microsoft.fluentui.tokenized.persona.Person
import org.junit.Assert
import org.junit.Test

class InitialsTest {

    companion object {
        private val person = Person("First", "Last", "firstlast@example.com")
        private val firstNamePerson = Person("First", email = "firstlast@example.com")
        private val lastNamePerson = Person("", "Last", email = "firstlast@example.com")
        private val personWithDigit = Person("First", "2nd", "firstthe2nd@example.com")
        private val personWithSpecialChar = Person("_First", "@Last", email = "firstthe2nd@example.com")
        private val noNamePerson = Person("", email = "firstlast@example.com")
        private val noNameNoEmailPerson = Person("", email = "")
        private val group = Group(listOf(person, personWithDigit, personWithSpecialChar, noNamePerson), groupName = "Gang Of 4")
        private val groupNoNameNoEmail = Group(listOf(person, personWithDigit, personWithSpecialChar, noNamePerson), groupName = "")
        private val groupNoName = Group(listOf(person, personWithDigit, personWithSpecialChar, noNamePerson), groupName = "", email = "4@example.com")
        private val groupDigit = Group(listOf(person, personWithDigit, personWithSpecialChar, noNamePerson), groupName = "4")
        private val groupAlphaNumeric = Group(listOf(person, personWithDigit, personWithSpecialChar, noNamePerson), groupName = "4 People")
        private val groupSingleName = Group(listOf(person, personWithDigit, personWithSpecialChar, noNamePerson), groupName = "Four")
    }

    @Test
    fun testGetInitials() {
        val expectedInitials = "FL"
        val resultantInitials = person.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsFirstNameOnly() {
        val expectedInitials = "F"
        val resultantInitials = firstNamePerson.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsLastNameOnly() {
        val expectedInitials = "L"
        val resultantInitials = lastNamePerson.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsWithDigits() {
        val expectedInitials = "FN"
        val resultantInitials = personWithDigit.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsWithSpecialChars() {
        val expectedInitials = "FL"
        val resultantInitials = personWithSpecialChar.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsNoName() {
        val expectedInitials = "F"
        val resultantInitials = noNamePerson.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetInitialsNoNameNoEmail() {
        val expectedInitials = ""
        val resultantInitials = noNameNoEmailPerson.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetGroupInitials() {
        val expectedInitials = "GO"
        val resultantInitials = group.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetGroupInitialsNoNameNoEmail() {
        val expectedInitials = ""
        val resultantInitials = groupNoNameNoEmail.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetGroupInitialsNoName() {
        val expectedInitials = "4"
        val resultantInitials = groupNoName.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetGroupInitialsDigit() {
        val expectedInitials = "4"
        val resultantInitials = groupDigit.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetGroupInitialsAlphaNumeric() {
        val expectedInitials = "4P"
        val resultantInitials = groupAlphaNumeric.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }

    @Test
    fun testGetGroupInitialsSingleName() {
        val expectedInitials = "F"
        val resultantInitials = groupSingleName.getInitials()
        Assert.assertEquals(expectedInitials, resultantInitials)
    }
}