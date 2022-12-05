package ru.ambulance.doctorservice.broker.retry

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import ru.ambulance.broker.retry.AbstractRetryListener

@Configuration
class DoctorRetryListener: AbstractRetryListener() {

    @Value("\${kafka.retry.doctorRetryTopic}")
    private val doctorRetryTopic: String = "doctorRetryTopic"

    override fun getRetryTopic(): String = doctorRetryTopic
}