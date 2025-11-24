package com.example.course.domain.student.dto

import com.example.course.domain.lecture.enums.Semester
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class PostLectureBasketRequest(

    @field:NotBlank
    val lectureBasketName: String?,

    @field:NotNull
    val year: Int?,

    @field:NotNull
    val semester: Semester?

)