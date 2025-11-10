package com.example.course.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {

    // Global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G000", "서버 내부에 문제가 발생했습니다."),

    // Department
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "D000", "해당 전공이 존재하지 않습니다."),

    // Student
    INVALID_STUDENT_NAME_LENGTH(HttpStatus.BAD_REQUEST, "S000", "학생 이름은 2글자 이상 4글자 이하여야 합니다."),
    INVALID_STUDENT_NUMBER_LENGTH(HttpStatus.BAD_REQUEST, "S001", "학번의 길이는 9글자입니다."),
    INVALID_STUDENT_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "S002", "비밀번호는 8글자 이상 20글자 이하여야 합니다."),
    INVALID_STUDENT_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "S003", "학번의 앞 4글자는 2000 이상 2025 이하여야 합니다.")
}