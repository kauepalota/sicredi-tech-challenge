package me.kauepalota.sicredi.techchallenge.controller

import jakarta.validation.Valid
import me.kauepalota.sicredi.techchallenge.dto.SessionCreateDto
import me.kauepalota.sicredi.techchallenge.dto.SessionUpdateDto
import me.kauepalota.sicredi.techchallenge.service.SessionService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/session", produces = ["application/json"])
class SessionController(val service : SessionService) {
    @GetMapping
    fun getAll() = service.getAllSessions()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int) = service.getSession(id)

    @PostMapping
    fun create(@RequestBody @Valid dto: SessionCreateDto) = service.createSession(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody dto: SessionUpdateDto) = service.updateSession(id, dto)
}