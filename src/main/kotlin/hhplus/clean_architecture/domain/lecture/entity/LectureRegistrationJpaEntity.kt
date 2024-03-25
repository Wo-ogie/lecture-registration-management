package hhplus.clean_architecture.domain.lecture.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Table(
    name = "lecture_registration",
    uniqueConstraints = [UniqueConstraint(
        name = "idx__lecture_registration__user_and_lecture",
        columnNames = ["user_id", "lecture_id"]
    )]
)
@Entity
class LectureRegistrationJpaEntity(
    val userId: Long,

    @JoinColumn(name = "lecture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val lecture: LectureJpaEntity,

    val registrationTime: LocalDateTime,

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_registration_id")
    val id: Long? = null,
) {
    companion object {
        fun from(lectureRegistration: LectureRegistration, lecture: LectureJpaEntity): LectureRegistrationJpaEntity {
            return LectureRegistrationJpaEntity(
                userId = lectureRegistration.userId,
                lecture = lecture,
                registrationTime = lectureRegistration.registrationTime
            )
        }
    }

    fun toDomain(): LectureRegistration {
        return LectureRegistration(
            id = id,
            userId = userId,
            lectureId = lecture.id!!,
            registrationTime = registrationTime
        )
    }
}