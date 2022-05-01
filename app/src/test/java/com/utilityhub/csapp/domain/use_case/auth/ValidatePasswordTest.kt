package com.utilityhub.csapp.domain.use_case.auth

import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class ValidatePasswordTest {

    private lateinit var validatePassword: ValidatePassword

    @Before
    fun setUp() {
        validatePassword = ValidatePassword()
    }

    @Test
    fun `Password has less than 6 chars, returns error`() {
        val response = validatePassword("1234")

        assertEquals(response.isValid, false)
    }

    @Test
    fun `Password is blank, returns error`() {
        val response = validatePassword("")

        assertEquals(response.isValid, false)
    }

    @Test
    fun `Password is correct, returns true`() {
        val response = validatePassword("123456")

        assertEquals(response.isValid, true)
    }
}