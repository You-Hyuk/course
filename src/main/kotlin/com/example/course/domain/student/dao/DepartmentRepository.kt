package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.Department
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DepartmentRepository : JpaRepository<Department, Long> {
    fun findDepartmentByName(departmentName: String): Department?

    @Query(
        """
        SELECT d.id
        FROM Department d
        WHERE d.name LIKE %:name%
    """
    )
    fun findIdsByNameContaining(name: String): List<Long>
}