package ru.ambulance.appeal.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.dto.AppealDto
import java.util.UUID

interface AppealService {

    fun findById(appealId: UUID) : Mono<AppealDto>

    // TODO For differences between multi-stream and single-stream in webflux. Remove after learn.
    fun findAll(): Flux<AppealDto>

}