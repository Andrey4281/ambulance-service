package ru.ambulance.nurseservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.dao.TreatmentResultRepository
import ru.ambulance.nurseservice.model.entity.TreatmentResult
import ru.ambulance.nurseservice.service.TreatmentResultService

@Service
class TreatmentResultServiceImpl(private val treatmentResultRepository: TreatmentResultRepository) : TreatmentResultService {
    override fun save(treatmentResult: TreatmentResult): Mono<TreatmentResult> = treatmentResultRepository.save(treatmentResult)
}