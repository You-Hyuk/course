package com.example.course.domain.lecture.dao

import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.enums.CourseType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager
import org.springframework.test.context.ActiveProfiles

@DataJpaTest
@ActiveProfiles("test")
@EntityScan("com.example.course.domain")
class CourseRepositoryTest(
    @Autowired val courseRepository: CourseRepository,
    @Autowired val testEntityManager: TestEntityManager
) {
    @Test
    fun 이름이_포함된_과목을_조회한다() {
        //given
        val course = testEntityManager.persist(
            Course(
                name = "컴퓨터공학개론",
                type = CourseType.전기,
                credit = 3,
                grade = 1,
                departmentId = 1
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = courseRepository.findFilteredCourseIds(
            null,
            CourseType.전기,
            null,
            null,
            null
        )

        // then
        Assertions.assertThat(result).containsExactly(course.id)
    }

    @Test
    fun 전공이_동일한_과목을_조회한다() {
        //given
        val course = testEntityManager.persist(
            Course(
                name = "컴퓨터공학개론",
                type = CourseType.전기,
                credit = 3,
                grade = 1,
                departmentId = 1
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = courseRepository.findFilteredCourseIds(
            null,
            null,
            listOf(1),
            null,
            null
        )

        // then
        Assertions.assertThat(result).containsExactly(course.id)
    }

    @Test
    fun 수강_학년이_동일한_과목을_조회한다() {
        //given
        val course = testEntityManager.persist(
            Course(
                name = "컴퓨터공학개론",
                type = CourseType.전기,
                credit = 3,
                grade = 1,
                departmentId = 1
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = courseRepository.findFilteredCourseIds(
            null,
            null,
            null,
            1,
            null
        )

        // then
        Assertions.assertThat(result).containsExactly(course.id)
    }

    @Test
    fun 학점이_동일한_과목을_조회한다() {
        //given
        val course = testEntityManager.persist(
            Course(
                name = "컴퓨터공학개론",
                type = CourseType.전기,
                credit = 3,
                grade = 1,
                departmentId = 1
            )
        )

        testEntityManager.flush()
        testEntityManager.clear()

        // when
        val result = courseRepository.findFilteredCourseIds(
            null,
            null,
            null,
            null,
            3
        )

        // then
        Assertions.assertThat(result).containsExactly(course.id)
    }
}