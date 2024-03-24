package hhplus.clean_architecture.domain.lecture.repository

interface LectureRegistrationRepository {
    fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean
}