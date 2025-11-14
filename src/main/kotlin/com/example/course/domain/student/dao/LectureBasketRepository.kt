package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.LectureBasket
import org.springframework.data.jpa.repository.JpaRepository

interface LectureBasketRepository : JpaRepository<LectureBasket, Long> {
}