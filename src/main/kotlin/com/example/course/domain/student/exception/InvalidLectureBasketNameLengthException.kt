package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class InvalidLectureBasketNameLengthException : CustomException(ErrorCode.INVALID_LECTURE_BASKET_NAME_LENGTH)