package ru.ambulance.doctorservice.broker.saga

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.CreatingAppealEvent
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.broker.events.outbox.OutboxEvent
import ru.ambulance.broker.service.MessageService
import ru.ambulance.config.broker.ReactiveKafkaConsumer
import ru.ambulance.doctorservice.service.DoctorService
import ru.ambulance.doctorservice.service.DoctorShiftService
import ru.ambulance.enums.AppealStatus
import java.util.*

//https://habr.com/ru/post/565056/
//https://lankydan.dev/2018/03/15/doing-stuff-with-spring-webflux
@Configuration
class CreatingAppealSagaAppealRequestListener : ReactiveKafkaConsumer<CreatingAppealEvent, OutboxEvent>() {

    @Value("\${kafka.topics.appealRequestTopic}")
    private val appealRequestTopic: String = "appealRequestTopic"

    @Value("\${kafka.topics.appealResponseTopic}")
    private val appealResponseTopic: String = "appealResponseTopic"

    @Autowired
    private lateinit var doctorService: DoctorService;

    @Autowired
    private lateinit var doctorShiftService: DoctorShiftService

    @Autowired
    private lateinit var doctorMessageService: MessageService<DoctorResponseOnCreatingAppealEvent>

    override fun getTopic(): String = appealRequestTopic

    //TODO asemenov подумать над оптимистик лок для shift - тк инкремент идет
    //TODO проверить почему поток валится при ошибке и пустой кейс!!!!
    @Transactional
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
                .flatMap { doctorMessageService.sendMessage(null, appealResponseTopic, it) }
    }

    override fun getEventClass(): Class<CreatingAppealEvent> = CreatingAppealEvent::class.java
}