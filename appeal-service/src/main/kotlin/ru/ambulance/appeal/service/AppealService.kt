package ru.ambulance.appeal.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.model.rdto.CreateAppealRdto

interface AppealService {

    fun findById(appealId: String) : Mono<Appeal>
//
//    // TODO For differences between multi-stream and single-stream in webflux. Remove after learn.
//    fun findAll(): Flux<AppealDto>

    fun createNewAppeal(createAppealRdto: CreateAppealRdto): Mono<String>

    fun save(appeal: Appeal) : Mono<Appeal>

    fun showAppealList(appealStatues: List<String>?,
                       appealIds: List<String>?,
                       doctorId: String?) : Flux<Appeal>
}