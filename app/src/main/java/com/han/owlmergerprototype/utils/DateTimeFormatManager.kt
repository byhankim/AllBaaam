package com.han.owlmergerprototype.utils

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeFormatManager {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun getCurrentDatetime(): String {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
                return current.format(formatter)
            }
            val date = Date()
            val formatter = SimpleDateFormat("yyyyMMddHHmmss")
            return formatter.format(date)
        }

        fun getTimeGapFromNow(datetime: String): String {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val current = LocalDateTime.now()
                val formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
            }
            val date = Date()
            val formatter = SimpleDateFormat("yyyyMMddHHmmss")



            val gap = getCurrentDatetime().toLong() - datetime.toLong()
            return when {
                gap < 60 -> "${gap}초 전"
                gap in 60..3599 -> "${gap/60}분 전"
                gap in 3600..86399 -> "${gap/3600}시간 전"
                gap in 86400..604799 -> "${gap/86400}일 전"
                else -> "${gap/604800}주 전" //52주
            }
        }

        fun fromNow(datetime: String) {
            // "2021-05-27T01:08:33.395Z"
            // 1622045313395

            val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))
            val date = sdf.parse(datetime)
            // sdf.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"))



            Log.e("[timeinmillis]", date?.time.toString())
        }
    }
}