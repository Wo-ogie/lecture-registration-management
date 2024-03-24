package hhplus.clean_architecture.common.exception

import org.springframework.http.HttpStatus

abstract class ConflictException(
    override val errorMessage: String,
) : CustomException(HttpStatus.CONFLICT, errorMessage) {
}