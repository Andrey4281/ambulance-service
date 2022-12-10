package ru.ambulance.nurseservice.broker.command

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import reactor.core.publisher.Mono
import ru.ambulance.broker.events.examination.CreatingExaminationEvent
import ru.ambulance.nurseservice.broker.AbstractNurseServiceListener

@Configuration
class NurseCreateExaminationCommandListener: AbstractNurseServiceListener<CreatingExaminationEvent, List<String>>() {

    @Value("\${kafka.topics.examinationRequestTopic}")
    private val examinationRequestTopic: String = "examinationRequestTopic"

    override fun getErrorObject(): List<String> = arrayListOf()

    override fun getTopic(): String = examinationRequestTopic

    override fun getSuccessHandler(value: CreatingExaminationEvent): Mono<List<String>> {
        TODO("Not yet implemented")
    }

    override fun getEventClass(): Class<CreatingExaminationEvent> = CreatingExaminationEvent::class.java

    @Bean("NurseCreateExaminationCommandListener")
    override fun consumer(kafkaProperties: KafkaProperties, objectMapper: ObjectMapper): ApplicationRunner = abstractConsumer(kafkaProperties, objectMapper)
}