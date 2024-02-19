package me.kauepalota.sicredi.techchallenge.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotEmpty

data class TopicRequestDto(
    @field:NotEmpty(message = "The topic name must not be empty")
    @Schema(description = "The topic name", example = "The topic name", required = true)
    val name: String
)
