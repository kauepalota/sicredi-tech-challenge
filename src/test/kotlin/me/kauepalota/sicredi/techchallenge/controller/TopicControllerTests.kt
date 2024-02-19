package me.kauepalota.sicredi.techchallenge.controller

import com.fasterxml.jackson.databind.ObjectMapper
import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.model.Topic
import net.datafaker.Faker
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
class TopicControllerTests {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    private val faker = Faker()

    @Test
    fun `should create a topic when valid topic is provided`() {
        mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/topic")
                .contentType("application/json")
                .content("{\"name\": \"${faker.lorem().word()}\"}")
                .accept("application/json")
        ).andExpect(status().isCreated)
    }

    @Test
    fun `should return ok when updating a created topic`() {
        val contentAsString = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/topic")
                .contentType("application/json")
                .content("{\"name\": \"${faker.lorem().word()}\"}")
                .accept("application/json")
        )
            .andExpect(status().isCreated)
            .andReturn()

        val topicId = objectMapper.readTree(contentAsString.response.contentAsString).get("id").asLong()

        mockMvc.perform(
            MockMvcRequestBuilders.put("/api/v1/topic/{id}", topicId)
                .contentType("application/json")
                .content("{\"name\": \"${faker.lorem().word()}\"}")
                .accept("application/json")
        ).andExpect(status().isOk)
    }

    @Test
    fun `should return no content when deleting a created topic`() {
        val contentAsString = mockMvc.perform(
            MockMvcRequestBuilders.post("/api/v1/topic")
                .contentType("application/json")
                .content("{\"name\": \"${faker.lorem().word()}\"}")
                .accept("application/json")
        )
            .andExpect(status().isCreated)
            .andReturn()

        val topic = objectMapper.readValue(contentAsString.response.contentAsString, Topic::class.java)

        mockMvc.perform(
            MockMvcRequestBuilders.delete("/api/v1/topic/{id}", topic.id)
                .accept("application/json")
        ).andExpect(status().isNoContent)
    }

    @Test
    fun `should return ok when getting all topics`() {
        mockMvc.perform(
            MockMvcRequestBuilders.get("/api/v1/topic")
                .accept("application/json")
        ).andExpect(status().isOk)
    }
}