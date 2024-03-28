package hhplus.clean_architecture.lecture.exception

import hhplus.clean_architecture.common.exception.ConflictException

class LectureCapacityExceededException : ConflictException("최대 수강 인원이 이미 초과되었습니다.")