package me.kauepalota.sicredi.techchallenge.service

import me.kauepalota.sicredi.techchallenge.dto.TopicRequestDto
import me.kauepalota.sicredi.techchallenge.exception.ResourceNotFoundException
import me.kauepalota.sicredi.techchallenge.model.Topic
import me.kauepalota.sicredi.techchallenge.repository.TopicRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class TopicService(val repository: TopicRepository) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getTopic(id: Int): Topic {
        val find = repository.findById(id)
        if (find.isEmpty) {
            throw ResourceNotFoundException("Topic with id $id not found.")
        }

        return find.get()
    }

    fun getAllTopics(): MutableList<Topic> {
        return repository.findAll()
    }

    fun createTopic(dto: TopicRequestDto): Topic {
        logger.info("Creating topic with name ${dto.name}.")

        return repository.save(Topic(0, dto.name!!))
    }

    fun updateTopic(id: Int, dto: TopicRequestDto): Topic {
        return repository.findById(id).map { topic ->
            topic.name = dto.name!!
            repository.save(topic)
        }.orElseThrow {
            ResourceNotFoundException("Topic with id $id not found.")
        }
    }

    fun deleteTopic(id: Int) {
        if (repository.findById(id).isEmpty) {
            throw ResourceNotFoundException("Topic with id $id not found.")
        }

        repository.deleteById(id)

        logger.info("Topic with id $id deleted.")
    }
}