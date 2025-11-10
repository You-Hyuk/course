package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class DuplicateStudentException: CustomException(ErrorCode.DUPLICATE_STUDENT_EXCEPTION) {

}