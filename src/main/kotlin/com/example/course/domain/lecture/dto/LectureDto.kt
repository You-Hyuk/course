package com.example.course.domain.lecture.dto

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.lecture.enums.CourseType
import com.example.course.domain.lecture.enums.TimeSlot
import com.example.course.domain.lecture.util.toMergedTimeRanges

data class LectureDto(
    val lectureId: Long,
    val courseName: String,
    val professorName: String,
    val lectureTimes: List<String>,
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
            lectureTimes: List<String>
        ): LectureDto {
            return LectureDto(
                lectureId = lecture.id!!,
                courseName = course.name,
                professorName = professor.name,
                lectureTimes = lectureTimes,
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