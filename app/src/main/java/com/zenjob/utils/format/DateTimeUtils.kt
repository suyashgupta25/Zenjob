package com.zenjob.utils.format

import com.zenjob.utils.AppConstants.Companion.EMPTY
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private const val inputDateFormat = "yyyy-MM-dd'T'HH:mm:ssZ"
    private const val outputDateFormat = "dd.MM."
    private const val outputTimeFormat = "HH:mm"
    private const val DASH = "-"

    @JvmStatic
    fun formatDate(dateValue: String?): String {
        dateValue?.let {
            try {
                val date = SimpleDateFormat(inputDateFormat, Locale.getDefault()).parse(dateValue)
                return SimpleDateFormat(outputDateFormat, Locale.getDefault()).format(date)
            } catch (e: ParseException) {
                System.err.print(e)
            }
        }
        return EMPTY
    }

    @JvmStatic
    fun formatTime(beginDateValue: String?, endDateValue: String?): String {
        beginDateValue?.let {
            endDateValue?.let {
                try {
                    val beginDate = SimpleDateFormat(inputDateFormat, Locale.getDefault()).parse(beginDateValue)
                    val endDate = SimpleDateFormat(inputDateFormat, Locale.getDefault()).parse(endDateValue)
                    val formatBegin = SimpleDateFormat(outputTimeFormat, Locale.getDefault()).format(beginDate)
                    val formatEnd = SimpleDateFormat(outputTimeFormat, Locale.getDefault()).format(endDate)
                    return formatBegin + DASH + formatEnd
                } catch (e: ParseException) {
                    System.err.print(e)
                }
            }
        }
        return EMPTY
    }

}