package com.example.course.domain.student.dto

import jakarta.validation.constraints.NotBlank

data class PostAddLectureToBasketRequest(

    @field:NotBlank
    val lectureId: Long?

)