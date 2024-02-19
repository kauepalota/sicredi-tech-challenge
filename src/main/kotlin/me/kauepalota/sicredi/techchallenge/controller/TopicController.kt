package me.kauepalota.sicredi.techchallenge.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import me.kauepalota.sicredi.techchallenge.dto.TopicRequestDto
import me.kauepalota.sicredi.techchallenge.service.TopicService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Topic", description = "Topic API")
@RequestMapping("api/v1/topic", produces = ["application/json"])
class TopicController(val service : TopicService) {
    @GetMapping
    @Operation(summary = "Get all topics", description = "Get all topics summarized")
    @ApiResponse(responseCode = "200", description = "List of topics")
    fun getAll() = service.getAllTopics()

    @GetMapping("/{id}")
    @Operation(summary = "Get a topic", description = "Retrieve the result data of a topic.")
    @ApiResponse(responseCode = "200", description = "Topic found")
    @ApiResponse(responseCode = "404", description = "Topic not found")
    fun getOne(@PathVariable id: Int) = service.getTopic(id)

    @PostMapping
    @Operation(summary = "Create a topic", description = "Submission of the necessary data to create a topic.")
    @ApiResponse(responseCode = "201", description = "Topic created")
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid dto: TopicRequestDto) = service.createTopic(dto)

    @PutMapping("/{id}")
    @Operation(summary = "Update a topic", description = "Submission of the necessary data to update a session.")
    @ApiResponse(responseCode = "200", description = "Topic updated")
    @ApiResponse(responseCode = "404", description = "Topic not found")
    fun update(@PathVariable id: Int, @Valid @RequestBody dto: TopicRequestDto) = service.updateTopic(id, dto)

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a topic", description = "Delete a topic by its id.")
    @ApiResponse(responseCode = "204", description = "Topic deleted")
    @ApiResponse(responseCode = "404", description = "Topic not found")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) = service.deleteTopic(id)
}