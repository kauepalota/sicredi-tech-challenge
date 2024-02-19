package me.kauepalota.sicredi.techchallenge.dto

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SessionVoteCreateDto(
    @field:Size(min = 11, max = 11, message = "The CPF must have 11 characters")
    @field:NotNull
    @Schema(description = "The affiliated CPF", example = "12345678901", required = true)
    val cpf: String?,

    @field:NotNull(message = "The vote choice must be true or false")
    @Schema(description = "The vote choice", example = "true", required = true)
    val choice: Boolean?
)

data class SessionVoteUpdateDto(
    @field:NotNull(message = "The vote choice must be true or false")
    @Schema(description = "The vote choice", example = "true", required = true)
    val choice: Boolean?
)

data class SessionVoteResponseDto(
    @Schema(description = "The vote identifier", example = "1")
    val id: Int,

    @Schema(description = "The session identifier", example = "1")
    @JsonProperty("session_id")
    val sessionId: Int,

    @Schema(description = "The affiliated CPF", example = "12345678901")
    val cpf: String,

    @Schema(description = "The vote choice", example = "true")
    val choice: Boolean
)

