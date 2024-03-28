package hhplus.clean_architecture.lecture.presistence

import hhplus.clean_architecture.lecture.domain.Lecture
import hhplus.clean_architecture.lecture.domain.LectureRepository
import hhplus.clean_architecture.lecture.exception.LectureNotFoundException
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

    override fun findAll(): List<Lecture> {
        return lectureJpaRepository.findAll()
            .map(LectureJpaEntity::toDomain)
    }

    override fun save(lecture: Lecture): Lecture {
        val savedLecture = lectureJpaRepository.save(LectureJpaEntity.from(lecture))
        return savedLecture.toDomain()
    }
}