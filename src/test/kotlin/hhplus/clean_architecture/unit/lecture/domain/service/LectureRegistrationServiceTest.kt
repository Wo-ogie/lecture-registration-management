package hhplus.clean_architecture.unit.lecture.domain.service

import hhplus.clean_architecture.lecture.domain.entity.Lecture
import hhplus.clean_architecture.lecture.domain.entity.LectureRegistration
import hhplus.clean_architecture.lecture.domain.repository.LectureRegistrationRepository
import hhplus.clean_architecture.lecture.domain.service.LectureRegistrationService
import hhplus.clean_architecture.lecture.domain.service.LectureService
import hhplus.clean_architecture.lecture.exception.LectureRegistrationNotStartedException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import java.time.LocalDateTime

@DisplayName("Service - LectureRegistration")
@ExtendWith(MockitoExtension::class)
class LectureRegistrationServiceTest {

    @InjectMocks
    private lateinit var sut: LectureRegistrationService

    @Mock
    private lateinit var lectureService: LectureService

    @Mock
    private lateinit var lectureRegistrationRepository: LectureRegistrationRepository

    @Test
    fun `유저 id와 특정 강의의 id가 주어지고, 주어진 유저가 특정 강의에 등록했는지 등록 여부를 조회한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val expectedResult = true
        given(lectureRegistrationRepository.existsByUserAndLecture(userId, lectureId))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.existsByUserAndLecture(userId, lectureId)

        // then
        then(lectureRegistrationRepository).should().existsByUserAndLecture(userId, lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `강의 id가 주어지고, 주어진 강의에 대한 신청 이력 개수를 조회한다`() {
        // given
        val lectureId = 1L
        val expectedResult = 5L
        given(lectureRegistrationRepository.getCountByLecture(lectureId))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.getCountByLecture(lectureId)

        // then
        then(lectureRegistrationRepository).should().getCountByLecture(lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `주어진 특강 id에 해당하는 특강을 신청한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val lecture = Lecture(
            id = lectureId,
            title = "test",
            registrationStartTime = LocalDateTime.of(2024, 3, 1, 12, 0)
        )
        val expectedResult = LectureRegistration(
            id = 3L,
            userId = userId,
            lectureId = lectureId,
            registrationTime = LocalDateTime.now()
        )
        given(lectureService.getByIdWithLock(lectureId))
            .willReturn(lecture)
        given(lectureRegistrationRepository.existsByUserAndLecture(userId, lectureId))
            .willReturn(false)
        given(lectureRegistrationRepository.getCountByLecture(lectureId))
            .willReturn(0L)
        given(lectureRegistrationRepository.save(any()))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.register(userId, lectureId)

        // then
        then(lectureService).should().getByIdWithLock(lectureId)
        then(lectureRegistrationRepository).should().existsByUserAndLecture(userId, lectureId)
        then(lectureRegistrationRepository).should().getCountByLecture(lectureId)
        then(lectureRegistrationRepository).should().save(any())
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(expectedResult.id).isEqualTo(actualResult.id)
        assertThat(expectedResult.userId).isEqualTo(actualResult.userId)
        assertThat(expectedResult.lectureId).isEqualTo(actualResult.lectureId)
    }

    @Test
    fun `주어진 특강 id에 해당하는 특강을 신청한다, 아직 신청 가능 시각이 되지 않은 특강이라면, 예외가 발생한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val lecture = Lecture(
            id = lectureId,
            title = "test",
            registrationStartTime = LocalDateTime.of(2025, 1, 1, 12, 0)
        )
        given(lectureService.getByIdWithLock(lectureId))
            .willReturn(lecture)

        // when
        val throwable = catchThrowable { sut.register(userId, lectureId) }

        // then
        then(lectureService).should().getByIdWithLock(lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(throwable).isInstanceOf(LectureRegistrationNotStartedException::class.java)
    }

    @Test
    fun `주어진 특강 id에 해당하는 특강을 신청한다, 이미 신청한 특강이라면, 예외가 발생한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val lecture = Lecture(
            id = lectureId,
            title = "test",
            registrationStartTime = LocalDateTime.of(2024, 3, 1, 12, 0)
        )
        given(lectureService.getByIdWithLock(lectureId))
            .willReturn(lecture)
        given(lectureRegistrationRepository.existsByUserAndLecture(userId, lectureId))
            .willReturn(true)

        // when
        val throwable = catchThrowable { sut.register(userId, lectureId) }

        // then
        then(lectureService).should().getByIdWithLock(lectureId)
        then(lectureRegistrationRepository).should().existsByUserAndLecture(userId, lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(throwable).isInstanceOf(hhplus.clean_architecture.lecture.exception.LectureAlreadyRegisteredException::class.java)
    }

    @Test
    fun `주어진 특강 id에 해당하는 특강을 신청한다, 만약 수강 인원이 꽉 찬 특강이라면, 예외가 발생한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val lecture = Lecture(
            id = lectureId,
            title = "test",
            maxParticipants = 30,
            registrationStartTime = LocalDateTime.of(2024, 3, 1, 12, 0)
        )
        given(lectureService.getByIdWithLock(lectureId))
            .willReturn(lecture)
        given(lectureRegistrationRepository.existsByUserAndLecture(userId, lectureId))
            .willReturn(false)
        given(lectureRegistrationRepository.getCountByLecture(lectureId))
            .willReturn(lecture.maxParticipants.toLong())

        // when
        val throwable = catchThrowable { sut.register(userId, lectureId) }

        // then
        then(lectureService).should().getByIdWithLock(lectureId)
        then(lectureRegistrationRepository).should().existsByUserAndLecture(userId, lectureId)
        then(lectureRegistrationRepository).should().getCountByLecture(lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(throwable).isInstanceOf(hhplus.clean_architecture.lecture.exception.LectureCapacityExceededException::class.java)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(lectureService).shouldHaveNoMoreInteractions()
        then(lectureRegistrationRepository).shouldHaveNoMoreInteractions()
    }
}