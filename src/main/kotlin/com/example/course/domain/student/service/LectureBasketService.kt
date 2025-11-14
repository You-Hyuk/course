package com.example.course.domain.student.service

import com.example.course.domain.lecture.dao.LectureRepository
import com.example.course.domain.lecture.dao.LectureTimeRepository
import com.example.course.domain.lecture.exception.LectureNotFoundException
import com.example.course.domain.student.dao.LectureBasketLectureRepository
import com.example.course.domain.student.dao.LectureBasketRepository
import com.example.course.domain.student.dao.StudentRepository
import com.example.course.domain.student.dto.PostAddLectureToBasketRequest
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
    val lectureRepository: LectureRepository
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
}