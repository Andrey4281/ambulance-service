package ru.ambulance.doctorservice.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.model.rdto.CloseAppealRdto
import ru.ambulance.doctorservice.model.rdto.ExaminationRdto
import ru.ambulance.doctorservice.service.DoctorShiftService
import ru.ambulance.doctorservice.service.ExaminationService


@Component
class DoctorHandler(private val doctorShiftService: DoctorShiftService,
                    private val examinationService: ExaminationService) {

    fun beginShift(request: ServerRequest): Mono<ServerResponse> {
        val doctorId = request.pathVariable("doctorId")
        //TODO asemenov tZone плюс перезатирается в queryParam
        return ok().contentType(MediaType.APPLICATION_JSON).body(doctorShiftService.beginShift(doctorId = doctorId,
                tZone = request.queryParams().getFirst("tZone")
                        ?: "UTC+3"),
                String::class.java)
    }

    fun endShift(request: ServerRequest): Mono<ServerResponse> {
        val doctorId = request.pathVariable("doctorId")
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(doctorShiftService.endShift(doctorId), Void::class.java)
    }

    fun createExamination(request: ServerRequest): Mono<ServerResponse> {
        val createExaminationRdto: Mono<ExaminationRdto> = request.bodyToMono(ExaminationRdto::class.java)
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(createExaminationRdto.flatMap(examinationService::createNewExamination),
                        String::class.java))
    }

    fun closeAppeal(request: ServerRequest): Mono<ServerResponse> {
        val closeAppealRdto: Mono<CloseAppealRdto> = request.bodyToMono(CloseAppealRdto::class.java)
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(closeAppealRdto.flatMap(doctorShiftService::closeAppeal),
                        String::class.java))
    }
}