package ru.ambulance.nurseservice.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.rdto.InvestigationResultRdto
import ru.ambulance.nurseservice.model.rdto.TreatmentResultRdto
import ru.ambulance.nurseservice.service.InvestigationResultService
import ru.ambulance.nurseservice.service.NurseShiftService
import ru.ambulance.nurseservice.service.TreatmentResultService

@Component
class NurseHandler(private val nurseShiftService: NurseShiftService,
                   private val investigationResultService: InvestigationResultService,
                   private val treatmentResultService: TreatmentResultService) {

    fun beginShift(request: ServerRequest): Mono<ServerResponse> {
        val nurseId = request.pathVariable("nurseId")
        //TODO asemenov tZone плюс перезатирается в queryParam
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(nurseShiftService.beginShift(nurseId = nurseId,
                tZone = request.queryParams().getFirst("tZone")
                        ?: "UTC+3"),
                String::class.java)
    }

    fun endShift(request: ServerRequest): Mono<ServerResponse> {
        val nurseId = request.pathVariable("nurseId")
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(nurseShiftService.endShift(nurseId), Void::class.java)
    }

    fun updateInvestigationResult(request: ServerRequest): Mono<ServerResponse> {
        val investigationResultRdto: Mono<InvestigationResultRdto> = request.bodyToMono(InvestigationResultRdto::class.java)
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(investigationResultRdto.flatMap(investigationResultService::updateInvestigationResult),
                        String::class.java))
    }

    fun updateTreatmentResult(request: ServerRequest): Mono<ServerResponse> {
        val treatmentResultRdto: Mono<TreatmentResultRdto> = request.bodyToMono(TreatmentResultRdto::class.java)
        return ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromPublisher(treatmentResultRdto.flatMap(treatmentResultService::updateTreatmentResult),
                        String::class.java))
    }
}