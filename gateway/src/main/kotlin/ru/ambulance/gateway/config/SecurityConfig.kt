package ru.ambulance.gateway.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
class SecurityConfig {

    @Bean
    fun springSecurityFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        // Authenticate through configured OpenID Provider
        http.csrf().disable()
            .authorizeExchange()
            .anyExchange()
            .authenticated()

        return http.build()
    }
}