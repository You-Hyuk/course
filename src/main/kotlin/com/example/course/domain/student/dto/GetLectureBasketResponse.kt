package com.example.course.domain.student.dto

import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.student.entity.LectureBasket

data class GetLectureBasketResponse(
    val lectureBasketId: Long,
    val lectureBasketName: String,
    val year: Int,
    val semester: Semester,
    val lectures: List<LectureInBasketDto>
) {
    companion object {
        fun from(lectureBasket: LectureBasket, lecture: List<LectureInBasketDto>): GetLectureBasketResponse {
            return GetLectureBasketResponse(
                lectureBasketId = lectureBasket.id!!,
                lectureBasketName = lectureBasket.name,
                year = lectureBasket.year,
                semester = lectureBasket.semester,
                lectures = lecture
            )
        }

        fun empty(year: Int, semester: Semester) =
            GetLectureBasketResponse(0L, "", year, semester, emptyList())
    }
}