package com.example.course.domain.student.service

import com.example.course.domain.lecture.dao.CourseRepository
import com.example.course.domain.lecture.dao.LectureRepository
import com.example.course.domain.lecture.dao.LectureTimeRepository
import com.example.course.domain.lecture.dao.ProfessorRepository
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.lecture.exception.CourseNotFoundException
import com.example.course.domain.lecture.exception.LectureNotFoundException
import com.example.course.domain.lecture.exception.ProfessorNotFoundException
import com.example.course.domain.student.dao.LectureBasketLectureRepository
import com.example.course.domain.student.dao.LectureBasketRepository
import com.example.course.domain.student.dao.StudentRepository
import com.example.course.domain.student.dto.GetLectureBasketResponse
import com.example.course.domain.student.dto.LectureInBasketDto
import com.example.course.domain.student.dto.PostAddLectureToBasketRequest
import com.example.course.domain.student.dto.PostLectureBasketRequest
import com.example.course.domain.student.entity.LectureBasket
import com.example.course.domain.student.enums.Status
import com.example.course.domain.student.exception.LectureBasketNotFoundException
import com.example.course.domain.student.exception.StudentNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class LectureBasketService(
    val lectureBasketRepository: LectureBasketRepository,
    val lectureBasketLectureRepository: LectureBasketLectureRepository,
    val lectureTimeRepository: LectureTimeRepository,
    val studentRepository: StudentRepository,
    val lectureRepository: LectureRepository,
    val professorRepository: ProfessorRepository,
    val courseRepository: CourseRepository
) {
    @Transactional
    fun addLectureToBasket(studentId: Long, lectureBasketId: Long, request: PostAddLectureToBasketRequest) {
        if (!studentRepository.existsById(studentId)) {
            throw StudentNotFoundException()
        }

        val lectureBasket = lectureBasketRepository.findById(lectureBasketId)
            .orElseThrow { LectureBasketNotFoundException() }

        val lecture = lectureRepository.findById(request.lectureId!!)
            .orElseThrow { LectureNotFoundException() }

        lectureBasket.addLecture(lecture) { lectureTimeRepository.findAllByLectureId(lecture.id!!) }

        lectureBasketRepository.save(lectureBasket)
    }

    @Transactional
    fun generateLectureBasket(studentId: Long, request: PostLectureBasketRequest) {
        if (!studentRepository.existsById(studentId)) {
            throw StudentNotFoundException()
        }

        val lectureBasket = LectureBasket(
            studentId = studentId,
            name = request.lectureBasketName!!,
            year = request.year!!,
            semester = request.semester!!,
            status = determineLectureBasketStatus(request.year, request.semester),
        )

        lectureBasketRepository.save(lectureBasket)
    }

    fun findLectureBasket(studentId: Long, lectureBasketId: Long): GetLectureBasketResponse {
        if (!studentRepository.existsById(studentId)) {
            throw StudentNotFoundException()
        }

        val lectureBasket = lectureBasketRepository.findById(lectureBasketId)
            .orElseThrow { LectureBasketNotFoundException() }

        val lectureDtos = lectureBasket.getLectures()
            .map { buildLectureInBasketDto(it.lectureId) }

        return GetLectureBasketResponse.from(lectureBasket, lectureDtos)
    }

    private fun determineLectureBasketStatus(year: Int, semester: Semester): Status {
        if (lectureBasketRepository.existsByYearAndSemester(year, semester)) {
            return Status.NORMAL
        }
        return Status.DEFAULT
    }

    private fun buildLectureInBasketDto(lectureId: Long): LectureInBasketDto {
        val lecture = lectureRepository.findById(lectureId)
            .orElseThrow { LectureNotFoundException() }

        val course = courseRepository.findById(lecture.courseId)
            .orElseThrow { CourseNotFoundException() }

        val professor = professorRepository.findById(lecture.professorId)
            .orElseThrow { ProfessorNotFoundException() }

        val times = lectureTimeRepository.findAllByLectureId(lectureId)

        return LectureInBasketDto.from(lecture, course, professor, times)
    }
}