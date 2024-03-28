package hhplus.clean_architecture.lecture.domain

import hhplus.clean_architecture.lecture.exception.LectureNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class LectureService(
    private val lectureRepository: LectureRepository,
) {

    /**
     * Id로 강의를 단건 조회한다.
     *
     * @param lectureId 조회할 강의의 id
     * @return 조회된 강의
     * @throws LectureNotFoundException id에 해당하는 강의가 없는 경우
     */
    @Throws(LectureNotFoundException::class)
    fun getById(lectureId: Long): Lecture {
        return lectureRepository.getById(lectureId)
    }

    /**
     * 전체 강의 목록을 조회한다.
     *
     * @return 전체 강의 리스트
     */
    fun findAll(): List<Lecture> {
        return lectureRepository.findAll()
    }

    /**
     * 새로운 강의를 생성한다.
     *
     * @param title 강의 제목
     * @param registrationStartTime 수강 신청 가능 시각
     * @param maxParticipants 수강 최대 인원
     * @return 생성 및 저장된 강의
     */
    @Transactional
    fun create(
        title: String,
        lectureTime: LocalDateTime,
        registrationStartTime: LocalDateTime,
        maxParticipants: Int = 30,
    ): Lecture {
        val lecture = Lecture(title, lectureTime, registrationStartTime, maxParticipants)
        return lectureRepository.save(lecture)
    }
}