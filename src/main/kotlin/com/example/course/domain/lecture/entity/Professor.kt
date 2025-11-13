package com.example.course.domain.lecture.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "professors")
class Professor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "department_id", updatable = false, nullable = false)
    val departmentId: Long,

    @Column(name = "name", updatable = false, nullable = false)
    val name: String
)