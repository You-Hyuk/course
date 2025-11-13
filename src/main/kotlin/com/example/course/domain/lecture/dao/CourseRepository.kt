package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.enums.CourseType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface CourseRepository : JpaRepository<Course, Long> {
    @Query(
        """
        SELECT c.id
        FROM Course c
        WHERE (:name IS NULL OR c.name LIKE %:name%)
            AND (:type IS NULL OR c.type = :type)
            AND (:departmentIds IS NULL OR c.departmentId IN :departmentIds)
            AND (:grade IS NULL OR c.grade = :grade)
            AND (:credit IS NULL OR c.credit = :credit)
    """
    )
    fun findFilteredCourseIds(
        @Param("name") name: String?,
        @Param("type") type: CourseType?,
        @Param("departmentIds") departmentIds: List<Long>?,
        @Param("grade") grade: Int?,
        @Param("credit") credit: Int?
    ): List<Long>
}