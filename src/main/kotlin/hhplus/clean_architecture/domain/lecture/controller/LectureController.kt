package hhplus.clean_architecture.domain.lecture.controller

import hhplus.clean_architecture.domain.lecture.dto.response.CheckLectureRegistrationResponse
import hhplus.clean_architecture.domain.lecture.service.LectureRegistrationService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/api/lectures")
@RestController
class LectureController(
    private val lectureRegistrationService: LectureRegistrationService,
) {

    @GetMapping("/check-registrations")
    fun checkRegistration(
        @RequestParam userId: Long,
        @RequestParam lectureId: Long,
    ): CheckLectureRegistrationResponse {
        TODO("Not yet implemented")
    }
}