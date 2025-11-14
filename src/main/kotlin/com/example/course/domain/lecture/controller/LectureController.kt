package com.example.course.domain.lecture.controller

import com.example.course.domain.lecture.dto.GetLectureResponse
import com.example.course.domain.lecture.enums.CourseType
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.lecture.enums.TimeSlot
import com.example.course.domain.lecture.service.LectureService
import com.example.course.global.response.ApiResponse
import com.example.course.global.util.ApiUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/lectures")
class LectureController(
    val lectureService: LectureService
) {
    @GetMapping("")
    fun readLectures(
        @RequestParam(required = false) name: String?,
        @RequestParam(name = "professor", required = false) professorName: String?,
        @RequestParam(name = "time-slots", required = false) timeSlots: List<TimeSlot>?,
        @RequestParam(name = "semester", required = false) semester: Semester?,
        @RequestParam(name = "year", required = false) year: Int?,
        @RequestParam(name = "course-type", required = false) courseType: CourseType?,
        @RequestParam(name = "department", required = false) departmentName: String?,
        @RequestParam(required = false) grade: Int?,
        @RequestParam(required = false) credit: Int?,
        @RequestParam(required = false, defaultValue = "1") page: Int,
        @RequestParam(required = false, defaultValue = "30") size: Int
    ): ApiResponse<GetLectureResponse> {
        val response = lectureService.findLectures(
            name,
            professorName,
            timeSlots ?: emptyList(),
            semester,
            year,
            courseType,
            departmentName,
            grade,
            credit,
            page,
            size
        )
        return ApiUtil.success(response)
    }

}