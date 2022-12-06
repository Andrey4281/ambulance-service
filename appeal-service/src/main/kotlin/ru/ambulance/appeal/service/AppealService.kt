package ru.ambulance.appeal.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.entity.Appeal
import ru.ambulance.appeal.model.rdto.CreateAppealRdto
import ru.ambulance.enums.AppealStatus

interface AppealService {

    fun findById(appealId: String) : Mono<Appeal>

    fun createNewAppeal(createAppealRdto: CreateAppealRdto): Mono<String>

    fun save(appeal: Appeal) : Mono<Appeal>

    fun showAppealList(appealStatues: List<String>?,
                       appealIds: List<String>?,
                       doctorId: String?) : Flux<Appeal>

    fun updateAppealStatus(doctorId: String,
                           appealId: String,
                           appealStatus: AppealStatus): Mono<String>
}