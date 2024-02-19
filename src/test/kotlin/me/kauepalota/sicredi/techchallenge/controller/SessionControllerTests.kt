package me.kauepalota.sicredi.techchallenge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.dto.SessionResponseDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@Import(TestTechChallengeApplication::class)
@Sql(scripts = ["/setup.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class SessionControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun `should return ok when listing all sessions`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/session")
                .accept("application/json")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should create a session when valid topic is provided`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/session")
                .contentType("application/json")
                .content("{\"topic_id\": 1}")
                .accept("application/json")
        ).andExpect(status().isCreated)
    }

    @Test
    fun `should return not found when creating a session with invalid topic id`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/session")
                .contentType("application/json")
                .content("{\"topic_id\": 999}")
                .accept("application/json")
        ).andExpect(status().isNotFound)
    }

    @Test
    fun `should return ok when updating a created session`() {
        val contentAsString = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/session")
                .contentType("application/json")
                .content("{\"topic_id\": 1}")
                .accept("application/json")
        )
            .andExpect(status().isCreated)
            .andReturn()
            .response.contentAsString

        val response = objectMapper.readValue(contentAsString, SessionResponseDto::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/session/${response.id}")
                .contentType("application/json")
                .content("{\"duration\": 30000}")
                .accept("application/json")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should return ok when searching for a created session`() {
        val contentAsString = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/session")
                .contentType("application/json")
                .content("{\"topic_id\": 1}")
                .accept("application/json")
        )
            .andExpect(status().isCreated)
            .andReturn()
            .response.contentAsString

        val response = objectMapper.readValue(contentAsString, SessionResponseDto::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/session/${response.id}")
                .accept("application/json")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should return no content when deleting a created session`() {
        val contentAsString = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/session")
                .contentType("application/json")
                .content("{\"topic_id\": 1}")
                .accept("application/json")
        )
            .andExpect(status().isCreated)
            .andReturn()
            .response.contentAsString

        val response = objectMapper.readValue(contentAsString, SessionResponseDto::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/session/{id}", response.id)
        ).andExpect(status().isNoContent)
    }
}