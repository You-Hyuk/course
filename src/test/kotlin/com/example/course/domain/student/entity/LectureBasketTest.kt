package com.example.course.domain.student.entity

import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.lecture.enums.TimeSlot
import com.example.course.domain.student.enums.Color
import com.example.course.domain.student.enums.Status
import com.example.course.domain.student.exception.DuplicateLectureInBasketException
import com.example.course.domain.student.exception.InvalidLectureBasketNameLengthException
import com.example.course.domain.student.exception.InvalidLectureSemesterException
import com.example.course.domain.student.exception.LectureTimeConflictException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertNotNull
import org.junit.jupiter.api.assertThrows

class LectureBasketTest {

    @Test
    fun 수강_바구니_생성_성공() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )

        // when & then
        assertNotNull(lectureBasket)
    }

    @Test
    fun 수강_바구니_강의_추가_성공() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }

        // when
        lectureBasket.addLecture(lecture, loadTimes)
        val lectures = lectureBasket.getLectures()

        // then
        assertEquals(1, lectures.size)
        assertEquals(lecture.id, lectures[0].lectureId)
        assertEquals(lectures[0].color, Color.COLOR_1)
    }

    @Test
    fun 수강_바구니_강의_제거_성공() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }
        lectureBasket.addLecture(lecture, loadTimes)

        //when
        lectureBasket.removeLecture(lecture)

        // then
        val lectures = lectureBasket.getLectures()
        assertEquals(0, lectures.size)
    }

    @Test
    fun 수강_바구니_강의_색상_변경_성공() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }
        lectureBasket.addLecture(lecture, loadTimes)

        // when
        lectureBasket.changeColor(lecture.id!!, Color.COLOR_2)

        // then
        val lectures = lectureBasket.getLectures()

        assertEquals(lectures[0].color, Color.COLOR_2)
    }

    @Test
    fun 수강_바구니_이름_변경_성공() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )

        // when
        lectureBasket.rename("이름")

        // then
        assertEquals(lectureBasket.name, "이름")
    }

    @Test
    fun 수강_바구니_DEFAULT_상태_변경_성공() {
        //given
        val defaultLectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val normalLectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.NORMAL
        )

        // when
        normalLectureBasket.changeStatusToDefault(defaultLectureBasket)

        // then
        assertEquals(normalLectureBasket.status, Status.DEFAULT)
        assertEquals(defaultLectureBasket.status, Status.NORMAL)
    }

    @Test
    fun 수강_바구니_이름이_20자보다_많은_경우_예외_발생() {
        assertThrows<InvalidLectureBasketNameLengthException> {
            LectureBasket(
                name = "aaaaaaaaaaaaaaaaaaaaa",
                studentId = 1,
                year = 2025,
                semester = Semester.SECOND,
                status = Status.DEFAULT
            )
        }
    }

    @Test
    fun 수강_바구니_강의_추가_시_연도가_일치하지_않는_경우_예외_발생() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2024,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }

        // when & then
        assertThrows<InvalidLectureSemesterException> {
            lectureBasket.addLecture(lecture, loadTimes)
        }
    }

    @Test
    fun 수강_바구니_강의_추가_시_학기가_일치하지_않는_경우_예외_발생() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.FIRST,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }

        // when & then
        assertThrows<InvalidLectureSemesterException> {
            lectureBasket.addLecture(lecture, loadTimes)
        }
    }

    @Test
    fun 수강_바구니_강의_추가_시_시간이_중복되는_강의가_존재하는_경우_예외_발생() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture1 = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val lecture2 = Lecture(
            id = 2,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10002"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }
        lectureBasket.addLecture(lecture1, loadTimes)

        // when & then
        assertThrows<LectureTimeConflictException> {
            lectureBasket.addLecture(lecture2, loadTimes)
        }
    }

    @Test
    fun 수강_바구니_강의_추가_시_동일한_강의가_존재하는_경우_예외_발생() {
        //given
        val lectureBasket = LectureBasket(
            id = 1,
            name = "name",
            studentId = 1,
            year = 2025,
            semester = Semester.SECOND,
            status = Status.DEFAULT
        )
        val lecture = Lecture(
            id = 1,
            courseId = 1,
            professorId = 1,
            year = 2025,
            semester = Semester.SECOND,
            capacity = 30,
            currentEnrollment = 0,
            code = "10001"
        )
        val loadTimes: (Long) -> List<LectureTime> = { lectureId ->
            listOf(
                LectureTime(
                    lectureId = lectureId,
                    timeSlot = TimeSlot.MON_P1
                )
            )
        }
        lectureBasket.addLecture(lecture, loadTimes)

        // when & then
        assertThrows<DuplicateLectureInBasketException> {
            lectureBasket.addLecture(lecture, loadTimes)
        }
    }
}