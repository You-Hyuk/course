package com.example.course.domain.student.exception

import com.example.course.global.exception.CustomException
import com.example.course.global.exception.ErrorCode

class LectureBasketDeletionDeniedException : CustomException(ErrorCode.LECTURE_BASKET_DELETION_DENIED)