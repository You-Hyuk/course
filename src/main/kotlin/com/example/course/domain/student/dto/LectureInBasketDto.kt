package com.example.course.domain.student.dto

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.lecture.enums.TimeSlot

data class LectureInBasketDto(
    val lectureId: Long,
    val courseName: String,
    val professorName: String,
    val lectureTimes: List<String>
) {
    companion object {
        fun from(
            lecture: Lecture,
            course: Course,
            professor: Professor,
            lectureTimes: List<String>
        ): LectureInBasketDto {
            return LectureInBasketDto(
                lectureId = lecture.id!!,
                courseName = course.name,
                professorName = professor.name,
                lectureTimes = lectureTimes
            )
        }
    }
}