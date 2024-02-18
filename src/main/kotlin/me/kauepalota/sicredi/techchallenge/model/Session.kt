package me.kauepalota.sicredi.techchallenge.model

import jakarta.persistence.*
import org.hibernate.annotations.CreationTimestamp
import java.time.Instant
import java.util.concurrent.TimeUnit

@Entity
@Table
data class Session(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int,

    @OneToOne
    var topic: Topic,

    @CreationTimestamp
    val creationTimestamp: Instant = Instant.now(),

    @Column(nullable = false)
    var duration: Long = TimeUnit.MINUTES.toMillis(1),

    @OneToMany(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE], mappedBy = "session")
    var votes: MutableList<SessionVote> = mutableListOf()
) {
    fun isOpen() = creationTimestamp.plusMillis(duration).isAfter(Instant.now())
}