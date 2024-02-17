package me.kauepalota.sicredi.techchallenge.repository

import me.kauepalota.sicredi.techchallenge.model.Session
import org.springframework.data.jpa.repository.JpaRepository

interface SessionRepository : JpaRepository<Session, Int>