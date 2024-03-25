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
    fun toDomain(): Lecture {
        return Lecture(
            id = id,
            title = title,
            maxParticipants = maxParticipants,
            registrationStartTime = registrationStartTime
        )
    }
}
