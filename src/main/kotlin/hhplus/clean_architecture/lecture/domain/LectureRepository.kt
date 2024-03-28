package hhplus.clean_architecture.lecture.domain

import hhplus.clean_architecture.lecture.domain.Lecture
import hhplus.clean_architecture.lecture.exception.LectureNotFoundException

interface LectureRepository {

    @Throws(LectureNotFoundException::class)
    fun getById(lectureId: Long): Lecture

    @Throws(LectureNotFoundException::class)
    fun getByIdWithLock(lectureId: Long): Lecture

    fun findAll(): List<Lecture>

    fun save(lecture: Lecture): Lecture
}