package hhplus.clean_architecture.domain.lecture.service

import hhplus.clean_architecture.domain.lecture.entity.Lecture
import hhplus.clean_architecture.domain.lecture.exception.LectureNotFoundException
import hhplus.clean_architecture.domain.lecture.repository.LectureRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class LectureService(
    private val lectureRepository: LectureRepository,
) {

    @Throws(LectureNotFoundException::class)
    fun getById(lectureId: Long): Lecture {
        return lectureRepository.getById(lectureId)
    }

    @Throws(LectureNotFoundException::class)
    fun getByIdWithLock(lectureId: Long): Lecture {
        return lectureRepository.getByIdWithLock(lectureId)
    }

    @Transactional
    fun create(
        title: String,
        registrationStartTime: LocalDateTime,
        maxParticipants: Int = 30,
    ): Lecture {
        val lecture = Lecture(title, registrationStartTime, maxParticipants)
        return lectureRepository.save(lecture)
    }
}