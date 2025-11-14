package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class LectureTimeConflictException : CustomException(ErrorCode.LECTURE_TIME_CONFLICT)