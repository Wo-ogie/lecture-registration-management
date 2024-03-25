package hhplus.clean_architecture.common.exception

import org.springframework.http.HttpStatus

abstract class LockedException(
    override val errorMessage: String,
) : CustomException(HttpStatus.LOCKED, errorMessage) {
}