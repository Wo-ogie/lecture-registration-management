package hhplus.clean_architecture.lecture.dto.response

import hhplus.clean_architecture.lecture.domain.entity.LectureRegistration
import java.time.LocalDateTime

data class LectureRegisterResponse(
    val id: Long,
    val userId: Long,
    val lectureId: Long,
    val registrationTime: LocalDateTime,
) {
    companion object {
        fun from(lectureRegistration: LectureRegistration): LectureRegisterResponse {
            return LectureRegisterResponse(
                id = lectureRegistration.id!!,
                userId = lectureRegistration.userId,
                lectureId = lectureRegistration.lectureId,
                registrationTime = lectureRegistration.registrationTime
            )
        }
    }
}
