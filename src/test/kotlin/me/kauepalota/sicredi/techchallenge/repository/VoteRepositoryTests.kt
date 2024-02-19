package me.kauepalota.sicredi.techchallenge.repository

import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.model.SessionVote
import net.datafaker.Faker
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.Sql.*

@Import(TestTechChallengeApplication::class)
@Sql(scripts = ["/setup.sql"], executionPhase = ExecutionPhase.BEFORE_TEST_CLASS)
@AutoConfigureMockMvc
@SpringBootTest
class VoteRepositoryTests {
    @Autowired
    private lateinit var voteRepository: SessionVoteRepository

    @Autowired
    private lateinit var sessionRepository: SessionRepository

    private val faker = Faker()

    @Test
    fun `should create a vote`() {
        val session = sessionRepository.findById(1).orElseThrow()

        val vote = voteRepository.save(
            SessionVote(
                0,
                faker.cpf().valid(false),
                session,
                true
            )
        )

        assertNotEquals(0, vote.id)
        assertEquals(1, vote.session.id)
    }

    @Test
    fun `should return all created votes`() {
        var session = sessionRepository.findById(1).orElseThrow()

        val vote = voteRepository.save(
            SessionVote(
                0,
                faker.cpf().valid(false),
                session,
                true
            )
        )

        val all = voteRepository.findAll()

        assertTrue(all.any { it.id == vote.id })

        session = sessionRepository.findById(session.id).orElseThrow()

        assertTrue(session.votes.size > 1)
        assertTrue(session.votes.any { it.id == vote.id })
    }

    @Test
    fun `should delete a vote`() {
        val session = sessionRepository.findById(1).orElseThrow()

        val vote = voteRepository.save(
            SessionVote(
                0,
                faker.cpf().valid(false),
                session,
                true
            )
        )

        voteRepository.deleteById(vote.id)

        assertTrue(voteRepository.findById(vote.id).isEmpty)
    }
}
