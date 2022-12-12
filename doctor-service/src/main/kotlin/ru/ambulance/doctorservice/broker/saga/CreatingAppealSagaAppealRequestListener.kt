package ru.ambulance.doctorservice.broker.saga

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.CreatingAppealEvent
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.doctorservice.broker.AbstractDoctorServiceListener
import ru.ambulance.doctorservice.broker.outbox.DoctorMessageServiceImpl
import ru.ambulance.doctorservice.service.DoctorService
import ru.ambulance.doctorservice.service.DoctorShiftService
import ru.ambulance.enums.AppealStatus
import java.util.*

@Configuration
class CreatingAppealSagaAppealRequestListener : AbstractDoctorServiceListener<CreatingAppealEvent, OutboxEvent>() {

    @Value("\${kafka.topics.appealRequestTopic}")
    private val appealRequestTopic: String = "appealRequestTopic"

    @Value("\${kafka.topics.appealResponseTopic}")
    private val appealResponseTopic: String = "appealResponseTopic"

    @Autowired
    private lateinit var doctorService: DoctorService;

    @Autowired
    private lateinit var doctorShiftService: DoctorShiftService

    @Autowired
    private lateinit var doctorMessageService: DoctorMessageServiceImpl

    @Autowired
    private lateinit var  transactionalOperator: TransactionalOperator

    override fun getTopic(): String = appealRequestTopic

    //TODO asemenov подумать над оптимистик лок для shift - тк инкремент идет
    override fun getSuccessHandler(creatingAppealEvent: CreatingAppealEvent): Mono<OutboxEvent> {
        return doctorService.findRequiredDoctorWithMinActiveAppeal(hospitalId = creatingAppealEvent.hospitalId,
                specialization = creatingAppealEvent.primaryRequiredDoctor.name).filter(Objects::nonNull).flatMap {
            doctorShiftService.findActiveShiftByDoctorId(it)
        }.flatMap {
            it.activeAppealCount = it.activeAppealCount + 1
            it.totalAppealCount = it.totalAppealCount + 1
            doctorShiftService.updateShift(it)
        }.map {
            DoctorResponseOnCreatingAppealEvent(doctorId = it.doctorId, appealStatus = AppealStatus.ASSIGNED, isSuccess = true,
                    eventId = UUID.randomUUID().toString(), appealId = creatingAppealEvent.appealId)
        }.switchIfEmpty(Mono.just(DoctorResponseOnCreatingAppealEvent(appealStatus = AppealStatus.UNAVAILABLE_DOCTOR,
                isSuccess = false, eventId = UUID.randomUUID().toString(), appealId = creatingAppealEvent.appealId, doctorId = null)))
                .flatMap { doctorMessageService.sendMessage(null, appealResponseTopic, it) }.`as` { transactionalOperator.transactional(it) }
    }

    override fun getEventClass(): Class<CreatingAppealEvent> = CreatingAppealEvent::class.java

    override fun getErrorObject(): OutboxEvent = OutboxEvent(eventId = "", messageKey="", eventBodyJson = "", sendToTopic = "")

    @Bean("CreatingAppealSagaAppealRequestListener")
    override fun consumer(kafkaProperties: KafkaProperties, objectMapper: ObjectMapper): ApplicationRunner = abstractConsumer(kafkaProperties, objectMapper)
}