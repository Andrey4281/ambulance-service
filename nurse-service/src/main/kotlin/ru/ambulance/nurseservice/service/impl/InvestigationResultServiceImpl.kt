package ru.ambulance.nurseservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.dao.InvestigationResultRepository
import ru.ambulance.nurseservice.model.entity.InvestigationResult
import ru.ambulance.nurseservice.service.InvestigationResultService

@Service
class InvestigationResultServiceImpl(private val investigationResultRepository: InvestigationResultRepository)
    : InvestigationResultService {
    override fun insert(investigationResult: InvestigationResult): Mono<InvestigationResult> = investigationResultRepository.save(investigationResult)
}