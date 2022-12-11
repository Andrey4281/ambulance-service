package ru.ambulance.nurseservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import ru.ambulance.broker.events.outbox.OutboxEvent

@Repository
interface OutboxEventRepository : ReactiveCrudRepository<OutboxEvent, String>