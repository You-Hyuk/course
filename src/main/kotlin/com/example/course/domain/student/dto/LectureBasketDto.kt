package com.example.course.domain.student.dto

import com.example.course.domain.student.entity.LectureBasket
import com.example.course.domain.student.enums.Status

data class LectureBasketDto(
    val lectureBasketId: Long,
    val lectureBasketName: String,
    val status: Status
) {
    companion object {
        fun from(lectureBasket: LectureBasket): LectureBasketDto {
            return LectureBasketDto(
                lectureBasketId = lectureBasket.id!!,
                lectureBasketName = lectureBasket.name,
                status = lectureBasket.status
            )
        }
    }
}