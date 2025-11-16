package com.example.course.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val code: String,
    val message: String
) {

    // Global
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G000", "서버 내부에 문제가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "G001", "올바르지 않은 입력값입니다."),

    // Department
    DEPARTMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "D000", "해당 전공이 존재하지 않습니다."),

    // Student
    INVALID_STUDENT_NAME_LENGTH(HttpStatus.BAD_REQUEST, "S000", "학생 이름은 2글자 이상 4글자 이하여야 합니다."),
    INVALID_STUDENT_NUMBER_LENGTH(HttpStatus.BAD_REQUEST, "S001", "학번의 길이는 9글자입니다."),
    INVALID_STUDENT_PASSWORD_LENGTH(HttpStatus.BAD_REQUEST, "S002", "비밀번호는 8글자 이상 20글자 이하여야 합니다."),
    INVALID_STUDENT_NUMBER_FORMAT(HttpStatus.BAD_REQUEST, "S003", "학번의 앞 4글자는 2000 이상 2025 이하여야 합니다."),
    DUPLICATE_STUDENT_EXCEPTION(HttpStatus.CONFLICT, "S004", "동일한 학번의 계정이 존재합니다."),
    INVALID_STUDENT_CREDENTIALS(HttpStatus.UNAUTHORIZED, "S005", "학생 인증 정보가 올바르지 않습니다."),
    STUDENT_NOT_FOUND(HttpStatus.NOT_FOUND, "S006", "해당 학생이 존재하지 않습니다."),

    // Lecture Basket
    LECTURE_BASKET_NOT_FOUND(HttpStatus.NOT_FOUND, "LB000", "해당 수강 바구니가 존재하지 않습니다."),
    LECTURE_TIME_CONFLICT(HttpStatus.CONFLICT, "LB001", "기존 수강 바구니 내의 강의와 시간이 중복됩니다."),
    INVALID_LECTURE_BASKET_NAME_LENGTH(HttpStatus.BAD_REQUEST, "LB002", "수강 바구니 이름은 1글자 이상 20글자 이하여야 합니다."),
    LECTURE_BASKET_ACCESS_DENIED(HttpStatus.FORBIDDEN, "LB003", "해당 수강 바구니 접근 권한이 존재하지 않습니다."),
    INVALID_LECTURE_SEMESTER_EXCEPTION(HttpStatus.BAD_REQUEST, "LB004", "수강 바구니와 연도 혹은 학기가 일치하지 않습니다."),


    // Lecture
    LECTURE_NOT_FOUND(HttpStatus.NOT_FOUND, "L000", "해당 강의가 존재하지 않습니다."),
    LECTURE_CAPACITY_EXCEEDED(HttpStatus.BAD_REQUEST, "L001", "강의 수강 인원이 가득 찼습니다."),

    // Lecture In Basket
    DUPLICATE_LECTURE_IN_BASKET(HttpStatus.BAD_REQUEST, "LIB000", "동일한 강의가 수강 바구니에 존재합니다."),
    LECTURE_NOT_FOUND_IN_BASKET(HttpStatus.NOT_FOUND, "LIB001", "해당 강의가 수강 바구니에 존재하지 않습니다."),
    LECTURE_IN_BASKET_NOT_FOUND(HttpStatus.NOT_FOUND, "LIB002", "해당 수강 바구니 내의 강의가 존재하지 않습니다."),

    // Professor
    PROFESSOR_NOT_FOUND(HttpStatus.NOT_FOUND, "P000", "해당 교수가 존재하지 않습니다."),

    // Course
    COURSE_NOT_FOUND(HttpStatus.NOT_FOUND, "C000", "해당 과목이 존재하지 않습니다.")
}