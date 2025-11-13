package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.enums.Semester
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@EntityScan("com.example.course.domain")
class LectureRepositoryTest (
    @Autowired val lectureRepository: LectureRepository,
    @Autowired val testEntityManager: TestEntityManager
) {

    @Test
    fun CourseId_목록으로_필터링한_강의를_조회한다() {
        //given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SPRING,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureRepository.findFilteredLectures(
            listOf(1),
            null,
            null,
            null,
            null,
            PageRequest.of(0, 20)
        )

        // then
        Assertions.assertThat(result.content.first().id).isEqualTo(lecture.id)
    }

    @Test
    fun ProfessorId_목록으로_필터링한_강의를_조회한다() {
        //given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SPRING,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureRepository.findFilteredLectures(
            null,
            listOf(1),
            null,
            null,
            null,
            PageRequest.of(0, 20)
        )

        // then
        Assertions.assertThat(result.content.first().id).isEqualTo(lecture.id)
    }

    @Test
    fun 학기로_필터링한_강의를_조회한다() {
        //given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SPRING,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureRepository.findFilteredLectures(
            null,
            null,
            Semester.SPRING,
            null,
            null,
            PageRequest.of(0, 20)
        )

        // then
        Assertions.assertThat(result.content.first().id).isEqualTo(lecture.id)
    }

    @Test
    fun 년도로_필터링한_강의를_조회한다() {
        //given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SPRING,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureRepository.findFilteredLectures(
            null,
            null,
            null,
            2025,
            null,
            PageRequest.of(0, 20)
        )

        // then
        Assertions.assertThat(result.content.first().id).isEqualTo(lecture.id)
    }

    @Test
    fun LectureId_목록으로_필터링한_강의를_조회한다() {
        //given
        val lecture = testEntityManager.persist(
            Lecture(
                courseId = 1,
                professorId = 1,
                year = 2025,
                semester = Semester.SPRING,
                capacity = 30,
                currentEnrollment = 0,
                code = "10001"
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = lectureRepository.findFilteredLectures(
            null,
            null,
            null,
            null,
            listOf(lecture.id!!),
            PageRequest.of(0, 20)
        )

        // then
        Assertions.assertThat(result.content.first().id).isEqualTo(lecture.id)
    }
}