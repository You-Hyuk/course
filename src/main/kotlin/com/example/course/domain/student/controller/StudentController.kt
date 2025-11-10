package com.example.course.domain.student.controller

import com.example.course.domain.student.dto.PostStudentRequest
import com.example.course.domain.student.service.StudentService
import com.example.course.global.response.ApiResponse
import com.example.course.global.util.ApiUtil
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students")
class StudentController(
    val studentService: StudentService
) {
    @PostMapping("/signup")
    fun processSignUp(@RequestBody request: PostStudentRequest): ApiResponse<Void> {
        studentService.generateNewStudent(request)
        return ApiUtil.successOnly()
    }
}