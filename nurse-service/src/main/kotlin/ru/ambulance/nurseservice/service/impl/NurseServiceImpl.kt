package ru.ambulance.nurseservice.service.impl

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import ru.ambulance.nurseservice.dao.NurseRepository
import ru.ambulance.nurseservice.dao.projection.ProcedureKindWithNurse
import ru.ambulance.nurseservice.service.NurseService

@Service
class NurseServiceImpl(private val nurseRepository: NurseRepository) : NurseService {

    override fun findRequiredNurseWithMinActiveInvestigation(hospitalId: String, investigationKindId: String): Mono<ProcedureKindWithNurse> =
            nurseRepository.findRequiredNurseWithMinActiveInvestigation(hospitalId = hospitalId, investigationKindId = investigationKindId)
                    .switchIfEmpty(Mono.just(ProcedureKindWithNurse(id = null, treatment = investigationKindId)))

    override fun findRequiredNurseWithMinActiveTreatment(hospitalId: String, treatmentKindId: String): Mono<ProcedureKindWithNurse> =
            nurseRepository.findRequiredNurseWithMinActiveTreatment(hospitalId = hospitalId, treatmentKindId = treatmentKindId)
                    .switchIfEmpty(Mono.just(ProcedureKindWithNurse(id = null, treatment = treatmentKindId)))
}