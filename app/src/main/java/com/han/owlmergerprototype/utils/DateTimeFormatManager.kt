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
            } else {
                val date = Date()
                val formatter = SimpleDateFormat("yyyyMMddHHmmss")
                return formatter.format(date)
            }
        }

        fun getXXX() {
            //TODO
        }
    }
}