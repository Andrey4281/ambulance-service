package ru.ambulance.nurseservice.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.dto.InvestigationResultDto
import ru.ambulance.nurseservice.model.entity.InvestigationResult
import ru.ambulance.nurseservice.model.rdto.InvestigationResultRdto

interface InvestigationResultService {
    fun insert(investigationResult: InvestigationResult): Mono<InvestigationResult>

    fun updateInvestigationResult(investigationResultRdto: InvestigationResultRdto): Mono<String>

    fun showInvestigationResultList(appealId: String?, examinationId: String?, nurseId: String?): Flux<InvestigationResultDto>
}