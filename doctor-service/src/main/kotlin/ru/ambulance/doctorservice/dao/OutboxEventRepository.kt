package ru.ambulance.doctorservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.ambulance.broker.events.outbox.OutboxEvent

interface OutboxEventRepository : ReactiveCrudRepository<OutboxEvent, String>