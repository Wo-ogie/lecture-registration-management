package hhplus.clean_architecture.domain.lecture.repository

import hhplus.clean_architecture.domain.lecture.entity.Lecture
import hhplus.clean_architecture.domain.lecture.exception.LectureNotFoundException

interface LectureRepository {

    @Throws(LectureNotFoundException::class)
    fun getById(lectureId: Long): Lecture
    
    fun save(lecture: Lecture): Lecture
}