package com.example.course.domain.student.entity

import com.example.course.domain.student.exception.InvalidStudentNameLengthException
import com.example.course.domain.student.exception.InvalidStudentNumberLengthException
import com.example.course.domain.student.exception.InvalidStudentPasswordLengthException
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows

class StudentTest {

    @Test
    fun 학생_검증_성공() {
        val student = Student(
            name = "이름",
            password = "12345678",
            number = "202512345",
            departmentId = 1L
        )

        assertNotNull(student)
    }

    @Test
    fun 이름이_2글자_이하인_경우_예외발생() {
        assertThrows<InvalidStudentNameLengthException> {
            Student(
                name = "이",
                password = "12345678",
                number = "202512345",
                departmentId = 1L
            )
        }
    }

    @Test
    fun 이름이_4글자_이상인_경우_예외발생() {
        assertThrows<InvalidStudentNameLengthException> {
            Student(
                name = "이름이름이",
                password = "12345678",
                number = "202512345",
                departmentId = 1L
            )
        }
    }

    @Test
    fun 비밀번호가_8글자_이하인_경우_예외발생() {
        assertThrows<InvalidStudentPasswordLengthException> {
            Student(
                name = "이름",
                password = "1234567",
                number = "202512345",
                departmentId = 1L
            )
        }
    }

    @Test
    fun 비밀번호가_20글자_이하인_경우_예외발생() {
        assertThrows<InvalidStudentPasswordLengthException> {
            Student(
                name = "이름",
                password = "123456789012345678901",
                number = "202512345",
                departmentId = 1L
            )
        }
    }

    @Test
    fun 학번이_9글자가_아닌_경우_예외발생() {
        assertThrows<InvalidStudentNumberLengthException> {
            Student(
                name = "이름",
                password = "12345678",
                number = "2025123456",
                departmentId = 1L
            )
        }
    }

    @Test
    fun 학번의_앞_네글자가_2000보다_작은_경우_예외발생() {
        assertThrows<InvalidStudentNumberLengthException> {
            Student(
                name = "이름",
                password = "12345678",
                number = "1999123456",
                departmentId = 1L
            )
        }
    }

    @Test
    fun 학번의_앞_네글자가_2025보다_큰_경우_예외발생() {
        assertThrows<InvalidStudentNumberLengthException> {
            Student(
                name = "이름",
                password = "12345678",
                number = "2026123456",
                departmentId = 1L
            )
        }
    }
}