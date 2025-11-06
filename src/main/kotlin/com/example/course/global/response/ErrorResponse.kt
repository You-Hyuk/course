package com.example.course.global.response

import com.example.course.global.exception.ErrorCode
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("status", "code", "errorMessage")
data class ErrorResponse(
    @get:JsonProperty("status")
    val status: Int,

    @get:JsonProperty("code")
    val code: String,

    @get:JsonProperty("errorMessage")
    val errorMessage: String
) {
    companion object {
        fun from(errorCode: ErrorCode): ErrorResponse {
            return ErrorResponse(
                status = errorCode.status.value(),
                code = errorCode.code,
                errorMessage = errorCode.message
            )
        }
    }
}
