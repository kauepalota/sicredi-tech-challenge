package me.kauepalota.sicredi.techchallenge.dto

import jakarta.validation.constraints.NotEmpty

data class TopicRequestDto(
    @field:NotEmpty(message = "The name must not be empty")
    val name: String?
)
