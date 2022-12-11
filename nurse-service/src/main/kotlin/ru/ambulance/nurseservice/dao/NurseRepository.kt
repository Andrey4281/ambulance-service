package ru.ambulance.nurseservice.dao

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.dao.projection.ProcedureKindWithNurse
import ru.ambulance.nurseservice.model.entity.Nurse

@Repository
interface NurseRepository : ReactiveCrudRepository<Nurse, String> {
    @Query("SELECT n.id AS id, nikr.investigation_kind_id AS treatment FROM nurse n\n" +
            "    INNER JOIN nurse_shift ns ON (n.id = ns.nurse_id AND ns.is_active=true)\n" +
            "    INNER JOIN nurse_investigation_kind_relation nikr on n.id = nikr.nurse_id\n" +
            "WHERE n.hospital_id = :hospitalId AND nikr.investigation_kind_id = :investigationKindId\n" +
            "ORDER BY ns.active_investigation_count LIMIT 1")
    fun findRequiredNurseWithMinActiveInvestigation(@Param("hospitalId") hospitalId: String,
                                                    @Param("investigationKindId") investigationKindId: String): Mono<ProcedureKindWithNurse>

    @Query("SELECT n.id AS id, ntkr.treatment_kind_id AS treatment FROM nurse n\n" +
            "    INNER JOIN nurse_shift ns ON (n.id = ns.nurse_id AND ns.is_active=true)\n" +
            "    INNER JOIN nurse_treatment_kind_relation ntkr on n.id = ntkr.nurse_id\n" +
            "WHERE n.hospital_id = :hospitalId AND ntkr.treatment_kind_id = :treatmentKindId\n" +
            "ORDER BY ns.active_investigation_count LIMIT 1")
    fun findRequiredNurseWithMinActiveTreatment(@Param("hospitalId") hospitalId: String,
                                                @Param("treatmentKindId") treatmentKindId: String): Mono<ProcedureKindWithNurse>


}