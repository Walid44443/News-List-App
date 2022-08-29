package com.linkdevelopment.walid44443.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {
    //MM-dd-yyyy hh:mm a
    fun publishDateConvert(
        stringDate: String?,
    ): String {
        if (!stringDate.isNullOrEmpty()) {
            val oldPattern = "yyyy-MM-dd'T'HH:mm:ss"
            val newPattern = "MMM d, yyyy"

            val oldDateFormat = SimpleDateFormat(oldPattern)
            val oldDate = oldDateFormat.parse(stringDate)
            val newDateFormat = SimpleDateFormat(newPattern, Locale.getDefault())

            val newDate: String = newDateFormat.format(oldDate)
            if (newDate.isEmpty())
                return ""
            else
                return newDate
        } else {
            return ""
        }
    }
}