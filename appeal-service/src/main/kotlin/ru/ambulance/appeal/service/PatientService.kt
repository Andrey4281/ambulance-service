package ru.ambulance.appeal.service

import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.entity.Patient

interface PatientService {
    fun findPatientById(id:String): Mono<Patient>
}