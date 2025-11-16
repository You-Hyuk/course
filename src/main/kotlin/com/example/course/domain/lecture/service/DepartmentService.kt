package com.example.course.domain.lecture.service

import com.example.course.domain.lecture.exception.GetDepartmentsResponse
import com.example.course.domain.student.dao.DepartmentRepository
import org.springframework.stereotype.Service

@Service
class DepartmentService(
    val departmentRepository: DepartmentRepository
) {
    fun findDepartments(): GetDepartmentsResponse {
        val departments = departmentRepository.findAll()
        return GetDepartmentsResponse.from(departments)
    }


}