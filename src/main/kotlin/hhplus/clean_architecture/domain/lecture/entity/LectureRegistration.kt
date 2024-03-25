package hhplus.clean_architecture.domain.lecture.entity

import java.time.LocalDateTime

data class LectureRegistration(
    val id: Long? = null,
    val userId: Long,
    val lectureId: Long,
    val registrationTime: LocalDateTime,
)