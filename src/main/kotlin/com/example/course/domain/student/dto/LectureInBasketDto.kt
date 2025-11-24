package com.example.course.domain.student.dto

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.student.entity.LectureInBasket
import com.example.course.domain.student.enums.Color

data class LectureInBasketDto(
    val lectureInBasketId: Long,
    val courseName: String,
    val professorName: String,
    val lectureTimes: List<String>,
    val lectureColor: Color
) {
    companion object {
        fun from(
            course: Course,
            professor: Professor,
            lectureTimes: List<String>,
            lectureInBasket: LectureInBasket
        ): LectureInBasketDto {
            return LectureInBasketDto(
                lectureInBasketId = lectureInBasket.id!!,
                courseName = course.name,
                professorName = professor.name,
                lectureTimes = lectureTimes,
                lectureColor = lectureInBasket.color
            )
        }
    }
}