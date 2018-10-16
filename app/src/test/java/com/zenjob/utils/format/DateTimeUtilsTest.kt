package com.zenjob.utils.format

import android.app.Application
import com.zenjob.BuildConfig
import com.zenjob.utils.AppConstants
import com.zenjob.utils.AppConstants.Companion.EMPTY
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(constants = BuildConfig::class, sdk = [21], application = Application::class)
class DateTimeUtilsTest {

    @Test
    fun testFormatDateNull() {
        val output = DateTimeUtils.formatDate(null)
        Assert.assertEquals(AppConstants.EMPTY, output)
    }

    @Test
    fun testFormatDateEmpty() {
        val output = DateTimeUtils.formatDate(EMPTY)
        Assert.assertEquals(EMPTY, output)
    }

    @Test
    fun testFormatDate() {
        val output = DateTimeUtils.formatDate("2001-12-22T12:23:33+0200")
        Assert.assertEquals("22.12.", output)
    }

    @Test
    fun testFormatTimeNull() {
        val output = DateTimeUtils.formatTime(null, null)
        Assert.assertEquals(AppConstants.EMPTY, output)
    }

    @Test
    fun testFormatTimeEmpty() {
        val output = DateTimeUtils.formatTime(EMPTY, EMPTY)
        Assert.assertEquals(EMPTY, output)
    }

    @Test
    fun testFormatTime() {
        val output = DateTimeUtils.formatTime("2001-12-22T12:23:33+0200", "2001-12-22T14:17:33+0000")
        Assert.assertNotEquals(null, output)
        Assert.assertNotEquals(EMPTY, output)
        Assert.assertEquals(11, output.length)
    }

}