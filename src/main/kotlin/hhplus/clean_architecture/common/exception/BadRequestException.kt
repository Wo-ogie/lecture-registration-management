package hhplus.clean_architecture.common.exception

import org.springframework.http.HttpStatus

abstract class BadRequestException(
    override val errorMessage: String,
) : CustomException(HttpStatus.BAD_REQUEST, errorMessage) {
}