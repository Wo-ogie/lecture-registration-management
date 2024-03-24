package hhplus.clean_architecture.domain.lecture.service

import hhplus.clean_architecture.domain.lecture.repository.LectureRegistrationRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
@Service
class LectureRegistrationService(
    private val lectureRegistrationRepository: LectureRegistrationRepository,
) {

    /**
     * 주어진 유저가 특정 강의에 등록했는지 여부를 조회한다.
     *
     * @param userId 강의 등록 여부를 조회할 유저의 id
     * @param lectureId 등록 여부를 조회할 강의 id
     * @return 강의 등록 여부. 주어진 유저가 해당 강의에 등록했다면 true, 그렇지 않다면 false
     */
    fun existsByUserAndLecture(userId: Long, lectureId: Long): Boolean {
        TODO("Not yet implemented")
    }
}