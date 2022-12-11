package ru.ambulance.gateway.config


import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = io.swagger.v3.oas.annotations.info.Info(
    title = "Swagger Gateway Service",
    version = "1.0",
    description = "Documentation APIs v1.0"
)
)
class GatewayOpenApiConfig {
    @Bean
    open fun customOpenAPI(): OpenAPI {
        val bearerAuthenticate = "basicAuth"
        return OpenAPI()
            .info(
                Info()
                .title("Gateway Swagger")
            )
            .addSecurityItem(SecurityRequirement().addList(bearerAuthenticate))
            .components(
                Components()
                .addSecuritySchemes(bearerAuthenticate,
                    SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")))
    }

}