package com.example.course.domain.student.dao

import com.example.course.domain.student.entity.LectureBasketLecture
import org.springframework.data.jpa.repository.JpaRepository

interface LectureBasketLectureRepository : JpaRepository<LectureBasketLecture, Long> {
}