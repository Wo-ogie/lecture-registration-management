package hhplus.clean_architecture.domain.lecture.entity

import jakarta.persistence.*

@Table(name = "lecture")
@Entity
class LectureJpaEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    val id: Long,

    val title: String,

    val maxParticipants: Int,
)
