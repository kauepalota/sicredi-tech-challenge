package me.kauepalota.sicredi.techchallenge.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import me.kauepalota.sicredi.techchallenge.dto.SessionCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionUpdateDto
import me.kauepalota.sicredi.techchallenge.service.SessionService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Session", description = "Session API")
@RequestMapping("api/v1/session", produces = ["application/json"])
class SessionController(val service : SessionService) {
    @GetMapping
    @Operation(summary = "Get all sessions", description = "Get all sessions from the database.")
    @ApiResponse(responseCode = "200", description = "List of sessions")
    fun getAll() = service.getAllSessions()

    @GetMapping("/{id}")
    @Operation(summary = "Get a session", description = "Get a session from the database.")
    @ApiResponse(responseCode = "200", description = "Session found")
    @ApiResponse(responseCode = "404", description = "Session not found")
    fun getOne(@PathVariable id: Int) = service.getSession(id)

    @PostMapping
    @Operation(summary = "Create a session", description = "Create a session in the database.")
    @ApiResponse(responseCode = "201", description = "Session created")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid dto: SessionCreateDto) = service.createSession(dto)

    @PutMapping("/{id}")
    @Operation(summary = "Update a session", description = "Update a session in the database.")
    @ApiResponse(responseCode = "200", description = "Session updated")
    @ApiResponse(responseCode = "404", description = "Session not found")
    fun update(@PathVariable id: Int, @Valid @RequestBody dto: SessionUpdateDto) = service.updateSession(id, dto)

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a session", description = "Delete a session from the database.")
    @ApiResponse(responseCode = "204", description = "Session deleted")
    @ApiResponse(responseCode = "404", description = "Session not found")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = service.deleteSession(id)
}