package me.kauepalota.sicredi.techchallenge

import org.springframework.boot.fromApplication
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.boot.with
import org.springframework.context.annotation.Bean
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.ExecutionPhase
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@TestConfiguration(proxyBeanMethods = false)
@Sql(scripts = ["/setup.sql"], executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
class TestTechChallengeApplication {
    @Bean
    @ServiceConnection
    fun postgresContainer(): PostgreSQLContainer<*> {
        return PostgreSQLContainer(DockerImageName.parse("postgres:latest"))
    }

    @Bean
    @ServiceConnection
    fun rabbitMqContainer() : KafkaContainer {
        return KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"))
    }
}

fun main(args: Array<String>) {
    fromApplication<TechChallengeApplication>().with(TestTechChallengeApplication::class).run(*args)
}
