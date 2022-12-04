package ru.ambulance.appeal.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.ambulance.appeal.model.entity.Patient

interface PatientRepository : ReactiveCrudRepository<Patient, String> {
}