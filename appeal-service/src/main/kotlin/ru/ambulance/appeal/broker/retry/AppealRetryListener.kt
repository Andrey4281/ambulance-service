package ru.ambulance.appeal.broker.retry

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import ru.ambulance.broker.retry.AbstractRetryListener

@Configuration
class AppealRetryListener: AbstractRetryListener() {

    @Value("\${kafka.retry.appealRetryTopic}")
    private val appealRetryTopic: String = "appealRetryTopic"

    override fun getRetryTopic(): String = appealRetryTopic
}