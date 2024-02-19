package me.kauepalota.sicredi.techchallenge.model

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.*

@Entity
@Table
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "The topic id", example = "1")
    val id: Int,

    @Column(nullable = false)
    @Schema(description = "The topic name", example = "Everyone will receive a bonus this year!")
    var name: String
)