package hhplus.clean_architecture.unit.domain.lecture.service

import hhplus.clean_architecture.domain.lecture.entity.Lecture
import hhplus.clean_architecture.domain.lecture.exception.LectureNotFoundException
import hhplus.clean_architecture.domain.lecture.repository.LectureRepository
import hhplus.clean_architecture.domain.lecture.service.LectureService
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.given
import org.mockito.kotlin.then

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
        val expectedResult = Lecture(lectureId, "title", 30)
        given(lectureRepository.getById(lectureId))
            .willReturn(expectedResult)

        // when
        val actualResult = sut.getById(lectureId)

        // then
        then(lectureRepository).should().getById(lectureId)
        then(lectureRepository).shouldHaveNoMoreInteractions()
        assertThat(actualResult).isEqualTo(expectedResult)
    }

    @Test
    fun `강의 id가 주어지고, id와 일치하는 강의를 단건 조회한다, 만약 존재하지 않는 강의 id라면 예외가 발생한다`() {
        // given
        val lectureId = 1L
        given(lectureRepository.getById(lectureId))
            .willThrow(LectureNotFoundException::class.java)

        // when
        val throwable = catchThrowable { sut.getById(lectureId) }

        // then
        then(lectureRepository).should().getById(lectureId)
        then(lectureRepository).shouldHaveNoMoreInteractions()
        assertThat(throwable).isInstanceOf(LectureNotFoundException::class.java)
    }
}