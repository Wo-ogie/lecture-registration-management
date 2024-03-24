package hhplus.clean_architecture.domain.lecture.repository

import hhplus.clean_architecture.domain.lecture.entity.LectureRegistrationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LectureRegistrationJpaRepository : JpaRepository<LectureRegistrationJpaEntity, Long> {
    
    fun existsByUserIdAndLectureId(userId: Long, lectureId: Long): Boolean
}