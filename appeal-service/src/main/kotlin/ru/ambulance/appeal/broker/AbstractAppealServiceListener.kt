package ru.ambulance.appeal.broker

import org.springframework.beans.factory.annotation.Value
import ru.ambulance.broker.events.base.BaseEvent
import ru.ambulance.config.broker.ReactiveKafkaConsumer

abstract class AbstractAppealServiceListener<T : BaseEvent, D : Any> : ReactiveKafkaConsumer<T, D>() {

    @Value("\${kafka.retry.appealRetryTopic}")
    private val appealRetryTopic: String = "appealRetryTopic"

    @Value("\${kafka.retry.appealDeadLetterTopic}")
    private val appealDeadLetterTopic: String = "appealDeadLetterTopic"

    override fun getRetryTopicName(): String = appealRetryTopic

    override fun getDeadLetterTopicName(): String = appealDeadLetterTopic
}