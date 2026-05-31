package com.vasilisa.iskraclientapp.data.shared

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

object DateFormatter {

    private val outputFormatter =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun format(input: String?): String {
        if (input.isNullOrBlank()) return "—"

        return try {
            parseToZonedDateTime(input)
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(outputFormatter)

        } catch (e: Exception) {
            "—"
        }
    }

    private fun parseToZonedDateTime(input: String): ZonedDateTime {

        return try {
            // 1) ISO с timezone: 2026-05-31T16:32:59Z / +03:00
            ZonedDateTime.parse(input)

        } catch (e1: DateTimeParseException) {
            try {
                // 2) LocalDateTime без timezone: 2026-05-31T16:32:59
                LocalDateTime.parse(input)
                    .atZone(ZoneId.of("UTC"))

            } catch (e2: DateTimeParseException) {
                try {
                    // 3) fallback ISO_INSTANT
                    Instant.parse(input)
                        .atZone(ZoneId.systemDefault())

                } catch (e3: Exception) {
                    throw IllegalArgumentException("Unknown date format: $input")
                }
            }
        }
    }
}