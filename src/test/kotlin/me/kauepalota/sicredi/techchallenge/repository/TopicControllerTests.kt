package me.kauepalota.sicredi.techchallenge.repository

import me.kauepalota.sicredi.techchallenge.TestTechChallengeApplication
import me.kauepalota.sicredi.techchallenge.model.Topic
import net.datafaker.Faker
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import

@Import(TestTechChallengeApplication::class)
@SpringBootTest
class TopicControllerTests {
    private lateinit var topicRepository: TopicRepository

    private val faker = Faker()

    @Test
    fun `should create a topic`() {
        topicRepository.save(Topic(0, faker.lorem().sentence()))

        val all = topicRepository.findAll()

        assert(all.isNotEmpty())
    }

    @Test
    fun `should return all created topics`() {
        topicRepository.save(Topic(0, faker.lorem().sentence()))

        val all = topicRepository.findAll()

        assert(all.isNotEmpty())
    }

    @Test
    fun `should delete a topic`() {
        val topic = topicRepository.save(Topic(0, faker.lorem().sentence()))

        topicRepository.deleteById(topic.id)

        assert(topicRepository.findById(topic.id).isEmpty)
    }
}