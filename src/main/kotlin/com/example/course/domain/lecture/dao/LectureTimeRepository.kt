package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.enums.TimeSlot
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LectureTimeRepository : JpaRepository<LectureTime, Long> {
    @Query(
        """
        SELECT lt.lectureId
        FROM LectureTime lt
        WHERE lt.timeSlot IN :timeSlots
        GROUP BY lt.lectureId
        HAVING COUNT(DISTINCT lt.timeSlot) = (
            SELECT COUNT(DISTINCT lt2.timeSlot)
            FROM LectureTime lt2
            WHERE lt2.lectureId = lt.lectureId
        )
    """
    )
    fun findLectureIdsContainedIn(
        @Param("timeSlots") timeSlots: List<TimeSlot>
    ): List<Long>


    fun findAllByLectureIdIn(lectureIds: List<Long>): List<LectureTime>
}