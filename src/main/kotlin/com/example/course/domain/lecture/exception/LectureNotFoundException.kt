package com.example.course.domain.lecture.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class LectureNotFoundException : CustomException(ErrorCode.LECTURE_NOT_FOUND)