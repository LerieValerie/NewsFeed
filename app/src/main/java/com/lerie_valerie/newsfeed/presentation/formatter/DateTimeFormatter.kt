package com.lerie_valerie.newsfeed.presentation.formatter

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.datetime.toLocalDateTime
import java.time.format.DateTimeFormatter

class DateTimeFormatter {
    companion object {
        fun getDateWithDot(dateInstant: Instant?): String? {
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
            return dateInstant?.toLocalDateTime(TimeZone.currentSystemDefault())
                ?.toJavaLocalDateTime()
                ?.format(formatter)
        }
    }
}