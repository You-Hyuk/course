package com.example.course.global.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder("status", "code", "errorMessage")
data class ErrorResponse(

    @JsonProperty("status")
    val status: Int,

    @JsonProperty("message")
    val code: String,

    @JsonProperty("errorMessage")
    val errorMessage: String
)
