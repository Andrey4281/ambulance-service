package ru.ambulance.appeal.broker.saga

import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.appeal.DoctorResponseOnCreatingAppealEvent
import ru.ambulance.config.ReactiveKafkaConsumer

@Configuration
class CreatingAppealSagaDoctorResponseListener: ReactiveKafkaConsumer<DoctorResponseOnCreatingAppealEvent>() {

    override fun getTopic(): String = "DoctorResponseOnCreatingAppealEvent"

    //TODO asemenov - временная реализация заменить потом на обработку саги
    override fun getSuccessHandler(value: DoctorResponseOnCreatingAppealEvent): Mono<DoctorResponseOnCreatingAppealEvent> = Mono.empty()

   override fun getEventClass(): Class<DoctorResponseOnCreatingAppealEvent> = DoctorResponseOnCreatingAppealEvent::class.java
}