package me.kauepalota.sicredi.techchallenge.service

import me.kauepalota.sicredi.techchallenge.dto.SessionCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionUpdateDto
import me.kauepalota.sicredi.techchallenge.exception.ResourceNotFoundException
import me.kauepalota.sicredi.techchallenge.model.Session
import me.kauepalota.sicredi.techchallenge.repository.SessionRepository
import org.modelmapper.ModelMapper
import org.springframework.stereotype.Service

@Service
class SessionService(val sessionRepository: SessionRepository, val topicService: TopicService) {
    fun getSession(id: Int): Session {
        val find = sessionRepository.findById(id)
        if (find.isEmpty) {
            throw ResourceNotFoundException("Session with id $id not found.")
        }

        return find.get()
    }

    fun getAllSessions(): MutableList<Session> {
        return sessionRepository.findAll()
    }

    fun createSession(dto: SessionCreateDto): Session {
        val topic = topicService.getTopic(dto.topicId!!)

        val session = Session(0, topic).apply {
            dto.duration?.let {
                this.duration = it
            }
        }

        return sessionRepository.save(session)
    }

    fun updateSession(id: Int, dto: SessionUpdateDto): Session {
        println(dto)

        return sessionRepository.findById(id).map { session ->
            dto.topicId?.let {
                session.topic = topicService.getTopic(it)
            }

            dto.duration?.let {
                session.duration = it
            }

            sessionRepository.save(session)
        }.orElseThrow {
            ResourceNotFoundException("Session with id $id not found.")
        }
    }

    fun deleteSession(id: Int) {
        sessionRepository.deleteById(id)
    }
}