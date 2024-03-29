package hhplus.clean_architecture.lecture.domain

import hhplus.clean_architecture.lecture.exception.LectureCapacityExceededException
import hhplus.clean_architecture.lecture.exception.LectureRegistrationNotStartedException
import java.time.LocalDateTime

data class Lecture(
    val title: String,
    val lectureTime: LocalDateTime,
    val registrationStartTime: LocalDateTime,
    val maxParticipants: Int = 30,
    val id: Long? = null,
) {

    @Throws(
        LectureRegistrationNotStartedException::class,
        LectureCapacityExceededException::class
    )
    fun validateRegistrationAvailable(nowDateTime: LocalDateTime, currentParticipantCount: Long) {
        if (registrationStartTime.isAfter(nowDateTime)) {
            throw LectureRegistrationNotStartedException()
        }
        if (currentParticipantCount >= maxParticipants) {
            throw LectureCapacityExceededException()
        }
    }
}