package hhplus.clean_architecture.domain.lecture.service

import hhplus.clean_architecture.domain.lecture.entity.Lecture
import hhplus.clean_architecture.domain.lecture.exception.LectureNotFoundException
import hhplus.clean_architecture.domain.lecture.repository.LectureRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class LectureService(
    private val lectureRepository: LectureRepository,
) {

    @Throws(LectureNotFoundException::class)
    fun getById(lectureId: Long): Lecture {
        return lectureRepository.getById(lectureId)
    }
}