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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_registration_id")
    val id: Long? = null,

    val userId: Long,

    @JoinColumn(name = "lecture_id")
    @ManyToOne(fetch = FetchType.LAZY)
    val lecture: LectureJpaEntity,

    val registrationTime: LocalDateTime,
)