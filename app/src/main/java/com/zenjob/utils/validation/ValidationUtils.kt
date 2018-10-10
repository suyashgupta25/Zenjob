package com.zenjob.utils.validation

import android.text.TextUtils
import android.util.Patterns

object ValidationUtils {

    @JvmStatic
    fun isValidEmail(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    @JvmStatic
    fun isValidPassword(target: String?): Boolean {
        return !TextUtils.isEmpty(target) && target!!.length > 5
    }

}