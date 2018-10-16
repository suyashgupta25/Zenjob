package com.zenjob.utils.validation

import android.app.Application
import com.zenjob.BuildConfig
import com.zenjob.utils.validation.ValidationUtils.isValidEmail
import com.zenjob.utils.validation.ValidationUtils.isValidPassword
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], application = Application::class)
class ValidationUtilsTest {

    @Test
    fun testIsValidEmailNull() {
        assertEquals(false, isValidEmail(null))
    }

    @Test
    fun testIsValidEmailEmpty() {
        assertEquals(false, isValidEmail(""))
    }

    @Test
    fun testIsValidEmailRandom() {
        assertEquals(false, isValidEmail("wedne8q3kdwa932rj"))
    }

    @Test
    fun testIsValidEmail() {
        assertEquals(true, isValidEmail("sfd@sdsd.dsd"))
    }

    @Test
    fun testIsValidPasswordNull() {
        assertEquals(false, isValidPassword(null))
    }

    @Test
    fun testIsValidPasswordEmpty() {
        assertEquals(false, isValidPassword(""))
    }

    @Test
    fun testIsValidPasswordRandom() {
        assertEquals(false, isValidPassword("qw32"))
    }

    @Test
    fun testIsValidPassword() {
        assertEquals(true, isValidPassword("qgvg65@w32"))
    }
}