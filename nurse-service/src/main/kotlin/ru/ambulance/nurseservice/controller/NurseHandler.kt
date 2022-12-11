package ru.ambulance.nurseservice.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.service.NurseShiftService

@Component
class NurseHandler(private val nurseShiftService: NurseShiftService) {

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
}