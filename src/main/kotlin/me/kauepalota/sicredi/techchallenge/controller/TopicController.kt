package me.kauepalota.sicredi.techchallenge.controller

import jakarta.validation.Valid
import me.kauepalota.sicredi.techchallenge.dto.TopicRequestDto
import me.kauepalota.sicredi.techchallenge.service.TopicService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("api/v1/topic", produces = ["application/json"])
class TopicController(val service : TopicService) {
    @GetMapping
    fun getAll() = service.getAllTopics()

    @GetMapping("/{id}")
    fun getOne(@PathVariable id: Int) = service.getTopic(id)

    @PostMapping
    fun create(@RequestBody @Valid dto: TopicRequestDto) = service.createTopic(dto)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @Valid @RequestBody dto: TopicRequestDto) = service.updateTopic(id, dto)
}