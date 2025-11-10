package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class InvalidStudentNumberLengthException : CustomException(ErrorCode.INVALID_STUDENT_NUMBER_LENGTH)