package com.example.course.domain.student.controller

import com.example.course.domain.student.dto.GetLectureBasketResponse
import com.example.course.domain.student.dto.GetLectureBasketsResponse
import com.example.course.domain.student.dto.GetLectureInBasketResponse
import com.example.course.domain.student.dto.PatchLectureBasketNameRequest
import com.example.course.domain.student.dto.PostAddLectureToBasketRequest
import com.example.course.domain.student.dto.PostLectureBasketRequest
import com.example.course.domain.student.dto.PatchLectureColorInBasketRequest
import com.example.course.domain.student.service.LectureBasketService
import com.example.course.global.response.ApiResponse
import com.example.course.global.util.ApiUtil
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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

    @GetMapping
    fun readLectureBaskets(@PathVariable studentId: Long): ApiResponse<List<GetLectureBasketsResponse>> {
        val response = lectureBasketService.findLectureBaskets(studentId)
        return ApiUtil.success(response)
    }

    @GetMapping("/default")
    fun readDefaultLectureBasket(
        @PathVariable studentId: Long
    ): ApiResponse<GetLectureBasketResponse> {
        val response = lectureBasketService.findDefaultLectureBasket(studentId)
        return ApiUtil.success(response)
    }

    @GetMapping("/{lectureBasketId}/lectures/{lectureInBasketId}")
    fun readLectureInBasket(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long,
        @PathVariable lectureInBasketId: Long
    ): ApiResponse<GetLectureInBasketResponse> {
        val response = lectureBasketService.findLectureInBasket(studentId, lectureBasketId, lectureInBasketId)
        return ApiUtil.success(response)
    }

    @PatchMapping("/{lectureBasketId}/name")
    fun updateLectureBasketName(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long,
        @RequestBody request: PatchLectureBasketNameRequest
    ): ApiResponse<Void> {
        lectureBasketService.modifyLectureBasketName(studentId, lectureBasketId, request)
        return ApiUtil.successOnly()
    }

    @PatchMapping("/{lectureBasketId}/default")
    fun updateDefaultLectureBasket(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long
    ): ApiResponse<Void> {
        lectureBasketService.modifyDefaultLectureBasket(studentId, lectureBasketId)
        return ApiUtil.successOnly()
    }

    @PatchMapping("/{lectureBasketId}/lectures/{lectureInBasketId}/color")
    fun updateLectureBasketColor(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long,
        @PathVariable lectureInBasketId: Long,
        @RequestBody request: PatchLectureColorInBasketRequest
    ): ApiResponse<Void> {
        lectureBasketService.modifyLectureColorInBasket(
            studentId,
            lectureBasketId,
            lectureInBasketId,
            request
        )
        return ApiUtil.successOnly()
    }

    @DeleteMapping("/{lectureBasketId}")
    fun removeLectureBasket(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long
    ): ApiResponse<Void> {
        lectureBasketService.deleteLectureBasket(studentId, lectureBasketId)
        return ApiUtil.successOnly()
    }

    @DeleteMapping("/{lectureBasketId}/lectures/{lectureInBasketId}")
    fun removeLectureInBasket(
        @PathVariable studentId: Long,
        @PathVariable lectureBasketId: Long,
        @PathVariable lectureInBasketId: Long
    ): ApiResponse<Void> {
        lectureBasketService.deleteLectureInBasket(studentId, lectureBasketId, lectureInBasketId)
        return ApiUtil.successOnly()
    }
}