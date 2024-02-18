package me.kauepalota.sicredi.techchallenge

import me.kauepalota.sicredi.techchallenge.model.Session
import me.kauepalota.sicredi.techchallenge.model.SessionVote
import me.kauepalota.sicredi.techchallenge.model.Topic
import me.kauepalota.sicredi.techchallenge.repository.SessionRepository
import me.kauepalota.sicredi.techchallenge.repository.SessionVoteRepository
import me.kauepalota.sicredi.techchallenge.repository.TopicRepository
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import java.util.*

@Import(TestTechChallengeApplication::class)
@SpringBootTest
class TechChallengeApplicationTests {
    @Autowired
    private lateinit var topicRepository: TopicRepository

    @Autowired
    private lateinit var sessionVoteRepository: SessionVoteRepository

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    companion object {
        private lateinit var faker: Faker

        @BeforeAll
        @JvmStatic
        fun setup() {
            faker = Faker(Locale("en-US"))
        }
    }

    @Test
    fun `should create a topic`() {
        val name = "Everyone will have a ${faker.animal().name()}"

        val topic = topicRepository.save(Topic(0, name))

        assertNotEquals(0, topic.id)
        assertEquals(name, topic.name)
    }

    @Test
    fun `should return all created topics`() {
        val name = "Everyone will have a ${faker.animal().name()}"

        topicRepository.save(Topic(0, name))

        val all = topicRepository.findAll()

        assertTrue(all.any { it.name == name })
    }

    @Test
    fun `should create and then update a topic`() {
        val name = "Everyone will have a ${faker.animal().name()}"

        val topic = topicRepository.save(Topic(0, name))

        val newName = "Everyone will have a ${faker.animal().name()}"

        val updated = topicRepository.save(topic.copy(name = newName))

        assertEquals(updated.id, topic.id)
        assertEquals(updated.name, newName)
    }

    @Test
    fun `should create a topic and then create a vote session`() {
        val name = "Everyone will have a ${faker.animal().name()}"

        val topic = topicRepository.save(Topic(0, name))

        assertNotEquals(0, topic.name)
        assertEquals(name, topic.name)

        val session = sessionRepository.save(Session(0, topic, votes = mutableListOf()))

        assertEquals(topic.id, session.topic.id)
        assertEquals(0, session.votes.size)
        assertEquals(60000, session.duration)
        assertTrue(session.isOpen())
    }

    @Test
    fun `should create a topic and then create a expired vote session`() {
        val name = "Everyone will have a ${faker.animal().name()}"

        val topic = topicRepository.save(Topic(0, name))

        assertNotEquals(0, topic.name)
        assertEquals(name, topic.name)

        val session = sessionRepository.save(Session(0, topic, votes = mutableListOf(), duration = 0))

        assertEquals(topic.id, session.topic.id)
        assertEquals(0, session.votes.size)
        assertEquals(0, session.duration)
        assertFalse(session.isOpen())
    }

    @Test
    fun `should create a topic, then create a vote session, then create a vote`() {
        val name = "Everyone will have a ${faker.animal().name()}"

        val topic = topicRepository.save(Topic(0, name))

        assertNotEquals(0, topic.name)
        assertEquals(name, topic.name)

        var session = sessionRepository.save(Session(0, topic, votes = mutableListOf()))

        assertEquals(topic.id, session.topic.id)
        assertEquals(0, session.votes.size)
        assertEquals(60000, session.duration)
        assertTrue(session.isOpen())

        val cpf = faker.cpf().valid(false)

        assertEquals(11, cpf.length)

        val vote = sessionVoteRepository.save(SessionVote(0, cpf, session, true))

        assertNotEquals(0, vote.id)
        assertEquals(cpf, vote.cpf)
        assertEquals(session.id, vote.session.id)
        assertTrue(vote.choice)

        assertEquals(0, session.votes.size)

        session = sessionRepository.findById(session.id).get()

        assertEquals(1, session.votes.size)
    }
}
