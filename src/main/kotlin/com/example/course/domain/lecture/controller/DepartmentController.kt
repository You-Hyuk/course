package com.example.course.domain.lecture.controller

import com.example.course.domain.lecture.exception.GetDepartmentsResponse
import com.example.course.domain.lecture.service.DepartmentService
import com.example.course.global.response.ApiResponse
import com.example.course.global.util.ApiUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/departments")
class DepartmentController(private val departmentService: DepartmentService) {

    @GetMapping
    fun readDepartments(): ApiResponse<GetDepartmentsResponse> {
        val response = departmentService.findDepartments()
        return ApiUtil.success(response)
    }
}