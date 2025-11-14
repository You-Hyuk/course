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
    val timeSlots: List<TimeSlot>
) {
    companion object {
        fun from(
            lecture: Lecture,
            course: Course,
            professor: Professor,
            lectureTimes: List<LectureTime>
        ): LectureInBasketDto {
            return LectureInBasketDto(
                lectureId = lecture.id!!,
                courseName = course.name,
                professorName = professor.name,
                timeSlots = lectureTimes.map { it.timeSlot }
            )
        }
    }
}