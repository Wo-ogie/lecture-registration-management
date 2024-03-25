package hhplus.clean_architecture.domain.lecture.dto.response

import java.time.LocalDateTime

data class LectureRegisterResponse(
    val id: Long,
    val userId: Long,
    val lectureId: Long,
    val registrationTime: LocalDateTime,
)
