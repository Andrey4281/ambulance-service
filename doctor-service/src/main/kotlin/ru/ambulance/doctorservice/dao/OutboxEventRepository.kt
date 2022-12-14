package ru.ambulance.doctorservice.dao

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.ambulance.broker.events.outbox.OutboxEvent

@Repository
interface OutboxEventRepository : ReactiveCrudRepository<OutboxEvent, String> {
    @Query("SELECT * FROM outbox_event FOR UPDATE")
    fun findAllWithLock(): Flux<OutboxEvent>
}