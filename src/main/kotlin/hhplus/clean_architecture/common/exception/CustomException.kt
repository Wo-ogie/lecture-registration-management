package hhplus.clean_architecture.common.exception

import org.springframework.http.HttpStatus

abstract class CustomException(
    open val httpStatus: HttpStatus,
    open val errorMessage: String,
) : RuntimeException() {
}