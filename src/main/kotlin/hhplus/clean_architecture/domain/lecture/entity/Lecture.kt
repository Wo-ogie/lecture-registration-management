package hhplus.clean_architecture.domain.lecture.entity

import java.time.LocalDateTime

data class Lecture(
    val title: String,
    val registrationStartTime: LocalDateTime,
    val maxParticipants: Int = 30,
    val id: Long? = null,
)