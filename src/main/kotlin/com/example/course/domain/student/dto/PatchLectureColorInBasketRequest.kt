package com.example.course.domain.student.dto

import com.example.course.domain.student.enums.Color
import jakarta.validation.constraints.NotBlank

data class PatchLectureColorInBasketRequest(

    @field:NotBlank
    val color: Color?

)