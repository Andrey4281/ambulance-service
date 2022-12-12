package ru.ambulance.nurseservice.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.relational.core.query.Query
import reactor.core.publisher.Flux
import java.util.*

interface ProcedureResultRepository<T> {
    fun showProcedureResultList(appealId: String?, examinationId: String?, nurseId: String?): Flux<T> {
        val criteries: MutableList<Criteria> = mutableListOf()
        appealId?.let {
            criteries.add(Criteria.where("appealId").`is`(appealId))
        }
        examinationId?.let {
            criteries.add(Criteria.where("appealId").`is`(examinationId))
        }
        nurseId?.let {
            criteries.add(Criteria.where("nurseId").`is`(nurseId))
        }
        val finalCriteria: Optional<Criteria> = criteries.stream().reduce { c1, c2 -> c1.and(c2) }
        return if (finalCriteria.isPresent) {
            getEntityTemplate().select(getQueriedClass()).matching(Query.query(finalCriteria.get())).all()
        } else {
            getEntityTemplate().select(getQueriedClass()).all()
        }
    }

    fun getEntityTemplate(): R2dbcEntityTemplate

    fun getQueriedClass(): Class<T>
}