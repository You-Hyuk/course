package com.example.course.domain.student.dto

import jakarta.validation.constraints.NotNull

data class PostAddLectureToBasketRequest(

    @field:NotNull
    val lectureId: Long?

)