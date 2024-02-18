package me.kauepalota.sicredi.techchallenge.controller

import jakarta.validation.Valid
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteResponseDto
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteUpdateDto
import me.kauepalota.sicredi.techchallenge.exception.ParamNotValidException
import me.kauepalota.sicredi.techchallenge.service.VoteService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/vote", produces = ["application/json"])
class VoteController(val service: VoteService) {
    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int) = service.getVote(id)

    @GetMapping("/{sessionId}/{cpf}")
    fun getOneBySessionIdAndCpf(
        @PathVariable sessionId: Int,
        @PathVariable cpf: String
    ): SessionVoteResponseDto {
        if (cpf.length != 11) {
            throw ParamNotValidException("The CPF must have 11 characters")
        }

        return service.getVote(sessionId, cpf)
    }

    @PostMapping("/{sessionId}")
    fun create(
        @PathVariable sessionId: Int,
        @Valid @RequestBody dto: SessionVoteCreateDto
    ) = service.createVote(sessionId, dto)

    @PutMapping("/{id}")
    fun update(
        @PathVariable id: Int,
        @Valid @RequestBody dto: SessionVoteUpdateDto
    ) = service.updateVote(id, dto)
}