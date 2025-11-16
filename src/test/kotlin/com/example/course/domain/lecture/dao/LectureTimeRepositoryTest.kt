package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.lecture.enums.TimeSlot
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@EntityScan("com.example.course.domain")
class LectureTimeRepositoryTest(
    @param:Autowired val lectureTimeRepository: LectureTimeRepository,
    @param:Autowired val testEntityManager: TestEntityManager
) {

    @Test
    fun 선택된_시간의_강의를_조회한다() {
        // given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SECOND,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )

        testEntityManager.persist(LectureTime(lectureId = lecture.id!!, timeSlot = TimeSlot.MON_P1))
        testEntityManager.persist(LectureTime(lectureId = lecture.id!!, timeSlot = TimeSlot.MON_P2))

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureTimeRepository.findLectureIdsContainedIn(
            listOf(TimeSlot.MON_P1, TimeSlot.MON_P2, TimeSlot.MON_P3, TimeSlot.MON_P4)
        )

        // then
        assertThat(result).containsExactly(lecture.id)
    }

    @Test
    fun 선택되지_않은_시간의_강의는_조회하지_않는다() {
        // given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SECOND,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )
        testEntityManager.persist(LectureTime(lectureId = lecture.id!!, timeSlot = TimeSlot.MON_P5))
        testEntityManager.persist(LectureTime(lectureId = lecture.id!!, timeSlot = TimeSlot.MON_P6))

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureTimeRepository.findLectureIdsContainedIn(
            listOf(TimeSlot.MON_P1, TimeSlot.MON_P2, TimeSlot.MON_P3, TimeSlot.MON_P4)
        )

        // then
        assertThat(result).isEmpty()
    }
}