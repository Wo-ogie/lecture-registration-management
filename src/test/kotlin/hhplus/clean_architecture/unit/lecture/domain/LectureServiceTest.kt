package hhplus.clean_architecture.unit.lecture.domain

import hhplus.clean_architecture.lecture.domain.Lecture
import hhplus.clean_architecture.lecture.domain.LectureRepository
import hhplus.clean_architecture.lecture.domain.LectureService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.given
import org.mockito.kotlin.then
import java.time.LocalDateTime

@DisplayName("Service - Lecture")
@ExtendWith(MockitoExtension::class)
class LectureServiceTest {

    @InjectMocks
    private lateinit var sut: LectureService

    @Mock
    private lateinit var lectureRepository: LectureRepository

    @Test
    fun `강의 id가 주어지고, id와 일치하는 강의를 단건 조회한다`() {
        // given
        val lectureId = 1L
        val expectedResult = Lecture(
            id = lectureId,
            title = "title",
            lectureTime = LocalDateTime.of(2024, 4, 1, 15, 0),
            maxParticipants = 30,
            registrationStartTime = LocalDateTime.of(2024, 3, 1, 12, 0)
        )
        given(lectureRepository.getById(lectureId))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.getById(lectureId)

        // then
        then(lectureRepository).should().getById(lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `강의 id가 주어지고, id와 일치하는 강의를 단건 조회한다, 만약 존재하지 않는 강의 id라면 예외가 발생한다`() {
        // given
        val lectureId = 1L
        given(lectureRepository.getById(lectureId))
            .willThrow(hhplus.clean_architecture.lecture.exception.LectureNotFoundException::class.java)

        // when
        val throwable = catchThrowable { sut.getById(lectureId) }

        // then
        then(lectureRepository).should().getById(lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(throwable).isInstanceOf(hhplus.clean_architecture.lecture.exception.LectureNotFoundException::class.java)
    }

    @Test
    fun `전체 강의 목록을 조회한다`() {
        // given
        val expectedResult = listOf(
            Lecture(
                id = 1L,
                title = "test",
                lectureTime = LocalDateTime.of(2024, 4, 1, 15, 0),
                registrationStartTime = LocalDateTime.of(2024, 3, 1, 12, 0)
            )
        )
        BDDMockito.given(lectureRepository.findAll())
            .willReturn(expectedResult)

        // when
        val actualResult = sut.findAll()

        // then
        BDDMockito.then(lectureRepository).should().findAll()
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).hasSize(expectedResult.size)
    }

    @Test
    fun `최대 수강 인원을 포함한 강의 정보가 주어지고, 주어진 정보로 신규 강의를 생성한다`() {
        // given
        val title = "title"
        val lectureTime = LocalDateTime.of(2024, 4, 1, 15, 0)
        val registrationStartTime = LocalDateTime.of(2024, 4, 1, 12, 0)
        val maxParticipants = 30
        val expectedResult = Lecture(
            title = title,
            lectureTime = lectureTime,
            registrationStartTime = registrationStartTime,
            maxParticipants = maxParticipants,
            id = 1L
        )
        given(lectureRepository.save(any()))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.create(title, lectureTime, registrationStartTime, maxParticipants)

        // then
        then(lectureRepository).should().save(any())
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `최대 수강 인원을 제외한 강의 정보가 주어지고, 주어진 정보로 신규 강의를 생성하면, 기본 30으로 최대 수강 인원이 설정된 강의가 생성된다`() {
        // given
        val title = "title"
        val lectureTime = LocalDateTime.of(2024, 4, 1, 15, 0)
        val registrationStartTime = LocalDateTime.of(2024, 4, 1, 12, 0)
        val expectedResult = Lecture(
            title = title,
            lectureTime = lectureTime,
            registrationStartTime = registrationStartTime,
            maxParticipants = 30,
            id = 1L
        )
        given(lectureRepository.save(any()))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.create(title, lectureTime, registrationStartTime)

        // then
        then(lectureRepository).should().save(any())
        verifyEveryMocksShouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(lectureRepository).shouldHaveNoMoreInteractions()
    }
}