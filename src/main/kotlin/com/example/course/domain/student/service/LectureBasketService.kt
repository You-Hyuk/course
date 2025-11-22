package com.example.course.domain.student.service

import com.example.course.domain.lecture.dao.CourseRepository
import com.example.course.domain.lecture.dao.LectureRepository
import com.example.course.domain.lecture.dao.LectureTimeRepository
import com.example.course.domain.lecture.dao.ProfessorRepository
import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.lecture.enums.TimeSlot
import com.example.course.domain.lecture.exception.CourseNotFoundException
import com.example.course.domain.lecture.exception.LectureNotFoundException
import com.example.course.domain.lecture.exception.ProfessorNotFoundException
import com.example.course.domain.lecture.util.toMergedTimeRanges
import com.example.course.domain.student.dao.LectureBasketRepository
import com.example.course.domain.student.dao.LectureInBasketRepository
import com.example.course.domain.student.dao.StudentRepository
import com.example.course.domain.student.dto.GetLectureBasketResponse
import com.example.course.domain.student.dto.GetLectureBasketsResponse
import com.example.course.domain.student.dto.GetLectureInBasketResponse
import com.example.course.domain.student.dto.LectureBasketDto
import com.example.course.domain.student.dto.LectureInBasketDto
import com.example.course.domain.student.dto.PatchLectureBasketNameRequest
import com.example.course.domain.student.dto.PatchLectureColorInBasketRequest
import com.example.course.domain.student.dto.PostAddLectureToBasketRequest
import com.example.course.domain.student.dto.PostLectureBasketRequest
import com.example.course.domain.student.entity.LectureBasket
import com.example.course.domain.student.entity.LectureInBasket
import com.example.course.domain.student.exception.LectureNotFoundInBasketException
import com.example.course.domain.student.enums.Status
import com.example.course.domain.student.exception.LectureBasketAccessDeniedException
import com.example.course.domain.student.exception.LectureBasketNotFoundException
import com.example.course.domain.student.exception.LectureInBasketNotFoundException
import com.example.course.domain.student.exception.StudentNotFoundException
import com.example.course.domain.student.util.SemesterResolver
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureBasketService(
    val lectureBasketRepository: LectureBasketRepository,
    val lectureTimeRepository: LectureTimeRepository,
    val studentRepository: StudentRepository,
    val lectureRepository: LectureRepository,
    val professorRepository: ProfessorRepository,
    val courseRepository: CourseRepository,
    val lectureInBasketRepository: LectureInBasketRepository
) {
    @Transactional
    fun addLectureToBasket(studentId: Long, lectureBasketId: Long, request: PostAddLectureToBasketRequest) {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)

        validateLectureBasketAccess(lectureBasket, studentId)

        val lecture = lectureRepository.findById(request.lectureId!!)
            .orElseThrow { LectureNotFoundException() }

        lectureBasket.addLecture(lecture) { id ->
            lectureTimeRepository.findAllByLectureId(id)
        }

        lectureBasketRepository.save(lectureBasket)
    }

    @Transactional
    fun generateLectureBasket(studentId: Long, request: PostLectureBasketRequest) {
        validateStudentExists(studentId)

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
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)
        validateLectureBasketAccess(lectureBasket, studentId)

        val lectureDtos = lectureBasket.getLectures()
            .map { buildLectureInBasketDto(it.lectureId) }

        return GetLectureBasketResponse.from(lectureBasket, lectureDtos)
    }

    fun findLectureBaskets(studentId: Long): List<GetLectureBasketsResponse> {
        validateStudentExists(studentId)

        val lectureBaskets = lectureBasketRepository.findLectureBasketsByStudentId(studentId)

        return groupByYearAndSemester(lectureBaskets)
            .sortedWith(
                compareByDescending<GetLectureBasketsResponse> { it.year }
                    .thenBy { it.semester.order }
            )
    }

    @Transactional
    fun deleteLectureBasket(studentId: Long, lectureBasketId: Long) {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)

        validateLectureBasketAccess(lectureBasket, studentId)

        lectureBasket.validateDeletable()
        lectureBasketRepository.delete(lectureBasket)
    }

    fun findDefaultLectureBasket(studentId: Long): GetLectureBasketResponse {
        validateStudentExists(studentId)
        val (year, semester) = SemesterResolver.resolve(now = LocalDate.now())

        val lectureBasket = lectureBasketRepository.findLectureBasketByStudentIdAndYearAndSemesterAndStatus(
            studentId = studentId,
            year = year,
            semester = semester,
            status = Status.DEFAULT
        ).orElse(null)
            ?: return GetLectureBasketResponse.empty(year, semester)

        val lectureDtos = lectureBasket.getLectures()
            .map { buildLectureInBasketDto(it.lectureId) }

        return GetLectureBasketResponse.from(lectureBasket, lectureDtos)
    }

    @Transactional
    fun deleteLectureInBasket(studentId: Long, lectureBasketId: Long, lectureInBasketId: Long) {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)

        validateLectureBasketAccess(lectureBasket, studentId)

        val lectureInBasket = findLectureInBasket(lectureInBasketId)
        lectureBasket.validateLectureInBasket(lectureInBasket)

        val lecture = findLecture(lectureInBasket.lectureId)

        lectureBasket.removeLecture(lecture)
    }

    @Transactional
    fun modifyLectureBasketName(studentId: Long, lectureBasketId: Long, request: PatchLectureBasketNameRequest) {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)

        validateLectureBasketAccess(lectureBasket, studentId)

        lectureBasket.rename(request.lectureBasketName!!)
    }

    @Transactional
    fun modifyDefaultLectureBasket(studentId: Long, lectureBasketId: Long) {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)

        validateLectureBasketAccess(lectureBasket, studentId)

        val (year, semester) = SemesterResolver.resolve(now = LocalDate.now())

        val defaultLectureBasket = lectureBasketRepository.findLectureBasketByStudentIdAndYearAndSemesterAndStatus(
            studentId,
            year,
            semester,
            Status.DEFAULT
        ).orElseThrow { LectureBasketNotFoundException() }

        lectureBasket.changeStatusToDefault(defaultLectureBasket)
    }

    @Transactional
    fun modifyLectureColorInBasket(
        studentId: Long,
        lectureBasketId: Long,
        lectureInBasketId: Long,
        request: PatchLectureColorInBasketRequest
    ) {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)
        validateLectureBasketAccess(lectureBasket, studentId)

        val lectureInBasket = findLectureInBasket(lectureInBasketId)
        lectureBasket.validateLectureInBasket(lectureInBasket)

        lectureBasket.changeColor(lectureInBasket.lectureId, request.color!!)
    }

    fun findLectureInBasket(
        studentId: Long,
        lectureBasketId: Long,
        lectureInBasketId: Long
    ): GetLectureInBasketResponse {
        validateStudentExists(studentId)

        val lectureBasket = findLectureBasket(lectureBasketId)
        validateLectureBasketAccess(lectureBasket, studentId)

        val lectureInBasket = findLectureInBasket(lectureInBasketId)
        lectureBasket.validateLectureInBasket(lectureInBasket)

        val lecture = findLectureByLectureInBasket(lectureInBasket)
        val course = findCourse(lecture)
        val professor = findProfessor(lecture)

        val lectureTimes: List<LectureTime> = lectureTimeRepository.findAllByLectureId(lecture.id!!)
        val timeSlots: List<TimeSlot> = lectureTimes.map { it.timeSlot }

        return GetLectureInBasketResponse.from(
            lectureInBasket,
            professor,
            lecture,
            course,
            timeSlots.toMergedTimeRanges()
        )
    }

    private fun findLectureByLectureInBasket(lectureInBasket: LectureInBasket): Lecture {
        val lecture = lectureRepository.findById(lectureInBasket.lectureId)
            .orElseThrow { LectureNotFoundInBasketException() }
        return lecture
    }

    private fun findLectureInBasket(lectureInBasketId: Long): LectureInBasket {
        val lectureInBasket = lectureInBasketRepository.findById(lectureInBasketId)
            .orElseThrow { LectureInBasketNotFoundException() }
        return lectureInBasket
    }

    private fun findLectureBasket(lectureBasketId: Long): LectureBasket {
        val lectureBasket = lectureBasketRepository.findById(lectureBasketId)
            .orElseThrow { LectureBasketNotFoundException() }
        return lectureBasket
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

        val course = findCourse(lecture)

        val professor = findProfessor(lecture)

        val times = lectureTimeRepository.findAllByLectureId(lectureId)
        val timeSlots = times.map { it.timeSlot }

        return LectureInBasketDto.from(lecture, course, professor, timeSlots.toMergedTimeRanges())
    }

    private fun findProfessor(lecture: Lecture): Professor {
        val professor = professorRepository.findById(lecture.professorId)
            .orElseThrow { ProfessorNotFoundException() }
        return professor
    }

    private fun findCourse(lecture: Lecture): Course {
        val course = courseRepository.findById(lecture.courseId)
            .orElseThrow { CourseNotFoundException() }
        return course
    }

    private fun findLecture(lectureInBasketId: Long): Lecture {
        val lecture = lectureRepository.findById(lectureInBasketId)
            .orElseThrow { LectureNotFoundInBasketException() }
        return lecture
    }

    private fun validateStudentExists(studentId: Long) {
        if (!studentRepository.existsById(studentId)) {
            throw StudentNotFoundException()
        }
    }

    private fun groupByYearAndSemester(
        baskets: List<LectureBasket>
    ): List<GetLectureBasketsResponse> {
        return baskets
            .groupBy { it.year to it.semester }
            .map { (key, group) ->
                val (year, semester) = key
                GetLectureBasketsResponse.from(
                    year = year,
                    semester = semester,
                    lectureBaskets = group.map { LectureBasketDto.from(it) }
                )
            }
    }

    private fun validateLectureBasketAccess(lectureBasket: LectureBasket, studentId: Long) {
        if (lectureBasket.studentId != studentId) {
            throw LectureBasketAccessDeniedException()
        }
    }
}