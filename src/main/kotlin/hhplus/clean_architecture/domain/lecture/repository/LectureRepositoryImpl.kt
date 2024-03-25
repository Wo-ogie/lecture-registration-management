package hhplus.clean_architecture.domain.lecture.repository

import hhplus.clean_architecture.domain.lecture.entity.Lecture
import hhplus.clean_architecture.domain.lecture.entity.LectureJpaEntity
import hhplus.clean_architecture.domain.lecture.exception.LectureNotFoundException
import org.springframework.stereotype.Repository

@Repository
class LectureRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository,
) : LectureRepository {

    override fun getById(lectureId: Long): Lecture {
        return lectureJpaRepository.findById(lectureId)
            .orElseThrow(::LectureNotFoundException)
            .toDomain()
    }

    override fun getByIdWithLock(lectureId: Long): Lecture {
        return lectureJpaRepository.findByIdWithLock(lectureId)
            .orElseThrow(::LectureNotFoundException)
            .toDomain()
    }

    override fun save(lecture: Lecture): Lecture {
        val savedLecture = lectureJpaRepository.save(LectureJpaEntity.from(lecture))
        return savedLecture.toDomain()
    }
}