package hhplus.clean_architecture.lecture.dto.response

import hhplus.clean_architecture.lecture.domain.Lecture
import java.time.LocalDateTime

data class LectureResponse(
    val id: Long,
    val title: String,
    val lectureTime: LocalDateTime,
    val registrationStartTime: LocalDateTime,
    val maxParticipants: Int,
) {
    companion object {
        fun from(lecture: Lecture): LectureResponse {
            return LectureResponse(
                id = lecture.id!!,
                title = lecture.title,
                lectureTime = lecture.lectureTime,
                registrationStartTime = lecture.registrationStartTime,
                maxParticipants = lecture.maxParticipants
            )
        }
    }
}
