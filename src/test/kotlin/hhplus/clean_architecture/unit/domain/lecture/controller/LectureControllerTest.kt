package hhplus.clean_architecture.unit.domain.lecture.controller

import com.fasterxml.jackson.databind.ObjectMapper
import hhplus.clean_architecture.domain.lecture.controller.LectureController
import hhplus.clean_architecture.domain.lecture.dto.request.LectureRegisterRequest
import hhplus.clean_architecture.domain.lecture.entity.LectureRegistration
import hhplus.clean_architecture.domain.lecture.service.LectureRegistrationService
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
    private lateinit var lectureRegistrationService: LectureRegistrationService

    @Test
    fun `유저와 강의 id가 주어지고, 주어진 강의를 신청한다`() {
        // given
        val userId = 1L
        val lectureId = 2L
        val registerRequest = LectureRegisterRequest(userId)
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
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(expectedResult.id))
            .andExpect(jsonPath("$.userId").value(expectedResult.userId))
            .andExpect(jsonPath("$.lectureId").value(expectedResult.lectureId))
            .andExpect(jsonPath("$.registrationTime").value(expectedResult.registrationTime))
        then(lectureRegistrationService).should().register(userId, lectureId)
        then(lectureRegistrationService).shouldHaveNoMoreInteractions()
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
        then(lectureRegistrationService).shouldHaveNoMoreInteractions()
    }
}