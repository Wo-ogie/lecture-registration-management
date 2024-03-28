package hhplus.clean_architecture.lecture.exception

import hhplus.clean_architecture.common.exception.ConflictException

class LectureAlreadyRegisteredException : ConflictException("이미 수강하고 있는 강의입니다.")
