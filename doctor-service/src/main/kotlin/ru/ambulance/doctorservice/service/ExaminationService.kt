package ru.ambulance.doctorservice.service

import reactor.core.publisher.Mono
import ru.ambulance.doctorservice.model.rdto.ExaminationRdto

interface ExaminationService {
    fun createNewExamination(examinationRdto: ExaminationRdto): Mono<String>
}