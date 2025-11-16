package com.example.course.domain.lecture.exception

import com.example.course.domain.student.entity.Department

data class GetDepartmentsResponse(

    val departmentNames: List<String>

) {
    companion object {
        fun from(departments: List<Department>): GetDepartmentsResponse {
            return GetDepartmentsResponse(
                departmentNames = departments.map { it.name }
            )
        }
    }
}