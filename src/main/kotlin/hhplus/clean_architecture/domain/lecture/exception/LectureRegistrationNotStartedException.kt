package hhplus.clean_architecture.domain.lecture.exception

import hhplus.clean_architecture.common.exception.LockedException

class LectureRegistrationNotStartedException : LockedException("신청 가능 시간이 아닙니다. 나중에 다시 시도해주세요.")