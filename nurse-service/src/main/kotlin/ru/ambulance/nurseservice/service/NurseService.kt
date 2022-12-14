package ru.ambulance.nurseservice.service

import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.dao.projection.ProcedureKindWithNurse

interface NurseService {
    fun findRequiredNurseWithMinActiveInvestigation(hospitalId: String, investigationKindId: String): Mono<ProcedureKindWithNurse>

    fun findRequiredNurseWithMinActiveTreatment(hospitalId: String, treatmentKindId: String): Mono<ProcedureKindWithNurse>

    fun isExistAvailableTreatmentOrInvestigation(appealId: String): Mono<Boolean>
}