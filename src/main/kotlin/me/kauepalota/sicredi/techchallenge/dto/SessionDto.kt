package me.kauepalota.sicredi.techchallenge.dto

import jakarta.validation.constraints.NotNull

data class SessionCreateDto(
    @field:NotNull(message = "The topicId must not be null")
    val topicId: Int?,

    val duration: Long?
)

data class SessionUpdateDto(
    val topicId: Int?,

    val duration: Long?
)