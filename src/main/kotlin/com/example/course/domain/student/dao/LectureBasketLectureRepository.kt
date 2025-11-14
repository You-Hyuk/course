package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.LectureInBasket
import org.springframework.data.jpa.repository.JpaRepository

interface LectureBasketLectureRepository : JpaRepository<LectureInBasket, Long> {
}