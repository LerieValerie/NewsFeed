package com.lerie_valerie.newsfeed.presentation.formatter

import kotlinx.datetime.*
import kotlinx.datetime.TimeZone
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class DateTimeFormatter {
    companion object {
        fun getDateWithDot(dateInstant: Instant?): String? {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            return dateInstant?.toLocalDateTime(TimeZone.currentSystemDefault())?.toJavaLocalDateTime()
                ?.format(formatter)
        }
    }
}