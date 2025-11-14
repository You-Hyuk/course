package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class InvalidLectureSemesterException : CustomException(ErrorCode.INVALID_LECTURE_SEMESTER_EXCEPTION)