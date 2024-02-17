package me.kauepalota.sicredi.techchallenge.model

import jakarta.persistence.*

@Entity
@Table
data class SessionVote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false, unique = true, length = 11)
    val cpf: String,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "session_id")
    val session: Session,

    @Column(nullable = false)
    val choice: Boolean
)