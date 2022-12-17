package ru.ambulance.authservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.ambulance.authservice.model.entity.AuthUser

@Repository
interface AuthUserRepository : ReactiveCrudRepository<AuthUser, String> {
    fun findFirstByLogin(login: String): Mono<AuthUser>
}