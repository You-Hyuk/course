package com.example.course.domain.lecture.dto

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.lecture.enums.CourseType
import com.example.course.domain.lecture.enums.TimeSlot

data class LectureDto(
    val lectureId: Long,
    val courseName: String,
    val professorName: String,
    val timeSlots: List<TimeSlot>,
    val courseGrade: Int,
    val courseType: CourseType,
    val courseCredit: Int,
    val lectureCode: String,
    val lectureCurrentEnrollment: Int,
    val lectureCapacity: Int
) {
    companion object {
        fun from(
            lecture: Lecture,
            course: Course,
            professor: Professor,
            lectureTimes: List<LectureTime>
        ): LectureDto {
            return LectureDto(
                lectureId = lecture.id!!,
                courseName = course.name,
                professorName = professor.name,
                timeSlots = lectureTimes.map { it.timeSlot }.sortedBy { it.ordinal },
                courseGrade = course.grade,
                courseType = course.type,
                courseCredit = course.credit,
                lectureCode = lecture.code,
                lectureCurrentEnrollment = lecture.currentEnrollment,
                lectureCapacity = lecture.capacity
            )
        }
    }
}