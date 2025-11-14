package com.example.course.domain.student.dto

import jakarta.validation.constraints.NotBlank

data class PatchLectureBasketNameRequest(

    @field:NotBlank
    val lectureBasketName: String?

)