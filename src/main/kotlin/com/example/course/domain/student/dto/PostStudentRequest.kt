package com.example.course.domain.student.dto

data class PostStudentRequest(
    var studentName: String,
    var studentNumber: String,
    var password: String,
    var departmentName: String
)
