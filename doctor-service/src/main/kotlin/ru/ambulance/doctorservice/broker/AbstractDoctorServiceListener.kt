package ru.ambulance.doctorservice.broker

import org.springframework.beans.factory.annotation.Value
import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.config.broker.ReactiveKafkaConsumer

abstract class AbstractDoctorServiceListener<T : BaseEvent, D : Any> : ReactiveKafkaConsumer<T, D>() {

    @Value("\${kafka.retry.doctorRetryTopic}")
    private val doctorRetryTopic: String = "doctorRetryTopic"

    @Value("\${kafka.retry.doctorDeadLetterTopic}")
    private val doctorDeadLetterTopic: String = "doctorDeadLetterTopic"

    override fun getRetryTopicName(): String = doctorRetryTopic

    override fun getDeadLetterTopicName(): String = doctorDeadLetterTopic
}