package ru.ambulance.doctorservice.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.service.DoctorShiftService


@Component
class DoctorHandler(val doctorShiftService: DoctorShiftService) {

    fun beginShift(request: ServerRequest) : Mono<ServerResponse> {
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
}