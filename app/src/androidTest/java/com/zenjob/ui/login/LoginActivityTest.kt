package com.zenjob.ui.login

import android.support.test.espresso.Espresso
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.filters.LargeTest
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import com.zenjob.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class LoginActivityTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule<LoginActivity>(LoginActivity::class.java)

    @Test
    fun testLoginSuccess() {
        Espresso.onView(ViewMatchers.withId(R.id.et_login_email)).perform(replaceText("suyash.gupta25@gmail.com"))
        Espresso.onView(ViewMatchers.withId(R.id.et_login_password)).perform(replaceText("testing12345"))
        Espresso.onView(ViewMatchers.withId(R.id.btn_login_submit)).perform(click())
        Thread.sleep(5000)
        Espresso.onView(ViewMatchers.withId(R.id.rv_offers)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}