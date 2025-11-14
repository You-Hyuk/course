package com.example.course.domain.student.dao

import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.student.entity.LectureBasket
import org.springframework.data.jpa.repository.JpaRepository

interface LectureBasketRepository : JpaRepository<LectureBasket, Long> {
    fun existsByYearAndSemester(year: Int, semester: Semester): Boolean

    fun findLectureBasketsByStudentId(studentId: Long): List<LectureBasket>
}