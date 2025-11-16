package com.example.course.domain.lecture.util

import com.example.course.domain.lecture.enums.TimeSlot
import java.time.LocalTime

private val dayMap = mapOf(
    "MON" to "월",
    "TUE" to "화",
    "WED" to "수",
    "THU" to "목",
    "FRI" to "금"
)

fun TimeSlot.toTimeRange(): Triple<String, LocalTime, LocalTime> {
    val nameParts = this.name.split("_")
    val dayEng = nameParts[0]
    val period = nameParts[1].substring(1).toInt()

    val dayKor = dayMap[dayEng]!!

    val start = LocalTime.of(9, 0).plusMinutes((period - 1) * 30L)
    val end = start.plusMinutes(30)

    return Triple(dayKor, start, end)
}

fun List<TimeSlot>.toMergedTimeRanges(): List<String> {
    if (this.isEmpty()) return emptyList()

    val converted = this.map { it.toTimeRange() }
        .sortedWith(compareBy({ it.first }, { it.second }))

    val result = mutableListOf<Triple<String, LocalTime, LocalTime>>()

    var (curDay, curStart, curEnd) = converted[0]

    for (i in 1 until converted.size) {
        val (day, start, end) = converted[i]

        if (day == curDay && start == curEnd) {
            curEnd = end
        } else {
            result.add(Triple(curDay, curStart, curEnd))
            curDay = day
            curStart = start
            curEnd = end
        }
    }

    result.add(Triple(curDay, curStart, curEnd))

    return result.map { (day, start, end) ->
        "$day ${start} ~ ${end}"
    }
}
