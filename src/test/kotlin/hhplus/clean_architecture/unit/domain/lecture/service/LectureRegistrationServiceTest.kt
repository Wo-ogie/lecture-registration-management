package hhplus.clean_architecture.unit.domain.lecture.service

import hhplus.clean_architecture.domain.lecture.repository.LectureRegistrationRepository
import hhplus.clean_architecture.domain.lecture.service.LectureRegistrationService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@DisplayName("Service - LectureRegistration")
@ExtendWith(MockitoExtension::class)
class LectureRegistrationServiceTest {

    @InjectMocks
    private lateinit var sut: LectureRegistrationService

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
        then(lectureRegistrationRepository).shouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }
}