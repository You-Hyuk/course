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
        GROUP BY lt.lectureId
        HAVING SUM(CASE WHEN lt.timeSlot NOT IN :timeSlots THEN 1 ELSE 0 END) = 0
            AND SUM(CASE WHEN lt.timeSlot IN :timeSlots THEN 1 ELSE 0 END) > 0
    """
    )
    fun findLectureIdsContainedIn(
        @Param("timeSlots") timeSlots: List<TimeSlot>
    ): List<Long>


    fun findAllByLectureIdIn(lectureIds: List<Long>): List<LectureTime>
}