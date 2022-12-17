package ru.ambulance.authservice.config

import kotlinx.coroutines.FlowPreview
import org.springdoc.kotlin.docRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import ru.ambulance.authservice.controller.AuthHandler
import ru.ambulance.authservice.service.AuthUserService
import ru.ambulance.config.web.WebConfig

@Configuration
class AuthWebConfig(private val authHandler: AuthHandler) : WebConfig() {

    @Bean
    @FlowPreview
    fun authRouter(): RouterFunction<ServerResponse> = docRouter {
        POST("/auth/signUp", accept(MediaType.APPLICATION_JSON), authHandler::signUp)
        {
            it.operationId("signUp")
                    .beanClass(AuthUserService::class.java)
                    .beanMethod("signUp")
        }
        POST("/auth/signIn", accept(MediaType.APPLICATION_JSON), authHandler::signIn)
        {
            it.operationId("signIn")
                    .beanClass(AuthUserService::class.java)
                    .beanMethod("signIn")
        }
    }
}