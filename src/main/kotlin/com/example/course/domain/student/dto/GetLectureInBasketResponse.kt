package com.example.course.domain.student.dto

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.student.entity.LectureInBasket
import com.example.course.domain.student.enums.Color

data class GetLectureInBasketResponse(
    val lectureInBasketId: Long,
    val courseName: String,
    val professorName: String,
    val lectureCode: String,
    val courseCredit: Int,
    val lectureTimes: List<String>,
    val lectureColor: Color
) {
    companion object {
        fun from(
            lectureInBasket: LectureInBasket,
            professor: Professor,
            lecture: Lecture,
            course: Course,
            lectureTime: List<String>,
        ): GetLectureInBasketResponse {
            return GetLectureInBasketResponse(
                lectureInBasketId = lectureInBasket.id!!,
                courseName = course.name,
                professorName = professor.name,
                lectureCode = lecture.code,
                courseCredit = course.credit,
                lectureTimes = lectureTime,
                lectureColor = lectureInBasket.color
            )
        }
    }
}