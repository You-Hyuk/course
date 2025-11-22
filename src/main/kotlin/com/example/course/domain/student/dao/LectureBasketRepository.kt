package com.example.course.domain.student.dao

import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.student.entity.LectureBasket
import com.example.course.domain.student.enums.Status
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional

interface LectureBasketRepository : JpaRepository<LectureBasket, Long> {
    fun existsByStudentIdAndYearAndSemester(studentId: Long, year: Int, semester: Semester): Boolean

    fun findLectureBasketsByStudentId(studentId: Long): List<LectureBasket>

    fun findLectureBasketByStudentIdAndYearAndSemesterAndStatus(
        studentId: Long,
        year: Int,
        semester: Semester,
        status: Status
    ): Optional<LectureBasket>

    fun findByStudentIdAndStatus(studentId: Long, status: Status): LectureBasket
}