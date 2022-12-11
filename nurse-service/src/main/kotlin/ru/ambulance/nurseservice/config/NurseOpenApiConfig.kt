package ru.ambulance.nurseservice.config

import org.springframework.context.annotation.Configuration
import ru.ambulance.config.web.OpenApiConfig

@Configuration
class NurseOpenApiConfig: OpenApiConfig() {

    override fun getApiTitle(): String = "Nurse service API"
}