package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.Department
import org.springframework.data.jpa.repository.JpaRepository

interface DepartmentRepository : JpaRepository<Department, Long> {
    fun findDepartmentByName(departmentName: String): Department?
}