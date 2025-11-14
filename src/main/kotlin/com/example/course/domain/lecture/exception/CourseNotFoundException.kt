package com.example.course.domain.lecture.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class CourseNotFoundException : CustomException(ErrorCode.COURSE_NOT_FOUND)