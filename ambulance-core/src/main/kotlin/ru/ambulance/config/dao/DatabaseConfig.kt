package ru.ambulance.config.dao

import io.r2dbc.spi.ConnectionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.r2dbc.connection.R2dbcTransactionManager
import org.springframework.transaction.ReactiveTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.transaction.reactive.TransactionalOperator

@Configuration
@EnableTransactionManagement
open class DatabaseConfig {

    @Bean
    open fun transactionManager(connectionFactory: ConnectionFactory?): ReactiveTransactionManager? {
        return connectionFactory?.let { R2dbcTransactionManager(it) }
    }

    @Bean
    open fun transactionalOperator(transactionManager: ReactiveTransactionManager?): TransactionalOperator? {
        return TransactionalOperator.create(transactionManager!!)
    }
}