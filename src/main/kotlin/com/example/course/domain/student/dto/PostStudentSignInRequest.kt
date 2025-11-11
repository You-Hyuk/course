package com.example.course.domain.student.dto

import jakarta.validation.constraints.NotBlank

data class PostStudentSignInRequest(

    @field:NotBlank
    val studentNumber: String?,

    @field:NotBlank
    val password: String?
)