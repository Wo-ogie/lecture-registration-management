package hhplus.clean_architecture.lecture.presistence

import org.springframework.data.jpa.repository.JpaRepository

interface LectureRegistrationJpaRepository : JpaRepository<LectureRegistrationJpaEntity, Long> {

    fun existsByUserIdAndLectureId(userId: Long, lectureId: Long): Boolean

    fun countByLectureId(lectureId: Long): Long
}