package ru.ambulance.appeal.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.appeal.dao.PatientRepository
import ru.ambulance.appeal.model.entity.Patient
import ru.ambulance.appeal.service.PatientService

@Service
class PatientServiceImpl(private val patientRepository: PatientRepository): PatientService {
    override fun findPatientById(id:String): Mono<Patient> = patientRepository.findById(id)
}