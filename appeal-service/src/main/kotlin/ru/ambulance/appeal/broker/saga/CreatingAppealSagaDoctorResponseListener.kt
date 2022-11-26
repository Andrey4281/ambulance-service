package ru.ambulance.appeal.broker.saga

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.service.AppealService
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.config.ReactiveKafkaConsumer
import ru.ambulance.enums.AppealStatus

@Configuration
class CreatingAppealSagaDoctorResponseListener: ReactiveKafkaConsumer<DoctorResponseOnCreatingAppealEvent, Appeal>() {

    @Autowired
    private lateinit var appealService: AppealService;

    override fun getTopic(): String = "DoctorResponseOnCreatingAppealEvent"

    override fun getSuccessHandler(responseEvent: DoctorResponseOnCreatingAppealEvent): Mono<Appeal> {
        return responseEvent.eventId.let { appealService.findById(it) }.flatMap {
            it.isNewObject = false
            if (responseEvent.isSuccess) {
                it.appealStatus = AppealStatus.ASSIGNED.name
                it.currentDoctorId = responseEvent.doctorId
            } else {
                it.appealStatus = AppealStatus.ERROR.name
            }
            appealService.save(it)
        }
    }

   override fun getEventClass(): Class<DoctorResponseOnCreatingAppealEvent> = DoctorResponseOnCreatingAppealEvent::class.java
}