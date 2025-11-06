package com.example.course.global.util

import com.example.course.global.response.ApiResponse

object ApiUtil {

    fun <T> success(response: T): ApiResponse<T> =
        ApiResponse(
            success = true,
            response = response
        )

    fun successOnly(): ApiResponse<Void> =
        ApiResponse(
            success = true
        )
}