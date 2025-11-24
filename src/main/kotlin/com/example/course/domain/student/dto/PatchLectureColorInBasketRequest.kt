package com.example.course.domain.student.dto

import com.example.course.domain.student.enums.Color
import jakarta.validation.constraints.NotNull

data class PatchLectureColorInBasketRequest(

    @field:NotNull
    val color: Color?

)