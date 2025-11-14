package com.example.course.domain.student.dto

import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.student.entity.LectureBasket

data class GetLectureBasketResponse(
    val year: Int,
    val semester: Semester,
    val lectures: List<LectureInBasketDto>
) {
    companion object {
        fun from(lectureBasket: LectureBasket, lecture: List<LectureInBasketDto>): GetLectureBasketResponse {
            return GetLectureBasketResponse(
                year = lectureBasket.year,
                semester = lectureBasket.semester,
                lectures = lecture
            )
        }

        fun empty(year: Int, semester: Semester) =
            GetLectureBasketResponse(year, semester, emptyList())
    }
}