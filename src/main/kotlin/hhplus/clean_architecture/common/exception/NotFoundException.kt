package hhplus.clean_architecture.common.exception

import org.springframework.http.HttpStatus

abstract class NotFoundException(
    override val errorMessage: String,
) : CustomException(HttpStatus.NOT_FOUND, errorMessage) {
}