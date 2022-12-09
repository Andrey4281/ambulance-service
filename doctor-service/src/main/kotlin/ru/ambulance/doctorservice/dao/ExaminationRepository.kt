package ru.ambulance.doctorservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import ru.ambulance.doctorservice.model.entity.Examination

interface ExaminationRepository : ReactiveCrudRepository<Examination, String>