package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class DuplicateLectureInBasketException : CustomException(ErrorCode.DUPLICATE_LECTURE_IN_BASKET)