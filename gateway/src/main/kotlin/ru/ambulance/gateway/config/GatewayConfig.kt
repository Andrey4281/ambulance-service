package ru.ambulance.gateway.config

import org.springframework.cloud.gateway.filter.factory.RequestRateLimiterGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.SpringCloudCircuitBreakerFilterFactory
import org.springframework.cloud.gateway.filter.ratelimit.RedisRateLimiter
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec
import org.springframework.cloud.gateway.route.builder.PredicateSpec
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class GatewayConfig {

    @Bean
    fun customRouteLocator(builder: RouteLocatorBuilder): RouteLocator = builder.routes()
            .route("path_route") { r: PredicateSpec ->
                r.path("/get")
                    .uri("http://httpbin.org")
            }
            .route("host_route") { r: PredicateSpec ->
                r.host("*.myhost.org")
                    .uri("http://httpbin.org")
            }
            .route("rewrite_route") { r: PredicateSpec ->
                r.host("*.rewrite.org")
                    .filters { f: GatewayFilterSpec ->
                        f.rewritePath(
                            "/foo/(?<segment>.*)",
                            "/\${segment}"
                        )
                    }
                    .uri("http://httpbin.org")
            }
            .route("circuitbreaker_route") { r: PredicateSpec ->
                r.host("*.circuitbreaker.org")
                    .filters { f: GatewayFilterSpec ->
                        f.circuitBreaker { c: SpringCloudCircuitBreakerFilterFactory.Config ->
                            c.name = "slowcmd"
                        }
                    }
                    .uri("http://httpbin.org")
            }
            .route("circuitbreaker_fallback_route") { r: PredicateSpec ->
                r.host("*.circuitbreakerfallback.org")
                    .filters { f: GatewayFilterSpec ->
                        f.circuitBreaker { c: SpringCloudCircuitBreakerFilterFactory.Config ->
                            c.setName(
                                "slowcmd"
                            ).setFallbackUri("forward:/circuitbreakerfallback")
                        }
                    }
                    .uri("http://httpbin.org")
            }
            .route("limit_route") { r: PredicateSpec ->
                r.host("*.limited.org")
                    .and()
                    .path("/anything/**")
                    .filters { f: GatewayFilterSpec ->
                        f.requestRateLimiter { c: RequestRateLimiterGatewayFilterFactory.Config ->
                            c.rateLimiter = redisRateLimiter()
                        }
                    }
                    .uri("http://httpbin.org")
            }
            .build()
    @Bean
    fun redisRateLimiter(): RedisRateLimiter = RedisRateLimiter(1, 2)

}