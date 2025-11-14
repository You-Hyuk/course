package com.example.course.domain.student.entity

import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.student.exception.DuplicateLectureInBasketException
import com.example.course.domain.student.exception.LectureTimeConflictException
import jakarta.persistence.CascadeType
import jakarta.persistence.Embeddable
import jakarta.persistence.FetchType
import jakarta.persistence.OneToMany

@Embeddable
class LectureBasketLectures(

    @OneToMany(mappedBy = "lectureBasket", cascade = [(CascadeType.ALL)], fetch = FetchType.LAZY, orphanRemoval = true)
    private val lectures: MutableList<LectureBasketLecture> = mutableListOf()

) {
    fun add(
        newLecture: LectureBasketLecture,
        loadTimes: (Long) -> List<LectureTime>
    ) {
        validateDuplicate(newLecture.lectureId)
        validateTimeConflict(newLecture.lectureId, loadTimes)

        lectures.add(newLecture)
    }

    fun remove(lecture: Lecture) {
        val target = lectures.find { it.lectureId == lecture.id }
            ?: throw LectureNotFoundInBasketException()

        lectures.remove(target)
    }

    fun toList(): List<LectureBasketLecture> {
        return lectures.toList()
    }

    private fun validateDuplicate(lectureId: Long) {
        if (lectures.any { it.lectureId == lectureId })
            throw DuplicateLectureInBasketException()
    }

    private fun validateTimeConflict(
        newLectureId: Long,
        loadTimes: (Long) -> List<LectureTime>
    ) {
        val existingTimes = lectures.flatMap { loadTimes(it.lectureId) }
        val newTimes = loadTimes(newLectureId)

        if (existingTimes.any { e -> newTimes.any { it.timeSlot == e.timeSlot } })
            throw LectureTimeConflictException()
    }

}