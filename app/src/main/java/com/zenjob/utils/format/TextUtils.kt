package com.zenjob.utils.format

import com.zenjob.utils.AppConstants.Companion.EMPTY

object TextUtils {

    private const val euro = "\u20ac"


    @JvmStatic
    fun formatTextForCurrency(value: String?): String {
        value?.let {
            if (value.contains("EUR", ignoreCase = true)) {
                return value.replace("EUR", euro)
            } else {
                return value
            }
        }
        return EMPTY
    }

}