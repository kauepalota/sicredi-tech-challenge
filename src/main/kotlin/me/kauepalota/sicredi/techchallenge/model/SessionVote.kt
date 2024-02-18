package me.kauepalota.sicredi.techchallenge.model

import jakarta.persistence.*

@Entity
@Table(
    uniqueConstraints = [
        UniqueConstraint(columnNames = ["session_id", "cpf"])
    ]
)
data class SessionVote(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @Column(nullable = false, length = 11)
    val cpf: String,

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    @JoinColumn(name = "session_id")
    val session: Session,

    @Column(nullable = false)
    var choice: Boolean
)