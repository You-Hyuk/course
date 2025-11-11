package com.example.course.domain.lecture.entity

import com.example.course.domain.lecture.enums.CourseType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "courses")
class Course(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "name", nullable = false, updatable = false)
    val name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = true, updatable = false)
    val type: CourseType,

    @Column(name = "credit", nullable = true, updatable = false)
    val credit: Int,

    @Column(name = "grade", nullable = false, updatable = false)
    val grade: Int,

    @Column(name = "department_id", nullable = false, updatable = true)
    val departmentId: Long
)
