package com.example.course.domain.student.entity

import com.example.course.domain.student.exception.InvalidStudentNameLengthException
import com.example.course.domain.student.exception.InvalidStudentNumberFormatException
import com.example.course.domain.student.exception.InvalidStudentNumberLengthException
import com.example.course.domain.student.exception.InvalidStudentPasswordLengthException
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

    init {
        validateNameLength(name)
        validateNumberLength(number)
        validatePasswordLength(password)
        validateNumberFormat(number)
    }

    private fun validateNameLength(name: String) {
        if (name.length !in 2..4) {
            throw InvalidStudentNameLengthException()
        }
    }

    private fun validateNumberLength(number: String) {
        if (number.length != 9) {
            throw InvalidStudentNumberLengthException()
        }
    }

    private fun validateNumberFormat(number: String) {
        if (number.take(4).toInt() !in 2000..2025) {
            throw InvalidStudentNumberFormatException()
        }
    }

    private fun validatePasswordLength(password: String) {
        if (password.length !in 8..20) {
            throw InvalidStudentPasswordLengthException()
        }
    }
}
