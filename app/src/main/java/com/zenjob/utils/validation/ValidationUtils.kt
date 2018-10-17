package com.zenjob.utils.validation

import android.text.TextUtils
import android.util.Patterns
import com.zenjob.utils.AppConstants.Companion.MIN_LENGTH_PASSWORD

object ValidationUtils {

    @JvmStatic
    fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    @JvmStatic
    fun isValidPassword(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && target!!.length > MIN_LENGTH_PASSWORD
    }

}