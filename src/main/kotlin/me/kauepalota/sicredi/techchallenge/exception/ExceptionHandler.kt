package me.kauepalota.sicredi.techchallenge.exception

import org.apache.coyote.BadRequestException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.server.ResponseStatusException

@RestControllerAdvice
class ExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleResourceNotFoundException(e: ResourceNotFoundException): ErrorResponse {
        return ErrorResponse(e.message ?: "", HttpStatus.NOT_FOUND.value())
    }

    @ExceptionHandler(BadRequestException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleBadRequestException(e: BadRequestException): ErrorResponse {
        return ErrorResponse(e.message ?: "", HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValidException(e: MethodArgumentNotValidException): List<ErrorResponse> {
        return ErrorResponse.from(e)
    }

    @ExceptionHandler(ResponseStatusException::class)
    fun handleResponseStatusException(e: ResponseStatusException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(e.statusCode)
            .body(ErrorResponse(e.reason ?: "", e.statusCode.value()))
    }

    @ExceptionHandler(ParamNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleParamNotValidException(e: ParamNotValidException): ErrorResponse {
        return ErrorResponse(e.message ?: "", HttpStatus.BAD_REQUEST.value())
    }

    @ExceptionHandler(VoteNotAllowedException::class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun handleVoteNotAllowedException(e: VoteNotAllowedException): ErrorResponse {
        return ErrorResponse(e.message ?: "", HttpStatus.FORBIDDEN.value())
    }
}