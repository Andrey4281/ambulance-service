package ru.ambulance.config.web

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean

abstract class OpenApiConfig {

    @Bean
    open fun customOpenAPI(): OpenAPI {
        val bearerAuthenticate = "basicAuth"
        return OpenAPI()
                .info(Info()
                        .title(getApiTitle())
                )
                .addSecurityItem(SecurityRequirement().addList(bearerAuthenticate))
                .components(Components()
                    .addSecuritySchemes(bearerAuthenticate,
                        SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")))
    }

    abstract fun getApiTitle(): String
}