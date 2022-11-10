package ru.ambulance.appeal.controller

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import ru.ambulance.appeal.controller.mapper.toResponse
import ru.ambulance.appeal.service.AppealService
import java.util.*

@Component
class AppealHandler(val appealService: AppealService) {


        fun showAppeal(request: ServerRequest) : Mono<ServerResponse> {

                val id = UUID.fromString(request.pathVariable("appealId"))
                return appealService.findById(id)
                        .flatMap { toResponse(it) }
        }


        fun showAppealList(request: ServerRequest): Mono<ServerResponse> = appealService.findAll()
                .collectList()
                .flatMap { toResponse(it) }

}