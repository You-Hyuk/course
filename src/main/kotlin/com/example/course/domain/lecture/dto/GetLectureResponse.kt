package com.example.course.domain.lecture.dto

data class GetLectureResponse(
    val hasNext: Boolean,
    val lectures: List<LectureDto>
) {
    companion object {
        fun empty(): GetLectureResponse {
            return GetLectureResponse(false, emptyList())
        }

        fun of(hasNext: Boolean, lectures: List<LectureDto>): GetLectureResponse {
            return GetLectureResponse(hasNext, lectures)
        }
    }
}
