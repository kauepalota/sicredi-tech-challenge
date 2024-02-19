package me.kauepalota.sicredi.techchallenge.model.result

import io.swagger.v3.oas.annotations.media.Schema
import me.kauepalota.sicredi.techchallenge.model.Session

data class SessionResultData(
    @Schema(description = "The total votes", example = "10")
    val totalVotes: Int,

    @Schema(description = "The yes votes", example = "5")
    val yesVotes: Int,

    @Schema(description = "The no votes", example = "5")
    val noVotes: Int,

    @Schema(description = "The session result", example = "DRAW")
    val result: SessionResult,
) {
    companion object {
        fun from(session: Session): SessionResultData {
            val totalVotes = session.votes.size
            val yesVotes = session.votes.count { it.choice }
            val noVotes = session.votes.count { !it.choice }

            val result = when {
                yesVotes > noVotes -> SessionResult.YES
                yesVotes < noVotes -> SessionResult.NO
                else -> SessionResult.DRAW
            }

            return SessionResultData(totalVotes, yesVotes, noVotes, result)
        }
    }
}

enum class SessionResult {
    YES, NO, DRAW
}