package me.kauepalota.sicredi.techchallenge

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
@OpenAPIDefinition(
    info = Info(
        title = "Sicredi Tech Challenge",
        version = "1.0",
        description = "API for Sicredi Tech Challenge"
    )
)
class TechChallengeApplication

fun main(args: Array<String>) {
    runApplication<TechChallengeApplication>(*args)
}
