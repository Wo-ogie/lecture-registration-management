package hhplus.clean_architecture.lecture.domain.entity

import java.time.LocalDateTime

data class Lecture(
    val title: String,
    val registrationStartTime: LocalDateTime,
    val maxParticipants: Int = 30,
    val id: Long? = null,
)