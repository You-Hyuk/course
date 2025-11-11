package com.example.course.global.exception

open class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)