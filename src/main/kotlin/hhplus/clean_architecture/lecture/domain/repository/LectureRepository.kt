package hhplus.clean_architecture.lecture.domain.repository

import hhplus.clean_architecture.lecture.domain.entity.Lecture
import hhplus.clean_architecture.lecture.exception.LectureNotFoundException

interface LectureRepository {

    @Throws(LectureNotFoundException::class)
    fun getById(lectureId: Long): Lecture

    @Throws(LectureNotFoundException::class)
    fun getByIdWithLock(lectureId: Long): Lecture

    fun save(lecture: Lecture): Lecture
}