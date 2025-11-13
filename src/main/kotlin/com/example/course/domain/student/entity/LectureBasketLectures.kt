package com.example.course.domain.student.entity

import jakarta.persistence.Embeddable

@Embeddable
class LectureBasketLectures(

    @Transient
    private val items: MutableList<LectureBasketLecture> = mutableListOf()

)