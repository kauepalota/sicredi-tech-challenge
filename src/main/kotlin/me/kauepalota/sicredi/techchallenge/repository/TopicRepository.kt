package me.kauepalota.sicredi.techchallenge.repository

import me.kauepalota.sicredi.techchallenge.model.Topic
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TopicRepository : JpaRepository<Topic, Int>