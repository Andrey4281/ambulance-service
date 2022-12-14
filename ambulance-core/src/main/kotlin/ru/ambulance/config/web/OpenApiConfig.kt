package ru.ambulance.config.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean

abstract class OpenApiConfig {

    @Bean
    open fun customOpenAPI(): OpenAPI {
        return OpenAPI()
                .components(Components())
                .info(Info()
                        .title(getApiTitle())
                )
    }

    abstract fun getApiTitle(): String
}