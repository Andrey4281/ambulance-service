package ru.ambulance.doctorservice.config

import org.springframework.context.annotation.Configuration
import ru.ambulance.config.web.OpenApiConfig

@Configuration
class DoctorOpenApiConfig: OpenApiConfig() {

    override fun getApiTitle(): String = "Doctor service API"
}