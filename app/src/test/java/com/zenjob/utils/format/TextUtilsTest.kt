package com.zenjob.utils.format

import android.app.Application
import com.zenjob.BuildConfig
import com.zenjob.utils.AppConstants.Companion.EMPTY
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], application = Application::class)
class TextUtilsTest {

    private val euro = "\u20ac"

    @Test
    fun testFormatTextForCurrencyNull() {
        val formatTextForCurrency = TextUtils.formatTextForCurrency(null)
        Assert.assertEquals(EMPTY, formatTextForCurrency)
    }

    @Test
    fun testFormatTextForCurrencyEmpty() {
        val formatTextForCurrency = TextUtils.formatTextForCurrency(EMPTY)
        Assert.assertEquals(EMPTY, formatTextForCurrency)
    }

    @Test
    fun testFormatTextForCurrency() {
        val formatTextForCurrency = TextUtils.formatTextForCurrency("233 EUR")
        Assert.assertEquals("233 " + euro, formatTextForCurrency)
    }

}