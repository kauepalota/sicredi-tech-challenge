package me.kauepalota.sicredi.techchallenge.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class SessionVoteCreateDto(
    @field:Size(min = 11, max = 11, message = "The CPF must have 11 characters")
    @field:NotNull
    val cpf: String?,

    @field:NotNull
    val choice: Boolean?
)

data class SessionVoteUpdateDto(
    @field:NotNull
    val choice: Boolean?
)

data class SessionVoteResponseDto(
    val id: Int,

    val sessionId: Int,

    val cpf: String,

    val choice: Boolean
)

