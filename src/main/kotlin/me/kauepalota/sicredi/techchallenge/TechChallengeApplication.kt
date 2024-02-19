package me.kauepalota.sicredi.techchallenge

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
class TechChallengeApplication

fun main(args: Array<String>) {
    runApplication<TechChallengeApplication>(*args)
}
