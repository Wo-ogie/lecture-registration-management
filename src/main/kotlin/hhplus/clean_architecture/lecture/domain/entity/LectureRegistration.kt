package hhplus.clean_architecture.lecture.domain.entity

import java.time.LocalDateTime

data class LectureRegistration(
    val userId: Long,
    val lectureId: Long,
    val registrationTime: LocalDateTime,
    val id: Long? = null,
)