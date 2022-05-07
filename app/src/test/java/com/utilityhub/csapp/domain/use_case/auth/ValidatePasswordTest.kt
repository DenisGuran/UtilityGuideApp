package com.utilityhub.csapp.domain.use_case.auth

import com.utilityhub.csapp.R
import org.junit.Assert.assertEquals
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
        assertEquals(response.errorMessage!!.resId, R.string.min_length_password_error)
    }

    @Test
    fun `Password is blank, returns error`() {
        val response = validatePassword("")

        assertEquals(response.isValid, false)
        assertEquals(response.errorMessage!!.resId, R.string.required)
    }

    @Test
    fun `Password is correct, returns true`() {
        val response = validatePassword("123456")

        assertEquals(response.isValid, true)
        assertEquals(response.errorMessage!!.resId, R.string.null_string)
    }
}