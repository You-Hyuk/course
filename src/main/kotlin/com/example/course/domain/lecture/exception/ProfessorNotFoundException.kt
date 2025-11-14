package com.example.course.domain.lecture.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class ProfessorNotFoundException : CustomException(ErrorCode.PROFESSOR_NOT_FOUND)