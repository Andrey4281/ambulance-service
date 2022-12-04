package ru.ambulance.doctorservice.broker.outbox

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.MessageService
import ru.ambulance.doctorservice.dao.OutboxEventRepository

@Service
class DoctorMessageServiceImpl(private val objectMapper: ObjectMapper, private val outboxEventRepository: OutboxEventRepository)
    : MessageService<DoctorResponseOnCreatingAppealEvent>(objectMapper) {

    override fun saveMessageToDb(outboxEvent: OutboxEvent): Mono<OutboxEvent> = outboxEventRepository.save(outboxEvent)
}