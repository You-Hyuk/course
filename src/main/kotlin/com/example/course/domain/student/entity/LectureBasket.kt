package com.example.course.domain.student.entity

import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.student.enums.Color
import jakarta.persistence.Column
import jakarta.persistence.Embedded
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "lecture_baskets")
class LectureBasket(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    val id: Long? = null,

    @Column(name = "student_id", nullable = false, updatable = false)
    val studentId: Long,

    @Column(name = "name", nullable = false, updatable = true)
    val name: String,

    @Column(name = "lecture_basket_year", nullable = false, updatable = false)
    val year: Int,

    @Enumerated(EnumType.STRING)
    @Column(name = "semester", nullable = false, updatable = false, columnDefinition = "varchar(20)")
    val semester: Semester,

    @Embedded
    val lectures: LectureBasketLectures = LectureBasketLectures()

) {
    fun addLecture(
        lecture: Lecture,
        loadTimes: (Long) -> List<LectureTime>
    ) {
        lectures.add(
            LectureBasketLecture(
                lectureBasket = this,
                lectureId = lecture.id!!,
                color = Color.COLOR_1
            ),
            loadTimes
        )
    }
}