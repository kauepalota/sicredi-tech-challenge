package me.kauepalota.sicredi.techchallenge.publisher

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class VoteResultListener {
    private val logger = LoggerFactory.getLogger(VoteResultListener::class.java)

    @KafkaListener(topics = ["vote-session-result"], groupId = "\${spring.kafka.consumer.group-id}")
    fun consume(record: ConsumerRecord<String, String>) {
        logger.info("Received vote result from session with id ${record.key()}: ${record.value()}")
    }
}