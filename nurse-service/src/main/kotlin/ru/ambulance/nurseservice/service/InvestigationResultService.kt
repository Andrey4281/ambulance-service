package ru.ambulance.nurseservice.service

import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.entity.InvestigationResult

interface InvestigationResultService {
    fun save(investigationResult: InvestigationResult): Mono<InvestigationResult>
}