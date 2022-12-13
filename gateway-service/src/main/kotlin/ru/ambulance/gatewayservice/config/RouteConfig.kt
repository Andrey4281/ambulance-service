package ru.ambulance.gatewayservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.*
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RouteConfig {
    @Value("\${services.appealService}")
    private lateinit var appealService: String
    @Value("\${services.doctorService}")
    private lateinit var doctorService: String
    @Value("\${services.nurseService}")
    private lateinit var nurseService: String

    @Bean
    fun paths(builder: RouteLocatorBuilder): RouteLocator {
        return builder.routes()
                .route("appeal-service"
                ) { route: PredicateSpec ->
                    route.path("/appeal-service/**")
                            .filters { filter: GatewayFilterSpec -> filter.stripPrefix(1) }
                            .uri(appealService)
                }
                .route("doctor-service"
                ) { route: PredicateSpec ->
                    route.path("/doctor-service/**")
                            .filters { filter: GatewayFilterSpec -> filter.stripPrefix(1) }
                            .uri(doctorService)
                }
                .route("nurse-service"
                ) { route: PredicateSpec ->
                    route.path("/nurse-service/**")
                            .filters { filter: GatewayFilterSpec -> filter.stripPrefix(1) }
                            .uri(nurseService)
                }
                .build()
    }
}