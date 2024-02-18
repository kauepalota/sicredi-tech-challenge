package me.kauepalota.sicredi.techchallenge.model.result

data class SessionResultData(
    val totalVotes: Int,

    val yesVotes: Int,
    val noVotes: Int,

    val result: SessionResult,
)

enum class SessionResult {
    YES, NO, DRAW
}