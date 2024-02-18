package me.kauepalota.sicredi.techchallenge.exception

data class ErrorResponse(
    val message: String,
    val status: Int
)

data class ErrorsResponse(
    val errors: List<String>,
    val status: Int
)