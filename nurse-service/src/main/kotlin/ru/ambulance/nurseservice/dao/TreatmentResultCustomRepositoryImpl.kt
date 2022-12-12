package ru.ambulance.nurseservice.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import ru.ambulance.nurseservice.model.entity.TreatmentResult

@Repository
class TreatmentResultCustomRepositoryImpl(private val entityTemplate: R2dbcEntityTemplate): TreatmentResultCustomRepository {
    override fun getEntityTemplate(): R2dbcEntityTemplate = entityTemplate

    override fun getQueriedClass(): Class<TreatmentResult> = TreatmentResult::class.java
}