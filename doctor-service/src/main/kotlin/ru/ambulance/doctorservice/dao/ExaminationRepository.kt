package ru.ambulance.doctorservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import ru.ambulance.doctorservice.model.entity.Examination

@Repository
interface ExaminationRepository : ReactiveCrudRepository<Examination, String>