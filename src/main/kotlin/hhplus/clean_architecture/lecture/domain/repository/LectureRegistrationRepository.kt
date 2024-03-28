package hhplus.clean_architecture.lecture.domain.repository

import hhplus.clean_architecture.lecture.domain.entity.LectureRegistration

interface LectureRegistrationRepository {
    fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean

    fun getCountByLecture(lectureId: Long): Long

    fun save(lectureRegistration: LectureRegistration): LectureRegistration
}