package ru.ambulance.nurseservice.service

import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.dto.TreatmentResultDto
import ru.ambulance.nurseservice.model.entity.TreatmentResult
import ru.ambulance.nurseservice.model.rdto.TreatmentResultRdto

interface TreatmentResultService {
    fun insert(treatmentResult: TreatmentResult): Mono<TreatmentResult>

    fun updateTreatmentResult(treatmentResultRdto: TreatmentResultRdto): Mono<String>

    fun showTreatmentResultList(appealId: String?, examinationId: String?, nurseId: String?): Flux<TreatmentResultDto>
}