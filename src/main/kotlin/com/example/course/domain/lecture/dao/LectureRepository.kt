package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.enums.Semester
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LectureRepository : JpaRepository<Lecture, Long> {
    @Query(
        """
        SELECT l
        FROM Lecture l
        WHERE (:courseIds IS NULL OR l.courseId IN :courseIds)
            AND (:professorIds IS NULL OR l.professorId IN :professorIds)
            AND (:semester IS NULL OR l.semester = :semester)
            AND (:year IS NULL OR l.year = :year)
            AND (:lectureIds IS NULL OR l.id IN :lectureIds)
    """
    )
    fun findFilteredLectures(
        @Param("courseIds") courseIds: List<Long>?,
        @Param("professorIds") professorIds: List<Long>?,
        @Param("semester") semester: Semester?,
        @Param("year") year: Int?,
        @Param("lectureIds") lectureIds: List<Long>?,
        pageable: Pageable
    ): Slice<Lecture>
}