package hhplus.clean_architecture.lecture.exception

import hhplus.clean_architecture.common.exception.NotFoundException

class LectureNotFoundException : NotFoundException("특강 정보를 찾을 수 없습니다.") {
}