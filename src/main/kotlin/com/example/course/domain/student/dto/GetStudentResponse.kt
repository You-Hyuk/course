package com.example.course.domain.student.dto

import com.example.course.domain.student.entity.Department
import com.example.course.domain.student.entity.Student

data class GetStudentResponse(
    val studentName: String,
    val studentNumber: String,
    val departmentName: String
) {
    companion object {
        fun from(student: Student, department: Department): GetStudentResponse {
            return GetStudentResponse(
                student.name,
                student.number,
                department.name
            )
        }
    }
}