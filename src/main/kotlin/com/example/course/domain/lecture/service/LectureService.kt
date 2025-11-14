package com.example.course.domain.lecture.service

import com.example.course.domain.lecture.dao.CourseRepository
import com.example.course.domain.lecture.dao.LectureRepository
import com.example.course.domain.lecture.dao.LectureTimeRepository
import com.example.course.domain.lecture.dao.ProfessorRepository
import com.example.course.domain.lecture.dto.GetLectureResponse
import com.example.course.domain.lecture.dto.LectureDto
import com.example.course.domain.lecture.entity.Course
import com.example.course.domain.lecture.entity.Lecture
import com.example.course.domain.lecture.entity.LectureTime
import com.example.course.domain.lecture.entity.Professor
import com.example.course.domain.lecture.enums.CourseType
import com.example.course.domain.lecture.enums.Semester
import com.example.course.domain.lecture.enums.TimeSlot
import com.example.course.domain.student.dao.DepartmentRepository
import com.example.course.domain.student.util.SemesterResolver
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Slice
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class LectureService(
    val lectureRepository: LectureRepository,
    val courseRepository: CourseRepository,
    val professorRepository: ProfessorRepository,
    val departmentRepository: DepartmentRepository,
    val lectureTimeRepository: LectureTimeRepository
) {
    fun findLectures(
        name: String?,
        professorName: String?,
        timeSlots: List<TimeSlot>?,
        semester: Semester?,
        year: Int?,
        courseType: CourseType?,
        departmentName: String?,
        grade: Int?,
        credit: Int?,
        page: Int,
        size: Int
    ): GetLectureResponse {
        val pageable: Pageable = PageRequest.of(page - 1, size)

        val (resolvedYear, resolvedSemester) = resolveYearAndSemester(year, semester)

        val lectureSlice = findLectureSlice(
            name,
            professorName,
            timeSlots,
            resolvedSemester,
            resolvedYear,
            courseType,
            departmentName,
            grade,
            credit,
            pageable
        )
        val lectures = lectureSlice.content

        if (lectures.isEmpty()) return GetLectureResponse.empty()
        return GetLectureResponse.of(lectureSlice.hasNext(), mapToLectureDtos(lectures))
    }

    private fun findProfessorIdsByName(professorName: String): List<Long>? =
        professorRepository.findIdsByNameContaining(professorName)
            .takeIf { it.isNotEmpty() }

    private fun findDepartmentIdsByName(name: String?): List<Long>? =
        name?.let {
            departmentRepository.findIdsByNameContaining(it)
                .takeIf { it -> it.isNotEmpty() }
        }

    private fun findFilteredCourseIds(
        name: String?,
        courseType: CourseType?,
        departmentIds: List<Long>?,
        grade: Int?,
        credit: Int?,
    ): List<Long>? = courseRepository.findFilteredCourseIds(
        name = name,
        type = courseType,
        departmentIds = departmentIds,
        grade = grade,
        credit = credit
    ).takeIf { it.isNotEmpty() }

    private fun findLectureIdsByTimeSlots(timeSlots: List<TimeSlot>?): List<Long>? {
        return timeSlots?.takeIf { it.isNotEmpty() }?.let {
            lectureTimeRepository.findLectureIdsContainedIn(it)
        }
    }

    private fun findCourseMap(lectures: List<Lecture>): Map<Long, Course> {
        return courseRepository.findAllById(lectures.map { it.courseId })
            .associateBy { it.id!! }
    }

    private fun findProfessorMap(lectures: List<Lecture>): Map<Long, Professor> {
        return professorRepository.findAllById(lectures.map { it.professorId })
            .associateBy { it.id!! }
    }

    private fun findLectureTimeMap(lectures: List<Lecture>): Map<Long, List<LectureTime>> {
        return lectureTimeRepository.findAllByLectureIdIn(lectures.mapNotNull { it.id })
            .groupBy { it.lectureId }
    }

    private fun findLectureSlice(
        name: String?,
        professorName: String?,
        timeSlots: List<TimeSlot>?,
        semester: Semester?,
        year: Int?,
        courseType: CourseType?,
        departmentName: String?,
        grade: Int?,
        credit: Int?,
        pageable: Pageable
    ): Slice<Lecture> {
        val professorIds = professorName?.let(::findProfessorIdsByName)
        val courseIds = findFilteredCourseIds(
            name = name,
            courseType = courseType,
            departmentIds = findDepartmentIdsByName(departmentName),
            grade = grade,
            credit = credit
        )
        val lectureIdsByTimeSlot = findLectureIdsByTimeSlots(timeSlots)

        return lectureRepository.findFilteredLectures(
            courseIds = courseIds,
            professorIds = professorIds,
            semester = semester,
            year = year,
            lectureIds = lectureIdsByTimeSlot,
            pageable = pageable
        )
    }

    private fun mapToLectureDtos(lectures: List<Lecture>): List<LectureDto> {
        val lectureTimeMap = findLectureTimeMap(lectures)
        val courseMap = findCourseMap(lectures)
        val professorMap = findProfessorMap(lectures)

        return lectures.map { lecture ->
            val course = courseMap[lecture.courseId]!!
            val professor = professorMap[lecture.professorId]!!
            val times = lectureTimeMap[lecture.id]!!
            LectureDto.from(lecture, course, professor, times)
        }
    }

    private fun resolveYearAndSemester(
        year: Int?,
        semester: Semester?
    ): Pair<Int, Semester> {

        val (defaultYear, defaultSemester) = SemesterResolver.resolve(LocalDate.now())

        val resolvedYear = year ?: defaultYear
        val resolvedSemester = semester ?: defaultSemester

        return resolvedYear to resolvedSemester
    }
}