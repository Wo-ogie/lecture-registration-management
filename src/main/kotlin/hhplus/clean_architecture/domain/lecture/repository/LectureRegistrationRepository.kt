package hhplus.clean_architecture.domain.lecture.repository

import hhplus.clean_architecture.domain.lecture.entity.LectureRegistration

interface LectureRegistrationRepository {
    fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean

    fun getCountByLecture(lectureId: Long): Long

    fun save(lectureRegistration: LectureRegistration): LectureRegistration
}