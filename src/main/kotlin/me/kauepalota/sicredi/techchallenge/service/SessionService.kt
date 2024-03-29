package me.kauepalota.sicredi.techchallenge.service

import me.kauepalota.sicredi.techchallenge.dto.SessionCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionResponseDto
import me.kauepalota.sicredi.techchallenge.dto.SessionUpdateDto
import me.kauepalota.sicredi.techchallenge.exception.ResourceNotFoundException
import me.kauepalota.sicredi.techchallenge.model.Session
import me.kauepalota.sicredi.techchallenge.model.result.SessionResultData
import me.kauepalota.sicredi.techchallenge.repository.SessionRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class SessionService(val sessionRepository: SessionRepository, val topicService: TopicService) {
    private val logger = LoggerFactory.getLogger(javaClass)

    fun getSession(id: Int): SessionResponseDto {
        val find = sessionRepository.findById(id)
        if (find.isEmpty) {
            throw ResourceNotFoundException("Session with id $id not found.")
        }

        return parse(find.get())
    }

    fun getAllSessions(): List<SessionResponseDto> {
        return sessionRepository.findAll().map { parse(it) }
    }

    fun createSession(dto: SessionCreateDto): SessionResponseDto {
        val topic = topicService.getTopic(dto.topicId!!)

        val session = Session(0, topic).apply {
            dto.duration?.let {
                this.duration = it
            }
        }

        logger.info("Creating session with id ${session.id} and topic ${session.topic.id}.")

        return saveAndParse(session)
    }

    fun updateSession(id: Int, dto: SessionUpdateDto): SessionResponseDto {
        return sessionRepository.findById(id).map { session ->
            dto.topicId?.let {
                session.topic = topicService.getTopic(it)
            }

            saveAndParse(session)
        }.orElseThrow {
            ResourceNotFoundException("Session with id $id not found.")
        }
    }

    fun deleteSession(id: Int) {
        if (sessionRepository.findById(id).isEmpty) {
            throw ResourceNotFoundException("Session with id $id not found.")
        }

        logger.info("Deleting session with id $id.")

        sessionRepository.deleteById(id)
    }

    private fun saveAndParse(session: Session) = parse(sessionRepository.save(session))

    private fun parse(session: Session) = SessionResponseDto(
        session.id,
        session.topic.id,
        session.creationTimestamp,
        session.duration,
        SessionResultData.from(session),
        session.isOpen()
    )
}