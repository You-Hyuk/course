package com.example.course.global.exception

import com.example.course.global.response.ApiResponse
import com.example.course.global.response.ErrorResponse
import com.example.course.global.util.ApiUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

private val log = KotlinLogging.logger {}

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(CustomException::class)
    fun handleCustomException(exception: CustomException): ResponseEntity<ApiResponse<*>> {
        return handleException(exception, ErrorResponse.from(exception.errorCode))
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(exception: RuntimeException): ResponseEntity<ApiResponse<*>> {
        return handleException(exception, ErrorResponse.from(ErrorCode.INTERNAL_SERVER_ERROR))
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ApiResponse<*>> {
        return handleException(exception, ErrorResponse.from(ErrorCode.INVALID_INPUT_VALUE))
    }

    private fun handleException(exception: Exception, errorResponse: ErrorResponse): ResponseEntity<ApiResponse<*>> {
        log.error(exception) { "[${errorResponse.code}] ${exception::class.simpleName} ${errorResponse.errorMessage}" }
        return ResponseEntity.status(errorResponse.status)
            .body(ApiUtil.error(errorResponse))
    }
}