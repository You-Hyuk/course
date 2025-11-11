package com.example.course.domain.lecture.entity

import com.example.course.domain.lecture.enums.Semester
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "lectures")
class Lecture(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "course_id", updatable = true, nullable = false)
    val courseId: Long,

    @Column(name = "professor_id", updatable = true, nullable = true)
    val professorId: Long? = null,

    @Column(name = "year", updatable = true, nullable = false)
    val year: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "semester", updatable = true, nullable = false, columnDefinition = "VARCHAR(32)")
    val semester: Semester,

    @Column(name = "capacity", updatable = true, nullable = true)
    val capacity: Int,

    @Column(name = "current_enrollment", updatable = true, nullable = true)
    val currentEnrollment: Int,

    @Column(name = "code", nullable = false, updatable = false)
    val code: String
)
