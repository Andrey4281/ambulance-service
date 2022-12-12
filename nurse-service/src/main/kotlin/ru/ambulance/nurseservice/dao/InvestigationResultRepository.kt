package ru.ambulance.nurseservice.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import ru.ambulance.nurseservice.model.entity.InvestigationResult

@Repository
interface InvestigationResultRepository : ReactiveCrudRepository<InvestigationResult, String>, InvestigationResultCustomRepository