package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.Professor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ProfessorRepository : JpaRepository<Professor, Long> {
    @Query(
        """
        SELECT p.id
        FROM Professor p
        WHERE p.name LIKE %:name%
    """
    )
    fun findIdsByNameContaining(name: String): List<Long>
}