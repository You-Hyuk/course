package com.example.course.domain.student.controller

import com.example.course.domain.student.dto.GetLectureBasketResponse
import com.example.course.domain.student.dto.PostAddLectureToBasketRequest
import com.example.course.domain.student.dto.PostLectureBasketRequest
import com.example.course.domain.student.service.LectureBasketService
import com.example.course.global.response.ApiResponse
import com.example.course.global.util.ApiUtil
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/students/{studentId}/lecture-baskets")
class LectureBasketController(
    val lectureBasketService: LectureBasketService
) {
    @PostMapping("/{lectureBasketId}/lectures")
    fun addLectureToBasket(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long,
        @RequestBody request: PostAddLectureToBasketRequest
    ): ApiResponse<Void> {
        lectureBasketService.addLectureToBasket(studentId, lectureBasketId, request)
        return ApiUtil.successOnly()
    }

    @PostMapping
    fun generateLectureBasket(
        @PathVariable studentId: Long,
        @RequestBody request: PostLectureBasketRequest
    ): ApiResponse<Void> {
        lectureBasketService.generateLectureBasket(studentId, request)
        return ApiUtil.successOnly()
    }

    @GetMapping(("/{lectureBasketId}/lectures"))
    fun readLectureBasket(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long
    ): ApiResponse<GetLectureBasketResponse> {
        val response = lectureBasketService.findLectureBasket(studentId, lectureBasketId)
        return ApiUtil.success(response)
    }
}