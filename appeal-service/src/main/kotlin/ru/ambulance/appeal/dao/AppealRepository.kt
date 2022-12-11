package ru.ambulance.appeal.dao

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import ru.ambulance.appeal.model.entity.Appeal

@Repository
interface AppealRepository : ReactiveCrudRepository<Appeal, String>, AppealCustomRepository {
    fun findFirstByAppealIdAndCurrentDoctorId(appealId: String, currentDoctorId: String): Mono<Appeal>
}