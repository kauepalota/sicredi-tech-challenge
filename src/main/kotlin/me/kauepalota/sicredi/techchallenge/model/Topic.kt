package me.kauepalota.sicredi.techchallenge.model

import jakarta.persistence.*

@Entity
@Table
data class Topic(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false)
    var name: String
)