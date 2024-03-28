package hhplus.clean_architecture.unit.lecture.controller

import com.fasterxml.jackson.databind.ObjectMapper
import hhplus.clean_architecture.lecture.controller.LectureController
import hhplus.clean_architecture.lecture.domain.Lecture
import hhplus.clean_architecture.lecture.domain.LectureRegistration
import hhplus.clean_architecture.lecture.domain.LectureRegistrationService
import hhplus.clean_architecture.lecture.domain.LectureService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime

@DisplayName("Controller - Lecture")
@WebMvcTest(controllers = [LectureController::class])
class LectureControllerTest @Autowired constructor(
    private val mvc: MockMvc,
    private val mapper: ObjectMapper,
) {
    @MockBean
    private lateinit var lectureService: LectureService

    @MockBean
    private lateinit var lectureRegistrationService: LectureRegistrationService

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
        given(lectureService.findAll())
            .willReturn(expectedResult)

        // when & then
        mvc.perform(
            get("/api/lectures")
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.lectures.size()").value(expectedResult.size))
        then(lectureService).should().findAll()
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    @Test
    fun `유저와 강의 id가 주어지고, 주어진 강의를 신청한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val registerRequest = hhplus.clean_architecture.lecture.dto.request.LectureRegisterRequest(userId)
        val expectedResult = LectureRegistration(
            userId = userId,
            lectureId = lectureId,
            registrationTime = LocalDateTime.now(),
            id = 3L
        )
        given(lectureRegistrationService.register(userId, lectureId))
            .willReturn(expectedResult)

        // when & then
        mvc.perform(
            post("/api/lectures/$lectureId/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(registerRequest))
        ).andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").value(expectedResult.id))
            .andExpect(jsonPath("$.userId").value(expectedResult.userId))
            .andExpect(jsonPath("$.lectureId").value(expectedResult.lectureId))
            .andExpect(jsonPath("$.registrationTime").exists())
        then(lectureRegistrationService).should().register(userId, lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    @Test
    fun `유저 id와 특정 강의의 id가 주어지고, 주어진 유저가 특정 강의에 등록했는지 등록 여부를 조회한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val expectedResult = true
        given(lectureRegistrationService.existsByUserAndLecture(userId, lectureId))
            .willReturn(expectedResult)

        // when & then
        mvc.perform(
            get("/api/lectures/$lectureId/check-registrations")
                .param("userId", userId.toString())
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.isRegistered").value(expectedResult))
        then(lectureRegistrationService).should().existsByUserAndLecture(userId, lectureId)
        verifyEveryMocksShouldHaveNoMoreInteractions()
    }

    private fun verifyEveryMocksShouldHaveNoMoreInteractions() {
        then(lectureService).shouldHaveNoMoreInteractions()
        then(lectureRegistrationService).shouldHaveNoMoreInteractions()
    }
}