package ru.ambulance.config.scheduler

import io.r2dbc.spi.ConnectionFactory
import net.javacrumbs.shedlock.core.LockProvider
import net.javacrumbs.shedlock.provider.r2dbc.R2dbcLockProvider
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@EnableSchedulerLock(defaultLockAtMostFor = "PT30S")
@EnableScheduling
open class SchedulerConfig {

    @Bean
    open fun lockProvider(connectionFactory: ConnectionFactory): LockProvider {
        return R2dbcLockProvider(connectionFactory)
    }
}