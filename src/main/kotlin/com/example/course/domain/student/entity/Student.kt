package com.example.course.domain.student.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "students")
class Student(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "name", updatable = false, nullable = false)
    val name: String,

    @Column(name = "number", updatable = false, nullable = false)
    val number: String,

    @Column(name = "password", updatable = true, nullable = false)
    var password: String,

    @Column(name = "department_id", updatable = true, nullable = false)
    var departmentId: Long
) {
}
