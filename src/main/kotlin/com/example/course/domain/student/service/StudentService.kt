package com.example.course.domain.student.service

import com.example.course.domain.student.dao.DepartmentRepository
import com.example.course.domain.student.dao.StudentRepository
import com.example.course.domain.student.dto.PostStudentRequest
import com.example.course.domain.student.entity.Student
import com.example.course.domain.student.exception.DepartmentNotFoundException
import org.springframework.stereotype.Service

@Service
class StudentService(
    val studentRepository: StudentRepository,
    val departmentRepository: DepartmentRepository
) {
    fun generateNewStudent(request: PostStudentRequest) {
        val department = departmentRepository.findDepartmentByName(request.departmentName)
            ?: throw DepartmentNotFoundException()

        val student = Student(
            name = request.studentName,
            number = request.studentNumber,
            password = request.password,
            departmentId = department.id!!
        )

        studentRepository.save(student)
    }
}