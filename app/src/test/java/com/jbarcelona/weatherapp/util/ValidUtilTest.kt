package com.jbarcelona.weatherapp.util


import junit.framework.TestCase.assertEquals
import org.junit.Test

class ValidUtilTest {

    @Test
    fun isEmpty() {
        val result = ValidUtil.isEmpty("")
        assertEquals(true, result)
    }

    @Test
    fun isValidEmail() {
        val result = ValidUtil.isValidEmail("abc@test.com")
        assertEquals(true, result)
    }

    @Test
    fun isInvalidEmail() {
        val result = !ValidUtil.isValidEmail("abc@test.")
        assertEquals(true, result)
    }

    @Test
    fun isValidPassword() {
        val result = ValidUtil.isValidPassword("Abcd1234!")
        assertEquals(true, result)
    }

    @Test
    fun isInvalidPassword() {
        val result = !ValidUtil.isValidPassword("12@")
        assertEquals(true, result)
    }
}