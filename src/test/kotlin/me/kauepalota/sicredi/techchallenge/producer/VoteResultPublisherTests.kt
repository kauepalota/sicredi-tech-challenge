package me.kauepalota.sicredi.techchallenge.producer

import com.fasterxml.jackson.databind.ObjectMapper
import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.model.result.SessionResult
import me.kauepalota.sicredi.techchallenge.model.result.SessionResultData
import me.kauepalota.sicredi.techchallenge.publisher.VoteResultPublisher
import me.kauepalota.sicredi.techchallenge.repository.SessionRepository
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.kafka.core.KafkaTemplate

@Import(TestTechChallengeApplication::class)
@AutoConfigureMockMvc
@SpringBootTest
class VoteResultPublisherTests {
    private val mapper = ObjectMapper()

    @Test
    fun `should publish a result data`() {
        val template: KafkaTemplate<String, String> = Mockito.mock(KafkaTemplate::class.java) as KafkaTemplate<String, String>
        val repository = Mockito.mock(SessionRepository::class.java)

        val voteResultPublisher = VoteResultPublisher(template, ObjectMapper(), repository)

        val data = SessionResultData(1, 1, 0, SessionResult.YES)

        voteResultPublisher.publish(1, data)

        Mockito.verify(template).send("vote-session-result", "1", mapper.writeValueAsString(data))
    }
}