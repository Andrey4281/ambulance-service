package ru.ambulance.appeal.dao

import reactor.core.publisher.Flux
import ru.ambulance.appeal.model.entity.Appeal

interface AppealCustomRepository {
    fun showAppealList(appealStatues: List<String>?,
                       appealIds: List<String>?,
                       doctorId: String?) : Flux<Appeal>
}