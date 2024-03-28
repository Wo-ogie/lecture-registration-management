package hhplus.clean_architecture.lecture.domain

import hhplus.clean_architecture.lecture.domain.LectureRegistration

interface LectureRegistrationRepository {
    fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean

    fun getCountByLecture(lectureId: Long): Long

    fun save(lectureRegistration: LectureRegistration): LectureRegistration
}