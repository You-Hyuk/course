package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository : JpaRepository<Student, Long> {
}