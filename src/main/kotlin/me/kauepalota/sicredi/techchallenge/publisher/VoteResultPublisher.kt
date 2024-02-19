package me.kauepalota.sicredi.techchallenge.publisher

import com.fasterxml.jackson.databind.ObjectMapper
import me.kauepalota.sicredi.techchallenge.model.result.SessionResultData
import me.kauepalota.sicredi.techchallenge.repository.SessionRepository
import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class VoteResultPublisher(
    private val template: KafkaTemplate<String, String>,
    private val mapper: ObjectMapper,
    private val sessionRepository: SessionRepository
) {
    private val logger = LoggerFactory.getLogger(VoteResultPublisher::class.java)

    @Scheduled(fixedRate = 5000)
    fun publishAllClosedSessions() {
        val now = Instant.now()

        sessionRepository.findAll().filter {
            it.isOpen() && it.creationTimestamp.plusMillis(it.duration).isBefore(now)
        }.forEach {
            publish(it.id, SessionResultData.from(it))

            it.closed = true

            sessionRepository.save(it)
        }
    }

    fun publish(id: Int, data: SessionResultData) {
        template.send("vote-session-result", id.toString(), data.toJson())

        logger.info("Published vote result from session with id $id: ${data.toJson()}")
    }

    private fun SessionResultData.toJson(): String {
        return mapper.writeValueAsString(this)
    }
}