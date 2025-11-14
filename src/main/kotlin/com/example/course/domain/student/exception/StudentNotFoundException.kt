package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class StudentNotFoundException : CustomException(ErrorCode.STUDENT_NOT_FOUND)