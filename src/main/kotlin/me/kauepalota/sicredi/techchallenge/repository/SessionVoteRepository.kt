package me.kauepalota.sicredi.techchallenge.repository

import me.kauepalota.sicredi.techchallenge.model.SessionVote
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface SessionVoteRepository : JpaRepository<SessionVote, Int> {
    fun findBySessionIdAndCpf(sessionId: Int, cpf : String): Optional<SessionVote>
}
