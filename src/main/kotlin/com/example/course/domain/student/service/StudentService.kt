package com.example.course.domain.student.service

import com.example.course.domain.student.dao.DepartmentRepository
import com.example.course.domain.student.dao.StudentRepository
import com.example.course.domain.student.dto.GetStudentResponse
import com.example.course.domain.student.dto.PostStudentRequest
import com.example.course.domain.student.dto.PostStudentSignInRequest
import com.example.course.domain.student.dto.PostStudentSignInResponse
import com.example.course.domain.student.entity.Student
import com.example.course.domain.student.exception.DepartmentNotFoundException
import com.example.course.domain.student.exception.DuplicateStudentException
import com.example.course.domain.student.exception.InvalidStudentCredentialsException
import com.example.course.domain.student.exception.StudentNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class StudentService(
    val studentRepository: StudentRepository,
    val departmentRepository: DepartmentRepository
) {
    @Transactional
    fun generateNewStudent(request: PostStudentRequest) {
        val department = departmentRepository.findDepartmentByName(request.departmentName!!)
            ?: throw DepartmentNotFoundException()

        if (studentRepository.existsByNumber(request.studentNumber!!)) {
            throw DuplicateStudentException()
        }

        val student = Student(
            name = request.studentName!!,
            number = request.studentNumber!!,
            password = request.password!!,
            departmentId = department.id!!
        )

        studentRepository.save(student)
    }

    @Transactional
    fun signIn(request: PostStudentSignInRequest): PostStudentSignInResponse {
        val student = studentRepository.findByNumber(request.studentNumber!!)
            ?: throw InvalidStudentCredentialsException()

        if (!student.checkPassword(request.password!!)) {
            throw InvalidStudentCredentialsException()
        }

        return PostStudentSignInResponse(student.id!!)
    }

    fun findStudent(studentId: Long): GetStudentResponse {
        val student = studentRepository.findById(studentId)
            .orElseThrow { StudentNotFoundException() }

        val department = departmentRepository.findById(student.departmentId!!)
            .orElseThrow { DepartmentNotFoundException() }

        return GetStudentResponse.from(student, department)
    }
}