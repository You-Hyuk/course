package com.example.course.domain.student.dto

import com.example.course.domain.lecture.enums.Semester
import jakarta.validation.constraints.NotBlank

data class PostLectureBasketRequest(

    @field:NotBlank
    val lectureBasketName: String?,

    @field:NotBlank
    val year: Int?,

    @field:NotBlank
    val semester: Semester?

)