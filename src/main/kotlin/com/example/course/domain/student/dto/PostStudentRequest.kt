package com.example.course.domain.student.dto

import jakarta.validation.constraints.NotBlank

data class PostStudentRequest(

    @field:NotBlank
    val studentName: String?,

    @field:NotBlank
    val studentNumber: String?,

    @field:NotBlank
    val password: String?,

    @field:NotBlank
    val departmentName: String?
)
