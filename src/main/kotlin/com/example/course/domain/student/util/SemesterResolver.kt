package com.example.course.domain.student.util

import com.example.course.domain.lecture.enums.Semester
import java.time.LocalDate

object SemesterResolver {

    private val mapping = mapOf(
        Semester.FIRST to (3..6),
        Semester.SUMMER to (7..8),
        Semester.SECOND to (9..12),
        Semester.WINTER to (1..2)
    )

    fun resolve(now: LocalDate = LocalDate.now()): Pair<Int, Semester> {
        val month = now.monthValue
        val year = now.year

        val semester = mapping.entries.first { month in it.value }.key
        return year to semester
    }
}