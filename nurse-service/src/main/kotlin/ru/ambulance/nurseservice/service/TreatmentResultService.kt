package ru.ambulance.nurseservice.service

import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.model.entity.TreatmentResult

interface TreatmentResultService {
    fun save(treatmentResult: TreatmentResult): Mono<TreatmentResult>
}