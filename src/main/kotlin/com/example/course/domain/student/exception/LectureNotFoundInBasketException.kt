package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class LectureNotFoundInBasketException : CustomException(ErrorCode.LECTURE_NOT_FOUND_IN_BASKET)