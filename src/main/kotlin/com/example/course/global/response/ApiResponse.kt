package com.example.course.global.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("success", "response")
data class ApiResponse<T>(
    @JsonProperty("isSuccess")
    val success: Boolean,

    @JsonProperty("response")
    val response: T? = null,

    @JsonProperty("errorResponse")
    val errorResponse: ErrorResponse? = null
)
