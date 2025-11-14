package com.example.course.domain.student.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Embeddable
class LectureBasketLectures(

    @OneToMany(mappedBy = "lectureBasket", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, orphanRemoval = true)
    private val lectures: MutableList<LectureBasketLecture> = mutableListOf()

)