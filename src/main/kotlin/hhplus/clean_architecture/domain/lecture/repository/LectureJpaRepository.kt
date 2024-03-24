package hhplus.clean_architecture.domain.lecture.repository

import hhplus.clean_architecture.domain.lecture.entity.LectureJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface LectureJpaRepository : JpaRepository<LectureJpaEntity, Long>