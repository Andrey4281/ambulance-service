package ru.ambulance.appeal.controller

import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.BodyInserters.fromPublisher
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.dto.AppealDto
import ru.ambulance.appeal.model.rdto.CreateAppealRdto
import ru.ambulance.appeal.service.AppealService
import ru.ambulance.enums.AppealStatus

@Component
class AppealHandler(val appealService: AppealService) {

        fun createAppeal(request: ServerRequest) : Mono<ServerResponse> {
                val createAppealRdto : Mono<CreateAppealRdto> = request.bodyToMono(CreateAppealRdto::class.java)
                return ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(createAppealRdto.flatMap(appealService::createNewAppeal), String::class.java))
        }

        fun showAppealList(request: ServerRequest) : Mono<ServerResponse> {
                val params = request.queryParams()
                return ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(fromPublisher(appealService.showAppealList(params.getFirst("appealStatues")?.split(","),
                                params.getFirst("appealIds")?.split(","), params.getFirst("doctorId")),
                                AppealDto::class.java))
        }

        fun takeAppealForWork(request: ServerRequest): Mono<ServerResponse> {
                val appealId = request.pathVariable("appealId")
                val params = request.queryParams()
                return ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(appealService.updateAppealStatus(doctorId = params.getFirst("doctorId")!!,
                                appealId, AppealStatus.IN_PROGRESS), String::class.java)
        }
}