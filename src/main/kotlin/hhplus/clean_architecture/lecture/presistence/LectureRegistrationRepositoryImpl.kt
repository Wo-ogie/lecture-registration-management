package hhplus.clean_architecture.lecture.presistence

import hhplus.clean_architecture.lecture.domain.entity.LectureRegistration
import hhplus.clean_architecture.lecture.domain.repository.LectureRegistrationRepository
import hhplus.clean_architecture.lecture.exception.LectureNotFoundException
import hhplus.clean_architecture.lecture.presistence.entity.LectureRegistrationJpaEntity
import org.springframework.stereotype.Repository

@Repository
class LectureRegistrationRepositoryImpl(
    private val lectureJpaRepository: LectureJpaRepository,
    private val lectureRegistrationJpaRepository: LectureRegistrationJpaRepository,
) : LectureRegistrationRepository {

    override fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean {
        return lectureRegistrationJpaRepository.existsByUserIdAndLectureId(userId, lectureId)
    }

    override fun getCountByLecture(lectureId: Long): Long {
        return lectureRegistrationJpaRepository.countByLectureId(lectureId)
    }

    override fun save(lectureRegistration: LectureRegistration): LectureRegistration {
        val lecture = lectureJpaRepository.findById(lectureRegistration.lectureId)
            .orElseThrow(::LectureNotFoundException)
        val savedLectureRegistration = lectureRegistrationJpaRepository.save(
            LectureRegistrationJpaEntity.from(lectureRegistration, lecture)
        )
        return savedLectureRegistration.toDomain()
    }
}