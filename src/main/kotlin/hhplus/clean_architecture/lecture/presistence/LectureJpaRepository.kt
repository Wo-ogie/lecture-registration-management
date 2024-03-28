package hhplus.clean_architecture.lecture.presistence

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import java.util.*

interface LectureJpaRepository : JpaRepository<LectureJpaEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT lecture FROM LectureJpaEntity lecture WHERE lecture.id = :id")
    fun findByIdWithLock(id: Long): Optional<LectureJpaEntity>
}