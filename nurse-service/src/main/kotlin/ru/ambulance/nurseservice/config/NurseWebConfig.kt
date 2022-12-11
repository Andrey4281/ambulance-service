package ru.ambulance.nurseservice.config

import kotlinx.coroutines.FlowPreview
import org.springdoc.kotlin.docRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import ru.ambulance.config.web.WebConfig
import ru.ambulance.nurseservice.controller.NurseHandler
import ru.ambulance.nurseservice.service.NurseShiftService

@Configuration
class NurseWebConfig: WebConfig() {

    @Bean
    @FlowPreview
    fun nurseRouter(nurseHandler: NurseHandler) : RouterFunction<ServerResponse> = docRouter {
        POST("/nurse/beginShift/{nurseId}", accept(MediaType.APPLICATION_JSON), nurseHandler::beginShift)
        {
            it.operationId("beginShift")
                    .beanClass(NurseShiftService::class.java)
                    .beanMethod("beginShift")
        }
        PUT("/nurse/endShift/{nurseId}", accept(MediaType.APPLICATION_JSON), nurseHandler::endShift)
        {
            it.operationId("endShift")
                    .beanClass(NurseShiftService::class.java)
                    .beanMethod("endShift")
        }
    }
}