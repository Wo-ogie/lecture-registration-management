package hhplus.clean_architecture.domain.lecture.repository

import org.springframework.stereotype.Repository

@Repository
class LectureRegistrationRepositoryImpl(
    private val lectureRegistrationJpaRepository: LectureRegistrationJpaRepository
) : LectureRegistrationRepository {

    override fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean {
        return lectureRegistrationJpaRepository.existsByUserIdAndLectureId(userId, lectureId)
    }

    override fun getCountByLecture(lectureId: Long): Long {
        return lectureRegistrationJpaRepository.countByLectureId(lectureId)
    }
}