package me.kauepalota.sicredi.techchallenge.service

import me.kauepalota.sicredi.techchallenge.dto.SessionVoteCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteResponseDto
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteUpdateDto
import me.kauepalota.sicredi.techchallenge.exception.ResourceNotFoundException
import me.kauepalota.sicredi.techchallenge.exception.VoteNotAllowedException
import me.kauepalota.sicredi.techchallenge.model.SessionVote
import me.kauepalota.sicredi.techchallenge.repository.SessionRepository
import me.kauepalota.sicredi.techchallenge.repository.SessionVoteRepository
import org.springframework.stereotype.Service

@Service
class VoteService(val sessionRepository: SessionRepository, val voteRepository: SessionVoteRepository) {
    fun getVote(id: Int, cpf: String): SessionVoteResponseDto {
        return parse(voteRepository.findBySessionIdAndCpf(id, cpf).orElseThrow {
            throw ResourceNotFoundException("Vote from session with id $id and cpf $cpf not found.")
        })
    }

    fun getVote(id: Int): SessionVoteResponseDto {
        return parse(voteRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Vote with id $id not found.")
        })
    }

    fun createVote(sessionId: Int, dto: SessionVoteCreateDto): SessionVoteResponseDto {
        val session = sessionRepository.findById(sessionId).orElseThrow {
            throw ResourceNotFoundException("Session with id $sessionId not found.")
        }

        if (session.votes.any { it.cpf == dto.cpf }) {
            throw VoteNotAllowedException("Vote already registered for this CPF.")
        }

        if (!session.isOpen()) {
            throw VoteNotAllowedException("Vote session is already closed.")
        }

        val vote = SessionVote(0, dto.cpf!!, session, dto.choice!!)

        return parse(voteRepository.save(vote))
    }

    fun updateVote(id: Int, dto: SessionVoteUpdateDto): SessionVoteResponseDto {
        val vote = voteRepository.findById(id).orElseThrow {
            throw ResourceNotFoundException("Vote with id $id not found.")
        }

        dto.choice?.let {
            vote.choice = it
        }

        return parse(voteRepository.save(vote))
    }

    private fun parse(vote: SessionVote) = SessionVoteResponseDto(
        vote.id,
        vote.session.id,
        vote.cpf,
        vote.choice
    )
}
