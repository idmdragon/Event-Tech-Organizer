package com.maungedev.eventtechorganizer.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    fun convertMillisToString(timeMillis: Long): String {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timeMillis
        val sdf = SimpleDateFormat("d MMM yyyy", Locale.getDefault())
        return sdf.format(calendar.time)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertStringToMillis(stringDate: String): Long {
        val formatter = SimpleDateFormat("d MMM yyyy")
        val date = formatter.parse(stringDate) as Date
        return date.time
    }

 



}