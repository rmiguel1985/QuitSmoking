package org.project.quitsmoking.utils

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.OptIn
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.ExperimentalTime
import kotlin.time.toDuration

@OptIn(ExperimentalTime::class)
fun Long.toLocalDate(): String =
Instant.fromEpochMilliseconds(this)
    .toLocalDateTime(TimeZone.currentSystemDefault()).date.toString()

@OptIn(ExperimentalMaterial3Api::class)
fun TimePickerState.toFormatedTime(): String {
    val hour = this.hour.toString().padStart(2, '0')
    val minute = this.minute.toString().padStart(2, '0')

    return "$hour:$minute"
}

fun String.getSplitTime(): Pair<Int, Int> {
    val splitTime = this.ifEmpty { "00:00" }.split(":")
    val hour = splitTime.first().toIntOrNull()?: 0
    val time = splitTime.last().toIntOrNull()?: 0

    return hour to time
}

fun Long.minutesToDays(): Double {
    val duration: Duration = this.toDuration(DurationUnit.MINUTES)
    return duration.inWholeMinutes.toDouble() / (24.0 * 60.0)
}