package ru.ambulance.appeal.dao

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate
import org.springframework.data.r2dbc.query.Criteria
import org.springframework.data.r2dbc.query.Criteria.where
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import ru.ambulance.appeal.model.entity.Appeal
import java.util.Optional

@Repository
class AppealCustomRepositoryImpl(private val entityTemplate: R2dbcEntityTemplate) : AppealCustomRepository {

    override fun showAppealList(appealStatues: List<String>?, appealIds: List<String>?, doctorId: String?): Flux<Appeal> {
        val criteries: MutableList<Criteria> = mutableListOf()
        appealStatues?.let {
            criteries.add(where("appealStatus").`in`(appealStatues))
        }
        appealIds?.let {
            criteries.add(where("appealId").`in`(appealIds))
        }
        doctorId?.let {
            criteries.add(where("currentDoctorId").`is`(doctorId))
        }
        val finalCriteria: Optional<Criteria> = criteries.stream().reduce { c1, c2 -> c1.and(c2) }
        return if (finalCriteria.isPresent) {
            entityTemplate.select(Appeal::class.java).matching(Query.query(finalCriteria.get())).all()
        } else {
            entityTemplate.select(Appeal::class.java).all()
        }
    }
}