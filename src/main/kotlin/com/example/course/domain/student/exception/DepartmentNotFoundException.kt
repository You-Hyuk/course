package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class DepartmentNotFoundException : CustomException(ErrorCode.DEPARTMENT_NOT_FOUND)