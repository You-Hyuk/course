package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class LectureInBasketNotFoundException : CustomException(ErrorCode.LECTURE_IN_BASKET_NOT_FOUND)