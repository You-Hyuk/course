package com.example.course.domain.student.entity

import com.example.course.domain.student.enums.Color
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "lecture_basket_lectures")
class LectureBasketLecture(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    val id: Long? = null,

    @Column(name = "lecture_basket_id", nullable = false, updatable = false)
    val basketId: Long,

    @Column(name = "lecture_id", nullable = false, updatable = false)
    val lectureId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false, updatable = true, columnDefinition = "varchar(20)")
    val color: Color
)
