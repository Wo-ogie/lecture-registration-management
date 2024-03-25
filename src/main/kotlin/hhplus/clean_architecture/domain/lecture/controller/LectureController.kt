package hhplus.clean_architecture.domain.lecture.controller

import hhplus.clean_architecture.domain.lecture.dto.response.CheckLectureRegistrationResponse
import hhplus.clean_architecture.domain.lecture.service.LectureRegistrationService
import org.springframework.web.bind.annotation.*

@RequestMapping("/api/lectures")
@RestController
class LectureController(
    private val lectureRegistrationService: LectureRegistrationService,
) {

    @GetMapping("/{lectureId}/check-registrations")
    fun checkRegistration(
        @PathVariable lectureId: Long,
        @RequestParam userId: Long,
    ): CheckLectureRegistrationResponse {
        val isRegistered = lectureRegistrationService.existsByUserAndLecture(userId, lectureId)
        return CheckLectureRegistrationResponse(isRegistered)
    }
}