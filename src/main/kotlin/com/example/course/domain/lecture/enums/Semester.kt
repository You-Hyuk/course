package com.example.course.domain.lecture.enums

enum class Semester(val description: String, val order: Int) {
    FIRST("1학기", 4),
    SUMMER("여름 계절학기", 3),
    SECOND("2학기", 2),
    WINTER("겨울 계절학기", 1)
}