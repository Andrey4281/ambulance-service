package ru.ambulance.nurseservice.broker.retry

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import ru.ambulance.broker.retry.AbstractRetryListener

@Configuration
class NurseRetryListener: AbstractRetryListener() {

    @Value("\${kafka.retry.nurseRetryTopic}")
    private val nurseRetryTopic: String = "nurseRetryTopic"

    override fun getRetryTopic(): String = nurseRetryTopic
}