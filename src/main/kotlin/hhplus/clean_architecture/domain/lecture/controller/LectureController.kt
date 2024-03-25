package hhplus.clean_architecture.domain.lecture.controller

import hhplus.clean_architecture.domain.lecture.dto.request.LectureRegisterRequest
import hhplus.clean_architecture.domain.lecture.dto.response.CheckLectureRegistrationResponse
import hhplus.clean_architecture.domain.lecture.dto.response.LectureRegisterResponse
import hhplus.clean_architecture.domain.lecture.service.LectureRegistrationService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

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

    @PostMapping("/{lectureId}/register")
    fun register(
        @PathVariable lectureId: Long,
        @RequestBody lectureRegisterRequest: LectureRegisterRequest,
    ): ResponseEntity<LectureRegisterResponse> {
        val registration = lectureRegistrationService.register(
            userId = lectureRegisterRequest.userId,
            lectureId = lectureId
        )
        return ResponseEntity
            .created(URI.create("/api/lectures/${registration.id}/check-registrations"))
            .body(LectureRegisterResponse.from(registration))
    }
}