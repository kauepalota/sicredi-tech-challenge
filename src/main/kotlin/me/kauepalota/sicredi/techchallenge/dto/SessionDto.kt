package me.kauepalota.sicredi.techchallenge.dto

import jakarta.validation.constraints.NotNull
import me.kauepalota.sicredi.techchallenge.model.result.SessionResultData
import java.time.Instant

data class SessionCreateDto(
    @field:NotNull(message = "The topicId must not be null")
    val topicId: Int?,

    val duration: Long?
)

data class SessionUpdateDto(
    val topicId: Int?,

    val duration: Long?
)

data class SessionResponseDto(
    val id: Int,

    val topicId: Int,

    val creationTimestamp: Instant,

    val duration: Long,

    val result : SessionResultData,

    val isOpen : Boolean
)