package hhplus.clean_architecture.integration.lecture.domain

import hhplus.clean_architecture.lecture.domain.LectureRegistrationService
import hhplus.clean_architecture.lecture.domain.LectureService
import hhplus.clean_architecture.lecture.exception.LectureCapacityExceededException
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture

@ActiveProfiles("test")
@SpringBootTest
class LectureServiceConcurrencyTest @Autowired constructor(
    private val lectureService: LectureService,
    private val lectureRegistrationService: LectureRegistrationService,
) {

    @Test
    fun `동시에 여러 특강 신청 요청이 들어와도, 순차적으로 처리하여, 총 수강 인원이 최대 수강 인원을 넘지 않는다`() {
        // given
        val maxParticipants = 3
        val lecture = lectureService.create(
            title = "test",
            lectureTime = LocalDateTime.of(2024, 4, 1, 15, 0),
            registrationStartTime = LocalDateTime.of(2024, 3, 1, 12, 0),
            maxParticipants = maxParticipants
        )

        // when & then
        CompletableFuture.allOf(
            CompletableFuture.runAsync { lectureRegistrationService.register(userId = 1L, lectureId = lecture.id!!) },
            CompletableFuture.runAsync { lectureRegistrationService.register(userId = 2L, lectureId = lecture.id!!) },
            CompletableFuture.runAsync { lectureRegistrationService.register(userId = 3L, lectureId = lecture.id!!) },
            CompletableFuture.runAsync { lectureRegistrationService.register(userId = 4L, lectureId = lecture.id!!) },
            CompletableFuture.runAsync { lectureRegistrationService.register(userId = 5L, lectureId = lecture.id!!) }
        ).exceptionally { ex ->
            val exception = checkNotNull(ex.cause as? LectureCapacityExceededException)
            assertThat(exception).isInstanceOf(LectureCapacityExceededException::class.java)
            return@exceptionally null
        }.join()

        val count = lectureRegistrationService.getCountByLecture(lectureId = lecture.id!!)
        assertThat(count).isEqualTo(lecture.maxParticipants.toLong())
    }
}