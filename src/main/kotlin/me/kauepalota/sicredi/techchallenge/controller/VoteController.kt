package me.kauepalota.sicredi.techchallenge.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteResponseDto
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteUpdateDto
import me.kauepalota.sicredi.techchallenge.exception.ParamNotValidException
import me.kauepalota.sicredi.techchallenge.service.VoteService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/vote", produces = ["application/json"])
class VoteController(val service: VoteService) {
    @GetMapping("/{id}")
    @Operation(summary = "Get a vote", description = "Get a vote from the database.")
    @ApiResponse(responseCode = "200", description = "Vote found")
    @ApiResponse(responseCode = "404", description = "Vote not found")
    fun getOne(@PathVariable id: Int) = service.getVote(id)

    @GetMapping("/{sessionId}/{cpf}")
    @Operation(summary = "Get a vote by session id and cpf", description = "Get a vote from the database by session id and cpf.")
    @ApiResponse(responseCode = "200", description = "Vote found")
    @ApiResponse(responseCode = "404", description = "Vote not found")
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
    @Operation(summary = "Create a vote", description = "Create a vote in the database.")
    @ApiResponse(responseCode = "201", description = "Vote created")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(
        @PathVariable sessionId: Int,
        @Valid @RequestBody dto: SessionVoteCreateDto
    ) = service.createVote(sessionId, dto)

    @PutMapping("/{id}")
    @Operation(summary = "Update a vote", description = "Update a vote in the database.")
    @ApiResponse(responseCode = "200", description = "Vote updated")
    @ApiResponse(responseCode = "404", description = "Vote not found")
    fun update(
        @PathVariable id: Int,
        @Valid @RequestBody dto: SessionVoteUpdateDto
    ) = service.updateVote(id, dto)

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a vote", description = "Delete a vote from the database.")
    @ApiResponse(responseCode = "204", description = "Vote deleted")
    @ApiResponse(responseCode = "404", description = "Vote not found")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = service.deleteVote(id)
}