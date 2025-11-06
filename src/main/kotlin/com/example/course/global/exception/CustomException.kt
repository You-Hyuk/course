package com.example.course.global.exception

class CustomException(val errorCode: ErrorCode) : RuntimeException(errorCode.message)