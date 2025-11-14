package com.example.course.domain.student.dto

import com.example.course.domain.lecture.enums.Semester

data class GetLectureBasketsResponse(
    val year: Int,
    val semester: Semester,
    val lectureBaskets: List<LectureBasketDto>
) {
    companion object {
        fun from(year: Int, semester: Semester, lectureBaskets: List<LectureBasketDto>): GetLectureBasketsResponse {
            return GetLectureBasketsResponse(
                year = year,
                semester = semester,
                lectureBaskets = lectureBaskets)
        }
    }
}