package me.kauepalota.sicredi.techchallenge.exception

import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException

data class ErrorResponse(
    @Schema(description = "The error message", example = "Generic error message")
    val message: String,

    @Schema(description = "The error status", example = "999")
    val status: Int
) {
    constructor(error: FieldError) : this(error.defaultMessage ?: "", 400)

    companion object {
        fun from(exception: MethodArgumentNotValidException): List<ErrorResponse> {
            val errors = exception.bindingResult.fieldErrors

            return errors.map { ErrorResponse(it) }
        }
    }
}