package me.kauepalota.sicredi.techchallenge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.dto.SessionVoteResponseDto
import net.datafaker.Faker
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.*

@Import(TestTechChallengeApplication::class)
@Sql(scripts = ["/setup.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class VoteControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    companion object {
        private lateinit var faker: Faker

        @BeforeAll
        @JvmStatic
        fun setup() {
            faker = Faker(Locale("en-US"))
        }
    }

    @Test
    fun `should submit a vote when valid session is provided`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"${faker.cpf().valid(false)}\", \"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isCreated())
    }

    @Test
    fun `should return forbidden when attempting to submit a vote twice with the same CPF`() {
        val valid = faker.cpf().valid(false)

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"$valid\", \"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isCreated())

        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"$valid\", \"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isForbidden())
    }

    @Test
    fun `should return not found when submitting a vote with invalid session id`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 999)
                .contentType("application/json")
                .content("{\"cpf\": \"${faker.cpf().valid(false)}\", \"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isNotFound())
    }

    @Test
    fun `should return bad request when submitting a vote with invalid CPF`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"12345689\", \"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isBadRequest())
    }

    @Test
    fun `should return bad request when submitting a vote with invalid vote`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"${faker.cpf().valid(false)}\", \"choice\": null}")
                .accept("application/json")
        ).andExpect(status().isBadRequest())
    }

    @Test
    fun `should return ok when updating a created vote`() {
        val content = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"${faker.cpf().valid(false)}\", \"choice\": true}")
                .accept("application/json")
        )
            .andExpect(status().isCreated())
            .andReturn()
            .response.contentAsString

        val response = objectMapper.readValue(content, SessionVoteResponseDto::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/vote/{id}", response.id)
                .contentType("application/json")
                .content("{\"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isOk())
    }

    @Test
    fun `should return not found when updating a vote with invalid id`() {
        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/vote/{id}", 999)
                .contentType("application/json")
                .content("{\"choice\": true}")
                .accept("application/json")
        ).andExpect(status().isNotFound())
    }

    @Test
    fun `should return no content when deleting a created vote`() {
        val content = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/vote/{sessionId}", 1)
                .contentType("application/json")
                .content("{\"cpf\": \"${faker.cpf().valid(false)}\", \"choice\": true}")
                .accept("application/json")
        )
            .andExpect(status().isCreated())
            .andReturn()
            .response.contentAsString

        val response = objectMapper.readValue(content, SessionVoteResponseDto::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/vote/{id}", response.id)
                .accept("application/json")
        ).andExpect(status().isNoContent())
    }
}