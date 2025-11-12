package com.example.course.global.exception

import com.example.course.global.response.ApiResponse
import com.example.course.global.response.ErrorResponse
import com.example.course.global.util.ApiUtil
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException

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

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleValidationException(exception: MethodArgumentTypeMismatchException): ResponseEntity<ApiResponse<*>> {
        val paramName = exception.name
        val value = exception.value
        val requiredType = exception.requiredType?.simpleName

        val errorResponse = ErrorResponse.from(
            ErrorCode.INVALID_INPUT_VALUE,
            "요청 파라미터 '$paramName'의 값 '$value'는 유효한 $requiredType 형식이 아닙니다.",
        )

        return handleException(exception, errorResponse)
    }

    private fun handleException(exception: Exception, errorResponse: ErrorResponse): ResponseEntity<ApiResponse<*>> {
        log.error(exception) { "[${errorResponse.code}] ${exception::class.simpleName} ${errorResponse.errorMessage}" }
        return ResponseEntity.status(errorResponse.status)
            .body(ApiUtil.error(errorResponse))
    }
}