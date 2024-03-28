package hhplus.clean_architecture.lecture.domain

import hhplus.clean_architecture.lecture.domain.LectureRegistration
import hhplus.clean_architecture.lecture.domain.LectureRegistrationRepository
import hhplus.clean_architecture.lecture.domain.LectureRepository
import hhplus.clean_architecture.lecture.exception.LectureAlreadyRegisteredException
import hhplus.clean_architecture.lecture.exception.LectureCapacityExceededException
import hhplus.clean_architecture.lecture.exception.LectureRegistrationNotStartedException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Transactional(readOnly = true)
@Service
class LectureRegistrationService(
    private val lectureRepository: LectureRepository,
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
        return lectureRegistrationRepository.existsByUserAndLecture(userId, lectureId)
    }

    /**
     * 특정 강의에 대해 신청 이력 개수를 조회한다.
     *
     * @param lectureId 신청 이력 개수를 조회하고자 하는 강의의 id
     * @return 특정 강의에 대한 신청 이력 개수
     */
    fun getCountByLecture(lectureId: Long): Long {
        return lectureRegistrationRepository.getCountByLecture(lectureId)
    }

    /**
     * 강의에 등록한다. (수강 신청)
     *
     * @param userId 강의에 등록하고자 하는 유저의 id
     * @param lectureId 등록할 강의 id
     * @return 강의 등록 정보
     * @throws LectureRegistrationNotStartedException 수강 신청 가능 시각이 되지 않은 경우. 즉 수강 신청이 불가능한 경우
     * @throws LectureAlreadyRegisteredException 이미 수강중인 강의인 경우
     * @throws LectureCapacityExceededException 수강 최대 인원이 꽉 찬 경우
     */
    @Transactional
    fun register(userId: Long, lectureId: Long): LectureRegistration {
        val lecture = lectureRepository.getByIdWithLock(lectureId)
        val now = LocalDateTime.now()

        if (now.isBefore(lecture.registrationStartTime)) {
            throw LectureRegistrationNotStartedException()
        }

        if (lectureRegistrationRepository.existsByUserAndLecture(userId, lectureId)) {
            throw LectureAlreadyRegisteredException()
        }

        val currentParticipants = lectureRegistrationRepository.getCountByLecture(lectureId)
        if (currentParticipants >= lecture.maxParticipants) {
            throw LectureCapacityExceededException()
        }

        return lectureRegistrationRepository.save(
            LectureRegistration(
                userId = userId,
                lectureId = lectureId,
                registrationTime = now
            )
        )
    }
}