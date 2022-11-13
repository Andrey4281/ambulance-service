package ru.ambulance.appeal.config


import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@OpenAPIDefinition(info = io.swagger.v3.oas.annotations.info.Info(
    title = "Swagger Appeal Service",
    version = "1.0",
    description = "Documentation APIs v1.0"
)
)
class OpenApiConfig {

    @Bean
    fun customOpenAPI(): OpenAPI {
        return OpenAPI()
            .components(Components())
            .info(Info()
                .title("Appeal service API")
            )
    }

}