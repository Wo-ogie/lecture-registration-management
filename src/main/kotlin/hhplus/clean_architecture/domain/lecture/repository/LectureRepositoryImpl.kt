package hhplus.clean_architecture.domain.lecture.repository

import hhplus.clean_architecture.domain.lecture.entity.Lecture
import hhplus.clean_architecture.domain.lecture.exception.LectureNotFoundException
import org.springframework.stereotype.Repository

@Repository
class LectureRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository,
) : LectureRepository {

    override fun getById(lectureId: Long): Lecture {
        val lecture = lectureJpaRepository.findById(lectureId)
            .orElseThrow { LectureNotFoundException() }
        return lecture.toDomain()
    }
}