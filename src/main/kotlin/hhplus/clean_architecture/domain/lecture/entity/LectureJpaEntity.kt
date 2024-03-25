package hhplus.clean_architecture.domain.lecture.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(name = "lecture")
@Entity
class LectureJpaEntity(
    val title: String,

    val registrationStartTime: LocalDateTime,

    val maxParticipants: Int = 30,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    val id: Long? = null,
) {
    companion object {
        fun from(lecture: Lecture): LectureJpaEntity {
            return LectureJpaEntity(
                title = lecture.title,
                registrationStartTime = lecture.registrationStartTime,
                maxParticipants = lecture.maxParticipants
            )
        }
    }

    fun toDomain(): Lecture {
        return Lecture(
            id = id,
            title = title,
            maxParticipants = maxParticipants,
            registrationStartTime = registrationStartTime
        )
    }
}
