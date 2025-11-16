package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.LectureInBasket
import org.springframework.data.jpa.repository.JpaRepository

interface LectureInBasketRepository : JpaRepository<LectureInBasket, Long> {
}