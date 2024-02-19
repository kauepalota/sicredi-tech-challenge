package me.kauepalota.sicredi.techchallenge.repository

import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.model.Session
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import java.time.Instant

@Import(TestTechChallengeApplication::class)
@Sql(scripts = ["/setup.sql"], executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@SpringBootTest
class SessionRepositoryTests {
    @Autowired
    private lateinit var sessionRepository: SessionRepository

    @Autowired
    private lateinit var topicRepository: TopicRepository

    @Test
    fun `should create a session`() {
        val topic = topicRepository.findById(1).orElseThrow()

        val session = sessionRepository.save(Session(0, topic, Instant.now()))

        assertNotEquals(0, session.id)
        assertEquals(1, session.topic.id)
    }

    @Test
    fun `should return all created sessions`() {
        val topic = topicRepository.findById(1).orElseThrow()

        val session = sessionRepository.save(Session(0, topic, Instant.now()))

        val all = sessionRepository.findAll()

        assertTrue(all.any { it.id == session.id })
    }

    @Test
    fun `should delete a session`() {
        val topic = topicRepository.findById(1).orElseThrow()

        val session = sessionRepository.save(Session(0, topic, Instant.now()))

        sessionRepository.deleteById(session.id)

        assertTrue(sessionRepository.findById(session.id).isEmpty)
    }
}