package ru.ambulance.nurseservice.broker

import org.springframework.beans.factory.annotation.Value
import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.config.broker.ReactiveKafkaConsumer

abstract class AbstractNurseServiceListener<T : BaseEvent, D : Any> : ReactiveKafkaConsumer<T, D>() {

    @Value("\${kafka.retry.nurseRetryTopic}")
    private val nurseRetryTopic: String = "nurseRetryTopic"

    @Value("\${kafka.retry.nurseDeadLetterTopic}")
    private val nurseDeadLetterTopic: String = "nurseDeadLetterTopic"

    override fun getRetryTopicName(): String = nurseRetryTopic

    override fun getDeadLetterTopicName(): String = nurseDeadLetterTopic
}