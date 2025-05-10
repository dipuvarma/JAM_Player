package com.example.jamplayer.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun formatDateFromTimestamp(timestamp: Long): String {
    val date = Date(
        if (timestamp.toString().length == 13) timestamp // milliseconds
        else timestamp * 1000 // convert seconds to millis
    )
    val  pattern = "dd MMM yyyy"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    return formatter.format(date)
}
