package hhplus.clean_architecture.domain.lecture.entity

data class Lecture(
    val id: Long,
    val title: String,
    val maxParticipants: Int,
)