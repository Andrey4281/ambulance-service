package ru.ambulance.config.scheduler

import io.r2dbc.spi.ConnectionFactory
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.r2dbc.R2dbcLockProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
open class SchedulerConfig {

    @Bean
    open fun lockProvider(connectionFactory: ConnectionFactory): LockProvider = R2dbcLockProvider(connectionFactory)
}