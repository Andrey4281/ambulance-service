package ru.ambulance.doctorservice.config

import kotlinx.coroutines.FlowPreview
import org.springdoc.kotlin.docRouter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import ru.ambulance.config.web.WebConfig
import ru.ambulance.doctorservice.controller.DoctorHandler
import ru.ambulance.doctorservice.service.DoctorShiftService
import ru.ambulance.doctorservice.service.ExaminationService

@Configuration
class DoctorWebConfig : WebConfig() {

    @Bean
    @FlowPreview
    fun doctorRouter(doctorHandler: DoctorHandler): RouterFunction<ServerResponse> = docRouter {
        POST("/doctor/beginShift/{doctorId}", accept(MediaType.APPLICATION_JSON), doctorHandler::beginShift)
        {
            it.operationId("beginShift")
                    .beanClass(DoctorShiftService::class.java)
                    .beanMethod("beginShift")
        }
        PUT("/doctor/endShift/{doctorId}", accept(MediaType.APPLICATION_JSON), doctorHandler::endShift)
        {
            it.operationId("endShift")
                    .beanClass(DoctorShiftService::class.java)
                    .beanMethod("endShift")
        }
        POST("/doctor/createNewExamination", accept(MediaType.APPLICATION_JSON), doctorHandler::createExamination)
        {
            it.operationId("createNewExamination")
                    .beanClass(ExaminationService::class.java)
                    .beanMethod("createNewExamination")
        }
        PUT("/doctor/closeAppeal", accept(MediaType.APPLICATION_JSON), doctorHandler::closeAppeal)
        {
            it.operationId("closeAppeal")
                    .beanClass(DoctorShiftService::class.java)
                    .beanMethod("closeAppeal")
        }
    }
}