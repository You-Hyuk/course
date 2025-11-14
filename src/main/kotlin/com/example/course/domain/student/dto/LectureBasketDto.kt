package com.example.course.domain.student.dto

import com.example.course.domain.student.entity.LectureBasket
import com.example.course.domain.student.enums.Status

data class LectureBasketDto(
    val lectureBasketName: String,
    val status: Status
) {
    companion object {
        fun from(lectureBasket: LectureBasket): LectureBasketDto {
            return LectureBasketDto(
                lectureBasketName = lectureBasket.name,
                status = lectureBasket.status
            )
        }
    }
}