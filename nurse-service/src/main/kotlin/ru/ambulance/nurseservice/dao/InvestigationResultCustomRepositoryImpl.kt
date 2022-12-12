package ru.ambulance.nurseservice.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.stereotype.Repository
import ru.ambulance.nurseservice.model.entity.InvestigationResult

@Repository
class InvestigationResultCustomRepositoryImpl(private val entityTemplate: R2dbcEntityTemplate) : InvestigationResultCustomRepository {
    override fun getEntityTemplate(): R2dbcEntityTemplate = entityTemplate

    override fun getQueriedClass(): Class<InvestigationResult> = InvestigationResult::class.java
}