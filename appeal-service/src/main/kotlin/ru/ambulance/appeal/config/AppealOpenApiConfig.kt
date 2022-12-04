package ru.ambulance.appeal.config


import org.springframework.context.annotation.Configuration
import ru.ambulance.config.web.OpenApiConfig

@Configuration
class AppealOpenApiConfig: OpenApiConfig() {

    override fun getApiTitle(): String = "Appeal service API"
}