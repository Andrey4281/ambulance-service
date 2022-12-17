package ru.ambulance.authservice.config

import org.springframework.context.annotation.Configuration
import ru.ambulance.config.web.OpenApiConfig

@Configuration
class AuthOpenApiConfig: OpenApiConfig() {
    override fun getApiTitle(): String = "Auth service API"
}