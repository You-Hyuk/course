package com.example.course.domain.lecture.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class LectureCapacityExceededException : CustomException(ErrorCode.LECTURE_CAPACITY_EXCEEDED)