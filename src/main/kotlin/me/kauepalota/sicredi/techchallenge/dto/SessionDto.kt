package me.kauepalota.sicredi.techchallenge.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import me.kauepalota.sicredi.techchallenge.model.result.SessionResultData
import java.time.Instant

data class SessionCreateDto(
    @Schema(description = "The topic id", example = "1", required = true)
    @field:NotNull(message = "The topicId must not be null")
    val topicId: Int?,

    @Schema(description = "The session duration", example = "60000")
    val duration: Long?
)

data class SessionUpdateDto(
    @Schema(description = "The topic id", example = "1")
    val topicId: Int?,

    @Schema(description = "The session duration", example = "60000")
    val duration: Long?
)

data class SessionResponseDto(
    @Schema(description = "The session id", example = "1")
    val id: Int,

    @Schema(description = "The topic id", example = "1")
    val topicId: Int,

    @Schema(description = "The session creation timestamp", example = "2021-10-10T10:00:00Z")
    val creationTimestamp: Instant,

    @Schema(description = "The session duration", example = "60000")
    val duration: Long,

    @Schema(description = "The session result data")
    val result : SessionResultData,

    @Schema(description = "The session open status", example = "true")
    val isOpen : Boolean
)