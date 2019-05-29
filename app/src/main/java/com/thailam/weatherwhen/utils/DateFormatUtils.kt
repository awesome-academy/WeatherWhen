package com.thailam.weatherwhen.utils

import android.content.Context
import android.text.format.DateUtils
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateFormatUtils {
    /**
     * returns current date in "Month dd, yyyy" format
     */
    @JvmStatic
    fun getCurrentDate(): String =
        SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

    /**
     * returns day of week from Long epoch value
     * @param context (Context) : context for android DateUtils
     * @param number (Long) : number in epoch date Long format
     */
    @JvmStatic
    fun longToDayOfWeek(context: Context, number: Long): String =
        DateUtils.formatDateTime(context, number * 1000, DateUtils.FORMAT_SHOW_WEEKDAY)
}
