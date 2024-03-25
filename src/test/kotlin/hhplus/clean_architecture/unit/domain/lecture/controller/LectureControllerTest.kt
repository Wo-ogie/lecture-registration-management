package hhplus.clean_architecture.unit.domain.lecture.controller

import hhplus.clean_architecture.domain.lecture.controller.LectureController
import hhplus.clean_architecture.domain.lecture.service.LectureRegistrationService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.then
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@DisplayName("Controller - Lecture")
@WebMvcTest(controllers = [LectureController::class])
class LectureControllerTest(
    @Autowired private val mvc: MockMvc,
) {
    @MockBean
    private lateinit var lectureRegistrationService: LectureRegistrationService

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