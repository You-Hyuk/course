package com.example.course.domain.lecture.entity

import com.example.course.domain.lecture.enums.TimeSlot
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "lecture_times")
class LectureTime(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_time_id", nullable = false, updatable = false)
    val id: Long? = null,

    @Column(name = "lecture_id", nullable = false, updatable = false)
    val lectureId: Long,

    @Enumerated(EnumType.STRING)
    @Column(name = "time_slot", nullable = false, updatable = false, columnDefinition = "varchar(20)")
    val timeSlot: TimeSlot
)